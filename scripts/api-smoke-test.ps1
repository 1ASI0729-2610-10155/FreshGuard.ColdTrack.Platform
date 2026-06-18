$ErrorActionPreference = 'Stop'
$base = 'http://localhost:8080'
$results = [System.Collections.Generic.List[object]]::new()

function Invoke-ApiTest {
    param(
        [string]$Name,
        [string]$Method,
        [string]$Path,
        $Body = $null,
        [hashtable]$Headers = @{},
        [int[]]$Expected = @(200, 201, 202, 204)
    )

    try {
        $parameters = @{
            Uri = "$base$Path"
            Method = $Method
            Headers = $Headers
            UseBasicParsing = $true
            SkipHttpErrorCheck = $true
        }
        if ($null -ne $Body) {
            $parameters.ContentType = 'application/json'
            $parameters.Body = $Body | ConvertTo-Json -Depth 8
        }
        $response = Invoke-WebRequest @parameters
        $passed = $Expected -contains [int]$response.StatusCode
        $results.Add([pscustomobject]@{
            Test = $Name
            Method = $Method
            Path = $Path
            Status = [int]$response.StatusCode
            Pass = $passed
            Body = $response.Content
        })
        if ($response.Content) {
            try { return $response.Content | ConvertFrom-Json } catch { return $null }
        }
    } catch {
        $results.Add([pscustomobject]@{
            Test = $Name
            Method = $Method
            Path = $Path
            Status = 0
            Pass = $false
            Body = $_.Exception.Message
        })
        return $null
    }
}

$stamp = [DateTimeOffset]::UtcNow.ToUnixTimeMilliseconds()
$now = [DateTimeOffset]::UtcNow
$dateFormat = 'yyyy-MM-ddTHH:mm:ss'
$email = "api-test-$stamp@coldtrack.test"
$password = 'ColdTrack!2026'

Invoke-ApiTest 'Swagger/OpenAPI' GET '/v3/api-docs' | Out-Null
Invoke-ApiTest 'Protected endpoint rejects anonymous' GET '/api/v1/shipments' $null @{} @(401, 403) | Out-Null

$signup = Invoke-ApiTest 'Sign up' POST '/api/v1/authentication/sign-up' @{
    fullName = 'API Test User'; email = $email; password = $password; role = 'ROLE_LOGISTICS_ADMIN'
}
$login = Invoke-ApiTest 'Sign in' POST '/api/v1/authentication/sign-in' @{
    email = $email; password = $password
}
$auth = @{ Authorization = "Bearer $($login.token)" }

Invoke-ApiTest 'List roles' GET '/api/v1/roles' $null $auth | Out-Null
$users = Invoke-ApiTest 'List users' GET '/api/v1/users' $null $auth
if ($login.user.id) {
    Invoke-ApiTest 'Get user by id' GET "/api/v1/users/$($login.user.id)" $null $auth | Out-Null
}
$profiles = Invoke-ApiTest 'List profiles' GET '/api/v1/profiles' $null $auth
if ($profiles -and @($profiles).Count -gt 0) {
    Invoke-ApiTest 'Get profile by id' GET "/api/v1/profiles/$(@($profiles)[0].id)" $null $auth | Out-Null
} else {
    Invoke-ApiTest 'Missing profile returns not found' GET '/api/v1/profiles/999999' $null $auth @(404) | Out-Null
}
Invoke-ApiTest 'Create contact message' POST '/api/v1/contact-messages' @{
    fullName = 'API Tester'; email = $email; message = 'Automated API smoke test'
} | Out-Null
Invoke-ApiTest 'List testimonials' GET '/api/v1/testimonials' | Out-Null

$sensorCode = "SENS-$stamp"
Invoke-ApiTest 'Create sensor' POST '/api/v1/sensors' @{ id = $sensorCode } $auth | Out-Null
Invoke-ApiTest 'List sensors' GET '/api/v1/sensors' $null $auth | Out-Null

$shipment = Invoke-ApiTest 'Create shipment' POST '/api/v1/shipments' @{
    destination = 'Lima Distribution Center'
    driver = 'API Driver'
    cargoDescription = 'Vaccines'
    departureAt = $now.AddMinutes(5).ToString($dateFormat)
    estimatedArrivalAt = $now.AddHours(4).ToString($dateFormat)
} $auth
$shipmentCode = $shipment.id

Invoke-ApiTest 'List shipments' GET '/api/v1/shipments' $null $auth | Out-Null
Invoke-ApiTest 'Filter shipments' GET '/api/v1/shipments?status=REGISTERED' $null $auth | Out-Null
Invoke-ApiTest 'Get shipment by code' GET "/api/v1/shipments/$shipmentCode" $null $auth | Out-Null
Invoke-ApiTest 'Assign sensor' POST "/api/v1/shipments/$shipmentCode/sensor-assignments" @{ sensorCode = $sensorCode } $auth | Out-Null
Invoke-ApiTest 'Start shipment' POST "/api/v1/shipments/$shipmentCode/departures" $null $auth | Out-Null
Invoke-ApiTest 'Record telemetry' POST '/api/v1/telemetry-readings' @{
    sensorCode = $sensorCode
    temperature = 12.5
    humidity = 85.0
    recordedAt = $now.AddMinutes(10).UtcDateTime.ToString("yyyy-MM-ddTHH:mm:ss'Z'")
} $auth | Out-Null
Invoke-ApiTest 'Shipment telemetry' GET "/api/v1/shipments/$shipmentCode/telemetry-readings" $null $auth | Out-Null

$alerts = Invoke-ApiTest 'List alerts' GET '/api/v1/alerts' $null $auth
if ($alerts -and @($alerts).Count -gt 0) {
    $alertCode = @($alerts)[0].id
    Invoke-ApiTest 'Acknowledge alert' POST "/api/v1/alerts/$alertCode/acknowledgements" $null $auth | Out-Null
    Invoke-ApiTest 'Resolve alert' POST "/api/v1/alerts/$alertCode/resolutions" $null $auth | Out-Null
}

Invoke-ApiTest 'Dashboard' GET '/api/v1/analytics/dashboard' $null $auth | Out-Null
Invoke-ApiTest 'Shipment report' GET "/api/v1/reports/shipments/$shipmentCode" $null $auth | Out-Null
Invoke-ApiTest 'Complete shipment' POST "/api/v1/shipments/$shipmentCode/completions" $null $auth | Out-Null
Invoke-ApiTest 'Unassign sensor' DELETE "/api/v1/sensors/$sensorCode/assignment" $null $auth | Out-Null

$cancelShipment = Invoke-ApiTest 'Create cancellable shipment' POST '/api/v1/shipments' @{
    destination = 'Arequipa Hub'
    driver = 'Cancel Driver'
    cargoDescription = 'Frozen food'
    departureAt = $now.AddMinutes(20).ToString($dateFormat)
    estimatedArrivalAt = $now.AddHours(8).ToString($dateFormat)
} $auth
Invoke-ApiTest 'Cancel shipment' POST "/api/v1/shipments/$($cancelShipment.id)/cancellations" $null $auth | Out-Null

$results | Select-Object Test, Method, Path, Status, Pass | Format-Table -AutoSize
$failed = @($results | Where-Object { -not $_.Pass })
Write-Output "SUMMARY total=$($results.Count) passed=$($results.Count - $failed.Count) failed=$($failed.Count)"
if ($failed.Count) {
    Write-Output 'FAILURES'
    $failed | Select-Object Test, Status, Path, Body | Format-List
    exit 2
}

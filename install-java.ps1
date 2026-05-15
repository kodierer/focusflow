# Java Installation Script for FocusFlow
# Run as Administrator in PowerShell

Write-Host "Installing Java JDK 11 for FocusFlow..." -ForegroundColor Cyan

# Download URL for Microsoft Build of OpenJDK 11 (LTS)
$jdkUrl = "https://aka.ms/download-jdk/microsoft-jdk-11.0.16-windows-x64.msi"
$installerPath = "$env:TEMP\openjdk-11-installer.msi"

Write-Host "Downloading OpenJDK 11 from Microsoft..." -ForegroundColor Yellow
try {
    # Using BITS for reliable download
    Start-BitsTransfer -Source $jdkUrl -Destination $installerPath -DisplayName "OpenJDK 11 Download"
    Write-Host "✓ Download complete!" -ForegroundColor Green
} catch {
    Write-Host "✗ Download failed: $_" -ForegroundColor Red
    Write-Host "Trying alternative: choco install openjdk11" -ForegroundColor Yellow
    choco install openjdk11 -y --force
    exit
}

# Install the MSI
Write-Host "Installing OpenJDK 11..." -ForegroundColor Yellow
Start-Process -FilePath "msiexec.exe" -ArgumentList "/i", $installerPath, "/quiet", "/norestart" -Wait

# Set JAVA_HOME environment variable
Write-Host "Configuring JAVA_HOME environment variable..." -ForegroundColor Yellow
$javaPath = Get-ChildItem -Path "C:\Program Files\Microsoft\*" -Filter "jdk*" -Directory -ErrorAction SilentlyContinue | Select-Object -First 1
if ($javaPath) {
    [Environment]::SetEnvironmentVariable("JAVA_HOME", $javaPath.FullName, [System.EnvironmentVariableTarget]::Machine)
    Write-Host "✓ JAVA_HOME set to: $($javaPath.FullName)" -ForegroundColor Green
} else {
    $javaPath = "C:\Program Files\Java\jdk-11"
    if (Test-Path $javaPath) {
        [Environment]::SetEnvironmentVariable("JAVA_HOME", $javaPath, [System.EnvironmentVariableTarget]::Machine)
        Write-Host "✓ JAVA_HOME set to: $javaPath" -ForegroundColor Green
    }
}

# Add to PATH if not already there
$currentPath = [Environment]::GetEnvironmentVariable("PATH", [System.EnvironmentVariableTarget]::Machine)
$javabin = Join-Path $javaPath "bin"
if ($currentPath -notlike "*$javabin*") {
    $newPath = "$currentPath;$javabin"
    [Environment]::SetEnvironmentVariable("PATH", $newPath, [System.EnvironmentVariableTarget]::Machine)
    Write-Host "✓ Added Java bin directory to PATH" -ForegroundColor Green
}

# Cleanup
Remove-Item -Path $installerPath -ErrorAction SilentlyContinue

Write-Host "`n✓ Java installation complete!" -ForegroundColor Green
Write-Host "Restart PowerShell or any IDE to apply changes" -ForegroundColor Cyan
Write-Host "Verify with: java -version" -ForegroundColor Cyan

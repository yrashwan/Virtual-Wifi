net session >nul 2>&1
if not "%errorLevel%" == "0" (
  echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
  echo UAC.ShellExecute "%~s0", "%*", "", "runas", 1 >> "%temp%\getadmin.vbs"

  "%temp%\getadmin.vbs"
  exit /b 2
)

netsh interface show interface > %~dp0\interface_before.txt

netsh wlan start hostednetwork
if "%errorlevel%"=="0" (
  echo OUTPUT## Startup WLAN success, enjoy it!
) else (
  echo OUTPUT## Error: Started WLAN failure.
)

netsh interface show interface > %~dp0\interface_after.txt

net session >nul 2>&1
if not "%errorLevel%" == "0" (
  echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
  echo UAC.ShellExecute "%~s0", "%*", "", "runas", 1 >> "%temp%\getadmin.vbs"

  "%temp%\getadmin.vbs"
  exit /b 2
)

if "%_name%"=="" set _name=wlan
set _name=lolo
set _password=44444444
echo OUTPUT## %_name%
echo OUTPUT## %_password%
netsh wlan set hostednetwork mode=allow ssid=%_name% key=%_password%
if "%errorlevel%"=="0" (
  echo OUTPUT## Setup the WLAN success.
) else (
  echo OUTPUT## Setup the Wlan Failed!!
)

net session >nul 2>&1
if not "%errorLevel%" == "0" (
  echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
  echo UAC.ShellExecute "%~s0", "%*", "", "runas", 1 >> "%temp%\getadmin.vbs"

  "%temp%\getadmin.vbs"
  exit /b 2
)

cscript /nologo %~dp0\share.vbs > %~dp0\output.txt
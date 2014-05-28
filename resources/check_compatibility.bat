echo Check your WIFI adapter...
set supported=0
netsh wlan show drive | find "Hosted network supported" | find "Yes"
if %errorlevel%==0 set supported=1
if %supported% equ 1 (
  echo OUTPUT## Congratulation! You WIFI adapter support Ad-Hoc mode.
) else (
  echo OUTPUT## Oops! You WIFI adapter can't support Ad-Hoc mode^(hostednetwork^).
  exit /b 1
)

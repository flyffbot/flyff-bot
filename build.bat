@echo off

set "skip_fe="
set "skip_full_app="
set "skip_launcher="
set "skip_installer="

for %%i in (%*) do (
    if "%%~i"=="--skip-fe" (
        set "skip_fe=true"
    )
    if "%%~i"=="--skip-full-app" (
        set "skip_full_app=true"
    )
    if "%%~i"=="--skip-launcher" (
        set "skip_launcher=true"
    )
    if "%%~i"=="--skip-installer" (
        set "skip_installer=true"
    )
)

if defined skip_fe (
	echo "Skipped FE"
) else (
	cd fe
	call npm run build
	cd ..
)

if defined skip_full_app (
	echo "Skipped Full App"
) else (
	cd .\full-app
	call mvn clean compile package install
	cd ..
)

if defined skip_launcher (
	echo "Skipped Launcher"
) else (
	cd .\launcher
	call .\build.bat
	cd ..
)

if defined skip_installer (
	echo "Skipped Installer"
) else (
	md release
	md release\data
	move /Y .\full-app\target\FlyffBot-0.5.0-shaded.jar .\release\data\FlyffBot.jar
	move /Y .\launcher\FlyffBot.exe .\release
	call "C:\Program Files (x86)\Inno Setup 6\Compil32.exe" /cc release.iss
)

pause

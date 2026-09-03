```bat
@echo off

echo ==============================
echo       INICIANDO API
echo ==============================
echo.

echo Iniciando API...

docker stop minha-api >nul 2>&1
docker rm minha-api >nul 2>&1

docker run -d ^
    --name minha-api ^
    --network minha-rede ^
    -p 8080:8080 ^
    minha-api
if errorlevel 1 (
    echo.
    echo ERRO: Nao foi possivel iniciar a API.
    pause
    exit /b 1
)

echo.
echo ==============================
echo       API INICIADA!
echo ==============================
echo.
echo API: http://localhost:8080
echo.

pause
```

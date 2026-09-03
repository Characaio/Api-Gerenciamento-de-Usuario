@echo off

echo.
echo ==============================
echo      COMPILANDO PROJETO
echo ==============================
echo.

mvn clean package -DskipTests

if errorlevel 1 (
	echo.
	echo ERRO: Maven falhou.
	pause
	exit /b 1
)

echo ==============================
echo      CRIANDO IMAGEM DOCKER
echo ==============================
echo.

docker build -t minha-api .

if errorlevel 1 (
	echo.
	echo ERRO: Docker build falhou.
	pause
	exit /b 1
)

echo.
echo ==============================
echo          CONCLUIDO
echo ==============================

pause

@echo off

echo Parando o projeto inteiro...


docker stop meu-postgres

docker stop minha-api

if errorlevel 1 (
	echo.
	echo Algum erro ocorreu.
	pause
	exit /b 1

echo Projeto parado com sucesso

@echo off
setlocal

echo.
echo ==========================================
echo       INICIALIZANDO PROJETO LINDASSO
echo ==========================================
echo.


REM ==========================================
REM 1. CRIAR REDE DOCKER
REM ==========================================

echo [1/7] Verificando rede Docker...

docker network inspect minha-rede >nul 2>&1

if errorlevel 1 (
    echo Rede nao existe. Criando...
    docker network create minha-rede

    if errorlevel 1 (
        echo ERRO: Nao foi possivel criar a rede.
        pause
        exit /b 1
    )
) else (
    echo Rede minha-rede ja existe.
)

echo.


REM ==========================================
REM 2. CRIAR VOLUME DO POSTGRES
REM ==========================================

echo [2/7] Verificando volume PostgreSQL...

docker volume inspect postgres-dados >nul 2>&1

if errorlevel 1 (
    echo Volume nao existe. Criando...
    docker volume create postgres-dados

    if errorlevel 1 (
        echo ERRO: Nao foi possivel criar o volume.
        pause
        exit /b 1
    )
) else (
    echo Volume postgres-dados ja existe.
)

echo.


REM ==========================================
REM 3. POSTGRESQL
REM ==========================================

echo [3/7] Verificando PostgreSQL...

docker container inspect meu-postgres >nul 2>&1

if errorlevel 1 (

    echo Container PostgreSQL nao existe.
    echo Criando container...

    docker run -d ^
        --name meu-postgres ^
        --network minha-rede ^
        -e POSTGRES_PASSWORD=123456 ^
        -e POSTGRES_DB=minhabd ^
        -v postgres-dados:/var/lib/postgresql/data ^
        postgres

    if errorlevel 1 (
        echo ERRO: Nao foi possivel criar o PostgreSQL.
        pause
        exit /b 1
    )

) else (

    echo Container PostgreSQL ja existe.

    docker start meu-postgres >nul 2>&1
)

echo.


REM ==========================================
REM 4. ESPERAR POSTGRES
REM ==========================================

echo [4/7] Esperando PostgreSQL iniciar...

:wait_postgres

docker exec meu-postgres pg_isready -U postgres -d minhabd >nul 2>&1

if errorlevel 1 (
    echo PostgreSQL ainda nao esta pronto...
    timeout /t 2 /nobreak >nul
    goto wait_postgres
)

echo PostgreSQL esta pronto!
echo.


REM ==========================================
REM 5. COMPILAR SPRING BOOT
REM ==========================================

echo [5/7] Compilando projeto Maven...

call mvnw.cmd clean package

if errorlevel 1 (
    echo.
    echo ERRO: Maven falhou.
    pause
    exit /b 1
)

echo Maven concluido!
echo.


REM ==========================================
REM 6. CRIAR IMAGEM DOCKER
REM ==========================================

echo [6/7] Criando imagem Docker da API...

docker build -t lindasso .

if errorlevel 1 (
    echo.
    echo ERRO: Docker build falhou.
    pause
    exit /b 1
)

echo Imagem criada!
echo.


REM ==========================================
REM 7. RECRIAR API
REM ==========================================

echo [7/7] Iniciando API...

docker stop lindasso >nul 2>&1
docker rm lindasso >nul 2>&1

docker run -d ^
    --name lindasso ^
    --network minha-rede ^
    -p 8080:8080 ^
    lindasso

if errorlevel 1 (
    echo.
    echo ERRO: Nao foi possivel iniciar a API.
    pause
    exit /b 1
)

echo.
echo ==========================================
echo       PROJETO INICIADO COM SUCESSO!
echo ==========================================
echo.
echo PostgreSQL: meu-postgres
echo Banco:      minhabd
echo API:        http://localhost:8080
echo.
echo Para ver os logs da API:
echo docker logs -f lindasso
echo.

pause
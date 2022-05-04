<p align="center">Projeto de uma Rest Api CRUD com PostgreSQL</p>

<p align="center">

## Technologias usadas neste projeto
<img src="./src/assets/img/technology.png" alt="Tecnologias usadas" /> 
  
## Objetivo
Rest API Crud Application With Postgre.

## Banco de Dados 
Crie um banco de nome Servicos e...

```bash
src/main/resources/application-prod.properties
Utilizado postgresql

Após criação do banco trocar para update
de spring.jpa.hibernate.ddl-auto=create
para spring.jpa.hibernate.ddl-auto=update
  
```
  
## Dependências
```bash
para instalar lombok
C:\Users\USUARIO\.m2\repository\org\projectlombok\lombok\1.18.24\lombok-1.18.24.jar
Selecionar a pasta onde está o eclipse ou spring tools aperta em install

Reiniciar o eclipse ou spring tools
Depois vai na aba project > clean
ele vai gerar automaticamente os getters and setters

```
  
## Test JUnit 5
```bash
Para teste funcionar é preciso ativar o junit5

na aba Project>Properties> Java Build Path>Libraries

Na lista deve aparecer Junit5, se não estiver preciona add library...
Junit, next , depois escolhe na lista junit5 e finish
```

## Collection
o Json para uso no postman encontra-se na raiz do projeto
  
## Para acesso à URL Swagger
http://localhost:8080/swagger-ui.html
  
## Swagger
swagger.json << Arquivo encontra-se na Raiz
<img src="./src/assets/img/swagger.jpg" alt="Swagger" /> 
  
## Banco de Dados
servicos.sql encontra-se na pasta database
<img src="./src/assets/img/banco.jpg" alt="BancoPostgre" /> 
  
## Arquivo JSON parar usar no Insomnia ou Postman
o Json encontra-se na raiz no projeto
<img src="./src/assets/img/json.jpg" alt="Json" /> 

## Video Demo
<a href="https://youtu.be/HUl-Nuvemcw" target="_blank">Clique Aqui</a>

## Teste
<img src="./src/assets/img/teste.jpg" alt="testeUnit5" /> 

## Suporte

geraldo@gpsoft.com.br

## Sobre

[Geraldo P Melo](https://gpsoft.com.br)
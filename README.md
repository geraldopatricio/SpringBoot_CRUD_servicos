<p align="center">Projeto de uma Rest Api CRUD usando Banco PostgreSQL</p>

<p align="center">

## Tecnologias usadas neste projeto
<img src="./docs/images/technology.png" alt="Tecnologias usadas" /> 
  
## Objetivo
CRUD Rest API BackEnd em Spring Boot para consumir 3 tabelas em uma base de dados PostgreSql

## Banco de Dados 
Crie um banco de nome Servicos e...

```bash
src/main/resources/application-prod.properties
Utilizado postgresql altere login/senha de acordo com o seu servidor

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
  
## Teste
<img src="./docs/images/test.png" alt="testeUnit5" /> 

## Collection
o Json para uso no postman encontra-se na pasta docs/json
  
## Postman
<img src="./docs/images/postman.jpg" alt="Json" /> 
    
## Banco de Dados
script.sql encontra-se na pasta docs/banco
<img src="./docs/images/banco.jpg" alt="BancoPostgre" />   
  
> ## Para acesso à URL Swagger
> http://localhost:8080/swagger-ui.html
  
## Suporte
EMail: geraldo@gpsoft.com.br
WhatsApp: (85) 9 9150-8104

## Sobre
[Nossa Empresa](https://gpsoft.com.br)

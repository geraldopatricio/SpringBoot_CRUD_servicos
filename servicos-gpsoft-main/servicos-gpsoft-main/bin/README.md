#Banco de dados
src/main/resources/application-prod.properties
Utilizado postgresql

Após criação do banco trocar para update
de spring.jpa.hibernate.ddl-auto=create
para spring.jpa.hibernate.ddl-auto=update

---------------------------------------------
#Lombok
para instalar lombok
C:\Users\USUARIO\.m2\repository\org\projectlombok\lombok\1.18.24\lombok-1.18.24.jar
Selecionar a pasta onde está o eclipse ou spring tools aperta em install

Reiniciar o eclipse ou spring tools
Depois vai na aba project > clean
ele vai gerar automaticamente os getters and setters

---------------------------------------------
#Teste junit5
Para teste funcionar é preciso ativar o junit5

na aba Project>Properties> Java Build Path>Libraries

Na lista deve aparecer Junit5, se não estiver preciona add library...
Junit, next , depois escolhe na lista junit5 e finish
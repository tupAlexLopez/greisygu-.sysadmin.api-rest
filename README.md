# GREISYGU - BACKEND API REST

# Tecnologias utilizadas

* Openjdk v17.0
* Springboot v3.1.10
* Springboot DATA JPA v3.1.10
* Docker Engine v26.0.1
* Postgre SQL v16.0


# ¿Como correr el proyecto?
Primero que nada, debe tener clonado el repositorio que se encuentra en la plataforma de Github

``` git clone https://github.com/tupAlexLopez/greisygu-.sysadmin.api-rest.git ```


## Hay varias formas de correr el proyecto.
Debe dirigirse al directorio donde se encuentra el repositorio anteriormente creado.
* Se recomienda que lo haga a traves de la consola de su sistema (CMD, bash o el que usted utilice)
* Tambien puede hacer uso de IDEs(IntelliJ CE, Eclipse, STS, etc) o incluso editores de texto (VS Code)
  * **Si lo hace de esta manera, tenga en claro que se utilizara la consola de comandos para correr el proyecto.** 


## Usted puede correr el proyecto a traves de:
## Maven
Si opta por esta opcion, en **sistemas Linux debe conceder permisos al archivo mvnw** para que este
pueda ejecutar el proyecto correctamente.
#### Windows:
```
$ ./mvnw spring-boot:run
```
#### Linux:
```
$ chmod +x mvnw
$ ./mvnw spring-boot:run 
```

## Docker
**Si opta por esta opcion, debe tener instalado Docker en su sistema.**
``` 
$ docker build -t springboot-app .
$ docker run -d -p 8080:8080 --name springboot-api-rest-app springboot-app    
```

### Luego de ejecutar los comandos, debe dirigirse a http://localhost:8080
* Se abrira la pagina de **Open API** el cual, le ayudara a usted a realizar pruebas con la aplicacion.


### De igual manera usted puede ver el proyecto desde el siguiente enlace: https://greisygu-backend-api-rest.onrender.com


    

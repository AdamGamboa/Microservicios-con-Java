# Contenedores y Docker

## Tabla de Contenidos

1. [Creación y gestión de imágenes de Docker](#creación-y-gestión-de-imágenes-de-docker)
2. [Despliegue de contenedores Docker](#despliegue-de-contenedores-docker)  
3. [Multi-Stage Builds](#multi-stage-builds)
4. [Consejos y Buenas Prácticas](#consejos-y-buenas-prácticas) 
5. [Práctica](#práctica)



## Creación y gestión de imágenes de Docker
Veamos como interactuar con Docker desde linea de comandos

### Crear imagen
Recordemos el concepto de **`Imagen`** en docker, la cual se considera como un bloque de construcción. Para construir una imagen, necesitamos un **`Dockerfile`**, con las instrucciones de dicha imagen. 

```
FROM alpine:latest
WORKDIR /opt/
ENTRYPOINT echo "Hello World" 
```

Luego se utiliza el comando `docker build`

``` 
docker build . 
```
ó 
```
docker build -t my-hello-world:latest .
```
Donde:
- `.` es el directorio donde se encuentra el archivo Dockerfile desde mi posición actual. 
- `-t my-hellow-world:latest`, es el nombre y versión/tag de dicha imagen. 


### Descargar Imagen
También podemos "descargar" una imagen desde un **`Repositorio Docker`**. 
```
docker pull busybox:latest
```

### Listar Imagenes
Podemos ver que imagenes tenemos en nuestra máquinas con el comando `docker images` o `docker image ls`. Ambos son equivalentes.

```
$ docker images
REPOSITORY              TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
busybox                 latest              c51f86c28340        4 weeks ago         1.109 M
my-hellow-world         latest              21ebf05f9954        13 minutes ago      7.38MB
```

### Eliminar Imagen
Si deseamos eliminar una imagen, podemos hacerlo con el comando `docker rmi <image-id>` o con `docker image rm <image-id>`. Ambos son equivalentes.

```
$ docker rmi 21ebf05f9954
Untagged: my-hellow-world:latest
Deleted: sha256:21ebf05f9954584909da6ba7c9fced5c168c0b353ce4b77a76b0f8f2d5722bff
```

## Despliegue de contenedores Docker

### Correr un contenedor
Un contenedor es una "instancia" basada en una imagen. Una vez tenemos identificada una imagen, podemos ejecutar un contenedor con el comando `docker run <docker-image|docker-image-id>`.

``` 
$ docker run my-hellow-world:latest
```
ó
``` 
$ docker run 21ebf 
```

### Listar los contenedores 
Podemos ver una lista de los contenedores activos con el comando `docker ps`. 

```
$ docker ps
CONTAINER ID   IMAGE       COMMAND                  CREATED       STATUS       PORTS                               NAMES
ef63ea3511a2   mysql:8.0   "docker-entrypoint.s…"   9 hours ago   Up 9 hours   0.0.0.0:3306->3306/tcp, 33060/tcp   titickets-events_db-1
d99fe2fbfe92   mysql:8.0   "docker-entrypoint.s…"   9 hours ago   Up 9 hours   33060/tcp, 0.0.0.0:3308->3306/tcp   titickets-orders_db-1
0cf232f7fbc3   mysql:8.0   "docker-entrypoint.s…"   9 hours ago   Up 9 hours   33060/tcp, 0.0.0.0:3307->3306/tcp   titickets-users_db-1

```

El valor de `CONTAINER_ID` es muy importante para interactuar con otros comandos. 

### Detener un contenedor
Podemos para una instancia de un contenedor `docker stop <container-id>`

### Iniciar un contenedor
Un contenedor que está detenido puede iniciarse de nuevo con `docker start <container-id>`

### Eliminar un contenedor 
Podemos eliminar un contenedor definitivamente con `docker rm <container-id>`. 

### Ver los Logs en un contenedor 
Es muy común ver los logs de un contenedor para resolver errores o ver el estado de dicho contenedor. Esto lo podemos hacer con `docker logs <container-id>`. Podemos usar el parámetro `-f` para ver un streaming constante de los logs `docker logs -f <container-id>`.


### Comandos durante la ejecución

Cuando se ejecutá un contenedor, muchas veces es necesario indicar algunos parámetros para su ejecución como: variables de ambiente, nombre del contenedor, el puerto, entre otros. 

- **--name**, sirve para darle un nombre al contenedor.

```
$ docker run mysql:8.0 -name db-events
```

- **--env** o **-e**, sirve para setear una variable de ambiente en el contenedor. 
``` 
$ docker run mysql:8.0 -name db-events -e MYSQL_PORT=3306 -e MYSQL_USER=user
```

- **--port** o **-p**, sirve para indicar que puertos el contenedor se desea exponer y mapearlo con un puerto en la máquina huesped. El valor tiene el formato `<puerto-huesped>:<puerto-contenedor>`

``` 
$ docker run mysql:8.0 -name db-events -p 3306:3306-e MYSQL_PORT=3306 -e MYSQL_USER=user
```

Existen muchos más argumentos que se pueden indicar: https://docs.docker.com/reference/cli/docker/container/run/


## Multi-stage Builds
Docker multi-stage build es una opción de la plataforma de contendores utilizada con el objetivo de hacer más fácil los procesos relacionados con la optimización de los dockerfiles, así como su lectura y mantenimiento. Esta herramienta permite, además, la construcción de imágenes intermedias que contribuyen a aligerar la imagen final en el sistema.

Un uso es para poder tener un entorno que compile nuestro código desde el contenedor, por ejemplo que incluya el JDK de Java, pero en el siguiente paso generamos una imagen que utiliza el JRE (más liviano) para ejecutar el ejecutable generado en el paso 1. 

```
## Esta imagen compila nuestro código fuente
FROM openjdk:17-jdk-slim-buster AS builder

RUN apt-get update -y
RUN apt-get install -y binutils

# Nos ubicamos en una carpeta y copiamos el código
WORKDIR /app
COPY . .

# Compila y construye la aplicación
RUN ./gradlew build 

# Imagen más liviana
FROM openjdk:17-jre-slim

#  Copia el ejecuta de la imagen anterior
COPY --from=builder /app/target/my-application.jar /app

ENTRYPOINT java -jar /app/my-application.jar

```

## Consejos y Buenas Prácticas

- El consejo general al crear Dockerfiles es usar el mínimo de lineas necesarias para crear menos capas. También se debe prestar atención al orden de las instrucciones para hacer eficiente el uso de cache. 

- El orden de los comandos influye en el uso de la cache, al cambiar una linea las lineas posteriores quedarían invalidadas para usar en cache y deben ser procesadas de nuevo. Por lo tanto es mejor poner al final las lineas que vayan a ser modificadas más frecuentemente. 

- Si se van a instalar paquetes, es mejor todo en una linea que no en varias. 

- Si se puede usar una versión oficial de una imagen docker suele ser mejor opción que construirla uno mismo. 

- Usar WORKDIR con los valores de rutas para usar con COPY, ADD y RUN y no estar especificando en cada linea una ruta. (Los WORKDIR además no usan capas). 

- El uso de varias etapas (multi-stage) permite incluir varios FROM y poder crear etapas intermedias de las que copiar unicamente los ficheros necesarios para la imagen final. 

## Práctica

### Hola mundo en Docker
Creemos un archivo `Dockerfile` con el siguiente contenido. 

```
FROM alpine:latest
ARG DEFAULT_MESSAGE="Hello World"
ENV MESSAGE $DEFAULT_MESSAGE
ENTRYPOINT echo $MESSAGE
```

Este simplemente utiliza Alpine, que es una versión sumamente liviana de Linux como base, y ejecuta un ECHO del valor del env-var `$MESSAGE`, el cual por defecto tiene "Hello World". 

``` 
$ docker build -t my-message:latest .
```

Ejecutemos un contenedor para dicha imagen. 

```
$ docker run --name my-message my-message:latest 
Hello World
```

Ahora enviemos la variable de ambiente `MESSAGE` con un valor diferente.

```
$ docker run -e "MESSAGE=Hola Mundo" --name my-message my-message:latest
```

Puede ser que nos salga un error: 
```
 The container name "/my-message" is already in use by container "409323a748e5fb2a916a77c6e6a24954b27fdc8aa3c97cde5c716e295a6bb8d9". 
 ```

Esto se debe a que ya existe un contenedor con el mismo nombre. Vamos a eliminarlo entonces

```
$docker rm my-message
my-message
``` 

Ahora si podemos hacer `$docker ps -a` y verificamos no exista un contenedor activo o detenido con el nombre `my-message`. Es momento de correr de nuevo el comando `run`: 

```
$ docker run -e "MESSAGE=Hola Mundo" --name my-message my-message:latest
Hola Mundo
```

Ahora sí, nos imprimió "Hola Mundo"

---

### Dockerizar una aplicación Java
Ya hemos creado una aplicación Java en el pasado. Que tal si la dockerizamos?

- Comencemos por compilar nuestra aplicación Java. Utilicemos el proyecto maven, en la carpeta `/Laboratorio/Aplicación Java/simple-rest-api` de esta lección. Una vez en dicha carpeta, compilemos el código.

```
mvn install
```

Observe como dentro del directorio `/target/` hay un archivo `simple-rest-api-1.jar`. 

- Crearemos un archivo docker, en la raíz del proyecto, con el siguiente contenido 
```
FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/
RUN mkdir simple-rest-api
WORKDIR /simple-rest-api
COPY ./target/simple-rest-api-1.jar .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "simple-rest-api-1.jar" ]
```

y se construye dicha imagen de docker

```
$ docker build -t simple-rest-api:latest .
```

Finalmente ejecutamos un contenedor con la imagen creada.

```
$ docker run --name simple-rest-api -p 8080:8080 simple-rest-api:latest

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.4)

INFO 1 --- [simple-rest-api] [           main] c.curso.java.principiosrest.Application  : Starting Application v1 using Java 17.0.10 with PID 1 (/simple-rest-api/simple-rest-api-1.jar started by root in /simple-rest-api)
INFO 1 --- [simple-rest-api] [           main] c.curso.java.principiosrest.Application  : No active profile set, falling back to 1 default profile: "default"
INFO 1 --- [simple-rest-api] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
INFO 1 --- [simple-rest-api] [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
INFO 1 --- [simple-rest-api] [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.19]
INFO 1 --- [simple-rest-api] [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
INFO 1 --- [simple-rest-api] [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1075 ms
INFO 1 --- [simple-rest-api] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
INFO 1 --- [simple-rest-api] [           main] c.curso.java.principiosrest.Application  : Started Application in 2.302 seconds (process running for 2.733)
INFO 1 --- [simple-rest-api] [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
INFO 1 --- [simple-rest-api] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
INFO 1 --- [simple-rest-api] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 2 ms
INFO 1 --- [simple-rest-api] [nio-8080-exec-5] o.springdoc.api.AbstractOpenApiResource  : Init duration for springdoc-openapi is: 468 ms
```

Podemos acceder a la aplicación por medio del URL: http://localhost:8080/

Finalmente, pueden detener el contenedor y borrarlo
```
$ docker stop simple-rest-api

$ docker rm simple-rest-api
```

Listo!


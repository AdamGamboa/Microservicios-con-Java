# Estrategias de Despliegue

## Tabla de Contenidos
1. [Introducción](#introducción)
2. [Docker Compose](#docker-compose)   
3. [Kubernetes](#kubernetes)
4. [Práctica](#práctica)

## Introducción
Una de las complejidades de la arquitectura de micro servicios es su orquestración. Cuando tenemos decenas de micro-servicios para nuestra aplicación, comienza a hacerse dificil llevar un seguimiento y monitoreo manual del estado de cada uno de ellos, además de sus dependencias. 

Si a esto le anadimos que cada micro-servicio podría tener N cantidad de instancias siendo ejecutadas al mismo tiempo, sobre algún tipo de balanceo de cargas. Esta orquestración comienza a complicarse un poco más. 

Es por esto, que usualmente se delega esta tareas de creación de replicas, balanceo de cargas, dependencias entre servicios, a un orquestrador como lo son Docker Compose o Kubernetes.

---
## Docker Compose
Docker Compose es una herramienta para definir y ejecutar aplicaciones de Docker de varios contenedores. En Compose, se usa un archivo YAML para configurar los servicios de la aplicación. Después, con un solo comando, se crean y se inician todos los servicios de la configuración.

### ¿Cuál es la diferencia entre Docker y Docker Compose?
Docker es una plataforma de contenedores que te permite empaquetar y distribuir aplicaciones en entornos aislados llamados contenedores. Proporciona una forma eficiente y consistente de ejecutar aplicaciones en diferentes sistemas operativos. 

Por otro lado, Docker Compose es una herramienta que complementa a Docker, permitiéndote definir y orquestar múltiples contenedores para crear un entorno de desarrollo completo.

Mientras que Docker se centra en la ejecución y creación de contenedores individuales, Docker Compose se encarga de coordinar y gestionar múltiples contenedores que trabajan juntos para formar una aplicación.

### Archivo de Configuración
El primer paso para configurar un entorno de desarrollo con Docker Compose es crear el archivo de configuración `docker-compose.yml`. Este archivo define los servicios, contenedores, redes y volúmenes necesarios para tu aplicación. Veamos cómo se estructura este archivo y qué elementos debemos incluir.

```
version: '3.8'

services:
  service1:
    # Configuración del servicio 1

  service2:
    # Configuración del servicio 2

networks:
  network1:
    # Configuración de la red 1

volumes:
  volume1:
    # Configuración del volumen 1
```

### Parámetros y opciones comunes en Docker Compose

En el archivo `docker-compose.yml`, podemos utilizar varios parámetros y opciones para configurar nuestros servicios y contenedores. Algunos ejemplos comunes incluyen:

- **image**: especifica la imagen del contenedor a utilizar.
- **ports**: mapea los puertos del contenedor a puertos de la máquina anfitriona.
- **volumes**: monta volúmenes o directorios del sistema de archivos en el contenedor.
- **environment**: define variables de entorno para el contenedor.
- **depends_on**: especifica las dependencias entre servicios.

### Escalando un Servicio
Si se desea escalar un servicio, para que tenga varias replicas, a partir de docker-compose 3.5 existe el atributo `deploy.replicas`. 

```
version: '3.5'
services:
  web:
    image: my-web-app:latest
    deploy:
      replicas: 5
      endpoint_mode: dnsrr        ## Round Robin Load Balancing
    networks:
      - webnet

  nginx:
    image: nginx:latest
    volumes:
      - ./reverse_proxy.conf:/etc/nginx/conf.d/default.conf ##Archivo de configuracion de NGINX
    ports:
      - "80:80"
    networks:
      - webnet
    depends_on:
      - web
networks:
  webnet:
```

Al aplicar replicas a un servicio en docker-compose, docker compose utiliza un DNS interno que funciona de "Load balancer" interno para acceder a cualquiera de las instancias creadas para dicho servicio. 

Sin embargo, dicho servicio no puede exponer el mismo puerto para mas de una instancia. De tal manera que para accederlo es necesario agregar un "reverse proxy". 


### Comandos básicos en Docker Compose

- **docker-compose up**, Este comando se encargará de crear y ejecutar los contenedores según las configuraciones especificadas

- **docker-compose down**, Este comando se encargará de detener y eliminar los contenedores definidos en el archivo `docker-componse.yaml`.

- **docker-compose start**, inicia los contenedores existentes en el archivo `docker-compose.yml`.

- **docker-compose stop**, detiene los contenedores existentes en el archivo `docker-compose.yml`.

- **docker-compose ps**, muestra el estado de los contenedores definidos en el archivo `docker-compose.yml`.


---
## Kubernetes 
Kubernetes es una plataforma portable y extensible de código abierto para administrar cargas de trabajo y servicios. Kubernetes ofrece un entorno de administración centrado en contenedores. Kubernetes orquesta la infraestructura de cómputo, redes y almacenamiento para que las cargas de trabajo de los usuarios no tengan que hacerlo. 

### ¿Cuál es la diferencia entre Docker Compose y Kubernetes?
Aunque tanto **Docker Compose** como **Kubernetes** son herramientas de orquestación de contenedores, hay algunas diferencias clave entre ellas. 

**Docker Compose** es más adecuado para entornos de desarrollo y pruebas, donde se requiere una configuración rápida y sencilla de múltiples contenedores. Proporciona una experiencia de desarrollo fluida al permitirte definir y gestionar fácilmente un entorno local de desarrollo con varios servicios.

**Kubernetes**, por otro lado, es una plataforma más robusta y escalable diseñada para desplegar y gestionar aplicaciones en producción a gran escala. Ofrece características avanzadas de orquestación, como la gestión de clústeres, el balanceo de carga y la autoescalabilidad, lo que lo hace ideal para entornos de producción complejos y de alto rendimiento.

### Arquitectura Kubernetes

#### Cluster 
En el nivel más alto, un cluster de Kubernetes agrega los recursos computacionales disponibles en la infraestructura para ejecutar las aplicaciones en contenedores. Cada máquina del cluster (física o virtual) es un nodo.

En este clúster existen dos tipos de nodos. Los nodos master son los encargados de gestionar el clúster. Los nodos worker son los encargados de ejecutar los contenedores.

#### Nodo Master

En el nodo master de Kubernetes ejecuta el servidor de API. Este servidor es con el que se comunica la CLI: kubectl. Además, ejecuta también el planificador encargado de gestionar el uso de recursos y repartir la carga de trabajo en los nodos workers.

En estos nodos master ejecutan los controller manager, encargados de mantener el estado correcto del cluster (replicas, endpoints, balanceadores, recursos cloud, etc)

#### Nodo Worker
Los nodos worker ejecutan los pods que hayan sido planificados. 

![Kubernentes](./imagenes/kubernetes.avif)

### Componentes

Kubernetes posee muchos tipos de recursos disponibles según sea la necesidad. 
- Pods
- Deployment
- Statfulset
- Replicaset
- Daemonset
- Configmaps
- Secrets
- Persistent Volumes
- Jobs
- Ingress
entre otros.

Vamos a ver algunos de los principales y más utilizados.

#### Pods
Un Pod de Kubernetes es la unidad mínima para ejecutar una carga en el sistema. Los pods contienen al menos un contenedor y sus componentes se despliegan en un mismo host compartiendo los recursos. Estos contenedores comparten red y almacenamiento.

No se recomienda a los usuarios manejar pods directamente, sino usar las abstracciones sobre estos, como pueden ser los **Deployments**, **stateful sets** o **daemon set**.


#### Deployment 
Deployment en Kubernetes se refiere a un objeto que describe cómo se debe implementar y actualizar una aplicación en el clúster. Proporciona un enfoque declarativo para definir el estado deseado de la aplicación y permite que Kubernetes se encargue de llevar el estado actual al estado deseado de manera eficiente y confiable.

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
  labels:
    app: nginx
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.7.9
        ports:
        - containerPort: 80
```

#### Services 
Los servicios en Kubernetes permiten la comunicación entre varios componentes dentro y fuera de la aplicación. Los servicios de Kubernetes ayudan a conectar aplicaciones con otras aplicaciones o usuarios. 

Un servicio agrupa un grupo de pods del mismo tipo, y sirve de balanceador de cargas entre ellos. Si otro grupo de pods A, necesita comunicarse con estos pods B, los pods A no deben de saber dirección ip, o cuantos pods existen en B, la comunicación es con el servicio B, y este se encarga de direccionar la conexión. 


## Práctica

### Docker Compose

Utilizando como base el laboratorio de la lección 9, utilizando Docker vamos a crear contenedores para nuestra aplicación utilizando Docker Compose como orquestrador. 

- Revisemos algunas cosas en el codigo del proyecto, por ejemplos los archivos `application.properties` 

``` 
server.port=${APP_PORT:8091}

spring.application.name=book-service

stockservice.url=${STOCK_SERVICE_URL:http://localhost:8092}

# Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/

## Data Source to database
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/book_service
spring.datasource.username=${MYSQL_USER:user}
spring.datasource.password=${MYSQL_PASSWORD:password}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
Estos valores con un "placeholder" `${XXXX:default-value}`, son una caracteristica de Spring Config. Esto nos permite setear un valor desde una variable de ambiente, o Java System Property, de manera externa a la aplicacion. Y evitar dejar configuraciones "hardcodeadas". 

Estas las estamos utilizando para configurar, el puerto de inicio de la aplicacion, la conexion a base de datos e incluso URL de otros servicios. 

---

Iniciaremos con el proyecto ubicado en la carpeta `Inicio`. Vamos a compilar ambos proyectos, para generar los .jar respectivos. 

``` 
$ cd /Laboratorios/Inicio/stock-service
$ mvn package

$cd /Laboratorios/Inicio/book-service
$ mvn package
```

Luego, vamos a generar las imagenes docker para ambos proyectos. El archivo `Dockerfile` ya se encuentra en cada proyecto, en la raíz del mismo.

``` 
$ cd /Laboratorios/Inicio/stock-service
$ docker build -t stock-service:latest

$cd /Laboratorios/Inicio/book-service
$ docker build -t book-service:latest
```

Revisemos que las imagenes hayan sido creadas

```
$docker images 
REPOSITORY                   TAG                      IMAGE ID       CREATED         SIZE
stock-service                latest                   be80c3931ecd   42 hours ago    218MB
book-service                 latest                   db1f61f32f43   42 hours ago    231MB
```

Es momento de configurar docker-compose para orquestrar nuestros servicios. Vamos a abrir el archivo `compose.yaml` ubicado en el directorio `/Laboratorios/Inicio`. Por el momento solamente tiene 2 servicios para iniciar la base de datos de `book-service` y `stock-service`. 

```
version: '3.5'

services:
  book_db:
    image: mysql:8.0
    restart: always
    environment:
      MYSQL_DATABASE: 'book_service'
      # So you don't have to use root, but you can if you like
      MYSQL_USER: 'user'
      # You can use whatever password you like
      MYSQL_PASSWORD: 'password'
      # Password for root access
      MYSQL_ROOT_PASSWORD: 'password'
    ports:
      # <Port exposed> : <MySQL Port running inside container>
      - '3306:3306'
    expose:
      # Opens port 3306 on the container
      - '3306'
    # Where our data will be persisted
    volumes:
      - book-db:/var/lib/mysql

  stock_db:
    image: mysql:8.0
    restart: always
    environment:
      MYSQL_DATABASE: 'stock_service'
      # So you don't have to use root, but you can if you like
      MYSQL_USER: 'user'
      # You can use whatever password you like
      MYSQL_PASSWORD: 'password'
      # Password for root access
      MYSQL_ROOT_PASSWORD: 'password'
    ports:
      # <Port exposed> : <MySQL Port running inside container>
      - '3307:3306'
    expose:
      # Opens port 3306 on the container
      - '3306'
    # Where our data will be persisted
    volumes:
      - stock-db:/var/lib/mysql

  

# Names our volume
volumes:
  book-db:
  stock-db:
```

- Poner atención en como configura el nombre de la base de datos, el usuario y contrasena de MYSQL por medio de Variables de Ambiente. 
- Poner atención a como MySQL levanta en el puerto 3306, en los contenedores, pero en el caso de `stock_db` externamente el puerto es 3307. 

Ahora si, vamos a agregar nuestros servicios al docker-compose. Empecemos con Stock-Service. 
- Vamos a indicar el nombre del servicio `stock-service`.
- La imagen será la creada anteriormente: `stock-service:latest`
- La aplicación puede configurarse externamente por medio de variables de ambiente:
    - Indicamos variables para la conexion a la base de datos
    - Indicamos variables de ambiente para el puerto donde levantará la aplicación
- Indicar el mapeo de puertos: externamente `8092` que apunta a `8080` dentro del contenedor. 
- Indicar la dependencia a su base de datos `stock_db`, para que la aplicación se inicie hasta que la base de datos esté disponible.

```
stock-service:
    image: stock-service:latest
    restart: always
    environment:
      MYSQL_HOST: 'stock_db'
      MYSQL_PORT: '3306'
      MYSQL_USER: 'user'
      MYSQL_PASSWORD: 'password'
      APP_PORT: '8080'
    ports:
      # <Port exposed> : <Application Port running inside container>
      - '8092:8080'
    expose:
      # Opens port 8080 on the container
      - '8080'
    depends_on:
      - stock_db
```

Haremos lo mismo con `book-service`: 

- En este caso el nombre del servicio es `book-service` 
- La imagen es `book-service:latest`, creada anteriormente
- Variables de ambiente seteadas, para configuraciones externas
    - Conexion a base de datos `book_db` 
    - Puerto de inicio de la aplicacion `8080` 
    - URL para consumir el micro servicio `stock-service`. 
- El mapeo de puertos es `8091` para acceder externamente, que redirecciona a `8080` dentro del contenedor.
- Depende de 2 servicios disponibles: su base de datos `book_db` y `stock-service`. 

``` 
  book-service:
    image: book-service:latest
    restart: always
    environment:
      MYSQL_HOST: 'book_db'
      MYSQL_PORT: '3306'
      MYSQL_USER: 'user'
      MYSQL_PASSWORD: 'password'
      APP_PORT: '8080'
      STOCK_SERVICE_URL: 'http://stock-service:8080'
    ports:
      # <Port exposed> : <MySQL Port running inside container>
      - '8091:8080'
    expose:
      # Opens port 8080 on the container
      - '8080'
    depends_on:
      - book_db
      - stock-service
``` 

Con este archivo `compose.yaml` listo, nos podemos ubicar en la carpeta donde se encuentra y levantar los servicios con docker-compose. 

``` 
$ docker-compose up
``` 
o 
```
$ docker-compose up -d
```

Utilice la Postman, para acceder a los URL y comprobar el funcionamiento de la aplicacion. 
En este punto, con un solo comando iniciamos 2 microservicios, y sus respectivas bases de datos.

Podemos acceder a: 
- Book Service : http://localhost:8091/
- Stock Service : http://localhost:8092/

### Docker Compose Escalabilidad

Ahora bien, en el ejercicio anterior cada servicio tiene 1 instancia. Que tal si se desea tener alta disponibilidad, con mas de 1 instancia por servicio. 

- Podemos iniciar con el archivo `compose.yaml` del ejercicio anterior, o utilizar el archivo `compose.yaml` ubicado en el directorio `Laboratorio/Pre-Final`. 

- Vamos a iniciar por crear un archivo `reverse_proxy.conf`, y lo guarderemos junto al archivo `compose.yaml`. Este archivo es la configuracion de NGINX para un reverse proxy. 

``` 
 server {
  listen 80;
  server_name localhost;
  
  location / {
        proxy_pass http://book-service:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
``` 

Esta instrucciones dicen que cuando NGINX lea el puerto 80, en localhost, redireccione al URL `http://book-service:8080/`. Es decir, que redireccione al DNS interno del servicio `book-service`. 

- En el archivo `docker-compose.yaml` vamos a agregar un nuevo servicio `reverse-proxy`:

``` 
reverse-proxy:
    image: nginx:latest
    volumes:
      - ./reverse_proxy.conf:/etc/nginx/conf.d/default.conf
    ports:
      - "8091:80"
    depends_on:
      - book-service
``` 

Este servicio se llama `reverse-proxy`, su imagen es `nginx:latest`, utiliza el archivo de configuracion creado anteriormente `reverse_proxy.conf`, y mapeo el puerto `80` de nginx con el `8091` que es expuesto externamente. 

- Luego al servicio `book-service`, vamos a hacerle un par de cambios para agregarle escabilidad.
Agregando una seccion `deploy` donde se indica el numero de `replicas` deseadas.

``` 
deploy:
      replicas: 3
      endpoint_mode: dnsrr        ## Round Robin Load Balancing
``` 
Ademas, se elimina el mapeo de puertos. 

``` 
book-service:
    image: book-service:latest
    restart: always
    environment:
      MYSQL_HOST: 'book_db'
      MYSQL_PORT: '3306'
      MYSQL_USER: 'user'
      MYSQL_PASSWORD: 'password'
      APP_PORT: '8080'
      STOCK_SERVICE_URL: 'http://stock-service:8080'
    depends_on:
      - book_db
      - stock-service
    deploy:
      replicas: 3
      endpoint_mode: dnsrr        ## Round Robin Load Balancing
```

- Iniciamos la orquestracion con docker-compose. Asi, que corremos el comando: 
``` 
$ docker-compose up
```

Una vez iniciados las aplicaciones, podemos ver como hay 3 instancias para el servicio `book-service` 

``` 
$ docker-compose ps                                                                                                                                                                                     
NAME                    COMMAND                  SERVICE             STATUS              PORTS
final-book-service-1    "java -jar book-serv…"   book-service        running             8080/tcp
final-book-service-2    "java -jar book-serv…"   book-service        running             8080/tcp
final-book-service-3    "java -jar book-serv…"   book-service        running             8080/tcp
final-book_db-1         "docker-entrypoint.s…"   book_db             running             0.0.0.0:3306->3306/tcp
final-reverse-proxy-1   "/docker-entrypoint.…"   reverse-proxy       running             0.0.0.0:8091->80/tcp
final-stock-service-1   "java -jar stock-ser…"   stock-service       running             0.0.0.0:8092->8080/tcp
final-stock_db-1        "docker-entrypoint.s…"   stock_db            running             0.0.0.0:3307->3306/tcp
```


Podemos acceder a: 
- Book Service : http://localhost:8091/    (a traves de NGINX que distribuye a los servicios)
- Stock Service : http://localhost:8092/






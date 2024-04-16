# Estrategias de Despliegue

## Tabla de Contenidos
1. [Introducción](#introducción)
2. [Docker Compose](#docker-compose)   
3. [Proyecto](#proyecto)
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
    ports:
      - "80:80"
    networks:
      - webnet
    depends_on:
      - web
networks:
  webnet:
```

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
Aunque tanto **Docker Compose** como **Kubernetes** son herramientas de orquestación de contenedores, hay algunas diferencias clave entre ellas. Docker Compose es más adecuado para entornos de desarrollo y pruebas, donde se requiere una configuración rápida y sencilla de múltiples contenedores. Proporciona una experiencia de desarrollo fluida al permitirte definir y gestionar fácilmente un entorno local de desarrollo con varios servicios.

**Kubernetes**, por otro lado, es una plataforma más robusta y escalable diseñada para desplegar y gestionar aplicaciones en producción a gran escala. Ofrece características avanzadas de orquestación, como la gestión de clústeres, el balanceo de carga y la autoescalabilidad, lo que lo hace ideal para entornos de producción complejos y de alto rendimiento.


## Práctica



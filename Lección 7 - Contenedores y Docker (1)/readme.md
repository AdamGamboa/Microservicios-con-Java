# Contenedores y Docker

## Tabla de Contenidos


1. [Introducción a los contenedores](#introducción-a-los-contenedores)

    1.1. [Contenenedores vs Máquinas Virtuales](#contenedores-vs-máquinas-virtuales)

    1.2. [Qué es Docker?](#que-es-docker)

    1.3. [Alternativas a Docker](#alternativas-a-docker)

2. [Conceptos y componentes de Docker](#conceptos-y-componentes-de-docker)   
3. [Directivas en un Dockerfile](#directivas-en-un-dockerfile)



## Introducción a los contenedores
Los contenedores son una forma de virtualización del sistema operativo. Un solo contenedor se puede usar para ejecutar cualquier cosa, desde un microservicio o un proceso de software a una aplicación de mayor tamaño.

Dentro de un contenedor se encuentran todos los ejecutables, el código binario, las bibliotecas y los archivos de configuración necesarios. 

### Contenedores vs Máquinas Virtuales

Las VM (Máquinas Virtuales) se ejecutan en un entorno de hipervisor en el que cada máquina virtual debe incluir **su propio sistema operativo invitado** dentro del mismo, junto con sus archivos binarios, bibliotecas y archivos de aplicaciones correspondientes.

Esto consume una gran cantidad de recursos y genera mucha sobrecarga, especialmente cuando se ejecutan varias VM en el mismo servidor físico, cada una con su propio sistema operativo invitado.

Por el contrario, cada contenedor comparte el mismo sistema operativo host o kernel del sistema y tiene un tamaño mucho menor, a menudo de solo unos megabytes. Esto suele implicar que un contenedor puede tardar unos segundos en iniciarse (en comparación con los gigabytes y los minutos necesarios que requiere una VM típica).


### Que es Docker? 
Docker es un proyecto de código abierto para automatizar la implementación de aplicaciones como contenedores portátiles y autosuficientes que se pueden ejecutar en la nube o localmente. 

Docker es una plataforma para la contenedorización de aplicaciones. A Contenedor es una unidad autocontenida y ejecutable que contiene una aplicación y sus dependencias. Docker permite a los desarrolladores empaquetar aplicaciones en contenedores que pueden ejecutar en cualquier sistema compatible con Docker. 

Para crear, lanzar y gestionar contenedores, Docker ofrece una sencilla interfaz de línea de comandos. En la práctica, los desarrolladores se benefician de un uso optimizado de los recursos y un despliegue acelerado de las aplicaciones.

---
### Alternativas a Docker 
Pese a que Docker suele ser sinónimos de contanedores, existen otras alternativas para trabajar contenedores sin utilizar Docker.

- **Podman**: 
    - Podman, también conocido como POD Manager, es una herramienta de código abierto que las empresas utilizan para desarrollar, gestionar y ejecutar contenedores en sistemas Linux. 
    - Fue desarrollada originalmente por los equipos de ingeniería de Red Hat en colaboración con la comunidad de código abierto.
    - Podman no requiere un demonio central y no tiene procesos en segundo plano en su sistema, lo que resulta en operaciones más rápidas y eficaces.
    - Podman es compatible con la Open Container Initiative (OCI) y se integra a la perfección con otras herramientas y tecnologías compatibles con OCI. Puede utilizar imagenes creadas con Docker.

- **RKT**: 

    - RKT pertenece a la distribución de Core OS, desarrollada para la virtualización y manejo de contenedores. Hoy día es uno de los mayores competidore de Docker. RKT trabaja sobre plataformas linux como ArchLinux, Core OS por supuesto, Fedora, NixOS y otras.


    - Docker tiene la ventaja de que es más fácil de integrar mientras que RKT conlleva una instalacion y configuracion mas manual. 

    - Permite utilizar tambien Imagenes de Docker. A su vez también permite la integración con el orquestador Kubernetes y AWS.


- **Containerd**:

    - Containerd es un tiempo de ejecución de contenedores de código abierto. Es un estándar de la industria para crear y ejecutar contenedores. 
    - Containerd se desarrolló originalmente como parte de Docker, pero más tarde se separó como proyecto independiente. Es adecuado como alternativa a Docker para Windows y Linux.

---
## Conceptos y componentes de Docker
Es de gran importancia tener los siguientes conceptos claros, para entender correctamente el funcionamiento de Docker.

### Imagen Docker
Docker utiliza imágenes como bloques de construcción para los contenedores. Una imagen es una unidad de software independiente y ejecutable que contiene toda la información necesaria para la aplicación. 

Por ejemplo una imagen podría contener un sistema operativo Ubuntu con un servidor Apache y tu aplicación web instalada.

### Dockerfile
Es un archivo de configuración que se utiliza para crear imágenes. En dicho archivo se incluyen las instrucciones que indican es lo que queremos que tenga la imagen, herramientas, binarios, ejecutables, y comandos. 

```
FROM openjdk:17-jdk-slim-buster

WORKDIR /opt
RUN mkdir my-app

COPY ./build/libs/* /my-app
COPY ./target/my-application.jar /my-app

WORKDIR /opt/my-app
CMD java -jar my-application.jar
``` 


### Registro Docker
Docker almacena estas imágenes en un repositorio central para poder descargarlas y ejecutarlas fácilmente en distintos entornos. El registro Docker más notable es Docker Hub.

### Contenedor Docker
Son instancias en **ejecución** de una imagen. Podemos tener N cantidad de contenedores simultaneos de una misma imagen. 

### Volumes
No es una buena práctica guardar los datos persistentes dentro de un contenedor de Docker. Para eso están los volúmenes, fuera de los contenedores. Así podremos crear y borrar contenedores sin preocuparnos por que se borren los datos.
Además los volúmenes se utilizan para compartir datos entre contenedores.

### Servidor Docker
También conocido como el Docker engine, o Docker Daemon, es un programa que se ejecuta en segundo plano en tu ordenador y gestiona contenedores e imágenes Docker. Cuando utilizas la interfaz de línea de comandos de Docker.

### Cliente Docker
El cliente Docker permite a los usuarios interactuar con el daemon Docker mediante su interfaz de línea de comandos (CLI). En términos sencillos, es la parte principal de la arquitectura Docker para crear, gestionar y ejecutar aplicaciones en contenedores.

Cuando utilizas la CLI de Docker para pasar un comando, el cliente Docker envía el comando al daemon Docker que se ejecuta en tu ordenador, el cual lleva a cabo la operación solicitada. 


---
## Directivas en un Dockerfile

**FROM** indica la imagen que se va a usar como base. Es bueno escribir una versión si la imagen va a perdurar en el tiempo y será distribuida. No se recomienda usar “latest” ya que nunca sabremos cuando cambiará la imagen en remoto. 

```
FROM alpine:$VERSION
```

**RUN** ejecuta comandos para la construcción de la imagen, NO en el contenedor. 
```
RUN apk add socat nginx
RUN mkdir -p /run/nginx
```

**EXPOSE** define el puerto expuesto por el comando ejecutado, **es solo informativo** para que el usuario que use la imagen utilice las opciones de mapeo de puertos que quiera.
```
EXPOSE 8080
```

**USER** facilita configurar el propietario (UID/Grupo) usado por los comandos RUN, CMD y ENTRYPOINT. Solo puede definirse un grupo de usuario, no varios. Si no se define grupo, este será root. 

```
# Usando IDs.
USER 567:567
# Usando nombre de usuario.
USER manolo
```

**WORKDIR** configura el directorio de trabajo, sencillamente es la carpeta donde ejecutaran RUN, CMD, ENTRYPOINT, COPY y ADD sus instrucciones. Si no existe el directorio es creado automáticamente. Puede usarse varias veces

```
ENV DIRPATH=/root
WORKDIR $DIRPATH/carpeta1
WORKDIR subcarpeta1
RUN COMANDO1
WORKDIR /opt/
RUN COMANDO2
```

**ADD** permite copiar ficheros del host a la imagen y los contenedores dispondrán de esos ficheros. Se debe de usar siempre rutas relativas al directorio donde creemos la imagen con el `Dockerfile`. Para el destino sí se deben usar rutas absolutas. 

```
ADD "alpine-minirootfs-3.13.0-x86_64.tar.gz" /opt/codigoalpine
```

**COPY** no permite usar ficheros remotos y no descomprime los ficheros de manera automáticamente como si hace ADD. 

```
COPY directorio_local/ /opt/codigoalpine/
```

**ENTRYPOINT** y **CMD** permiten ejecutar un comando predeterminado al iniciar el contenedor. 
Como funcionalidad agregada usando la instrucción CMD junto con ENTRYPOINT, se puede usar para pasar o completar parámetros al ejecutable configurado en ENTRYPOINT. Esto facilita sobrescribir los parámetros desde la linea de comandos, cosa que con las opciones de linea de comandos de “entrypoint” es algo más laborioso. 

```
ENTRYPOINT ["nginx"]
CMD ["-g", "daemon off;"]
```
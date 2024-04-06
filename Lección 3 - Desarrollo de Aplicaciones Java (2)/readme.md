# Desarrollo de Aplicaciones Java 

## Tabla de Contenidos
1. [Spring Framework](#spring-framework)
2. [Spring Boot](#spring-boot)
3. [Rest API con Spring Boot](#rest-api-con-spring-boot)
4. [Spring Initializer](#spring-initializer)
5. [Prácticas](#prácticas)


## Spring Framework

Spring es un framework de código abierto ampliamente adoptado para crear aplicaciones empresariales en Java. Las características Spring Boot y Spring Framework ofrecen una infraestructura robusta y liviana para aplicaciones Java. Simplifica el desarrollo empresarial de Java al proporcionar un modelo integral de programación y configuración para partes de aplicaciones web y no web.

Spring se puede considerar como el padre del los frameworks Java, ya que da soporte a varios frameworks como: Hibernate/JPA, Struts, Tapestry, EJB, JSF, entre otros.


### Módulos de Spring 

- Spring MVC
- Spring Security
- Spring ORM
- Spring Test
- Spring AOP
- Spring Web Flow
- Spring JDBC
- Spring Integration
- Spring Actuator
- y muchos más... 

#### Spring Context y DI 

Este es el core de Spring, prácticamente todos los módulos de Spring dependen de su Spring Context y Dependency Injection (DI). Nos permite tener un Context donde se guardarán los "Managed Beans" que permiten la programación con bajo acomplamiento en este Framework. 

#### Spring Data

Spring Data es un módulo que agrupa diferentes implementaciones para acceder a los datos almacendos, pueden ser SQL, NoSQL.  Proporciona un grupo de funcionalides para simplificar el desarrollo de la capa de persistencia (Repositorios, DAO).

- Spring Data JPA
- Spring Data JDBC
- Spring Data Redis
- Spring Data Rest
- Spring Data Mongo
- y muchos más... 

#### Spring AOP 

AOP: Aspect Oriented Programming (Programación Orientada a Aspectos)

Spring AOP es uno de los componente clases de Spring Framework , pero no mucha gente usa los conceptos de programación aspectual , aunque cuando se conocen pueden llegar a ser muy útiles.

### Conceptos en Spring

#### Contexto, Components y Beans
Una de las carácteristicas de Spring es que se base en el concepto de IoC (Inversion of Control) y DI (Dependency Injection), por ello el ciclo de vida de los instancias es administrada por el Contexto de Spring. Spring se encargará de saber cuando crear una instancia, por cuanto tiempo tenerla disponible, cuando eliminarla, y si la instancia requiere de otros objetos crear dichas dependencias. 

A estos objetos administrador por el Contexto, se les llama Managed Beans. Los conceptos de Component y Bean hacen referencia a un objeto que es administrado por dicho contexto de Spring. Su diferencia radica en la forma de inicializar dicho objeto. 

- `@Component` se utiliza a nivel de clase. Spring generará objetos de dicha clase según sea necesario. 

```
@Component
public class MyComponent {

}
``` 

- `Bean` se utiliza a nivel de método, la instancia que retorne dicho método será administrada por Spring Context. 

```
@Configuration
public class MyConfig {

    @Bean
    public MyComponent myComponent() {
        return new MyComponent();
    }

}
```

#### Inyección de Dependencias (DI)

Cuando necesitemos de una instancia de un objeto en especifico en Spring, en lugar de crearla nosotros en el código, se solicitará a Spring que busque en su contexto una instancia disponible, y si no la tiene Spring y su Contexto la creará. 

En Spring solicitar instancias se realiza por el mecanismo de Inyección de Dependencia, usando la anotación `@Autowired` (propia de Spring) o `@Inject` (Estandar de Java). 



Spring permite inyectar dependencias de diferentes maneras: 

- **En el constructor**: 

```
@Component
public class MyController{ 

    private MyService myService;

    @Autowired
    public MyController(MyService myService){
        this.myService = myService;
    }

}
```

- **En el atributo**
```
@Component
public class MyController{ 

    @Autowired
    private MyService myService;

    public MyController(){
    }

}
```

- **En el setter**
```
@Component
public class MyController{ 

    private MyService myService;

    public MyController(){
    }

    @Autowired
    public void setMyService(MyService myService){
        this.myService = myService;
    }
}
```

Todos son válidos, pero el **uso más extendido** es la inyección **en el constructor**. Además este enfoque facilita la creación de pruebas unitarias. 


#### Scopes
Dado que Spring tiene el control en el ciclo de vida de dichas instancias (Managed Beans), podemos indicar diferentes comportamientos (que tantas instancias son creadas y por cuanto tiempo), a esto se le llaman Scopes. 

- **Singleton:**  Spring crea una unica instancia, que estará disponible mientras la aplicación esté siendo ejecutada. Si no se indica, este es el **scope por defecto**. 

- **Prototype:** Spring crea una instancia cada vez que se Inyecta. Es decir, no se reutiliza ninguna existen. 

Además si estamos en un entorno utilizando Spring MVC o Spring Webflux, tenemos otros Scopes disponibles: 

- **Request:** Asociado a un HTTP Request, se creará una instancia para dicho request, la cual puede ser reutilizada durante este Request. Request separados, creará sus propias instancias.

- **Session:** Asociada a un HTTP Session, es decir se creará una instancia para la sessión HTTP. Cada sesión tendrá su propia instancia. 

- **Application:** Asociado al ciclo de vida de un `ServletContext`. 

- **Websocket:** Asociado al ciclo de vida de un WebSocket.

#### Stereotypes

Spring proporciona algunas "especializaciones" de `@Component`, para asociarlas a ciertos usos, ayudar a organizar nuestro código mejor, y proporcionar algunas carácteristicas extras. 

- **@Controller:** es el que realiza las tareas de controlador y gestión de la comunicación entre el usuario y el aplicativo

- **@Service:** Este estereotipo se encarga de gestionar las operaciones de negocio más importantes a nivel de la aplicación y aglutina llamadas a varios repositorios de forma simultánea.

- **@Repository:** Es el estereotipo que se encarga de dar de alta un bean para que implemente el patrón repositorio. 

Además `@Controller` tiene una especialización más, `@RestController`, utilizada para tareas de comunicación para crear los endpoints en un Rest API.

---

## Spring Boot 

Spring Boot es una extensión de Spring Framework, que elimina gran parte del código repetitivo y la excesiva configuración que caracteriza a Spring Framework. Proporciona una plataforma preconfigurada para crear aplicaciones impulsadas por Spring con una configuración mínima basada en XML y anotaciones. 

Las aplicaciones creadas con Spring Boot se pueden iniciar con un solo comando, lo que las convierte en una opción ideal para el desarrollo rápido de aplicaciones.

En el pasado configurar un proyecto en Spring Framework podía tomar varios días incluso un par de semanas a personas con algo de experiencia en el framework. Sin embargo, con Spring Boot en tan solo algunos minutos podemos tener un proyecto base pre-configurado para iniciar a trabajar e ir ajustando en la marcha. 

Es por esto que Spring Boot ha tenido tanto auge para el desarrollo de micro-servicios, dadas sus caracteristicas _(de rápido desarrollo, su ejecución es sencilla no requiere de una servidor de aplicaciones, y liviano en cuanto a uso de recursos)_ que su uso en Micro servicios es muy extendido. 

Sin embargo, de igual manera se puede utilizar Spring Boot para crear una aplicación enterprise, de mayor tamano a un microservicio.

### Diferencia de Spring Framework y Spring Boot

**Donde es usado?**
_Spring Framework:_ Principalmente para aplicaciones enterprise de gran tamano.
_Spring Boot:_ Para desarrollo de microservicios

**Deployment Descriptor**
_Spring Framework:_ Es necesario el XML descriptor de configuración
_Spring Boot:_ No es necesario 

**Servidor de Aplicaciones** 
_Spring Framework:_ Se requiere un servidor de Aplicaciones: Tomcat, JBoss, GlassFish, etc
_Spring Boot:_  Incluye un servidor embebido y preconfigurado como: Tomcat y Jetty

**Configuraciones** 
_Spring Framework:_ Las configuraciones debe ser construidas manualmente
_Spring Boot:_ Las configuraciones por defecto permiten minima intervención.

**CLI Tools**
_Spring Framework:_  No incluye un tool de CLI (Linea de comandos) para desarrollo y testing
_Spring Boot:_ Incluye un CLI tool para desarrollo y testing de aplicaciones.





## Rest API con Spring Boot


## Spring Initializr

Spring Initializr es un sitio web que nos permite generar un proyecto Spring Boot, que contenga los módulos que necesitemos. En resumen, un wizard desde el cual podemos crear un proyecto base pre-configurado en unos pocos segundos.  

Este proyecto lo podemos descargar y empezar a utilizar en nuestras máquinas. Si ya Spring Boot nos simplificaba tener una aplicación base en poco tiempo, Spring Initializer es una más alla y reduce ese tiempo mucho más. 

### Creando un proyecto

- Navegar al sitio [Spring Initializr](https://start.spring.io/)
- Elegir la configuración de nuestro proyecto

![Spring Initializr](./imagenes/initializr.png)

- Presionar el botón Generar, descargarlo y abrirlo en un IDE para comenzar a trabajar



## Prácticas

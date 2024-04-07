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
Una de las carácteristicas de Spring es que se basa en el concepto de IoC (Inversion of Control) y DI (Dependency Injection), por ello el ciclo de vida de los instancias es administrada por el Contexto de Spring. Spring se encargará de saber cuando crear una instancia, por cuanto tiempo tenerla disponible, cuando eliminarla, y si la instancia requiere de otros objetos crear dichas dependencias. 

A estos objetos administrados por el Contexto, se les llama Managed Beans. Los conceptos de Component y Bean hacen referencia a un objeto que es administrado por dicho contexto de Spring. Su diferencia radica en la forma de inicializar dicho objeto. 

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


### @SpringBootApplication
Dado que Spring Boot genera un jar, que inicializa la aplicación Web, el punto de entrada de toda aplicación Spring Boot es una clase con la anotación `@SpringBootApplication` y con un método `public static void main (String [] args)` 

```
@SpringBootApplication
public class RestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}

}
```



**`@SpringBootApplication`** es una anotación que combina todas las siguientes:

- `@Configuration`: Marca la clase como una fuente de definición de Beans para el contexto de la aplicación.

- `@EnableAutoConfiguration`: Le indica a Spring comenzar a agregar beans al contexto de la aplcación con lo que tenga en el classPath. Esto incluye los beans definidos por nosotros, así como Beans y Configurations de módulos o librerías externas. 

- `@ComponentScan`: Le indica a Spring buscar `@Component` y `@Configuration` en el package actual y sub-packages. Esta anotación debe de utilizar junto a @Configuration.



## Rest API con Spring Boot
Rest API ha llegado a ser el estandar de facto para la construcción de una aplicación web. Podemos desde el back-end crear un Rest API que expone la lógica de negocio para la capa de presentación (Java Script o movil). Spring Boot permite crear un Rest API de manera muy sencilla. 

Simplemente creamos una clase normal de Java, con métodos, y con anotaciones de Spring la convertimos en un Rest API. Veamos un ejemplo de un Rest API en Spring. 

```
@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
```

### Anotaciones

**`@RestController`**: Esta anotación, marca la clase como un controller, donde cada método retorna un objeto que será convertido a JSON de manera automática. No es necesaria hacer la conversión manual. 

**`@RequestMapping`**: Permite indicar el URL para un método y su verbo HTTP. 
```
@RequestMapping(value = "/users", method = RequestMethod.GET)
public ResponseEntity<?> getUsers(){
    ... 
}
```


**`@GetMapping`**:  Anotación para mapear el método a un HTTP *GET*. Está basada en `RequestMapping`. Un `@GetMapping("/users")` es equivalente a `@RequestMapping(value="/users", method = RequestMethod.GET)`. 

**`@PostMapping`**:  Anotación para mapear el método a un HTTP *POST*. Está basada en `RequestMapping`. Un `@PostMapping("/users")` es equivalente a `@RequestMapping(value="/users", method = RequestMethod.POST)`. 

**`@PutMapping`**:  Anotación para mapear el método a un HTTP *PUT*. Está basada en `RequestMapping`. Un `@PutMapping("/users/34")` es equivalente a `@RequestMapping(value="/users/35", method = RequestMethod.PUT)`. 

**`@DeleteMapping`**:  Anotación para mapear el método a un HTTP *DELETE*. Está basada en `RequestMapping`. Un `@DeleteMapping("/users")` es equivalente a `@RequestMapping(value="/users", method = RequestMethod.DELETE)`. 


```
@GetMapping("/{id}")
public ResponseEntity<?> getBazz(@PathVariable String id){
    return new ResponseEntity<>(new Bazz(id, "Bazz"+id), HttpStatus.OK);
}

@PostMapping
public ResponseEntity<?> newBazz(@RequestParam("name") String name){
    return new ResponseEntity<>(new Bazz("5", name), HttpStatus.OK);
}

@PutMapping("/{id}")
public ResponseEntity<?> updateBazz(
  @PathVariable String id,
  @RequestParam("name") String name) {
    return new ResponseEntity<>(new Bazz(id, name), HttpStatus.OK);
}

@DeleteMapping("/{id}")
public ResponseEntity<?> deleteBazz(@PathVariable String id){
    return new ResponseEntity<>(new Bazz(id), HttpStatus.OK);
}
```




**`@RequestParam`**: Se utiliza para extraer "query parameters" del URL del HttpRequest. 

http://localhost:8080/api/users?status=active&limit=10

```
@GetMapping("/users)
public List<Users> getUsers(
    @RequestParam String status, 
    @RequestParam(name="limit", defaultValue="5") int limit){

}
```
- Si `@RequestParam` no tiene el atributo, usará el nombre del parámetro para el queryparameter del url. 
- Se puede definir un valor por defecto en caso que el URL no contenga dicho query parameter.
- El tipo del parámetro puede ser un Optional, de manera que si el URL no lo contiene el parámetro será vacío en el Optional. 

**`@PathVariable`**: Permite manejar "templates" de variables en el Path del URL. 

http://localhost:8080/api/employees/42
```
@GetMapping("/api/employees/{id}")
@ResponseBody
public String getEmployeesById(@PathVariable("id") String employeeId) {
    return "ID: " + id;
}
```

- Si el nombre de la variable es igual al nombre del parámetro en el URL, no es necesario indicar su nombre. 

**`@RequestBody`**: Utilizado recibir el Body del HttpRequest como un objeto de entrada nuestro método. Habilita la deserialización de manera automática, de manera que si el HttpRequest contiene un XML o un JSON, este se transforma a un objeto Java. 

```
@PostMapping("/user")
public ResponseEntity saveUser(@RequestBody User user) {
    userService.saveUser(user);
    return ResponseEntity.ok(HttpStatus.OK);
}
```

**`@ResponseStatus`**: Sirve para indicar un Http Status Code de respuesta al método. Por defecto, si no se indica, se envia un 200 si el método retorna un objeto o 204 si retorna `void`. 

```
@PostMapping("/user")
@ResponseStatus(HttpStatus.CREATED) //201
public User saveUser(@RequestBody User user) {
    User savedUser = userService.saveUser(user);
    return savedUser;
}
```

### Manejo de Excepciones
Es muy probable que nuestro API pueda presentar problemas o validaciones que lanzan una excepcion. Por ejemplo si un recurso no es encontrado, devolver un status code 404, y un mensaje de error, o un error 400 (BadRequest) si alguna validación no ha pasado.

Es necesario entonces, comprender algunas de las maneras de realizar Manejo de Excepciones en un Rest API en Spring 

#### ResponseStatusException 

En nuestro método podemos lanzar un excepción `ResponseStatusException`, en donde se indica el HttpStatus code respectivo y un mensaje de error. Spring entiende esta excepción y genera la respectiva respuesta http en nuestro API. 

```
@GetMapping(value = "/{id}")
public Foo findById(@PathVariable("id") Long id, HttpServletResponse response) {
    try {
        Foo resourceById = RestPreconditions.checkFound(service.findOne(id));

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
        return resourceById;
     }
    catch (MyResourceNotFoundException exc) {
         throw new ResponseStatusException(
           HttpStatus.NOT_FOUND, "Foo Not Found", exc);
    }
}
```

Es muy util cuando se requiere un control muy especifico en las respuesta según el comportamiento de nuestro método. 

#### @ControllerAdvice y @ExceptionHandler 
Esta alternativa es muy útil para manejar excepciones y sus respuesta de manera global en la aplicación. 

```
@ControllerAdvice
public class MyEntityExceptionHandler {

    @ExceptionHandler(value = MyValidationException.class)
    public ResponseEntity<Object> handleConflict(MyValidationException ex) {
        String bodyOfResponse = "This should be application specific";
        return new ResponseEntity<>("User not found: " + e.getMessage(), HttpStatus.BAD_REQUEST);
   
    }
}
```

Se crea una clase que se anota con `@ControllerAdvice` los métodos que contenga y que esten anotados con `@ExceptionHandler` van a mapear cualquier excepción de del tipo indicado que suceda de manera global en los diferentes RestControllers, generando una respuesta según lo programado en el método.


## Spring Initializr

Spring Initializr es un sitio web que nos permite generar un proyecto Spring Boot, que contenga los módulos que necesitemos. En resumen, un wizard desde el cual podemos crear un proyecto base pre-configurado en unos pocos segundos.  

Este proyecto lo podemos descargar y empezar a utilizar en nuestras máquinas. Si ya Spring Boot nos simplificaba tener una aplicación base en poco tiempo, Spring Initializer es una más alla y reduce ese tiempo mucho más. 

### Creando un proyecto

- Navegar al sitio [Spring Initializr](https://start.spring.io/)
- Elegir la configuración de nuestro proyecto

![Spring Initializr](./imagenes/initializr.png)

- Presionar el botón Generar, descargarlo y abrirlo en un IDE para comenzar a trabajar



## Prácticas

### Parte 1
- Usando el Spring Initializr, generar un proyecto Spring Boot 3.2.4, para Java 17, usando maven con empaquetado JAR. Descargarlo e importarlo al IDE. 

- Analizar el código generado. 
- Iniciar la aplicación con alguna de las opciones: 
    1) Puede usar el comando `./mvnw clean spring-boot:run` o `mvn clean spring-boot:run`.
    2) Click derecho en el método `public static void main` de la clase `RestapiApplication` 

- Agregar nuestro primer RestController. 

```
@RestController
public class HelloController {

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

}
```

- Acceder al URL http://localhost:8080/ y ver la respuesta. 

**Troubleshooting**
Dependiendo de la versión de Maven, o del IDE, puede ser que tenga que agregar las siguientes lineas en el `POM.xml` en la sección de `<properties>`.

```
<maven.compiler.source>17</maven.compiler.source>
<maven.compiler.target>17</maven.compiler.target>
```

### Parte 2

Usando como base el proyecto `rest-api2`.
1) Crear un rest controller que permita realizar operaciones CRUD (consultar, crear, modificar, eliminar) para usuarios.
    1) Los endpoints debe estar en la ruta `/api/users`
    2) Utilizar el Service existente en el proyecto
    3) Utilizar Postman para consumir los endpoints
    4) Manejar las excepciones para validaciones o eventos

Puede utilizar Postman, e importar la colleción creada para el laboratorio con los llamados a ejecutar para realizar las pruebas al API. 




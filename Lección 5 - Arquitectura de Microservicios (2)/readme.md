# Arquitectura de Microservicios

## Tabla de Contenidos
1. [Características de Microservicios](#características-de-microservicios)
2. [Tipos de Comunicación en Microservicios](#tipos-de-comunicación-en-microservicios)
3. [Consideraciones en comunicación de Microservicios](#consideraciones-en-la-comunicación-en-microservicios)

    3.1 [Tolerante a fallos](#tolerante-a-fallos)

    3.2 [Monitoreo e Instrumentación extensiva](#monitoreo-e-instrumentación-extensiva)

4. [Consumiendo un Rest API en Spring Boot](#consumiendo-un-rest-api-en-spring-boot)    
5. [Práctica](#práctica)

---
## Características de Microservicios
Un malentendido muy común en quienes intentan implementar una Arquitectura de Microservicios es que simplifican conceptos, y tienden a pensar que se hacer sistemas de tamano pequeno que exponen un Rest API. Pero una arquitectura de microservicio incluye más conceptos para considerarse realmente microservicios.

### Single Responsability
Cada microservicio entrega una única y completa capacidad del negocio. Es decir, ellos debe de realizar solamente una función en el negocio. Lo importante debería ser crear servicios poco acoplados para que tengamos autonomía de desarrollo, implementación y escala para cada servicio.


### Livianos y Pequenos
Los microservicios están diseñados para ser pequeños. Pero definir “pequeño” es subjetivo. 

No existe consenso ni prueba de fuego para esto, ya que la respuesta correcta depende del contexto empresarial y organizacional. 

Considerar el tamaño de los servicios como el principal mecanismo de estructuración puede generar demasiados servicios que sean difíciles de mantener hasta que se implemente la automatización. 

    
### Base de datos independiente
Cada microservicio tiene su propia base de datos. No debe configurar varios servicios para acceder o compartir la misma base de datos, ya que esto anula el propósito de la operación autónoma del microservicio.

Los datos pertenecientes a un microservicio específico son privados para ese servicio. Utilice APIs para permitir que otros servicios accedan a los datos propiedad de un microservicio. Apunte a pocas tablas de base de datos por servicio.

### Escalables
Los microservicios deben ser escalables tanto horizontal como verticalmente. Al ser escalable horizontalmente, podemos tener múltiples instancias del microservicio para aumentar el rendimiento del sistema.

El diseño de los microservicios debe soportar el escalamiento horizontal (scale-out). Servicios autónomos (cada servicio, alineado con una capacidad empresarial específica, posee sus datos y su lógica), débilmente acoplados, sin estado y cohesivos, respaldan la escalabilidad.    


---
## Tipos de Comunicación en Microservicios

Es importante mencionar que aunque usualmente se asocia Microservicios con Rest APIs, esto no es del todo cierto. Existen differentes tipos de API como medio de comunicación en los micro servicios. 

- Rest API
- gRPC
- Colas y Eventos

En cuanto a comunicación de Front-end hace al Back-end, por simplicidad de implementación el medio de comunicación si suele ser Rest API. Donde una aplicación basada en Javascript/Typescript consume servicios web Rest API expuestos por el Back-end. 

Pero, cuando hablamos de la comunicación en back-end entre sus microservicios podemos encontrar comunicaciones por medio de eventos y colas (**Event Driven Architecture**), por ejemplo utilizando Kafka como "broker" de eventos y mensajes. Esto ayuda a proveer una capa de tolerancia a fallos y muy **alta disponibilidad**, ya que si un microservicio no está disponible, al iniciar tendrá la capacidad de solicitar todos los eventos pendientes, y comenzar a procesarlos. 

También **gRPC** se ha colocado como un medio de comunicación sobre todo cuando se requiere de respuesta en tiempo real y volumenes de datos muy grandes. Proporcionando un muy alto rendimiento en su comunicación. 

Pero, definitivamente el caso más común y sencillo de implementar es por medio de un **Rest API**, donde es una alternativa válida para la mayoría de casos y escenarios que podemos enfrentar. 

---
## Consideraciones en la comunicación en Microservicios

### Tolerante a fallos
Fundamentalmente, la tolerancia a fallas significa que un sistema puede continuar operando y brindando servicio incluso si algo sale mal. Los problemas pueden ocurrir de muchas maneras, incluido mal funcionamiento del hardware, fallas del software, problemas de red e incluso errores humanos.

Se debe disenar y programar teniendo en mente que las cosas van fallar, y estar preparados para cada uno de esos escenarios. Los siguientes son patrones y estrategias a implementar en el código para tolerar los fallos.

#### Timeouts
Debemos evitar que un servicio en mal estado y con tiempos de respuesta más lentos de lo esperado afecte y degrade la funcionalidad de toda la aplicación. Por esto es primordial establecer timeouts a los llamados a otros servicios. Si el tiempo de espera es excedido, proceder a cancelar la solicitud y aplicar una respuesta alternativa de ser posible. 

#### Reintentos
En los microservicios es esperable que errores de comunicación temporales o errores de red puede afectar la funcionalidad. Al comunicarse con otro sistema se recomienda tener un mecanismo de reintento autómatico. Luego de un error esperar algunos milisegundos antes de hacer un reintento, y realizar un máximo de N intentos antes de decidir que realmente ha fallado el método.

Se recomienda en algunos casos emplear una estrategia "back-off", donde por ejemplo el primer reintento se haga a los 100ms, el segundo 200ms después, el tercero 400ms después, el cuarto 800ms despues.

#### Circuit Breakers
Este es un patrón para detectar y manejar interrupciones del servicio. Si el servicio falla repetidamente, el circuito se disparará y redirigirá la solicitud a un servicio alternativo o devolverá una respuesta alternativa predefinida. 

Este patrón ayuda a prevenir fallas en cascada y degrada suavemente los sistemas cuando los servicios dejan de estar disponibles. De esta manera se evita saturar el sistema haciendo llamados.

#### Fallback methods 
En caso de los reintentos fallen, o un circuit breaker esté "abierto", se puede responder una respuesta alternativa, con un contenido por defecto. 


#### Degradación Suavizada
Esto significa que los servicios deben diseñarse de manera que su funcionalidad se degrade suavemente en caso de falla. En lugar de fallar por completo, el servicio puede degradar su funcionalidad y proporcionar un conjunto limitado de funciones. Por ejemplo, una aplicación de comercio electrónico puede desactivar funciones no esenciales.


### Monitoreo e Instrumentación extensiva

Tanto el monitoreo como la instrumentación (escalar según recursos, recuperarse de fallos, alertas) debe de automatizarse. En una arquitectura de microservicios, nuestra aplicación puede estar compuesta de N servicios, y cada uno puede M cantidad de instancias. Estar al tanto del estado de cada uno de ellos es una tarea extensiva que dificilmente pueda ser realiza por un grupo de personas 24/7 de forma manual. 

#### Service Discovery
Un mecanismo por el cual se registre y encuentre en tiempo casi real cada una de las instancias disponibles para cada servicio en la aplicación.

Con el módulo **Spring Cloud** y **Netflix Eureka**, se puede implementar un servicio encargado esta funcionalidad en Spring de manera sencilla. Sin embargo, herramientas como **Kubernetes** posee su propio Service Discovery incorporado, por lo cual no sería necesario. 

#### Health Checks
Cada Servicio debe de proporcionar un mecanismo para saber su estado de salud, de manera que su el orquestador o mecanismo de instrumentación pueda saber cuando descartar una instancia cuyo estado no es el adecaudo y proceder a crear una nueva instancia de reemplazo de manera automática.

El mecanismo más sencillo suele ser endpoint Rest que detalle el estado actual del servicio y sus componentes. 

```
GET /my-service/healthcheck/

{
    "status": "healthy",
    "components" [
        {
            "name":"database",
            "status": "healthy",
        }, 
        ...
        {
            "name":"service-b",
            "status": "healthy",
        }
    ]
}

```


---
## Consumiendo un Rest API en Spring Boot
Hay diferentes maneras de consumir un Rest API en Spring. Vamos algunas de ellas. 

### Http Client
Http Client es la forma nativa en java de invocar un Rest API.

```
String payload = """
    {"title":"The Hobbit", "author":"J.R.R Tolkien", "year": 1937 }
    """;

HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("http://localhost:8080/api/book"))
      .Post(HttpRequest.BodyPublishers.ofString(payload))
      .build();

HttpResponse<String> response = client
    .send(request, BodyHandlers.ofString());

response.statusCode();
response.body();         
```

### Rest Template
Esta es la forma clásica de consumir un Rest en Spring. 

```
RestTemplate restTemplate = new RestTemplate();
Book book = new Book();
book.setTitle("The Hobbit");
book.setAuthor("J.R.R. Tolkien");
book.setYear(1937);

HttpEntity<Book> request = new HttpEntity<>(book);

ResponseEntity<String> response = restTemplate.exchange(
    "http://localhost:8080/api/book", HttpMethod.POST, String.class);

response.statusCode();
response.body();  
```

### Spring Open Feign 
Spring Open Feign genera el código del cliente basado en un interface con anotaciones que representa las caracteristicas del endpoint a consumir. 

Comencemos por agregar una dependencia.
``` 
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

Crear el `interface` con las anotaciones que mapean al endpoint a consumir.
```
@FeignClient(name = "book-service", url = "http://localhost:8081", path = "/api")
public interface BookClient {
 
    @PostMapping("/books/")
    ResponseEntity<Book> saveBook(@RequestBody Book book);
}
```

Se inyecta el client, Spring Feign va a generar una implementación con el código correspondiente.
En este momento solamente se utiliza los métodos en el cliente. 
```
@Autowired
private BookClient bookClient;

Book book = new Book();
book.setTitle("The Hobbit");
book.setAuthor("J.R.R. Tolkien");
book.setYear(1937);

ResponseEntity<Book> response = bookClient.saveBook(book);

response.status();
response.body(); 
```   

## Tolerancia a Fallos en Spring

Spring proporciona el módulo Spring Cloud, que incluye una gran variedad de librerías a nuestra disposición. Entre ellas tenemos **Hysterix** desarrollada por Netflix, y también **resilience4j**, ambas nos ayuda enriquecer nuestro micro servicios para que sean tolerante a fallos, y tengan muchas de las carácteristicas ya mencionadas. 


### Retry
Se agrega la dependencia
```
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-spring-boot2</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId> 
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Luego en cualquier método que se desee se re-intente automáticamente, se agrega la anotación `@Retry`

```
@Retry(name = "retryFindStock")
public Stock findStock(long stockId){
    System.out.println("llamando a FindStock("+stockId+")");
    ... 
    ...
}
```
Agregar al archivo `application.properties` la configuración del retry

```
resilience4j.retry.instances.retryFindStock.max-attempts=3
resilience4j.retry.instances.retryFindStock.wait-duration=1s
```

Si el método fallara, entonces automáticamente se re-intenta hasta un máximo de 3 veces. Cada re-intento esperará 1 segundo luego del anterior.

### Circuit Breaker
De igual manera en un método agregando la anotación `@CircuitBreaker` se puede configurar este patrón, de manera que si continua fallando de manera constante deshabilite la ejecución del método. 

```
@CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallBackAfterCircuitBreakerFindStock" )
public Stock findStock(long stockId){
    System.out.println("llamando a FindStock("+stockId+")");
    ... 
    ...
}
```
Se recomienda para un circuit breaker tener un método fallback (de respaldo), para que retorne alguna respuesta "dummy". 


## Práctica

Descargar el proyecto para la lección. Este se compone de 2 módulos `book-service` y `stock-service`. El proyecto `book-service` consume el Rest API de `stock-service`. 

Al ejecutar el proyecto `book-service` este usará en el puerto 8080. 
Al ejecutar el proyecto `stock-service` este usará el puerto 8091

- Analice el uso de Open Feign, para construir el cliente Rest de Stocks en el proyecto `book-service`.  

- Utilice la colección de Postman adjunta en el laboratorio, para consumir los Rest API de `book-service`, observe como al crear un Book, u obtener libros, este servicio está interactuando con `stock-service`. 

- Analice el manejo de Retries automáticos en el código, haciendo uso de Resilient4J. Analice las entradas agregadas en el archivo `application.properties`.


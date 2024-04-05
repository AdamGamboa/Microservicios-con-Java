# Desarrollo de Aplicaciones Java 

## Tabla de Contenidos
1. [Principios de diseno](#principios-de-diseno)
2. [Maven](#maven)
3. [Unit Tests](#unit-tests)
4. [Frameworks Comunes](#frameworks-comunes)
5. [Prácticas](#prácticas)

## Principios de diseno

### SOLID 
Siguiendo estos principios podemos crear un código que sea flexible y mantenible en el tiempo. Reduce la complejidad de anadir nuevas funcionalidades, aumenta la reusabilidad de componentes, y mejora la comprensión del código.

**S** - Single Responsability
**O** - Open/Closed
**L** - Liskov Sustitution
**I** - Interface Segregation
**D** - Dependency Inversion


### DRY 
En un acronimo del ingles *"Dont Repeat Yourself"*, es decir, *"No te repitas"*.

Según este principio toda pieza de funcionalidad nunca debería estar duplicada ya que esta duplicidad incrementa la dificultad en los cambios y su evolución posterior, puede perjudicar la claridad y crear un espacio paraposibles inconsistencias.

### Inversion of Control (IoC)
Como su nombre indica, “inversión de control”, se utiliza en el diseño orientado a objetos para delegar en un tercero diferentes tipos de flujos de control para lograr un bajo acoplamiento.

Existen diferentes maneras de implementar este principio y patrones de diseno que se utilizan:
- Dependency Injection
- Abstract Factory
- Strategy 

Frameworks como Spring en Java utilizan este principio como piedra angular en su funcionalidad. En Spring el desarrollador no crea instancias de los objetos, dejamos que Spring los cree como un componente (Managed Bean). Spring tiene un contexto donde almanena este. Spring utiliza la injección de dependencias como medio para obtener la instancia.

```

@Component
public class MyService {
    private final MyRepository repository;

    @Autowired
    public MyService(MyRepository repository){
        this.repository = repository;
    }

    public void method(){
        this.repository.anotherMethod();
    }
}

@Component 
public class MyRepository {
    public void anotherMethod();
}

```

En el ejemplo anterior, nunca se crea una instancia de `MyRepository` sinó, que fue "inyectada" por el framework. En el código nunca se ve un `new MyRepository()`, porque se delegó al framework esa tarea.

### The boy scout rule
Los Boy Scouts tienen la regla de dejar el campamento más limpio de lo que se lo encontraron y en caso de ensuciarlo, se limpia y se deja lo mejor posible para la siguiente persona que venga. 

Si se aplica esto al desarrollo de software, se puede decir que si vemos alguna parte del código que se pueda mejorar, independientemente de quién lo haya hecho, debemos hacerlo. Se promueve el trabajo en equipo por encima de la individualidad,ya que no solo es importante la tarea que esa persona ha realizado, sino el proyecto en general y si se ve que algo se puede mejorar, se hace. 

---
---
---

## Maven

### ¿Qué es Maven? 
Maven es una herramienta de software para la gestión y construcción de proyectos.

- Construir y estructurar un proyecto
- Manejar Dependencias
- Compilación de código y empaquetado
- Reportes y Documentación

Fue lanzado en 2004, con el objetivo de mejorar algunos aspectos de Apache Ant. 

### Mas Sobre Maven 
- Basado en el principio de Convención sobre Configuración
- Configuración por XML, por medio del archivo POM.xml
- Tiene un ciclo de vida definido (compile, test, package, install, deploy)
- Permite la configuración de plugins, para realizar tareas.
- Proyectos Agnosticos. Utilizables en cualquier Herramienta de Desarrollo (IDE: Netbeans, Eclipse, InteliJ, etc)

### Maven vs Ant vs Gradle 
#### Apache ANT 
- ANT fue la primera herramienta “moderna” de construcción de proyectos. Lanzado en el año 2000.
- Más orientado a crear scripts, para ejecutar las tareas.
- Configuración por archivos XML. Muy procedimental.
#### Gradle 
- Fue lanzado en el ano 2012. 
- Trata de potenciar las ventajas de Ant y Maven
- Configuración por formato Groovy.
- Utilizado por google para proyectos Android.

---
### Repositorios Maven 

#### Repositorio Central Maven
Es un sitio web donde se encuentra un gran número de librerías disponibles para ser utilizadas por cualquier proyecto.
 
Por defecto un proyecto Maven buscará en este sitio si no encuentra las dependencias en repositorios locales.

Se puede acceder en: [https://search.maven.org/](https://search.maven.org/)

#### Repositorios Remotos
En determinados entornos, Maven puede ser más que suficiente pero en grandes organizaciones puede que no.

- Acceso a Internet. 
- Consumo de ancho de banda.
- Uso de librerias propietarias excluidas de repositories.
- Publicar librerias propieas de la empresa.
- Facilitar el proceso de Integración Continua (CI)

Se puede instalar un Administrador/Gestor de Repositorios Maven.
- Sonatype Nexus
- Artifactory
- Archiva


#### Repositorio Local
¿Dónde deja maven los jar que se baja?

Además de los repositorios remotos también existe un repositorio local que lo utiliza como caché evitando la descarga en las siguientes generaciones del proyecto. 

Se reduce el tiempo que supondría volver a descargarse todos las librerías.
Todos los artefactos y librerias que se manejen en maven se guardarán en la carpeta `.m2/repository` ubicada en el directorio `/home` del usuario.

---
### Ciclo de Vida 
El ciclo de vida de Maven esta conformado por Goals/Metas. Las partes del ciclo de vida principal del proyecto Maven son:

- **compile:** Genera los ficheros .class compilando los fuentes .java

- **test:** Ejecuta los test automáticos de JUnit existentes, abortando el proceso si alguno de ellos falla.
 
- **package:** Genera el fichero .jar con los .class compilados
 
- **install:** Copia el fichero .jar a un directorio de nuestro ordenador donde maven deja todos los .jar. De esta forma esos .jar pueden utilizarse en otros proyectos maven en el mismo ordenador.
 
- **deploy:** Copia el fichero .jar a un servidor remoto, poniéndolo disponible para cualquier proyecto maven con acceso a ese servidor remoto.

Algunas metas están fuera del ciclo de vida por defecto pero pueden ser llamadas si se requiere.

- clean
- assembly
- site
- site-deploy
- Y mas….

Maven tiene una arquitectura de plugins, para poder ampliar su funcionalidad, aparte de los que ya trae por defecto.

---
### Dependencias 
Uno de los puntos fuertes de maven son las dependencias y su manejo.
Para indicarle a maven que necesitamos un jar determinado, debemos editar el fichero pom.xml

    <dependencies>
    	<dependency>
      		<groupId>junit</groupId>
      		<artifactId>junit</artifactId>
      		<version>3.8.1</version>
      		<scope>test</scope>
    	</dependency>
  	</dependencies>

Una dependencia puede ser: una librería (jar, war, ear) u otro proyecto maven.


#### Grupos, Artefactos, Version (GAV)
Las dependencias se identifican por un Grupo, Artefacto y versión.

##### Artefacto
Es un componente de software que podemos incluir en un proyecto como dependencia. 
Normalmente será un jar, pero podría ser de otro tipo, como un war por ejemplo.
 
Los artefactos pueden tener dependencias entre sí, por lo tanto, si incluimos un artefacto en un proyecto, también obtendremos sus dependencias.

##### Grupo
Un grupo es un conjunto de artefactos. Es una manera de organizarlos. Así por ejemplo todos los artefactos de Spring Framewok se encuentran en el grupo org.springframework.

##### Versión
Es la versión del artefacto.


---
#### Scope
El scope sirve para indicar el alcance de nuestra dependencia y su transitividad. Hay 6 tipos:

- **compile:** es la que tenemos por defecto sino especificamos scope. Indica que la dependencia es necesaria para compilar. La dependencia además se propaga en los proyectos dependientes.

- **provided:** Es como la anterior, pero esperas que el contenedor ya tenga esa librería. Un claro ejemplo es cuando desplegamos en un servidor de aplicaciones, que por defecto, tiene bastantes librerías que utilizaremos en el proyecto, así que no necesitamos desplegar la dependencia.

- **runtime:** La dependencia es necesaria en tiempo de ejecución pero no es necesaria para compilar.

- **test:** La dependencia es solo para testing que es una de las fases de compilación con maven. JUnit es un claro ejemplo de esto.

- **system:** Es como provided pero tienes que incluir la dependencia explicitamente. Maven no buscará este artefacto en tu repositorio local. Habrá que especificar la ruta de la dependencia mediante la etiqueta `<systemPath>`

- **import:** este solo se usa en la sección dependencyManagement. 


---

### Estructura de Carpetas en un Proyecto Java con Maven

Maven nos ayuda a solventar el problema que cada proyecto, IDE, desarrollador puede crear una estructura de un proyecto. Proporciona una estructura de carpetas COMÚN para todos los proyectos.

![Image](http://www.arquitecturajava.com/wp-content/uploads/00420.png)

- **src/main/java:** Contiene el código fuente con nuestras clases Java incluida la estructura de paquetes
- **src/main/resources:** Contiene ficheros de recursos como imagenes ficheros .properties etc
- **src/test/java:** Contiene el código fuente con nuestras clases Java para realizar test
- **src/test/resource:** Contiene ficheros de recursos como imagenes ficheros .properties etc para nuestros test
- **target:** Contiene los desplegables que generamos con Maven jar,war,ear etc
- **pom.xml:** Ya hemos hablado de el y es el fichero encargado de definir el concepto de Artefacto. 


#### Archivo POM.XML
Este es un ejemplo básico de un archivo POM.xml, debido al principio de Convencion sobre Configuración con este minimo archivo ya se toma por hecho la configuración de estructura carpetas del proyecto sin necesidad de indicarlo.

    <project xmlns="http://maven.apache.org/POM/4.0.0"
    		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    		xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    
		<modelVersion>4.0.0</modelVersion>
    	
		<groupId>com.hop2croft.examples</groupId>
    	<artifactId>example-post</artifactId>
    	<version>0.0.1-SNAPSHOT</version>
    
		<name>EjemploMaven</name>
    	<description>Ejemplo de proyecto con Maven</description>
	</project>

Al archivo pom.xml se le pueden agregar secciones para: 

- Propiedades: `<properties>...</propierties>`
- Dependencias: `<dependencies>...<dependencies/>`
- Módulos: `<modules>...</modules>`
- Construcción: `<build/>`
- Plugins: `</plugins>`
- Profiles: `<profiles/>`
- Información de Desarrollo: `<developers>, <mail-list>`

---
### Proyecto multi-módulo en Maven
Maven soporta agregración ademas de herencia de proyectos, con los cual se permite la creación de proyectos con multiples módulos. Un proyecto multi-modulo esta definido por un POM padre referenciando uno o más sub-módulos.El proyecto padre no crea un jar/war como los proyectos ya vistos. En lugar de eso, el solamente hace referencia a otros proyectos maven. 

Las configuraciones del pom padre, son heredadas a todos los submodulos. Esto nos permite configurar dependencias/plugins y comportamientos para todos los proyectos. Los sub-módulos deben hacer referencias ademas al POM padre.


#### POM PADRE

    <project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/maven-v4_0_0.xsd">
    	<modelVersion>4.0.0</modelVersion>

	    <groupId>com.example.myapp</groupId>
	    <artifactId>simple-parent</artifactId>
	    <packaging>pom</packaging>
	    <version>1.0</version>
	    <name>Proyecto Padre</name>
	
	    <modules>
	        <module>simple-module1</module>
	        <module>simple-module2</module>
	    </modules>
		
		<dependencies> 
			...
		</dependencies>
		
		<build>
			...
		</build>
	</project>


#### POM SUB-MÓDULO

    <project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/maven-v4_0_0.xsd">
	    <modelVersion>4.0.0</modelVersion>
	    <parent>
	        <groupId>com.example.myapp</groupId>
	        <artifactId>simple-parent</artifactId>
	        <version>1.0</version>
	    </parent>
	
	    <artifactId>simple-module1</artifactId>
	    <packaging>jar</packaging>
	    <name>Simple Submodule 1</name>
	
	    <build>
			...
		</build>
	</project>

### Plugins
En Maven tenemos los siguientes tipos de plugins: 

- Build Plugins: Ejecutados durante la fase de build 
- Reporting Plugins: Ejecutados durante la generación del sitio (site-deploy)

Un plugin permite extender el comportamiento de maven y realizar tareas especificas. Cada tarea en maven es un plugin, maven trae algunos instalados, se pueden instalar nuevos.

- Crear servicios-web
- Crear clases nuevas a partir de un xml 
- Generar reportes estadisticas de codigo
- Copiar archivos en otra direccion.

----
----
----

## Unit Tests 

Las **pruebas unitarias** pretenden probar el comportamiento correcto de las clases de manera aislada. Esto significa que se prueba la clase aislándola de su interacción con otras clases.

En Java, *JUnit* y *TestNG* son las librerías más utilizadas para implementar dicho tipo de pruebas al código. 

### Principios FIRST para la escritura de pruebas unitarias

**F**: _Fast_, los test se han de ejecutar rápidamente.
**I**: _Isolated_, los test se realizan sobre una clase sin interacción con otras.
**R**: _Repeatable_, el orden de ejecución de los test no debe influir en el resultado final.
**S**: _Self-validating_, los test se han de ejecutar de modo automático.
**T**: _Timely_, se han de crear al mismo tiempo que el software que se está creando.

### JUnit 
JUnit se trata de un Framework Open Source para la automatización de las pruebas (tanto unitarias, como de integración) en los proyectos Software. El framework provee al usuario de herramientas, clases y métodos que le facilitan la tarea de realizar pruebas en su sistema y así asegurar su consistencia y funcionalidad.

#### Creando Unit Tests con JUnit

- **src/test/java:** Contiene el código fuente con nuestras clases Java para realizar test

A manera de convención, se suele usar el nombre de la clase a validar más el sufijo `Test` en el nombre de la clase que contiene los tests.  Es decir, si la clase se llama `Aritmetica`, entonces las pruebas estarán en la clase `AritmeticaTest`. 

Cada prueba unitaria, de corresponder a un escenario a probar. Se debe crear un método con la anotación `@Test`, dicho método no debe recibir parámetros. El nombre del método es indiferente.

```
public class AritmeticaTest {

    @Test 
    public void testSuma(){
        Aritmetica aritmetica =  new Aritmetica();
        int resultado =  aritmetica.suma(1,2);
        assertEquals(2, resultado);
    }

    @Test 
    public void testResta(){
        Aritmetica aritmetica =  new Aritmetica();
        int resultado =  aritmetica.resta(12,5);
        assertEquals(7, resultado);
    }

}
```

#### Anotaciones

- `@Test`: Se coloca a nivel de un método, indica que dicho método es un scenario a ejecutar como parte del test.
- `@BeforeEach`: Se ejecuta antes de cada método marcado como un test `@Test`. Se puede usar para inicializar variables
- `@AfterEach`: Se ejecuta después de cada método marcado como un test test `@Test`. Se puede usar para inicializar variables.
- `BeforeAll`: Se ejecuta una unica vez, antes de todos los métodos `@Test`. 
- `@AfterAll`: Se ejecuta una única vez, después de todos los métodos `@Test`. 

```
public class AritmeticaTest {

    Aritmetica aritmetica;
    
    @BeforeEach
    public void init(){
        aritmetica = new Aritmetica();
    }

    @AfterEach
    public void finish(){
        System.out.println("Finalizando un test");
    }
        
    @Test 
    public void testSuma(){
        int resultado =  aritmetica.suma(1,2);
        assertEquals(2, resultado);
    }

    @Test 
    public void testResta(){
        int resultado =  aritmetica.resta(12,5);
        assertEquals(7, resultado);
    }
}
```

#### Asserts
El objetivo de los tests es validar que el resultado es el esperado, para ellos se puede usar los Asserts. 

- `assertEquals(expected, result)`: Verifica que ambos valores sean iguales
- `assertNotEquals(unexpected, result)`: Verifica que los valores NO sean iguales 
- `assertNull(result)`: Verifica que el valor sea `null`.
- `assertNotNull(result)` : Verifica que un valor no sea `null`. 
- `assertTrue(boolean)` : Verifica que el valor sea `true`.
- `assertFalse(boolean)` : Verifica que el valor sea `false`.
- `assertThrows(expectedException, runnable)`: Se puede utiliza para hacer un llamado del cual se espera una excepción. 

En caso que un assert sea fallido (su resultado no es el esperado), el test va a fallar y no continúa. Al final de todos los tests, un resumen será generado con los test exitosos y los fallidos.

### Mockito
Ahora bien, uno de los principios de FIRST para la creación de pruebas unitarias es la I (Isolated), es decir, que debe ser aislado, solo queremos probar nuestra clase y no otras dependencias.  

Como hacemos cuando nuestra clase tiene dependencias a otras instancias y objetos. Por ejemplo. 

```
public class MyService {

    private AnotherService anotherService;
    private MyRepository myRepository;

    public MyService(AnotherService anotherService, MyRepository myRepository){
        this.anotherServier = anotherService;
        this.myRepository = myRepository;
    }

    public String myMethod(String parameter){
        if(parameter == null){
            return "Hello";
        }
        int result = anotherService.calculation(parameter);
        if(result == null){
            return parameter;
        } else {
            String value = this.myRepository.someMethod(parameter);
            return value.toUpperCase()+ "Bar";
        }
    }
}

public class AnotherService {
    public int calculation(String text){
        //Logic
    }
}

public class MyRepository {
    public String someMethod(String text){
        //Logic
    }
}
```

En este caso, `Mockito` nos puede ayudar crear objetos Mock para dependencias de nuestra Clase a testear, de manera que tenemos el control de que valores queremos retornar en cada escenario, y de esta manera simplemente enfocarse en las pruebas de nuestra clase. 

```
import static org.mockito.Mockito.*;

public MyServiceTest {

    @Test 
    public void testA(){
        //Creo los mocks
        AnotherService anotherService = mock(AnotherService.class);
        MyRepository myRepository = mock(MyRepository.class);

        //Creo la instancia de la clase, usando los objetos mock
        MyService myService = MyService(anotherService, myRepository);

        //Se da comportamiento a los mocks
        when(anotherService.calculation("abc"))
            .thenReturn(42);

        when(myRepository.someMethod(any(String.class)))
            .thenReturn("Foo");

        //Ejecuto la prueba
        String result = myService.myMethod("Hola mundo");

        //Valido el resultado
        assertEquals("FOOBar", result);        
    }
}

```

Estos son algunos de los principales métodos que Mockito nos proporciona.

- `mock(Class)`:  Permite crea un objeto mock del tipo de clase indicado.
- `when(mock.metodo()).thenReturn(result)`: Da comportamiento al método en el mock, para que retorne el resultado deseado.
- `when(mock.metodo()).thenThrow(new RuntimeException())`: Da comportamient al método en el mock, para que lance la excepción deseada.
- `verify(mock).metodo()`: Verifica que el método indicado sea llamado durante el test.
- `verify(mock, times(5)).metodo()` : Verifica que el método indicado sea llamado exactamente 5 veces durante el test.
- `verify(mock, never()).metodo()`: Verifica que el método indicado nunca sea llamado durante el test.

### Dependencias

    <dependencies>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-api</artifactId>
                <version>5.0.2</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>5.0.2</version>
            </dependency>

            <dependency>
                <groupId>org.mockito</groupId>
                <artifactId>mockito-core</artifactId>
                <version>5.11.0</version>
                <scope>test</scope>
            </dependency>
    </dependencies>




----
----
----
## Frameworks comunes 

Java posee un amplio abanico de frameworks y tecnologías para crear aplicaciones orientadas a microservicios

- Jakarta EE (evolución de Java EE / J2EE)
- Spring Framework (incluye Spring Boot)
- Dropwizard
- Quarkus
- Micronaut
- Helidon

**Spring Framework**, con **Spring Boot** es uno de los frameworks más utilizados en el mercado en el mundo Java. Algunos lo conocen como el framework de frameworks, ya que tiene módulos y librerías para casi cualquier aspecto necesario en una aplicación sin importar los requerimientos que posea.

---
---
---


## Prácticas

### Parte 1: Maven 
Usando el proyecto Laboratorio de Maven, se requiere agregar las dependencias necesarias para que el código actualmente comentado pueda ser ejecutado sin errores. 

Dependencias externas: 
- `org.apache.commons:commons-math3:3.6.1`
- `org.apache.commons:commons-lang3:3.12.0`

**Solución**
En el proyecto maven final. 

### Parte 2: Unit Tests
Usando el proyecto de Laboratorio de Unit Tests, se requiere agregar algunos unit tests para las clases existentes. 

**Aritmetica**
- Agregue unit tests para cada uno de los métodos de la clase `Aritmética`
- Agregue además unos métodos que utilicen `@BeforeEach`, `@AfterEach` y `@BeforeAll`, puede simplemente imprimir un mensaje en consola para ver cuando se ejecutan.

**MyService**
En estos escenarios deseamos usar Mockito

- test -> `evenOrOdd_even`: Debe validar que cuando randomGenerator retorna un número par, el método `evenOrOdd` retorne el string `"El numero es par"`
- test -> `eventOrOdd_odd`: Debe validar que cuando randomGenerator retorn un número impar, el método `eventOrOdd` retorne el string `"El numero es impar"`
- test -> `convertirString_ok`: Debe validar que si  el método `convertirString` recibe el parámetro `"Hola Mundo"`, el resultado esperado debe ser `"HOLA MUNDO:ABCDEF"` y que si el parámetro `"Java Rules"` es recibido, entonces el parámetro debe ser `"JAVA RULES:123ZXY"`. Además verifique que el método `generator.getRandomString` fue llamado 2 veces. 

**Bonus** 
Pruebe correr los unit tests con el comando de maven. 

**Solución**
En el proyecto practica-unit-tests final. 



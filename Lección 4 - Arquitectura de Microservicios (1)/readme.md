# Arquitectura de Microservicios

## Tabla de Contenidos
1. [Introducción](#introducción)
2. [Principios Rest](#principios-rest)

    2.1. [Sin Estado](#sin-estado)

    2.2. [Nombres de recursos adecuados](#denominación-y-estructuración-adecuadas-de-los-recursos)

    2.3. [Usar los Status code en la respuesta](#usar-los-status-code-en-la-respuesta)

    2.4. [Respuesta de error Estandarizados](#respuesta-de-error-estandarizadas)

    2.5. [Idempotencia](#idempotencia)

3. [Buenas Prácticas](#buenas-practicas)

    3.1. [Generación automática de Documentación](#generación-automática-de-documentación)

    3.2. [Autenticación y Autorización](#implementar-autenticación-y-autorización)

    3.3. [Versionado](#versionado)
    
4. [Práctica](#práctica)

## Introducción

Los microservicios suelen ser sinónimos de Rest API. Sin embargo, hay que tener claro que no es del todo cierto, hay Microservicios que utilizan otros tipos de API para comunicación, como gRPC, graphQL, WebSocket, Eventos (Kafka, MQ), entre otros. 

Sin embargo, definitivamente las Rest API son el tipo de API más utilizada al construir microservicios. Es de mucha importancia tener claro conceptos de Rest API al crear nuestros microservicios. 

## Principios Rest

### Sin Estado (Apatridia)
Asegúrese de que todos los datos necesarios para procesar una solicitud se envíen dentro de la solicitud, ya sea en la URL, los encabezados de la solicitud o la carga útil.

**Evite** el uso de **sesiones** del lado del servidor u otros mecanismos del lado del servidor para almacenar información sobre los clientes. Los tokens de autenticación, como JWT (JSON Web Tokens), se pueden utilizar para transportar datos específicos del cliente necesarios para fines de autenticación y autorización.

### Usar métodos HTTP apropiadamente

El principio más importante en las APIs RESTful es el uso de los métodos HTTP, haciendo un uso correcto de verbos para cada operación:

- **GET**: Solicita información de recursos.

- **POST**: Creación de nuevos recursos.

- **PUT**: Actualiza un recurso existente en su totalidad.

- **PATCH**: Actualiza un recurso existente parcialmente.

- **DELETE**: Elimina un recurso existente.

Estos métodos son empleados por los clientes para crear, manipular y eliminar datos en los servidores, respectivamente.

### Denominación y estructuración adecuadas de los recursos

La denominación y estructuración de recursos son cruciales para crear API REST intuitivas y fáciles de usar. Las siguientes pautas pueden ayudarle a diseñar una estructuración y denominación de recursos efectiva:

- **Utilice sustantivos, no verbos**: en el diseño de API REST, los recursos deben representarse mediante sustantivos, no verbos. Por ejemplo, utilice `"/orders"` en lugar de `"/getOrders"` o `"/createOrder"`. Esto enfatiza el hecho de que se están manipulando recursos y no las acciones en sí.

- **Manténgalo simple y descriptivo:** use nombres que sean fáciles de entender y que transmitan con precisión el significado de un recurso. Por ejemplo, utilice `"/productos"` en lugar de `"/prdcts"` o `"/inventory_items"`. Esto ayuda a crear una API intuitiva que los desarrolladores pueden adoptar rápidamente.

- **Utilice plurales para los recursos de la colección:** cuando trabaje con una colección de recursos, utilice nombres en plural (p. ej., `/pedidos`, `/clientes`). Esto indica que el recurso hace referencia a una colección de elementos, lo que hace que la API sea más comprensible para los desarrolladores.

- **Anide recursos para representar relaciones:** cuando exista una jerarquía o relación clara entre recursos, utilice URL anidadas para expresarla. 
Por ejemplo, `"/orders/123/items"` se puede utilizar para representar elementos que pertenecen al pedido `123`. Esto también le permite representar relaciones complejas entre recursos utilizando una estructura de URL simple e intuitiva.

### Usar los Status Code en la respuesta

Al responder a una solicitud, se debe utilizar los códigos de estado correctamente dependiendo de la situación.

- **1XX**: Respuestas informativas.
- **2XX**: Peticiones correctas.
    - 200: Ok -> La solicitud fue correcta
    - 201: Created -> La solicitud fue correcta y creó el recurso
    - 202: Accepted -> La solicitud fue correcta, pero aún sigue ejecutando en segundo plano (proceso asincronos).
    - 204: No Content -> La solicitud fue correcta y no devuelve contenido de respuesta
    - 206: Partial Content -> La solicitud fue correcta, pero no devuelve toda la información, se invita a enviar otra solicitud para recibir más datos.

- **3XX**: Redirecciones.

- **4XX**: Errores del cliente.
    - 400: Bad Request -> Usado para validaciones donde el cliente no envió la información correctamente.
    - 401: Unauthorized -> El cliente no envió datos de autenticación o autorización correctos
    - 403: Forbidden -> El cliente puede estar autenticado, pero no tiene permitido hacer dicha operación. Usualmente por permisos de autorización.
    - 404: Not Found -> El recurso no se ha encontrado.
    - 409: Conflict -> Hay un conflicto con la solicitud. Por ejemplo, el objeto ya fue modificado y no tiene la última versión. 
    - 429: Too Many Requests -> Utilizado cuando se limita la cantidad de peticiones de un cliente en un tiempo determinado.

- **5XX**: Errores del servidor.

### Respuesta de Error Estandarizadas
Además de responder con un status code adecuado, se recomienda tener una respuesta de error estandarizada en la aplicación, que permita a los clientes entender la causa del fallo y darle un seguimiento. 

Se recomienda que tenga: 
- Mensaje de error
- Codigo de error
- Categoria

Se recomienda que no tenga: 
- Un stack trace o detalles técnicos del error
- Información sensible

Por ejemplo: 
```
{
    "message": "El usuario ya no está activo en el sistema",
    "category": "User",
    "code": "1250"
}

```

### Idempotencia
La ejecución repetida de una petición con los mismos parámetros sobre un mismo recurso tendrá el mismo efecto en el estado de nuestro recurso en el sistema si se ejecuta 1 o N veces.
![Idempotencia](./imagenes/idempotencia.png)

**Porque el POST no es idempotente?** 

Siguiendo los principios Rest, al llamar a un POST vamos a crear un nuevo registro. 
Si lo llamamos N veces con los mismos datos, debemos de generar N registros diferentes (todos con su propio id). 


## Buenas prácticas

### Generación automática de Documentación
A diferencia de otros tipos de API como SOAP, un Rest API no tiene como tal un contrato donde se pueda ver en primera instancia todos los métodos disponibles, su estructura, parámetros, respuestas y demás. 

Sin embargo, hay iniciativas que ayudan a esto como OpenAPI, la cual es una iniciativa abierta que promueve una especificación formal para describir HTTP APIs (Incluyendo Rest APIs). 

En la práctica, con Open API se genera un archivo descriptor (yaml, json) con detalles de nuestros métodos, el verbo HTTP, el URL, los parámetros esperados, las respuestas. Este archivo descriptor puede ser utilizado por los clientes como el "contrato" del API. 

Existen implementaciones de Open API, la más famosa es Swagger, que además de generar un archivo descriptor puede generar una página web con todos esta información de manera visualmente agradable al usuario. 

![Swager UI](./imagenes/1-swagger-ui.webp)

### Implementar Autenticación y Autorización

A no ser que nuestro API sea complemente público, se recomienda agregar Autenticación y Autorización a nuestros endpoints en un Rest API. Puede ser algo tan sencillo como HTTP Basic Authentication (la cual no es segura), o el uso de JWT (JSON Web Token) con Bearer tokens. 

El uso de JWT, y OAuth es la forma más extendida de aplicar autenticación y autorización a un Rest API. 


### Versionado

Versionar tu servicio REST proporciona flexibilidad, control y estabilidad tanto para los desarrolladores que mantienen la API como para aquellos que la consumen. A su vez, facilita la evolución de tu API a lo largo del tiempo sin causar interrupciones inesperadas en las aplicaciones de los usuarios.

También debemos mencionar que el versionado de API puede llevarse a cabo de diversas maneras, y dos enfoques comunes son mediante el uso de la ruta (URL) o mediante encabezados personalizados en las solicitudes HTTP. A continuación algunos ejemplos:

**Ejemplo con prefijo en la ruta:** 

```
/v1/users
/v2/users
```

**Ejemplo con subdominio:** 

```
https://api-v1.example.com/users
https://api-v2.example.com/users
```

**Ejemplo con encabezado “Api-Version”:**

```
GET /users HTTP/1.1
Host: api.example.com
Api-Version: 1
```

## Práctica

Usando como base el proyecto `principios-rest` creado con Spring Boot vamos a: 

- Corregir algunos endpoints para que sigan los principios Rest establecidos. 
    - Aplicar HTTP methods correctamente
    - URL de endpoints según el estandar
    - Status codes retornados según las mejores prácticas

- Generar una documentación del API usando Open API/Swagger 
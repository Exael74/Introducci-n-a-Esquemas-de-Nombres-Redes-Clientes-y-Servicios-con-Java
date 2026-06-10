# Introducción a Esquemas de Nombres, Redes, Clientes y Servicios con Java

Este repositorio contiene la solución extensa y completa al taller práctico de la asignatura de **Arquitectura de Software (ARSW)** enfocado en la programación de redes en Java. El objetivo principal de este proyecto es explorar, comprender y aplicar los conceptos fundamentales que rigen las comunicaciones a través de internet y redes locales. Entre los temas abarcados se encuentran el uso de protocolos de red orientados a conexión (TCP) y no orientados a conexión (UDP), la manipulación y lectura de URLs, la implementación de Servidores Web básicos y el desarrollo de sistemas distribuidos mediante RMI (Remote Method Invocation).

A continuación, se detalla el contenido de cada uno de los ejercicios desarrollados, especificando su objetivo pedagógico, una explicación exhaustiva de su funcionamiento interno a nivel de código y arquitectura, y las instrucciones precisas para su correcta compilación y ejecución.

---

## Ejercicio 1: Exploración y Desglose de la Clase URL

**Objetivo:** 
Comprender la anatomía de un Localizador Uniforme de Recursos (URL) y aprender a extraer programáticamente cada uno de sus componentes individuales haciendo uso de la API estándar de red provista por Java (`java.net.URL`).

**Cómo funciona:** 
El programa define una cadena de texto estática que representa una URL compleja, la cual incluye protocolo, nombre de host, puerto, ruta de acceso, parámetros de consulta (query) y fragmentos de referencia. Se instancia un objeto de la clase `URL` con dicha cadena. Aunque constructores basados en strings han sido marcados como deprecados en versiones recientes de Java en favor de la clase `URI`, el ejercicio demuestra su utilidad práctica. A través del objeto instanciado, el sistema invoca secuencialmente los métodos de extracción de la API (tales como `getProtocol()`, `getHost()`, `getPort()`, `getPath()`, `getQuery()`, `getFile()`, y `getRef()`), imprimiendo los resultados detallados en la salida estándar de la consola. Esto permite validar cómo Java descompone y entiende internamente una dirección web.

**Instrucciones de Ejecución:**
1. Navegue a través de la terminal hasta el directorio `ejercicio1`.
2. Compile el código fuente ejecutando el comando: `javac URLInfoPrinter.java` (reemplace el nombre si fue modificado en su entorno local).
3. Inicie la aplicación ejecutando: `java URLInfoPrinter`
4. Inspeccione en la consola el desglose exhaustivo de los componentes que conforman la URL de prueba.

> **Espacio para Evidencia - Ejercicio 1:**
> [Inserte aquí la captura de pantalla evidenciando la salida por consola del desglose de la URL]
> ![Evidencia Ejercicio 1](./imagenes/ejercicio1.png)

---

## Ejercicio 2: Aplicación Browser (Lector y Almacenador de URL)

**Objetivo:** 
Desarrollar una aplicación capaz de establecer una conexión HTTP básica a una URL proporcionada por el usuario, leer el flujo de datos resultante (típicamente código fuente HTML) y persistir dicha información en el almacenamiento local del sistema.

**Cómo funciona:** 
La aplicación solicita al usuario ingresar una dirección URL a través de la consola empleando la clase `Scanner` o `BufferedReader`. Una vez obtenida y validada la cadena, el sistema crea un objeto `URL` y procede a invocar el método `openStream()`. Este método devuelve un flujo de entrada (InputStream) asociado a la conexión de red. El programa encapsula este flujo dentro de un `BufferedReader` para optimizar la lectura línea por línea del documento remoto. De manera simultánea, se instancia un flujo de salida (ej. `PrintWriter` o `FileWriter`) apuntando a un archivo local denominado `Resultado.html`. A través de un bucle, el sistema itera sobre cada línea leída del servidor remoto y la escribe inmediatamente en el archivo local, logrando efectivamente clonar el código fuente de la página solicitada.

**Instrucciones de Ejecución:**
1. Navegue a través de la terminal hasta el directorio `ejercicio2`.
2. Compile el código fuente ejecutando el comando: `javac AplicacionBrowser.java`
3. Inicie la aplicación ejecutando: `java AplicacionBrowser`
4. Cuando el programa lo solicite, ingrese una URL válida y con el protocolo explícito (por ejemplo, `http://www.google.com/`).
5. Navegue en su explorador de archivos hacia la carpeta del proyecto y abra el archivo resultante `Resultado.html` utilizando su navegador web de preferencia para visualizar la página clonada.

> **Espacio para Evidencia - Ejercicio 2:**
> [Inserte aquí la captura de pantalla evidenciando la solicitud de la URL en consola y el navegador renderizando el archivo Resultado.html local]
> ![Evidencia Ejercicio 2](./imagenes/ejercicio2.png)

---

## Ejercicio 4.3.1: Arquitectura Cliente-Servidor TCP Básica (Cálculo de Cuadrados)

**Objetivo:** 
Implementar y comprender el funcionamiento de la comunicación bidireccional entre un cliente y un servidor utilizando el protocolo TCP (Transmission Control Protocol), el cual garantiza la entrega secuencial y confiable de paquetes de datos a través de Sockets en Java.

**Cómo funciona:** 
La arquitectura se divide en dos componentes independientes que se comunican a través de un puerto de red previamente acordado.
- **El Servidor (`SquareServer.java`):** Instancia un objeto `ServerSocket` vinculado al puerto lógico `35000`. Posteriormente, invoca el método bloqueante `accept()`, deteniendo el hilo de ejecución hasta que un cliente intente conectarse. Al establecer la conexión, obtiene los flujos de entrada y salida del `Socket` resultante. Lee la cadena de texto enviada por el cliente, la convierte a un tipo de dato numérico (`Double`), calcula su cuadrado multiplicando el valor por sí mismo y retorna el resultado formateado a través del flujo de salida hacia el cliente.
- **El Cliente (`SquareClient.java`):** Instancia un objeto `Socket` indicando la dirección IP local (`127.0.0.1`) y el puerto de destino (`35000`). Luego, entra en un ciclo donde captura las entradas del usuario a través de la terminal, las transmite por el flujo de salida del Socket hacia el servidor, y se queda en espera de la respuesta. Una vez recibida la respuesta calculada por el servidor, la imprime en pantalla.

**Instrucciones de Ejecución:**
1. Abra una terminal, navegue hasta el directorio `ejercicio4.3.1` y compile el servidor: `javac SquareServer.java`
2. Inicie el proceso del servidor: `java SquareServer`
3. Abra una **segunda terminal** (sin cerrar la primera), navegue al mismo directorio y compile el cliente: `javac SquareClient.java`
4. Inicie el proceso del cliente: `java SquareClient`
5. En la terminal del cliente, ingrese valores numéricos y presione Enter para observar cómo el servidor remoto procesa la solicitud y retorna el resultado al instante.

> **Espacio para Evidencia - Ejercicio 4.3.1:**
> [Inserte aquí una captura mostrando ambas terminales (cliente y servidor) interactuando y procesando los números ingresados]
> ![Evidencia Ejercicio 4.3.1](./imagenes/ejercicio4.3.1.png)

---

## Ejercicio 4.3.2: Servidor de Operaciones Matemáticas Dinámicas (Trigonometría)

**Objetivo:** 
Extender el modelo Cliente-Servidor TCP tradicional incorporando el manejo de estados persistentes dentro de la sesión del servidor, permitiendo alterar dinámicamente el comportamiento del sistema basado en comandos específicos enviados por el cliente.

**Cómo funciona:** 
Este ejercicio es una iteración avanzada del ejercicio anterior. El servidor se inicializa con un estado por defecto, configurado para calcular la función matemática "Coseno". Dentro del ciclo de lectura de datos provenientes del cliente, el servidor implementa lógica de enrutamiento condicional. Si la cadena recibida inicia con un prefijo especial reservado (específicamente `fun:` seguido de `sin`, `cos` o `tan`), el servidor no intenta realizar una operación matemática, sino que actualiza su estado interno registrando la nueva función a ejecutar. Si la entrada no contiene el prefijo, el servidor asume que es un valor numérico, revisa su estado actual y aplica la función trigonométrica correspondiente mediante la librería `Math` de Java. El resultado es formateado para mostrar qué operación fue aplicada sobre el valor y se retorna al cliente, demostrando cómo una sola conexión puede evolucionar en su contexto de ejecución.

**Instrucciones de Ejecución:**
1. Abra una terminal, navegue hasta la carpeta `ejercicio4.3.2` y compile ambos archivos: `javac TrigServer.java TrigClient.java`
2. Ejecute el servidor: `java TrigServer`
3. En una nueva terminal, ejecute el cliente: `java TrigClient`
4. Ingrese valores numéricos para observar la ejecución de la función por defecto (Coseno).
5. Ingrese explícitamente comandos como `fun:sin` o `fun:tan` para alterar el estado del servidor, y luego ingrese nuevos números para verificar el cambio de operación matemática.

> **Espacio para Evidencia - Ejercicio 4.3.2:**
> [Inserte aquí una captura de la consola del cliente demostrando la transición exitosa entre las diferentes funciones trigonométricas]
> ![Evidencia Ejercicio 4.3.2](./imagenes/ejercicio4.3.2.png)

---

## Ejercicio 4.4: Servidor Web Primario (Respuesta Estática de Petición Única)

**Objetivo:** 
Desmitificar el funcionamiento de los servidores web modernos mediante la construcción de un programa nativo en Java capaz de interpretar peticiones HTTP puras y responder con documentos formateados que un navegador comercial pueda renderizar de manera adecuada.

**Cómo funciona:** 
El programa `HttpServer.java` crea un `ServerSocket` asociado al puerto `35000`. Al acceder desde un navegador web estandarizado (Chrome, Firefox, etc.) empleando la dirección `localhost:35000`, el navegador establece una conexión TCP y despacha inmediatamente un bloque de texto plano que conforma la petición HTTP (incluyendo el método `GET`, la ruta, la versión del protocolo y los múltiples encabezados o "Headers" descriptivos). El servidor escrito en Java lee este bloque secuencialmente mediante un `BufferedReader` y lo imprime en la consola del desarrollador, lo cual sirve para ilustrar cómo lucen verdaderamente las transacciones en la web. Al terminar de leer la petición, el servidor responde estructurando cuidadosamente un paquete HTTP válido, el cual incluye la línea de estado `HTTP/1.1 200 OK`, el encabezado estricto `Content-Type: text/html` y un par de saltos de línea obligatorios. Finalmente, envía un pequeño documento HTML estático y cierra los flujos, finalizando la conexión.

**Instrucciones de Ejecución:**
1. Navegue a la carpeta `ejercicio4.4` y compile el servidor: `javac HttpServer.java`
2. Ejecute el servidor: `java HttpServer`
3. Abra su navegador web de preferencia y diríjase a la barra de direcciones, introduciendo: `http://localhost:35000/`
4. Revise simultáneamente la pantalla del navegador (para visualizar el HTML renderizado) y la terminal donde se ejecuta el servidor (para examinar los headers que el navegador envió por debajo).

> **Espacio para Evidencia - Ejercicio 4.4:**
> [Inserte aquí una imagen compartida mostrando el navegador renderizando el título y la consola mostrando el bloque del GET request]
> ![Evidencia Ejercicio 4.4](./imagenes/ejercicio4.4.png)

---

## Ejercicio 4.5.1: Servidor Web Multipetición con Soporte para Archivos Estáticos

**Objetivo:** 
Evolucionar el servidor web estático hacia un servicio continuo capaz de analizar las rutas solicitadas dinámicamente, localizar los recursos físicos en el disco duro y retornar los bytes correspondientes junto con sus tipos MIME adecuados, manteniéndose en ejecución prolongada.

**Cómo funciona:** 
El código de `HttpServerMulti.java` incorpora el ciclo de vida de conexión dentro de una estructura `while(true)`, garantizando que el `ServerSocket` vuelva a ejecutar el método `accept()` inmediatamente tras finalizar una transacción, previniendo el apagado automático de la aplicación. Cuando recibe una petición HTTP, el servidor descompone (mediante la función `split`) la línea inicial (ej. `GET /recurso.png HTTP/1.1`) para aislar la ruta solicitada. Posteriormente, concatena esta ruta a un directorio interno denominado `public/` y emplea la API `java.nio.file.Files` para validar si el archivo existe en el sistema. De ser encontrado, el servidor lee los bytes en bruto, invoca un método interno para determinar la naturaleza del archivo basado en su extensión (otorgando el Content-Type correcto como `text/css`, `image/jpeg` o `text/html`), y finalmente despacha la respuesta íntegra a través del `OutputStream`. Si el archivo no existe, retorna graciosamente un error HTTP `404 Not Found`.

**Instrucciones de Ejecución:**
1. Sitúese en el directorio `ejercicio4.5.1` y asegúrese de que el código esté compilado: `javac HttpServerMulti.java`
2. Verifique la existencia de la carpeta `public` que contenga archivos de prueba (como `index.html` o recursos de imagen).
3. Inicie la ejecución del servidor: `java HttpServerMulti`
4. Abra su navegador web e ingrese a `http://localhost:35000/index.html`. 
5. Intente recargar la página numerosas veces o solicite recursos inexistentes para verificar cómo el servidor procesa y mantiene su estabilidad frente a peticiones consecutivas y errores 404.

> **Espacio para Evidencia - Ejercicio 4.5.1:**
> [Inserte aquí las capturas de la página web renderizada en el navegador y las salidas de consola que muestran las peticiones consecutivas gestionadas por el ciclo while]
> ![Evidencia Ejercicio 4.5.1](./imagenes/ejercicio4.5.1.png)

---

## Ejercicio 5.2.1: Sincronización Temporal Utilizando Datagramas UDP

**Objetivo:** 
Construir e interactuar con aplicaciones fundamentadas en el protocolo UDP (User Datagram Protocol). A diferencia de TCP, UDP no mantiene una conexión abierta, ni verifica el orden o la llegada de la información. El ejercicio busca crear resiliencia en un cliente frente a pérdidas de paquetes o caídas del servidor.

**Cómo funciona:** 
- **El Servidor (`DatagramTimeServer.java`):** Instancia un `DatagramSocket` escuchando en el puerto `4445`. Por medio de un ciclo infinito, aguarda pasivamente recibir un paquete (`DatagramPacket`). Al registrar la llegada de un paquete, extrae instantáneamente la dirección IP y el puerto de origen de quien lo envió, obtiene la hora actual del sistema mediante el objeto `Date`, la convierte en un arreglo de bytes, empaqueta la información en un nuevo `DatagramPacket` dirigido al remitente y lo envía.
- **El Cliente (`DatagramTimeClient.java`):** Implementa un ciclo controlado que envía una solicitud vacía hacia el puerto del servidor cada cinco segundos. La característica vital de este cliente es la invocación de `socket.setSoTimeout(2000)`. Al no ser una conexión TCP garantizada, si el servidor sufriera una falla crítica o una desconexión en medio de la transacción, el cliente quedaría bloqueado eternamente esperando un paquete que jamás llegará. El timeout establecido asegura que, tras dos segundos de espera infructuosa, el sistema lance una excepción `SocketTimeoutException`. El bloque `catch` atrapa dicha excepción, previene el cierre inesperado del programa y continúa mostrando en pantalla la última hora válida almacenada previamente en memoria, reanudando la operación regular cuando el servidor retome el servicio.

**Instrucciones de Ejecución:**
1. Navegue al directorio `ejercicio5.2.1` y compile ambos componentes: `javac DatagramTimeServer.java DatagramTimeClient.java`
2. En una terminal, inicie la ejecución del servidor de tiempo: `java DatagramTimeServer`
3. En una segunda terminal independiente, inicie el cliente: `java DatagramTimeClient`
4. Permita que el sistema sincronice el tiempo durante varias iteraciones.
5. Emule una interrupción abrupta finalizando el proceso del servidor (utilizando `Ctrl+C`). Observe el comportamiento resiliente de la terminal del cliente, el cual deberá notificar la incapacidad de conexión pero mantendrá la operación empleando su última lectura, sobreviviendo la caída. Inicie el servidor nuevamente para observar su recuperación automática.

> **Espacio para Evidencia - Ejercicio 5.2.1:**
> [Inserte aquí las capturas evidenciando el funcionamiento normal seguido de la captura mostrando el manejo de errores al desconectar el servidor]
> ![Evidencia Ejercicio 5.2.1](./imagenes/ejercicio5.2.1.png)

---

## Ejercicio 6.4.1: Sistema de Chat P2P Transparente Mediante Java RMI

**Objetivo:** 
Aplicar los conceptos avanzados de Remote Method Invocation (RMI) para abstraer por completo la complejidad intrínseca del envío de flujos de bytes sobre la red. RMI permite a un programa invocar métodos de objetos que residen en una Máquina Virtual de Java (JVM) distante como si fuesen invocaciones estrictamente locales.

**Cómo funciona:** 
El sistema abandona el paradigma clásico de un servidor central. La clase `ChatApp.java` ostenta un diseño Peer-to-Peer (P2P), actuando simultáneamente en dos roles indispensables:
- **Como Servidor:** Implementa la interfaz compartida `ChatService` que extiende a `java.rmi.Remote`. Durante la inicialización, la aplicación exporta su propia instancia local como un "stub" (objeto proxy encargado de la serialización) utilizando `UnicastRemoteObject.exportObject`. Acto seguido, genera programáticamente su propio registro (`LocateRegistry.createRegistry`) en un puerto local especificado dinámicamente por el usuario, y se vincula bajo el nombre "ChatService". 
- **Como Cliente:** Solicita interactivamente los datos del destinatario deseado (dirección IP y puerto). A través del método estático `LocateRegistry.getRegistry`, la aplicación rastrea la JVM remota y utiliza la función de búsqueda o `lookup` para localizar el objeto remoto bautizado como "ChatService".
- **El flujo de datos:** Una vez establecidas las interfaces, cuando el usuario escribe una cadena de texto en consola, el sistema no gestiona sockets manualmente. Simplemente ejecuta la sentencia de código `remoteService.receiveMessage(username, msg)`. El framework RMI de Java serializa los objetos por debajo, los transmite por la red, ejecuta el código en el computador remoto del destinatario de manera transparente y produce un resultado en su terminal en tiempo real.

**Instrucciones de Ejecución:**
*(Para este escenario, se aconseja el uso de dos consolas simulando entornos separados)*
1. Sitúese en el directorio `ejercicio6.4.1` y ejecute la compilación del paquete de clases: `javac ChatService.java ChatApp.java`
2. En su primera consola (representando a un usuario, por ejemplo, "Alice"), ejecute la aplicación: `java ChatApp`
   - **Parámetro 1 (Nombre):** Ingrese un nombre identificador (ej. Alice)
   - **Parámetro 2 (Puerto Local):** Digite un puerto disponible (ej. 23000)
   - **Parámetro 3 (IP Destino):** Escriba `127.0.0.1`
   - *Nota de Operación:* No presione la tecla de ingreso para el puerto de destino hasta que el segundo cliente esté en plena ejecución, o la aplicación reportará la incapacidad de conectarse.
3. En su segunda consola (representando a "Bob"), inicie una instancia paralela: `java ChatApp`
   - **Parámetro 1 (Nombre):** Ingrese un nombre identificador (ej. Bob)
   - **Parámetro 2 (Puerto Local):** Digite un puerto distinto para evitar colisiones (ej. 23001)
   - **Parámetro 3 (IP Destino):** Escriba `127.0.0.1`
   - **Parámetro 4 (Puerto Destino):** Ingrese el puerto designado para Alice (`23000`)
4. Regrese velozmente a la consola número uno y complete el registro ingresando el puerto asignado para Bob (`23001`).
5. Redacte e intercambie cadenas de texto entre ambas terminales. Observe cómo los parámetros complejos del tipo String viajan a través de los objetos abstractos RMI para invocar las notificaciones locales del otro entorno JVM de manera instantánea.

> **Espacio para Evidencia - Ejercicio 6.4.1:**
> [Inserte aquí capturas conjuntas de ambas consolas (Alice y Bob) compartiendo y respondiendo a los mensajes en tiempo real empleando la arquitectura RMI]
> ![Evidencia Ejercicio 6.4.1](./imagenes/ejercicio6.4.1.png)

---

**Proyecto Documentado para la Materia de Arquitectura de Software.**


Dog Social Network es una aplicación web desarrollada con Spring Boot, que permite a los usuarios registrarse, iniciar sesión y gestionar sus perfiles en una red social dedicada a los amantes de los perros. Este README documenta el estado actual del proyecto y cómo configurarlo para su ejecución.

## Características

- **Registro de Usuarios:** Los usuarios pueden registrarse proporcionando un nombre de usuario, correo electrónico y contraseña.
- **Inicio de Sesión:** Los usuarios registrados pueden iniciar sesión en la aplicación.
- **Gestión de Perfiles:** Los usuarios pueden ver y editar la información de su perfil.
- **Repositorios JPA:** El proyecto utiliza Spring Data JPA para interactuar con una base de datos PostgreSQL.
- **Controladores REST:** Implementación de controladores REST para gestionar las operaciones CRUD de usuarios.

## Estructura del Proyecto

El proyecto está organizado de la siguiente manera:

```
dog-social-network/
│
├── src/main/java/com/morales/dog_social_network/
│   ├── config/                # Configuraciones de seguridad (actualmente no utilizadas)
│   ├── controllers/           # Controladores REST
│   │   ├── UserController.java
│   │   └── WebController.java
│   ├── models/                # Modelos de datos
│   │   ├── User.java
│   ├── repositories/          # Repositorios JPA
│   │   └── UserRepository.java
│   ├── services/              # Servicios de negocio
│   │   └── UserService.java
│   └── DogSocialNetworkApplication.java # Clase principal
│
├── src/main/resources/
│   ├── templates/             # Plantillas Thymeleaf
│   │   ├── login.html
│   │   ├── register.html
│   │   └── profile.html
│   └── application.properties # Configuraciones de la aplicación
│
├── .gitignore                 # Archivos y carpetas ignorados por Git
├── pom.xml                    # Archivo de configuración de Maven
└── README.md                  # Documentación del proyecto
```

## Requisitos

- **Java 17** o superior
- **Maven 3.6.0** o superior
- **PostgreSQL 13** o superior

## Configuración de la Base de Datos

1. Asegúrate de tener PostgreSQL instalado y ejecutándose en tu máquina.
2. Crea una base de datos para la aplicación:
   ```sql
   CREATE DATABASE dog_social_network;
   ```
3. Configura las credenciales de acceso en el archivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/dog_social_network
   spring.datasource.username=postgres
   spring.datasource.password=tu_contraseña
   ```

## Compilación y Ejecución

1. Clona este repositorio en tu máquina local:
   ```bash
   git clone https://github.com/tu_usuario/dog-social-network.git
   cd dog-social-network
   ```

2. Compila y empaqueta la aplicación utilizando Maven:
   ```bash
   mvn clean install
   ```

3. Ejecuta la aplicación:
   ```bash
   java -jar target/dog-social-network-0.0.1-SNAPSHOT.jar
   ```

4. Accede a la aplicación desde tu navegador en `http://localhost:8080`.

## Uso

### Registro de Usuarios

Visita `http://localhost:8080/register` para crear una nueva cuenta.

### Inicio de Sesión

Visita `http://localhost:8080/login` para iniciar sesión con tu cuenta.

### Perfil de Usuario

Después de iniciar sesión, serás redirigido a tu perfil en `http://localhost:8080/profile`.

## Próximos Pasos

- Mejorar la experiencia del usuario mostrando mensajes de error cuando no se pueda iniciar sesión o registrar un usuario.
- Añadir funcionalidad para subir y visualizar fotos de perfil.
- Implementar la seguridad de la aplicación con Spring Security o una alternativa personalizada.

## Contribuciones

Las contribuciones son bienvenidas. Siéntete libre de abrir un pull request o reportar un problema en el repositorio.

## Licencia

Este proyecto está licenciado bajo la MIT License. Para más detalles, revisa el archivo [LICENSE](LICENSE).

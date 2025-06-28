# Proyecto: Transformación Digital - Perfulandia SPA

Este repositorio contiene el desarrollo técnico del sistema basado en microservicios para la empresa Perfulandia SPA, como parte de la Evaluación Parcial 2 de la asignatura Desarrollo Full Stack I.

## Descripción General del Proyecto

Perfulandia SPA, una empresa chilena de perfumería con varias sucursales, enfrentaba problemas de rendimiento, disponibilidad y escalabilidad con su antiguo sistema monolítico. El presente proyecto propone una solución basada en microservicios, lo que permite una mayor eficiencia, flexibilidad, tolerancia a fallos y facilidad de mantenimiento. Esta transformación digital mejora tanto la gestión interna como la experiencia del cliente final.

## Arquitectura de Microservicios

El sistema está estructurado en microservicios independientes, cada uno responsable de una funcionalidad específica. Estos servicios se comunican entre sí usando APIs REST, lo que permite escalar y desplegar cada módulo de forma aislada.

### Microservicios Desarrollados

- usuarioservice: Gestiona usuarios, incluyendo creación, autenticación y modificación.
- productoservice: Administra el catálogo de productos y el inventario.
- pedidoservice: Maneja los pedidos de los clientes y el cambio de estados.
- carritoservice: Permite la gestión de carritos de compra por parte de los clientes.

## Tecnologías Utilizadas

- Java 17
- Spring Boot
- MySQL (usando Laragon localmente)
- Docker (propuesto para contenedores)
- Kubernetes (propuesto para orquestación)
- Postman
- GitHub
- IntelliJ IDEA

## Configuración de Bases de Datos

Se utilizó MySQL ejecutado localmente mediante Laragon. Cada microservicio tiene su propia base de datos para lograr un bajo acoplamiento, mayor escalabilidad y autonomía.

- usuarioservice
- productoservice
- pedidoservice
- carritoservice

## Endpoints y Pruebas

Cada microservicio implementa endpoints REST para operaciones CRUD. Ejemplos:

usuarioservice:
- GET /usuarios
- POST /usuarios

productoservice:
- GET /productos
- POST /productos

pedidoservice:
- GET /pedidos
- POST /pedidos

carritoservice:
- GET /carrito
- POST /carrito

### Resultado de Pruebas con Postman

Se realizaron pruebas exitosas utilizando Postman para verificar el correcto funcionamiento de los endpoints CRUD de los microservicios pedidoservice y carritoservice, así como su integración.

**Microservicio: carritoservice**

- Prueba: Crear Carrito  
  Método: POST /api/carrito  
  Resultado: Carrito creado exitosamente (HTTP 201)

- Prueba: Agregar Producto al Carrito  
  Método: POST /api/carrito/{id}/productos  
  Resultado: Producto agregado correctamente

**Microservicio: pedidoservice**

- Prueba: Generar Pedido desde Carrito  
  Método: POST /api/pedidos/{clienteId}  
  Resultado: Pedido creado con estado GENERADO

- Prueba: Cambiar Estado del Pedido  
  Método: PUT /api/pedidos/{id}/estado  
  Resultado: Estado actualizado correctamente

## CI/CD (Integración y Entrega Continua)

Se utilizó GitHub Actions para automatizar los flujos de integración y despliegue:

1. Push al repositorio.
2. Ejecución de pruebas automatizadas.
3. Revisión de código mediante pull requests.
4. Pruebas del sistema completo antes de fusionar a producción.

## Integrantes del Equipo

| Nombre              | Rol en el proyecto                     | Servicio principal trabajado         |
|---------------------|----------------------------------------|--------------------------------------|
| Carlos Moil         | Líder del equipo                       | pedidoservice                        |
| Mayckol Mardones    | Encargado de Backend y Base de Datos   | carritoservice                       |
| Francisco Vera      | Backend y edición de documentación     | logisticaservice (eliminado)         |

## Estructura del Repositorio

```plaintext
perfulandia-microservices
├── .idea
├── carritoservice
│   └── src
│       └── pom.xml
├── pedidoservice
│   └── src
│       └── pom.xml
├── productservice
│   └── src
│       └── pom.xml
├── usuarioservice
│   └── src
│       └── pom.xml
└── README.md
```

Cada microservicio contiene su propio archivo `pom.xml`, configuración de base de datos, controladores REST, entidades y repositorios.

## Colaboración en GitHub

El equipo organizó el trabajo utilizando una rama principal (`master`) y ramas individuales por integrante:

- `master`: rama principal del repositorio, donde se integran los microservicios estables.
- `carlos-moil`: rama del microservicio pedidoservice.
- `mayckol-mardones`: rama del microservicio carritoservice.
- `francisco-vera`: rama del microservicio logisticaservice (ya eliminado).

Cada integrante trabajó de forma independiente en su rama. Luego, se realizaron pull requests hacia `master`, revisando los cambios y resolviendo conflictos si era necesario.

## Lecciones Aprendidas

Durante el desarrollo del proyecto aprendimos:

- A diseñar e implementar una arquitectura basada en microservicios desde cero.
- La importancia del trabajo colaborativo y el control de versiones con Git.
- Cómo aplicar pruebas efectivas de endpoints RESTful utilizando Postman.
- Beneficios de desacoplar servicios y cómo mejorar escalabilidad y mantenimiento.

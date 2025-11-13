# Gestor de Clientes – Aplicación CRM básica

**Materia:** PMDM  
**Curso:** 2º DAM  
**Autor:** Damián Carrillo Arjones  
**Lenguaje:** Kotlin + XML  
**Entorno:** Android Studio  

## Descripción general

Aplicación Android que permite registrar, buscar y gestionar clientes como si fuera un pequeño CRM.  
Incluye persistencia local con SQLite, RecyclerView, validaciones de formulario, y una interfaz moderna basada en Material Design.

## Funcionalidades principales

### Pantalla principal (MainActivity)
- Muestra todos los clientes almacenados en una lista (RecyclerView).
- Campo de búsqueda por nombre o correo (filtro en tiempo real).
- Contador de clientes totales.
- Botón flotante “+” para añadir un nuevo cliente.

### Formulario de cliente (EditClientActivity)
- Campos: nombre, email, teléfono.
- Validaciones:
  - Campos obligatorios.
  - Email con formato correcto.
  - Teléfono con mínimo 9 dígitos.
- Botón “Guardar” para insertar o actualizar.
- Al mantener pulsado sobre un cliente → AlertDialog de confirmación para eliminar.

## Persistencia local (SQLite)

La app usa una base de datos local mediante SQLiteOpenHelper:

```sql
CREATE TABLE clients (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    phone TEXT NOT NULL
);
```

- La clase `ClientsDbHelper` gestiona la creación y actualización del esquema.
- `ClientRepository` implementa el CRUD:
  - insert(client)
  - update(client)
  - delete(id)
  - getAll()
  - search(query)
  - count()

## Estructura del proyecto

```
com.example.crm
├── data
│   ├── Client.kt
│   ├── ClientRepository.kt
│   └── ClientsDbHelper.kt
├── ui
│   ├── ClientAdapter.kt
│   ├── MainActivity.kt
│   └── EditClientActivity.kt
└── util
    └── Validators.kt
```

Layouts XML:
```
res/layout/
├── activity_main.xml
├── activity_edit_client.xml
└── item_client.xml
```

## Dependencias clave

Declaradas en `app/build.gradle.kts`:

```kotlin
implementation("androidx.recyclerview:recyclerview:1.3.2")
implementation("com.google.android.material:material:1.12.0")
implementation("androidx.appcompat:appcompat:1.7.0")
implementation("androidx.activity:activity-ktx:1.9.3")
implementation("androidx.constraintlayout:constraintlayout:2.1.4")
```

## Instrucciones para ejecutar

1. Clonar o abrir el proyecto en Android Studio.
2. Verificar versión mínima de SDK (minSdk = 24).
3. Pulsar **Run ▶** para compilar y lanzar en un emulador o dispositivo físico.
4. Añadir, buscar, editar y eliminar clientes libremente.

## Capturas de pantalla

*(Inserta tus capturas aquí una vez ejecutada la app)*

| Pantalla Principal | Formulario Cliente |
|--------------------|--------------------|
| ![main](docs/main.png) | ![form](docs/form.png) |

## Objetivos de aprendizaje alcanzados

- Implementación de CRUD completo con SQLite.
- Uso de RecyclerView + Adapter personalizado.
- Validación de formularios y manejo de errores.
- Gestión de eventos (click, long click, AlertDialog).
- Documentación y presentación profesional del proyecto.

## Modelo de datos

Entidad principal: **Cliente**
| Campo | Tipo | Descripción |
|--------|------|-------------|
| id | INTEGER (PK) | Identificador único autoincremental |
| name | TEXT | Nombre del cliente |
| email | TEXT | Correo electrónico |
| phone | TEXT | Teléfono (mínimo 9 dígitos) |

## Estado del proyecto

✅ CRUD funcional  
✅ Persistencia SQLite  
✅ Búsqueda dinámica  
✅ AlertDialog de confirmación  
✅ Diseño Material3  
✅ README completo  

> Desarrollado en Kotlin como práctica evaluable de PMDM – 2º DAM.

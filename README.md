# ⚓ Carta Náutica Interactiva

Aplicación de escritorio en **Java y JavaFX** para practicar navegación marítima sobre una carta náutica. Permite dibujar sobre la carta, medir con regla y transportador, y poner a prueba lo aprendido con preguntas. Incluye registro e inicio de sesión de usuarios, con seguimiento de aciertos y fallos.

Proyecto realizado en grupo por Samuel, Jaime y Ángel para la asignatura de IPC.

## ✨ Características

### Carta náutica y herramientas de dibujo
- **Carta náutica** con desplazamiento y **zoom** (slider y botones +/−).
- **Transportador** y **regla** movibles sobre la carta, con tamaño ajustable. La regla también se puede **rotar** con el botón derecho del ratón.
- **Líneas** y **círculos** con color y grosor configurables.
- **Texto** sobre la carta, con tamaño y color a elegir.
- **Marcas en X** para señalar puntos.
- **Brocha** para cambiar el color de los elementos ya dibujados.
- **Goma de borrar** y botón para **borrar todo**.
- **Coordenadas:** al hacer clic se trazan líneas guía horizontal y vertical.
- Indicador de la posición del ratón.

### Usuarios y preguntas
- **Registro** e **inicio de sesión** de usuarios.
- **Modificar perfil**, con avatar.
- **Problemas de navegación** con preguntas sobre la carta.
- **Historial de resultados**: aciertos y fallos por sesión.

## 🛠️ Tecnologías

- Java
- JavaFX (interfaces en FXML con hojas de estilo CSS)
- SQLite (persistencia de usuarios y sesiones)
- NetBeans / Apache Ant

## 📁 Estructura del proyecto

```
├── nbproject/            # Configuración del proyecto de NetBeans
├── build.xml
├── lib/
│   ├── IPC.jar           # Librería de la asignatura (modelo de datos)
│   └── sqlite.jar        # Driver JDBC de SQLite
└── src/
    ├── resources/        # Imágenes, iconos y estilos
    └── poiupv/           # Código fuente
        ├── PoiUPVApp.java                    # Clase principal
        ├── FXMLDocument*.fxml / Controller   # Carta náutica y herramientas
        ├── FXMLLogIn*                        # Inicio de sesión
        ├── FXMLRegister*                     # Registro
        ├── FXMLMenu*                         # Menú principal
        ├── FXMLModificarPerfil*              # Edición de perfil
        ├── FXMLlistapreguntas*, FXMLpreguntas* # Preguntas y problemas
        ├── FXMLMostrarResultados*            # Historial de resultados
        ├── Persona.java                      # Usuario en sesión
        └── Poi.java                          # Puntos de interés
```

## ⚙️ Requisitos y ejecución

- JDK 21 o superior
- JavaFX SDK (el proyecto usa la versión 23)
- NetBeans (recomendado)

1. Clona el repositorio:
   ```bash
   git clone https://github.com/SamuelPelegero/<nombre-del-repo>.git
   ```
2. Abre la carpeta como proyecto en NetBeans (**File → Open Project**).
3. Configura la librería de JavaFX en las propiedades del proyecto y comprueba que `lib/IPC.jar` y `lib/sqlite.jar` están en el classpath.
4. Ejecuta la clase principal `poiupv.PoiUPVApp`.


## 👥 Autores

- Samuel Pelegero
- Jaime
- Ángel

## 📄 Licencia

Proyecto académico.

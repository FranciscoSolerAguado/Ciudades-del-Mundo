# 📱 Ciudades del Mundo – Aplicación Android

**CiudadesSensores** es una aplicación Android desarrollada en Kotlin que permite explorar diferentes ciudades españolas, visualizar información asociada, acceder a sensores del dispositivo, y mostrar ubicaciones en un mapa.

La app está diseñada con un enfoque educativo, integrando uso de listas, adaptadores personalizados, actividades múltiples, sensores y navegación WebView.

---

## 🚀 Funcionalidades principales

### 🏙️ Listado de ciudades
- Muestra una lista de ciudades españolas con imágenes asociadas.
- Implementación mediante `RecyclerView` y adaptadores personalizados (`CiudadAdapter`, `CiudadEspAdapter`).
- Al seleccionar una ciudad se puede acceder a más información o funciones adicionales.

### 🗺️ Visualización en mapa
- Actividad dedicada `MapActivity`.
- Muestra la localización de una ciudad en un mapa (Google Maps API).

### 🧭 Uso de sensores del dispositivo
- Actividad `SensorActivity`.
- Acceso a sensores del móvil (por ejemplo, luz, acelerómetro, proximidad, etc.).
- Lectura en tiempo real y visualización sencilla para el usuario.

### 🌐 Navegador integrado
- `BrowserActivity` permite abrir contenido web dentro de la aplicación mediante `WebView`.

### 🧩 Arquitectura
- Proyecto basado en Android Studio con Kotlin.
- Navegación simple entre actividades con `Intent`.
- Patrón claro de separación de datos mediante la clase modelo `Ciudad.kt`.

---

## 🗂️ Estructura destacada del proyecto


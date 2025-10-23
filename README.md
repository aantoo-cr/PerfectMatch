# PerfectMatch

Implementación simplificada del módulo de perfilado solicitado.

- `backend/`: Aplicación Spring Boot 3 (Java 17) con entidades y DTOs usando la nomenclatura solicitada (`per`, `cue`, `pre`, `alt`, `res`, `ubi`).
  - Incluye controladores REST para perfiles, cuestionarios, preguntas, respuestas y ubicaciones.
  - Al crear un cuestionario se cargan automáticamente las preguntas y alternativas indicadas en el enunciado.
  - Base de datos en memoria H2 con datos iniciales de ubicaciones.
- `frontend/`: Código base de Angular standalone (>=17) para el flujo de creación de perfil, carga de foto, selección de ubicación y contestación del cuestionario.

## Backend

```bash
cd backend
mvn spring-boot:run
```

## Frontend

Integra el directorio `frontend/` en tu proyecto Angular existente e importa la ruta `ONBOARDING_ROUTES` dentro del módulo de rutas principal.

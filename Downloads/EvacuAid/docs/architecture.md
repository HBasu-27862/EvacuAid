# Architecture

EvacuAid is a single Spring Boot application. Spring serves the HTML, CSS and vanilla JavaScript from `src/main/resources/static` and exposes JSON APIs below `/api`. The browser uses those APIs for all building, routing, blockage and history data.

## Layers

- `api`: REST controller, request/response records, JSON layout import/export, and consistent input errors.
- `service`: transactional application operations and event recording.
- `repo`: Spring Data JPA persistence interfaces.
- `model`: persisted buildings, typed building places, passages, and simulation events.
- `graph`: in-memory adjacency-list projection and Dijkstra route search.

## Persistence

H2 runs in file mode at `./data/evacuaid`, so edits remain after restart. Hibernate creates/updates the schema for this student project. The H2 console is at `/h2-console` (JDBC URL `jdbc:h2:file:./data/evacuaid`, user `sa`, blank password). Do not expose the console publicly.

## Main API

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/buildings` | List saved buildings |
| GET | `/api/buildings/{id}` | Read one building graph |
| POST | `/api/buildings` | Create an empty building |
| POST | `/api/buildings/sample` | Load the demonstration layout once |
| DELETE | `/api/buildings/{id}` | Delete a building |
| POST | `/api/buildings/{id}/route` | Find up to six available routes |
| PATCH | `/api/buildings/{id}/passages/{code}` | Block or restore a passage |
| GET | `/api/history` | Read recent simulation events |
| GET | `/api/buildings/{id}/export` | Download layout JSON |
| POST | `/api/buildings/import` | Import validated JSON multipart file |

Routes carry total abstract cost units. This demo does not convert them into time or claim that they are measured real-world distances.

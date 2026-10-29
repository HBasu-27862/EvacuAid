# EvacuAid — Emergency Evacuation Route Planning Simulator

EvacuAid is a student project that models a building as rooms, junctions, staircases, exits and weighted passages. Java calculates routes with Dijkstra’s algorithm and recalculates after a passage is blocked. The responsive HTML/CSS/vanilla JavaScript interface talks to the Spring Boot REST API. H2 stores buildings and simulation events locally.

> **Educational simulator only.** This software is not certified for real emergency guidance. In an actual emergency, follow posted building procedures and instructions from emergency personnel.

## Requirements

- Java Development Kit (JDK) 21
- Internet access on first run so Maven Wrapper can download Maven 3.9.9
- VS Code (optional; any editor/browser works)

No Node.js, separate database, paid API or credentials are needed.

## Run on Windows

1. Extract the project ZIP.
2. Open the extracted `EvacuAid` folder in VS Code.
3. Open **Terminal → New Terminal** in VS Code.
4. Run `.\mvnw.cmd spring-boot:run` from the folder containing `pom.xml` (PowerShell). The first run downloads Maven; later runs reuse the local cache.
5. Wait for Spring Boot to report that the application started, then open [http://localhost:8080](http://localhost:8080).
6. Click **Load sample building**.

To run tests, use `.\mvnw.cmd test`. To create a runnable package, use `.\mvnw.cmd clean package`; then launch `java -jar target/evacuaid-1.0.0.jar`.

## Quick feature tour

- Load the sample academic building with two exits and interconnected routes.
- Switch between the sample's connected floors using the floor selector.
- Upload a PNG/JPEG blueprint per floor; add locations, drag their markers into place, and connect passages for route calculations.
- Choose a room and either a specific exit or the best reachable exit.
- Compare the calculated route alternatives and select one to highlight it.
- Block or restore passages; route requests omit blocked edges.
- Select a fire origin and spread-step count; hazardous passages are excluded and routes recalculate.
- Export a layout as JSON or import a validated JSON layout.
- Review recent route, blockage and restoration events.
- Building and event data are stored in `./data/` and survive app restarts.

## API examples

```http
POST /api/buildings/sample
POST /api/buildings/1/route
Content-Type: application/json

{"startCode":"LAB","exitCode":null,"automaticExit":true}
```

```http
PATCH /api/buildings/1/passages/P4
Content-Type: application/json

{"blocked":true}
```

Blueprint images are uploaded to `/api/buildings/{id}/floors/{floor}/blueprint` as multipart field `file`. Fire scenarios use `POST /api/buildings/{id}/fire-scenario` with `originCode`, `startCode`, `exitCode`, `automaticExit`, and `spreadSteps`.

See [architecture.md](docs/architecture.md), [algorithms.md](docs/algorithms.md), [oop-concepts.md](docs/oop-concepts.md), and [demo-guide.md](docs/demo-guide.md) for the API, data model, algorithms and presentation outline.

## Database

H2 file-mode database: `./data/evacuaid`. For the local H2 console, open `http://localhost:8080/h2-console` and connect with JDBC URL `jdbc:h2:file:./data/evacuaid`, username `sa`, and a blank password. Keep the console local; it is enabled to make this learning project easier to inspect.

## Tests and known scope

JUnit tests cover Dijkstra route cost and reconstruction, blocked edge avoidance, unreachable destinations, distinct alternatives, layout import/export, fire hazard rerouting, cross-floor routes, and Spring application startup. Alternative routes use an iterative edge exclusion heuristic, documented in `docs/algorithms.md`; it is not a complete K-shortest-path enumeration for every graph. The fire scenario is an abstract graph-hop exercise, not a physical fire or smoke simulation. Uploaded blueprints are visual backdrops: locations and passages must be mapped manually. Abstract route costs are not real-world travel times. Authentication and multi-user access are outside this single-user academic demo.

## Project structure

```text
src/main/java/edu/evacuaid/  Java application, API, services, JPA models and graph
src/main/resources/static/   Single-page HTML, CSS and browser JavaScript
src/test/java/               JUnit tests
docs/                        Academic and demonstration documentation
```

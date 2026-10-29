# Java OOP demonstration guide

| Concept | Where to show it |
|---|---|
| Encapsulation | `Building`, `Place`, `Passage`, and `SimulationEvent` keep fields private and expose controlled methods. `Building.addPlace` and `addPassage` maintain both sides of an association. |
| Abstraction | `Place` is abstract and declares `getType()`; a place is only instantiated through a concrete domain type. |
| Inheritance | `Room`, `Junction`, `Staircase`, and `EmergencyExit` extend `Place`. JPA stores them with a discriminator in one table. |
| Polymorphism | `EvacuationService` treats all subtypes as `Place`; overridden `getType()` and subtype checks use the actual runtime class. |
| Collections | `List` stores building entities and candidate routes; `Map` represents graph adjacency and distances; `Set` tracks visited vertices and excluded passages; `PriorityQueue` chooses the next cheapest graph vertex. |
| Exception handling | `ApiExceptionHandler` converts missing resources and invalid input into 404 and 400 JSON responses. Import reports malformed layouts without swallowing the error. |
| File handling | `LayoutFileService` uses Jackson and `MultipartFile` streams to export/import JSON, limits file size, and validates unique codes, endpoints, and positive weights. |
| Graph algorithm | `RouteFinder.shortest` runs Dijkstra against currently open passages and reconstructs the path from predecessor links. |

## Suggested oral explanation

“A building is persisted as places and weighted passages. The graph service creates an adjacency list for each route request. It skips every blocked passage, then Dijkstra’s priority queue visits the least costly known location first. A predecessor map reconstructs the route. The browser only draws the route returned by the Java backend.”

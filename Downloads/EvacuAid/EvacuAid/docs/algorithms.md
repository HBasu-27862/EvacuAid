# Graph algorithms

## Model

Each room, junction, staircase and exit is a vertex identified by a unique code within its building. A passage is an undirected weighted edge whose cost is a positive abstract unit. The graph is an adjacency list (`Map<String,List<Step>>`). A blocked edge is omitted when the graph is projected for a route request, so it cannot appear in the answer.

## Dijkstra

`RouteFinder.shortest` initializes the source cost to zero, keeps best known distances and predecessors, and uses a min-priority queue. It relaxes each open adjacent edge and reconstructs the destination route by following predecessors backwards. Unknown endpoints and disconnected destinations return no route.

With adjacency lists and a binary heap, the expected complexity is `O((V + E) log V)` and storage is `O(V + E)`.

## Alternatives

`RouteFinder.alternatives` repeatedly excludes a still-open edge and asks Dijkstra for the best candidate path under that exclusion, then selects the least-cost unseen candidate. This produces genuine distinct, loop-free shortest-path results for the sample graph and avoids fabricated alternatives. It is a compact heuristic, not Yen’s full K-shortest loopless-path algorithm; it may not enumerate every possible alternative in a complex graph. Each candidate search is `O((V + E) log V)`, repeated across edge exclusions up to the requested limit.

## Safety behavior

If no path remains after blocked passages are excluded, the API returns an empty route list. The user interface displays a no-route warning. The sample is for education only; real occupants must follow official local instructions.

## Fire-origin scenario

The user selects an origin location and chooses 0–8 spread steps. Breadth-first traversal expands over connected passages, one graph hop per step. Passages incident to reached locations are marked hazardous. Dijkstra and alternative-route searches exclude both manually closed and scenario-hazard passages. This model does not simulate heat, smoke, ventilation, fire doors, or physical time and must not guide real emergencies.

## Multiple floors

Each location stores a floor number. A passage connecting locations on different floors represents a stair or other vertical connection. Dijkstra can route through those links. The map displays one floor at a time, while the route list labels each location with its floor.

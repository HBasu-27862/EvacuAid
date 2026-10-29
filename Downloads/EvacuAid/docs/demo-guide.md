# University demonstration guide

1. Start the application and open `http://localhost:8080`.
2. Select **Load sample building**. Point out rooms, junctions, protected stair, two exits, and the connecting passages.
3. Select **Computer Lab** and calculate the best reachable exit. Show the route sequence and abstract cost.
4. Select an alternative in the results list to highlight that backend-computed route.
5. Block a passage on the current route. Recalculate and show that the highlighted route avoids it.
6. Block several passages until the requested exit cannot be reached. The simulator should report no safe available route rather than draw a blocked path.
7. Restore the passages, export the building as JSON, and import the file to create a copy.
8. Show recent activity to demonstrate persistent route and blockage history.
9. In the source, explain `Place` inheritance, encapsulation in `Passage`, collections in `RouteFinder`, and Dijkstra's priority queue.

This is an academic simulation, not a substitute for official evacuation plans or emergency services.

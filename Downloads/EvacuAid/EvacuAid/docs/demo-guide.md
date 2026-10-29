# University demonstration guide

1. Start the application and open `http://localhost:8080`.
2. Select **Load sample building**. Switch between its two floors and point out the stair connection, exits, and passages.
3. Select **Computer Lab** and calculate the best reachable exit. Show the route sequence and abstract cost.
4. Select an alternative in the results list to highlight that backend-computed route.
5. Block a passage on the current route. Recalculate and show that the highlighted route avoids it.
6. Block several passages until the requested exit cannot be reached. The simulator should report no safe available route rather than draw a blocked path.
7. Choose a fire origin and spread steps, then run the scenario. Show hazardous passages and route recalculation.
8. Upload a PNG/JPEG blueprint for the selected floor. Add locations and passages to build its graph overlay.
9. Export the layout as JSON, import it to create a copy, and show recent simulation history.
10. In the source, explain `Place` inheritance, `FloorBlueprint`, `FireScenario`, graph collections, and Dijkstra's priority queue.

Blueprint images are visual references; graph locations and passages must be mapped manually. The fire model operates on graph connections and is not a substitute for official evacuation plans or emergency services.

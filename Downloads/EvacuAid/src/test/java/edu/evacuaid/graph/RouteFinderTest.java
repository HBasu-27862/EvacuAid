package edu.evacuaid.graph;

import edu.evacuaid.model.*;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class RouteFinderTest {
 private Building graph(){Building b=new Building("Test","graph");b.addPlace(new Room("A","Start",0,0));b.addPlace(new Junction("B","Junction",1,0));b.addPlace(new Junction("C","Junction 2",1,1));b.addPlace(new EmergencyExit("X","Exit X",2,0));b.addPlace(new EmergencyExit("Y","Exit Y",2,1));b.addPassage(new Passage("AB","A","B",2));b.addPassage(new Passage("BX","B","X",2));b.addPassage(new Passage("AC","A","C",3));b.addPassage(new Passage("CY","C","Y",1));b.addPassage(new Passage("CB","C","B",1));return b;}
 @Test void shortestWeightedRouteIsReconstructed(){var r=new RouteFinder().shortest(graph(),"A","X",Set.of()).orElseThrow();assertEquals(java.util.List.of("A","B","X"),r.nodes());assertEquals(4,r.cost());}
 @Test void blockedPassageIsNeverUsedAndAlternativeIsFound(){Building b=graph();b.getPassages().stream().filter(e->e.getCode().equals("AB")).findFirst().orElseThrow().setBlocked(true);var r=new RouteFinder().shortest(b,"A","X",Set.of()).orElseThrow();assertTrue(r.nodes().contains("C"));assertFalse(r.nodes().indexOf("A")+1==r.nodes().indexOf("B"));}
 @Test void disconnectedGraphReturnsEmpty(){Building b=graph();b.getPassages().forEach(e->e.setBlocked(true));assertTrue(new RouteFinder().shortest(b,"A","X",Set.of()).isEmpty());}
 @Test void alternativeFinderProducesDistinctPaths(){var paths=new RouteFinder().alternatives(graph(),"A","X",3);assertTrue(paths.size()>=2);assertNotEquals(paths.get(0).nodes(),paths.get(1).nodes());}
}

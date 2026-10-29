package edu.evacuaid.graph;

import edu.evacuaid.model.*;
import java.util.*;

/** Weighted adjacency-list routing. Blocked passages are deliberately omitted. */
public class RouteFinder {
 public record Route(List<String> nodes,double cost) {}
 private record Step(String code,double cost) {}
 private record Candidate(String code,double distance) {}
 public Optional<Route> shortest(Building building,String start,String end,Set<String> excludedEdges){
  Map<String,List<Step>> graph=new HashMap<>(); building.getPlaces().forEach(p->graph.put(p.getCode(),new ArrayList<>()));
  for(Passage e:building.getPassages()) if(!e.isBlocked()&&!excludedEdges.contains(e.getCode())){
   if(!graph.containsKey(e.getFromCode())||!graph.containsKey(e.getToCode())) continue;
   graph.get(e.getFromCode()).add(new Step(e.getToCode(),e.getCost())); graph.get(e.getToCode()).add(new Step(e.getFromCode(),e.getCost()));
  }
  if(!graph.containsKey(start)||!graph.containsKey(end)) return Optional.empty();
  Map<String,Double> dist=new HashMap<>(); Map<String,String> prev=new HashMap<>(); Set<String> visited=new HashSet<>();
  PriorityQueue<Candidate> q=new PriorityQueue<>(Comparator.comparingDouble(Candidate::distance)); dist.put(start,0d); q.add(new Candidate(start,0));
  while(!q.isEmpty()) { Candidate cur=q.poll(); if(!visited.add(cur.code()))continue; if(cur.code().equals(end))break;
   for(Step step:graph.get(cur.code())) { double next=cur.distance()+step.cost(); if(next<dist.getOrDefault(step.code(),Double.POSITIVE_INFINITY)){dist.put(step.code(),next);prev.put(step.code(),cur.code());q.add(new Candidate(step.code(),next));} }
  }
  if(!dist.containsKey(end))return Optional.empty(); List<String> nodes=new ArrayList<>(); String at=end; nodes.add(at);
  while(!at.equals(start)){at=prev.get(at);if(at==null)return Optional.empty();nodes.add(at);} Collections.reverse(nodes); return Optional.of(new Route(nodes,dist.get(end)));
 }
 /** Practical K-path variant: iteratively exclude each edge of accepted paths, then sort/deduplicate. */
 public List<Route> alternatives(Building b,String start,String end,int limit){
  return alternatives(b,start,end,limit,Set.of());
 }
 public List<Route> alternatives(Building b,String start,String end,int limit,Set<String> initiallyExcluded){
  List<Route> found=new ArrayList<>(); Set<String> signatures=new HashSet<>(); Set<String> excluded=new LinkedHashSet<>(initiallyExcluded);
  shortest(b,start,end,excluded).ifPresent(r->{found.add(r);signatures.add(String.join("/",r.nodes()));});
  while(found.size()<limit){ Route best=null; String edgeToRemove=null;
   for(Passage e:b.getPassages()) if(!e.isBlocked()&&!excluded.contains(e.getCode())){
    Optional<Route> candidate=shortest(b,start,end,union(excluded,e.getCode()));
    if(candidate.isPresent()&&!signatures.contains(String.join("/",candidate.get().nodes()))&&(best==null||candidate.get().cost()<best.cost())){best=candidate.get();edgeToRemove=e.getCode();}
   }
   if(best==null)break; found.add(best);signatures.add(String.join("/",best.nodes()));excluded.add(edgeToRemove);
  }
  return found;
 }
 private Set<String> union(Set<String> s,String e){Set<String> n=new HashSet<>(s);n.add(e);return n;}
}

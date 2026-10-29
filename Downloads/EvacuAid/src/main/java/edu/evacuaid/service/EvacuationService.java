package edu.evacuaid.service;

import edu.evacuaid.api.ApiDtos.*; import edu.evacuaid.graph.RouteFinder; import edu.evacuaid.graph.RouteFinder.Route;
import edu.evacuaid.model.*; import edu.evacuaid.repo.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class EvacuationService {
 private final BuildingRepository buildings; private final SimulationEventRepository events; private final RouteFinder finder=new RouteFinder();
 public EvacuationService(BuildingRepository b,SimulationEventRepository e){buildings=b;events=e;}
 @Transactional(readOnly=true) public List<BuildingDto> all(){return buildings.findAll().stream().map(this::dto).toList();}
 @Transactional(readOnly=true) public BuildingDto get(long id){return dto(require(id));}
 @Transactional public BuildingDto create(CreateBuilding request){return dto(buildings.save(new Building(request.name(),request.description())));}
 @Transactional public BuildingDto update(long id,CreateBuilding request){Building b=require(id);b.setName(request.name());b.setDescription(request.description());return dto(b);}
 @Transactional public BuildingDto addPlace(long id,PlaceInput input){Building b=require(id);if(b.getPlaces().stream().anyMatch(p->p.getCode().equalsIgnoreCase(input.code())))throw new IllegalArgumentException("That location code already exists.");Place p=switch(input.type().toUpperCase()){case "ROOM"->new Room(input.code(),input.label(),input.x(),input.y());case "JUNCTION"->new Junction(input.code(),input.label(),input.x(),input.y());case "STAIR"->new Staircase(input.code(),input.label(),input.x(),input.y());case "EXIT"->new EmergencyExit(input.code(),input.label(),input.x(),input.y());default->throw new IllegalArgumentException("Type must be ROOM, JUNCTION, STAIR, or EXIT.");};b.addPlace(p);return dto(b);}
 @Transactional public BuildingDto addPassage(long id,PassageInput input){Building b=require(id);Set<String> codes=new HashSet<>();b.getPlaces().forEach(p->codes.add(p.getCode()));if(!codes.contains(input.fromCode())||!codes.contains(input.toCode()))throw new IllegalArgumentException("Passage endpoints must be existing location codes.");if(input.fromCode().equals(input.toCode())||b.getPassages().stream().anyMatch(p->p.getCode().equalsIgnoreCase(input.code())))throw new IllegalArgumentException("Passage code must be unique and endpoints must differ.");b.addPassage(new Passage(input.code(),input.fromCode(),input.toCode(),input.cost()));return dto(b);}
 @Transactional public void delete(long id){buildings.delete(require(id));}
 @Transactional public BuildingDto sample(){return buildings.findAll().stream().filter(b->b.getName().equals("Northstar Learning Centre")).findFirst().map(this::dto).orElseGet(()->{
  Building b=new Building("Northstar Learning Centre","Sample two-wing academic building");
  b.addPlace(new Room("LAB","Computer Lab",100,100));b.addPlace(new Room("LIB","Library",100,300));b.addPlace(new Room("OFF","Faculty Office",100,500));
  b.addPlace(new Junction("J1","North Junction",320,180));b.addPlace(new Junction("J2","Central Junction",500,320));b.addPlace(new Junction("J3","South Junction",320,470));b.addPlace(new Junction("J4","East Junction",700,320));
  b.addPlace(new EmergencyExit("EXIT-A","West Exit",850,150));b.addPlace(new EmergencyExit("EXIT-B","East Exit",850,500));b.addPlace(new Staircase("STAIR","Protected Stair",500,500));
  String[][] es={{"P1","LAB","J1","3"},{"P2","LIB","J1","2"},{"P3","OFF","J3","3"},{"P4","J1","J2","3"},{"P5","J2","J3","2"},{"P6","J2","J4","3"},{"P7","J3","STAIR","2"},{"P8","STAIR","J4","3"},{"P9","J4","EXIT-A","2"},{"P10","J4","EXIT-B","2"},{"P11","J1","EXIT-A","8"},{"P12","J3","EXIT-B","7"}};
  for(String[] e:es)b.addPassage(new Passage(e[0],e[1],e[2],Double.parseDouble(e[3])));return dto(buildings.save(b));});}
 @Transactional public PassageDto block(long id,String code,boolean blocked){Building b=require(id);Passage p=b.getPassages().stream().filter(x->x.getCode().equals(code)).findFirst().orElseThrow(()->new IllegalArgumentException("Passage not found: "+code));p.setBlocked(blocked);events.save(new SimulationEvent(id,blocked?"BLOCK":"RESTORE",null,null,null,code));return new PassageDto(p.getCode(),p.getFromCode(),p.getToCode(),p.getCost(),p.isBlocked());}
 @Transactional public List<RouteDto> route(long id,RouteRequest req){Building b=require(id);if(b.getPlaces().stream().noneMatch(p->p.getCode().equals(req.startCode())))throw new IllegalArgumentException("Starting location was not found.");
  List<Place> exits=b.getPlaces().stream().filter(EmergencyExit.class::isInstance).filter(p->req.automaticExit()||p.getCode().equals(req.exitCode())).toList();
  List<Route> routes=new ArrayList<>();for(Place exit:exits)routes.addAll(finder.alternatives(b,req.startCode(),exit.getCode(),3));
  routes.sort(Comparator.comparingDouble(Route::cost));List<RouteDto> result=routes.stream().limit(6).map(r->new RouteDto(r.nodes(),r.cost(),r.nodes().get(r.nodes().size()-1))).toList();
  String flat=result.isEmpty()?"":String.join(";",result.stream().map(r->String.join(",",r.nodes())).toList());events.save(new SimulationEvent(id,result.isEmpty()?"NO_ROUTE":"ROUTE",req.startCode(),result.isEmpty()?null:result.get(0).exitCode(),result.isEmpty()?null:result.get(0).cost(),flat));return result;
 }
 @Transactional(readOnly=true) public List<SimulationEvent> history(){return events.findTop100ByOrderByCreatedAtDesc();}
 private Building require(long id){return buildings.findById(id).orElseThrow(()->new NoSuchElementException("Building not found: "+id));}
 private BuildingDto dto(Building b){return new BuildingDto(b.getId(),b.getName(),b.getDescription(),b.getPlaces().stream().map(p->new PlaceDto(p.getCode(),p.getLabel(),p.getType(),p.getX(),p.getY())).toList(),b.getPassages().stream().map(p->new PassageDto(p.getCode(),p.getFromCode(),p.getToCode(),p.getCost(),p.isBlocked())).toList());}
}

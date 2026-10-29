package edu.evacuaid.api;

import com.fasterxml.jackson.databind.*; import edu.evacuaid.api.ApiDtos.*; import edu.evacuaid.model.*; import edu.evacuaid.repo.BuildingRepository;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import org.springframework.web.multipart.MultipartFile;
import java.io.IOException; import java.util.*;

@Service public class LayoutFileService {
 private final ObjectMapper mapper; private final BuildingRepository repo;
 public LayoutFileService(ObjectMapper mapper,BuildingRepository repo){this.mapper=mapper;this.repo=repo;}
 public byte[] export(BuildingDto dto)throws IOException{return mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(dto);}
 @Transactional public BuildingDto importLayout(MultipartFile file)throws IOException{
  if(file.isEmpty()||file.getSize()>1_000_000)throw new IllegalArgumentException("Choose a JSON file smaller than 1 MB.");
  JsonNode root;try{root=mapper.readTree(file.getInputStream());}catch(Exception e){throw new IllegalArgumentException("The uploaded file is not valid JSON.");}
  String name=root.path("name").asText("");String description=root.path("description").asText("");if(name.isBlank())throw new IllegalArgumentException("Building name is required.");
  List<PlaceDto> places=mapper.convertValue(root.path("places"),mapper.getTypeFactory().constructCollectionType(List.class,PlaceDto.class));
  List<PassageDto> passages=mapper.convertValue(root.path("passages"),mapper.getTypeFactory().constructCollectionType(List.class,PassageDto.class));
  if(places==null||passages==null||places.isEmpty())throw new IllegalArgumentException("Layout must include places and passages.");
  Set<String> codes=new HashSet<>();for(PlaceDto p:places)if(p.code()==null||!codes.add(p.code()))throw new IllegalArgumentException("Place codes must be present and unique.");
  Set<String> edgeCodes=new HashSet<>();for(PassageDto p:passages)if(p.code()==null||!edgeCodes.add(p.code())||!codes.contains(p.fromCode())||!codes.contains(p.toCode())||p.cost()<=0||!Double.isFinite(p.cost()))throw new IllegalArgumentException("Passage codes, endpoints, and positive costs must be valid.");
  Building b=new Building(name,description);for(PlaceDto p:places)b.addPlace(switch(p.type()==null?"ROOM":p.type().toUpperCase()){case "EXIT"->new EmergencyExit(p.code(),p.label(),p.x(),p.y());case "JUNCTION"->new Junction(p.code(),p.label(),p.x(),p.y());case "STAIR"->new Staircase(p.code(),p.label(),p.x(),p.y());default->new Room(p.code(),p.label(),p.x(),p.y());});
  for(PassageDto p:passages){Passage edge=new Passage(p.code(),p.fromCode(),p.toCode(),p.cost());edge.setBlocked(p.blocked());b.addPassage(edge);}b=repo.save(b);
  return new BuildingDto(b.getId(),b.getName(),b.getDescription(),b.getPlaces().stream().map(p->new PlaceDto(p.getCode(),p.getLabel(),p.getType(),p.getX(),p.getY())).toList(),b.getPassages().stream().map(p->new PassageDto(p.getCode(),p.getFromCode(),p.getToCode(),p.getCost(),p.isBlocked())).toList());
 }
}

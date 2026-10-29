package edu.evacuaid.api;

import edu.evacuaid.api.ApiDtos.*;
import edu.evacuaid.model.SimulationEvent;
import edu.evacuaid.service.EvacuationService;
import edu.evacuaid.service.BlueprintService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController @RequestMapping("/api")
public class EvacuAidController {
 private final EvacuationService service; private final LayoutFileService files; private final BlueprintService blueprints;
 public EvacuAidController(EvacuationService service,LayoutFileService files,BlueprintService blueprints){this.service=service;this.files=files;this.blueprints=blueprints;}
 @GetMapping("/buildings") public List<BuildingDto> buildings(){return service.all();}
 @GetMapping("/buildings/{id}") public BuildingDto building(@PathVariable long id){return service.get(id);}
 @PostMapping("/buildings") public ResponseEntity<BuildingDto> create(@Valid @RequestBody CreateBuilding request){return ResponseEntity.status(201).body(service.create(request));}
 @PutMapping("/buildings/{id}") public BuildingDto update(@PathVariable long id,@Valid @RequestBody CreateBuilding request){return service.update(id,request);}
 @PostMapping("/buildings/{id}/places") public BuildingDto addPlace(@PathVariable long id,@Valid @RequestBody PlaceInput input){return service.addPlace(id,input);}
 @PatchMapping("/buildings/{id}/places/{code}") public BuildingDto positionPlace(@PathVariable long id,@PathVariable String code,@Valid @RequestBody PositionInput input){return service.positionPlace(id,code,input);}
 @PostMapping("/buildings/{id}/passages") public BuildingDto addPassage(@PathVariable long id,@Valid @RequestBody PassageInput input){return service.addPassage(id,input);}
 @DeleteMapping("/buildings/{id}") public ResponseEntity<Void> delete(@PathVariable long id){service.delete(id);return ResponseEntity.noContent().build();}
 @PostMapping("/buildings/sample") public BuildingDto sample(){return service.sample();}
 @PostMapping("/buildings/{id}/route") public List<RouteDto> route(@PathVariable long id,@Valid @RequestBody RouteRequest request){return service.route(id,request);}
 @PostMapping("/buildings/{id}/fire-scenario") public FireScenarioResult fire(@PathVariable long id,@Valid @RequestBody FireScenarioRequest request){return service.simulateFire(id,request);}
 @DeleteMapping("/buildings/{id}/fire-scenario") public ResponseEntity<Void> clearFire(@PathVariable long id){service.clearFire(id);return ResponseEntity.noContent().build();}
 @GetMapping("/buildings/{id}/floors") public List<FloorDto> floors(@PathVariable long id){return service.floors(id);}
 @PostMapping(value="/buildings/{id}/floors/{floor}/blueprint",consumes=MediaType.MULTIPART_FORM_DATA_VALUE) public ResponseEntity<Void> uploadBlueprint(@PathVariable long id,@PathVariable int floor,@RequestPart("file") MultipartFile file)throws IOException{blueprints.upload(id,floor,file);return ResponseEntity.noContent().build();}
 @GetMapping("/buildings/{id}/floors/{floor}/blueprint") public ResponseEntity<byte[]> getBlueprint(@PathVariable long id,@PathVariable int floor){return blueprints.get(id,floor);}
 @DeleteMapping("/buildings/{id}/floors/{floor}/blueprint") public ResponseEntity<Void> deleteBlueprint(@PathVariable long id,@PathVariable int floor){blueprints.delete(id,floor);return ResponseEntity.noContent().build();}
 @PatchMapping("/buildings/{id}/passages/{code}") public PassageDto block(@PathVariable long id,@PathVariable String code,@RequestBody BlockRequest request){return service.block(id,code,request.blocked());}
 @GetMapping("/history") public List<SimulationEvent> history(){return service.history();}
 @GetMapping("/buildings/{id}/export") public ResponseEntity<byte[]> export(@PathVariable long id)throws IOException{byte[] data=files.export(service.get(id));return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=evacuaid-building-"+id+".json").body(data);}
 @PostMapping(value="/buildings/import",consumes=MediaType.MULTIPART_FORM_DATA_VALUE) public BuildingDto importLayout(@RequestPart("file") MultipartFile file)throws IOException{return files.importLayout(file);}
}

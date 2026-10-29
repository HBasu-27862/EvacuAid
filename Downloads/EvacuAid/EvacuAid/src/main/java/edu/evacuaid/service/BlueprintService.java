package edu.evacuaid.service;

import edu.evacuaid.model.*; import edu.evacuaid.repo.FloorBlueprintRepository;
import org.springframework.http.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO; import java.awt.image.BufferedImage; import java.io.*;

@Service public class BlueprintService {
 private static final long MAX_BYTES=5_000_000;
 private final FloorBlueprintRepository blueprints; private final EvacuationService evacuation;
 public BlueprintService(FloorBlueprintRepository blueprints,EvacuationService evacuation){this.blueprints=blueprints;this.evacuation=evacuation;}
 @Transactional public void upload(long buildingId,int floor,MultipartFile file)throws IOException{
  if(floor<1||floor>20)throw new IllegalArgumentException("Floor must be between 1 and 20.");evacuation.requireBuilding(buildingId);
  if(file.isEmpty()||file.getSize()>MAX_BYTES)throw new IllegalArgumentException("Choose a PNG or JPEG image smaller than 5 MB.");
  String mime=file.getContentType();if(!"image/png".equals(mime)&&!"image/jpeg".equals(mime))throw new IllegalArgumentException("Blueprints must be PNG or JPEG images.");
  byte[] bytes=file.getBytes();BufferedImage image=ImageIO.read(new ByteArrayInputStream(bytes));if(image==null||image.getWidth()>6000||image.getHeight()>6000)throw new IllegalArgumentException("The image could not be read or exceeds 6000 by 6000 pixels.");
  FloorBlueprint plan=blueprints.findByBuilding_IdAndFloorNumber(buildingId,floor).orElseGet(()->new FloorBlueprint(evacuation.requireBuilding(buildingId),floor,mime,bytes));if(plan.getId()!=null)plan.replace(mime,bytes);blueprints.save(plan);
 }
 @Transactional(readOnly=true) public ResponseEntity<byte[]> get(long buildingId,int floor){FloorBlueprint p=blueprints.findByBuilding_IdAndFloorNumber(buildingId,floor).orElseThrow(()->new java.util.NoSuchElementException("No blueprint uploaded for this floor."));return ResponseEntity.ok().contentType(MediaType.parseMediaType(p.getContentType())).cacheControl(CacheControl.noCache()).body(p.getImageData());}
 @Transactional public void delete(long buildingId,int floor){blueprints.delete(blueprints.findByBuilding_IdAndFloorNumber(buildingId,floor).orElseThrow(()->new java.util.NoSuchElementException("No blueprint uploaded for this floor.")));}
}

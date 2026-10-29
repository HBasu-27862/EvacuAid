package edu.evacuaid.service;

import edu.evacuaid.model.Building; import edu.evacuaid.model.FloorBlueprint; import edu.evacuaid.repo.FloorBlueprintRepository;
import org.junit.jupiter.api.Test; import org.springframework.mock.web.MockMultipartFile;
import javax.imageio.ImageIO; import java.awt.image.BufferedImage; import java.io.ByteArrayOutputStream; import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*; import static org.mockito.Mockito.*;

class BlueprintServiceTest {
 @Test void savesValidatedFloorImage()throws Exception{FloorBlueprintRepository repo=mock(FloorBlueprintRepository.class);EvacuationService evacuation=mock(EvacuationService.class);when(evacuation.requireBuilding(1L)).thenReturn(new Building("Test","Building"));when(repo.findByBuilding_IdAndFloorNumber(1,2)).thenReturn(Optional.empty());BufferedImage image=new BufferedImage(2,2,BufferedImage.TYPE_INT_RGB);ByteArrayOutputStream bytes=new ByteArrayOutputStream();ImageIO.write(image,"png",bytes);BlueprintService service=new BlueprintService(repo,evacuation);service.upload(1,2,new MockMultipartFile("file","floor.png","image/png",bytes.toByteArray()));verify(repo).save(any(FloorBlueprint.class));}
 @Test void rejectsSvgUpload()throws Exception{FloorBlueprintRepository repo=mock(FloorBlueprintRepository.class);BlueprintService service=new BlueprintService(repo,mock(EvacuationService.class));assertThrows(IllegalArgumentException.class,()->service.upload(1,1,new MockMultipartFile("file","plan.svg","image/svg+xml","<svg/>".getBytes())));verify(repo,never()).save(any());}
}

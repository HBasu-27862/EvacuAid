package edu.evacuaid.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.evacuaid.api.ApiDtos.*;
import edu.evacuaid.repo.BuildingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LayoutFileServiceTest {
 @Test void exportsLayoutAsJson()throws Exception{LayoutFileService service=new LayoutFileService(new ObjectMapper(),mock(BuildingRepository.class));var dto=new BuildingDto(7L,"Test","layout",java.util.List.of(new PlaceDto("R","Room","ROOM",1,2)),java.util.List.of());var tree=new ObjectMapper().readTree(service.export(dto));assertEquals("Test",tree.path("name").asText());assertEquals("R",tree.path("places").get(0).path("code").asText());}
 @Test void importsValidLayoutAndKeepsPassage(){BuildingRepository repo=mock(BuildingRepository.class);when(repo.save(any())).thenAnswer(i->i.getArgument(0));LayoutFileService service=new LayoutFileService(new ObjectMapper(),repo);String json="""
 {"name":"Test","description":"layout","places":[{"code":"R","label":"Room","type":"ROOM","x":1,"y":2},{"code":"E","label":"Exit","type":"EXIT","x":3,"y":4}],"passages":[{"code":"P","fromCode":"R","toCode":"E","cost":2,"blocked":false}]}
 """;var result=assertDoesNotThrow(()->service.importLayout(new MockMultipartFile("file","layout.json","application/json",json.getBytes())));assertEquals("Test",result.name());assertEquals(1,result.passages().size());verify(repo).save(any());}
 @Test void rejectsMissingPassageEndpoint(){BuildingRepository repo=mock(BuildingRepository.class);LayoutFileService service=new LayoutFileService(new ObjectMapper(),repo);String json="""
 {"name":"Test","places":[{"code":"R","label":"Room","type":"ROOM","x":1,"y":2}],"passages":[{"code":"P","fromCode":"R","toCode":"MISSING","cost":2,"blocked":false}]}
 """;assertThrows(IllegalArgumentException.class,()->service.importLayout(new MockMultipartFile("file","layout.json","application/json",json.getBytes())));verify(repo,never()).save(any());}
}

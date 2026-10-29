package edu.evacuaid.api;

import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest @AutoConfigureMockMvc class EvacuationApiIntegrationTest {
 @Autowired MockMvc mvc;
 @Test void sampleAndRouteEndpointsWork()throws Exception{String body=mvc.perform(post("/api/buildings/sample")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();var json=new com.fasterxml.jackson.databind.ObjectMapper().readTree(body);long id=json.path("id").asLong();mvc.perform(post("/api/buildings/"+id+"/route").contentType("application/json").content("{\"startCode\":\"LAB\",\"automaticExit\":true}")).andExpect(status().isOk()).andExpect(jsonPath("$[0].nodes[0]").value("LAB"));}
}

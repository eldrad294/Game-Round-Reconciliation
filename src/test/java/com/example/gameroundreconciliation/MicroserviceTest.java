package com.example.gameroundreconciliation;

import com.example.gameroundreconciliation.controllers.Microservice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ContextConfiguration(classes=Microservice.class)
@WebMvcTest(controllers = Microservice.class)
@Slf4j
public class MicroserviceTest {

    @MockBean
    private Microservice microservice;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertNotNull(this.microservice);
        assertNotNull(this.mockMvc);
    }

    @Test
    public void getBetsTest() throws Exception {
        String urlTemplate = "/getBets";
        log.info("Mock MVC template mock > {}", urlTemplate);
        mockMvc.perform(get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.displayName()))
                .andExpect(status().isOk());
    }

    @Test
    public void getBetsByInstanceTest() throws Exception {
        long gameInstanceId = 1L;
        String urlTemplate = "/getBets/" + gameInstanceId;
        log.info("Mock MVC template mock > {}", urlTemplate);
        mockMvc.perform(get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.displayName()))
                .andExpect(status().isOk()).andExpect(content().string(containsString("")));
    }

    @Test
    public void getDailyAggregatesTest() throws Exception {
        String from = "x";
        String to = "x";
        String userId = "x";
        String urlTemplate = "/getDailyAggregates/" + from + "/" + to + "/" + userId;
        log.info("Mock MVC template mock > {}", urlTemplate);
        mockMvc.perform(get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.displayName()))
                .andExpect(status().isOk()).andExpect(content().string(containsString("")));
    }

    @Test
    public void getHourlyAggregatesTest() throws Exception {
        String from = "x";
        String to = "x";
        String userId = "x";
        String urlTemplate = "/getHourlyAggregates/" + from + "/" + to + "/" + userId;
        log.info("Mock MVC template mock > {}", urlTemplate);
        mockMvc.perform(get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.displayName()))
                .andExpect(status().isOk()).andExpect(content().string(containsString("")));
    }
}
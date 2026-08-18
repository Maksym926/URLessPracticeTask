package com.test.urllessweb.controllers;

import com.test.gateway.UrlGateway;
import com.test.shortener.ShortenedURL;
import com.test.urllessweb.dto.CreateUrlRequest;
import com.test.urllessweb.dto.CreateUrlResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class ShortenerControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UrlGateway urlGateway ;

    @Test
    public void shouldReturn404OnNonExistingURLTest() throws Exception {
        mockMvc.perform(get("/NON-EXISTING-URL"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .string("url does not exists"));
    }
    @Test
    public void shouldReturn301OnRedirect() throws Exception{
        urlGateway.create("http://test", "abcTest");
        mockMvc.perform(get("/abcTest"))
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("Location", "http://test"));
    }
    @Test
    public void shouldReturn201OnCreatingURL() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateUrlRequest request = new CreateUrlRequest("http://test");
        String json = objectMapper.writeValueAsString(request);

        MvcResult result = mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andReturn();
        CreateUrlResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), CreateUrlResponse.class);
        ShortenedURL expected = urlGateway.getAll().get(0);

        assertEquals(expected.getUrl() , response.getOriginalUrl());
        assertEquals("http://urle.ss/" + expected.getId(), response.getUrl());

    }




}

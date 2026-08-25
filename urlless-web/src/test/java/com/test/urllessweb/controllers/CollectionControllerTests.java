package com.test.urllessweb.controllers;

import com.test.collection.UrlCollection;
import com.test.gateway.CollectionGateway;
import com.test.shortener.ShortenedURL;
import com.test.urllessweb.dto.CollectionRequest;
import com.test.urllessweb.dto.CollectionResponse;
import com.test.usecase.ShortenerUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class CollectionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShortenerUseCase shortenerInteractor;

    @Autowired
    private CollectionGateway collectionGateway;

    @Test
    public void shouldReturn404OnNonExistingCollection() throws Exception {
        mockMvc.perform(get("/collection/NON-EXISTING-COLLECTION"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void shouldReturn200AndJsonListOnExistingCollection() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ShortenedURL> shortenedURLS =
                createShortenedUrlListHelper(List.of("http://test1", "http://test2", "http://test3"));
        collectionGateway.create("abcd", shortenedURLS);

        MvcResult result = mockMvc.perform(get("/collection/abcd"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<ShortenedURL> collection = objectMapper.readValue(
                result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<>() {
                });

        assertEquals(shortenedURLS.size(),  collection.size());

        for(int i = 0; i<shortenedURLS.size(); i++){
            assertEquals(shortenedURLS.get(i).getId(), collection.get(i).getId());
        }
    }
    @Test
    public void shouldReturn201OnCreatingCollection() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ShortenedURL> shortenedURLS =
                createShortenedUrlListHelper(List.of("http://test1"));
        shortenedURLS.add(ShortenedURL.builder()
                .url("http://test3").build());
        CollectionRequest request = new CollectionRequest(shortenedURLS);
        String json = objectMapper.writeValueAsString(request);

        MvcResult result = mockMvc.perform(post("/collection").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andReturn();
        CollectionResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), CollectionResponse.class);

        assertEquals(shortenedURLS.get(0).getUrl(), response.getShortenedURLS().get(0).getUrl());
        assertNotEquals(shortenedURLS.get(1).getUrl(), response.getShortenedURLS().get(1).getUrl());


    }


    private List<ShortenedURL> createShortenedUrlListHelper(List<String> urls) {
        return new ArrayList<>(urls.stream().map(shortenerInteractor::create).toList());
    }

}

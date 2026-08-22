package com.test.urllessweb.controllers;

import com.test.collection.UrlCollection;
import com.test.gateway.CollectionGateway;
import com.test.shortener.ShortenedURL;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private List<ShortenedURL> createShortenedUrlListHelper(List<String> urls) {
        return new ArrayList<>(urls.stream().map(shortenerInteractor::create).toList());
    }

}

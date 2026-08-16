package com.test.urllessweb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class ShortenerControllerTests {
    @Autowired
    private MockMvc mockMvc;

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
        mockMvc.perform(get("/abcTest"))
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("Location", "http://test"));
    }

}

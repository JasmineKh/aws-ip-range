package com.jasmine.awsiprange.controller;


import com.jasmine.awsiprange.service.IPService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IPController.class)
public class IPControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPService ipService;


    @Test
    void getIpsByRegion_throwsBadRequestException_whenRegionIsEmpty() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ip/list")
                        .param("region", "")
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getIpsByRegion_throwsBadRequestException_whenRegionIsBlank() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ip/list")
                        .param("region", " ")
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getIpsByRegion_throwsBadRequestException_whenRegionIsNotValid() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ip/list")
                        .param("region", "test")
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getIpsByRegion_throwsUnsupportedMediaTypeException_whenProducerMediaTypeIsNotTextPlain() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ip/list")
                        .param("region", "cn")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnsupportedMediaType());
    }


    @Test
    void getIpsByRegion_throws5xxException_whenLoadAndFindIPsByRegionHasException() throws Exception {
        Mockito.doThrow(new Exception()).when(ipService).loadAndFindIPsByRegion(anyString());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ip/list")
                        .param("region", "cn")
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().is5xxServerError()).andReturn();

        Mockito.verify(ipService, Mockito.atLeastOnce()).loadAndFindIPsByRegion(any(String.class));
    }


    @Test
    void getIpsByRegion_returnStatusCode200_whenRegionIsValid() throws Exception {
        String region = "cn";
        String ips = "########ipv4s########\n" +
                "54.222.88.0/24\n" +
                "52.82.169.16/28\n" +
                "71.131.192.0/18";
        Mockito.when(ipService.loadAndFindIPsByRegion(region)).thenReturn(ips);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/ip/list")
                        .param("region", region)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();

        Mockito.verify(ipService, Mockito.atLeastOnce()).loadAndFindIPsByRegion(any(String.class));
        Assertions.assertNotNull(content);
        Assertions.assertTrue(content.contains("########ipv4s########"));
    }


}

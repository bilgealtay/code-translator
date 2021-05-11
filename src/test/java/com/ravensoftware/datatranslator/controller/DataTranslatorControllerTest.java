package com.ravensoftware.datatranslator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravensoftware.datatranslator.constants.MessageConstants;
import com.ravensoftware.datatranslator.entity.FileRequest;
import com.ravensoftware.datatranslator.service.DataTranslatorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by bilga
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DataTranslatorController.class)
public class DataTranslatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataTranslatorService dataTranslatorService;

    ObjectMapper mapper =  new ObjectMapper();

    @Test
    public void should_return_bad_request_when_body_is_empty() throws Exception {

        FileRequest fileRequest = new FileRequest();

        mockMvc.perform(post("/read-line")
                .content(mapper.writeValueAsString(fileRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_tableFilePath_is_null() throws Exception {

        FileRequest fileRequest = new FileRequest(null, "a.txt", "b.txt", "c.txt");

        mockMvc.perform(post("/read-line")
                .content(mapper.writeValueAsString(fileRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_columnDefinitionFilePath_is_null() throws Exception {

        FileRequest fileRequest = new FileRequest("a.txt", null, "b.txt", "c.txt");

        mockMvc.perform(post("/read-line")
                .content(mapper.writeValueAsString(fileRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_rowDefinitionFilePath_is_null() throws Exception {

        FileRequest fileRequest = new FileRequest("b.txt", "a.txt", null, "c.txt");

        mockMvc.perform(post("/read-line")
                .content(mapper.writeValueAsString(fileRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_newFilePath_is_null() throws Exception {

        FileRequest fileRequest = new FileRequest("a.txt", "b.txt", "c.txt", null);

        mockMvc.perform(post("/read-line")
                .content(mapper.writeValueAsString(fileRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_done_message_when_fileRequest_is_ok() throws Exception {

        FileRequest fileRequest = new FileRequest("a.txt", "b.txt", "c.txt", "d.txt");

        when( dataTranslatorService.readLine(fileRequest) )
                .thenReturn(MessageConstants.DONE);

        MvcResult result = mockMvc.perform(post("/read-line")
                .content(mapper.writeValueAsString(fileRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();

        Assert.assertEquals(MessageConstants.DONE, content);
    }
}

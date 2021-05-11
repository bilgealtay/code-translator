package com.ravensoftware.datatranslator.service;

import com.ravensoftware.datatranslator.constants.MessageConstants;
import com.ravensoftware.datatranslator.entity.FileRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by bilga
 */
@RunWith(SpringRunner.class)
public class DataTranslatorServiceTest {

    @TestConfiguration
    static class DataTranslatorServicesImplTestContextConfiguration {

        @Bean
        public DataTranslatorService dataTranslatorService() {
            return new DataTranslatorService();
        }
    }

    public static final String COLUMN_DEFINITION_FILE_PATH = "/Users/bilga/devhome/dataTranslatorFiles/columnConfiguration.txt";
    public static final String ROW_DEFINITION_FILE_PATH = "/Users/bilga/devhome/dataTranslatorFiles/rowConfiguration.txt";
    public static final String TABLE_FILE_PATH = "/Users/bilga/devhome/dataTranslatorFiles/table_large.txt";
    public static final String NEW_FILE_PATH = "/Users/bilga/devhome/dataTranslatorFiles/newTable.txt";

    @Autowired
    private DataTranslatorService dataTranslatorService;

    @Test
    public void should_not_return_done_message_when_fileRequest_is_null() throws Exception {
        FileRequest fileRequest = null;
        String result = dataTranslatorService.readLine(fileRequest);
        Assert.assertNotEquals(MessageConstants.DONE, result);
    }

    @Test
    public void should_not_return_done_message_when_columnDefinitionFilePath_is_null() throws Exception {
        FileRequest fileRequest = new FileRequest("a.txt", null, "b.txt", "c.txt");
        String result = dataTranslatorService.readLine(fileRequest);
        Assert.assertNotEquals(MessageConstants.DONE, result);
    }

    @Test
    public void should_not_return_done_message_when_rowDefinitionFilePath_is_null() throws Exception {
        FileRequest fileRequest = new FileRequest("a.txt", "b.txt", null, "c.txt");
        String result = dataTranslatorService.readLine(fileRequest);
        Assert.assertNotEquals(MessageConstants.DONE, result);
    }

    @Test
    public void should_not_return_done_message_when_newFilePath_is_null() throws Exception {
        FileRequest fileRequest = new FileRequest("a.txt", "c.txt", "b.txt", null);
        String result = dataTranslatorService.readLine(fileRequest);
        Assert.assertNotEquals(MessageConstants.DONE, result);
    }

    @Test
    public void should_not_return_done_message_when_tableFilePath_is_null() throws Exception {
        FileRequest fileRequest = new FileRequest(null, "a.txt", "b.txt", "c.txt");
        String result = dataTranslatorService.readLine(fileRequest);
        Assert.assertNotEquals(MessageConstants.DONE, result);
    }

    @Test
    public void should_return_fileNotFound_message_when_file_does_not_exist() throws Exception {
        FileRequest fileRequest = new FileRequest("/Users/bilga/devhome/dataTranslatorFiles/a.txt", "a.txt", "b.txt", "c.txt");
        String result = dataTranslatorService.readLine(fileRequest);
        Assert.assertEquals(MessageConstants.FILE_NOT_FOUND, result);
    }

    @Test
    public void should_return_done_message_when_files_exist() throws Exception {
        FileRequest fileRequest = new FileRequest(TABLE_FILE_PATH, COLUMN_DEFINITION_FILE_PATH, ROW_DEFINITION_FILE_PATH, NEW_FILE_PATH);
        String result = dataTranslatorService.readLine(fileRequest);
        Assert.assertEquals(MessageConstants.DONE, result);
    }
}

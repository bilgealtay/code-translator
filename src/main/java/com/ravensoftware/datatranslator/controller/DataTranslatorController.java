package com.ravensoftware.datatranslator.controller;

import com.ravensoftware.datatranslator.entity.FileRequest;
import com.ravensoftware.datatranslator.service.DataTranslatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by bilga
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/")
public class DataTranslatorController {

    private DataTranslatorService dataTranslatorService;

    public DataTranslatorController(DataTranslatorService dataTranslatorService) {
        this.dataTranslatorService = dataTranslatorService;
    }

    @GetMapping(path = "read-line")
    public ResponseEntity readRow(@RequestBody @NotNull @Valid FileRequest fileRequest) {
        String result = dataTranslatorService.readLine(fileRequest);
        return ResponseEntity.ok(result);
    }
}

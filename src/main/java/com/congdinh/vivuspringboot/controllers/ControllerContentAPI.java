package com.congdinh.vivuspringboot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.congdinh.vivuspringboot.entities.Content;
import com.congdinh.vivuspringboot.services.ContentService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

// @Controller
// @RequestMapping("/api/content")
// public class ControllerContentAPI {
//    public final ContentService contentService;
//    public ControllerContentAPI(ContentService contentService) {
//        this.contentService = contentService;
//    }
//    @PostMapping
//    @Operation(summary  = "Add content")
//    public ResponseEntity<?> create(@RequestBody @Valid Content content,
//                                    BindingResult bindingResult){
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
//        }

//        Content content123 = contentService.save(content);
//        if (content123 == null) {
//            return ResponseEntity.ofNullable(null);
//        }
//        return ResponseEntity.ok(content123);
//    }
// }

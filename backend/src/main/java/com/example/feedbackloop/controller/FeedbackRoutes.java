package com.example.feedbackloop.controller;

import com.example.feedbackloop.service.FeedbackService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class FeedbackRoutes {

    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/generateAndVerify")
    public ResponseEntity<?> generateAndVerify(HttpServletRequest request,
                                               @RequestBody(required = false) HashMap<String,Object> requestBody) throws Exception {
        return feedbackService.generateAndVerify((String) requestBody.get("code"));
    }
}

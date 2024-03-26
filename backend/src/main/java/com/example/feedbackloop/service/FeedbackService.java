package com.example.feedbackloop.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service    // This annotation is used to mark the class as a service
public class FeedbackService {

    public ResponseEntity<?> generateAndVerify (String code) {
        // This method is used to generate and verify the feedback  for the given url

        HashMap<String, Object> response=new HashMap<>();
        response.put("status", "success");
        response.put("code", code);

        return ResponseEntity.ok(response);
    }
}

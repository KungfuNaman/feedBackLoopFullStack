package com.example.feedbackloop.controller;

import com.example.feedbackloop.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@CrossOrigin
public class FeedbackRoutes {

    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/generateAndVerify")
    public ResponseEntity<?> generateAndVerify(HttpServletRequest request,
                                               @RequestBody(required = false) HashMap<String,Object> requestBody) throws Exception {
        return feedbackService.generateAndVerify((String) requestBody.get("code"));
    }
    @PostMapping("/generateLlmOutput")
    public ResponseEntity<?> generateLlmOutput(HttpServletRequest request,
                                               @RequestBody(required = false) HashMap<String,Object> requestBody) throws Exception {
        return feedbackService.generateLlmOutput((String) requestBody.get("code"));
    }

    @PostMapping("/verifyWithInfer")
    public ResponseEntity<?> verifyWithInfer(HttpServletRequest request,
                                               @RequestBody(required = false) HashMap<String,Object> requestBody) throws Exception {
        return feedbackService.verifyWithInfer((String) requestBody.get("code"),(String) requestBody.get("fileName"));
    }
    @PostMapping("/verifyWithSymbolicExecution")
    public ResponseEntity<?> verifyWithSymbolicExecution(HttpServletRequest request,
                                               @RequestBody(required = false) HashMap<String,Object> requestBody) throws Exception {
        return feedbackService.verifyWithSymbolicExecution((String) requestBody.get("code"),(String) requestBody.get("fileName"));
    }
    @PostMapping("/verifyWithCheckstyle")
    public ResponseEntity<?> verifyWithCheckstyle(HttpServletRequest request,
                                               @RequestBody(required = false) HashMap<String,Object> requestBody) throws Exception {
        return feedbackService.verifyWithCheckstyle((String) requestBody.get("code"),(String) requestBody.get("fileName"),(String) requestBody.get("checkStyleConfig"));
    }
}

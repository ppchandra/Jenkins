package com.test.jenkins.web.rest;

import com.test.jenkins.model.EmailRequest;
import com.test.jenkins.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/jenkins-test/v1")
public class EmailResource {

    private final EmailService emailService;

    @Autowired
    public EmailResource(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping(value = "/hello")
    String getMessage(){
        return "Jenkins Deployment Test.!";
    }

    @PostMapping(value = "/send-email")
    String sendEmail(@Valid @RequestBody EmailRequest emailRequest) throws MessagingException {

        if(!StringUtils.isEmpty(emailRequest)){
            emailService.sendEmail(emailRequest);
        }
        return "Email Sent..!!";
    }
}

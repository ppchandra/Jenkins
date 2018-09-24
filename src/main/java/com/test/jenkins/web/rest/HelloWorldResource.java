package com.test.jenkins.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jenkins-test/v1")
public class HelloWorldResource {

    @GetMapping(value = "/hello")
    String getMessage(){
        return "Jenkins Deployment Test.!";
    }
}

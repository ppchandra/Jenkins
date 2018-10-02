package com.test.jenkins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailRequest implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String toEmail;
    private String fromEmail;
    private String subject;
    private String finalContent;
}

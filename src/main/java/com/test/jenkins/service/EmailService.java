package com.test.jenkins.service;

import com.test.jenkins.model.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendEmail(EmailRequest emailRequest) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        Context context = new Context();
        context.setVariable("name","Customer");
        context.setVariable("finalContent",emailRequest.getFinalContent());
        context.setVariable("signature","Service Center");
        String html = templateEngine.process("email-template", context);

        messageHelper.setText(html,true);
        messageHelper.setFrom(emailRequest.getFromEmail());
        messageHelper.setTo(emailRequest.getToEmail());
        messageHelper.setSubject(emailRequest.getSubject());
        //messageHelper.addAttachment("logo.png",new ClassPathResource("logo.png"));

        byte[] file = readBytesFromFile("C:\\Users\\ppcha\\Documents\\Prasanth_Resume.pdf");
        ByteArrayDataSource source = new ByteArrayDataSource(file,"application/octet-stream");
        messageHelper.addAttachment("Prasanth_Resume.pdf",source);

        emailSender.send(message);

    }

    private static byte[] readBytesFromFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;

    }
}

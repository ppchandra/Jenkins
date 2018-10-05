package com.test.jenkins.service;

import com.test.jenkins.model.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(EmailRequest emailRequest) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        Map model = new HashMap();
        model.put("name", "Customer");
        model.put("finalContent",emailRequest.getFinalContent());
        model.put("signature", "The Standard Service Center");

        Context context = new Context();
        context.setVariables(model);
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

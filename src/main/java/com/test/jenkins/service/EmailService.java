package com.test.jenkins.service;

import com.test.jenkins.model.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(EmailRequest emailRequest) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,"UTF-8");

        messageHelper.setFrom(emailRequest.getFromEmail());
        messageHelper.setTo(emailRequest.getToEmail());
        messageHelper.setSubject(emailRequest.getSubject());
        messageHelper.setText(emailRequest.getFinalContent());

        /*FileSystemResource file = new FileSystemResource(new File("C:\\Users\\ppcha\\Documents\\Prasanth_Resume.pdf"));
        messageHelper.addAttachment("Prasanth_Resume.pdf",file);*/

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

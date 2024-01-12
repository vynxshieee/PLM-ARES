package com.OOP.plmares.controllers.utilities;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.Properties;

public class MailSenderUtils {

    public static boolean sendMail(String to, String subject, String content) {
        final String username = "plmares01@gmail.com"; //  EMAIL
        final String password = "ipzz sjyn anay vhpx"; // APP GENERATED PASSWORD

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // replace with your SMTP server
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Email sent successfully!");
            JOptionPane.showMessageDialog(
                    null,
                    "Email sent successfully! ",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return true;

        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "An error occurred. Make sure you have a stable internet connection.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );
            e.printStackTrace();
            return false;
        }
    }
}

package com.mawell.mappingservice.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail
{
    public boolean SendMail (String inputMessage, String toAddress) throws MessagingException
    {
    boolean retval;
    try{
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "send.one.com");
        props.setProperty("mail.smtp.auth", "true"); // not necessary for my server, I'm not sure if you'll need it
        props.setProperty("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.port", "465");
        Session session = Session.getInstance(props, null);
        Transport transport = session.getTransport("smtp");
        transport.connect("mawell@bjarkmar.com", "yhog3mun");

        Message message = new MimeMessage(session);
        message.setSubject("Test");
       // message.setText(inputMessage);
        message.setContent(inputMessage, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress("mawell@bjarkmar.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("andreas.bjarkmar@mawell.com"));
        transport.sendMessage(message, message.getAllRecipients());
        System.out.println("Sending e-mail!"); //DEBUG
        retval=true;
    }
    catch(Exception e){
    	retval = false;
    }
        return retval;
    }
}

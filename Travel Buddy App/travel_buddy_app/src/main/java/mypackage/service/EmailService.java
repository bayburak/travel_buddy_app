package mypackage.service;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import mypackage.model.User;


public class EmailService {

    private static final String FROM_EMAIL = "travelBuddies@gmail.com"; //To be changed
    private static final String FROM_NAME = "Travel Buddies Team";
    private static final String PASSWORD = "password"; //To be changed


    public static boolean sendPasswordResetEmail(User user, String token) 
    {
        String toEmail = user.getE_mail();
        String name = user.getNameSurname();
        String subject = "Reset Password Request";

        String body = "Hi " + name + ",\n\n" + "We received a request to reset your password.\n" + "Use the following token to reset your password in the app:\n\n" + token + "\n\n" + "If you did not request this, you can ignore this email.\n\n" + "Thanks,\n" + "Travel Buddies";

        try 
        {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() 
            {
                protected PasswordAuthentication getPasswordAuthentication() 
                {
                    return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            return true;

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verifyEmailToken(User user, String token, String inputToken) 
    {
        return inputToken != null && inputToken.equals(token);
    }

    public static boolean sendWelcomeEmail(User user)
    {
        String toEmail = user.getE_mail();
        String name = user.getNameSurname();
        String subject = "Welcome to Travel Buddies!";
        
        String body = "Hi " + name + ",\n\n" + "Welcome to Travel Buddies! We're excited to have you on board.\n" + "Start exploring and connecting with travelers from around the world.\n\n" + "Safe travels,\n" + "The Travel Buddies Team";
    
        try 
        {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
    
            Session session = Session.getInstance(props, new Authenticator() 
            {
                protected PasswordAuthentication getPasswordAuthentication() 
                {
                    return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                }
            });
    
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
    
            Transport.send(message);
    
            return true;
    
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }
}

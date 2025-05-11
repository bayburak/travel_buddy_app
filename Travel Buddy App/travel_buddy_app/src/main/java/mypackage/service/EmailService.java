package mypackage.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import mypackage.model.User;

import java.util.Properties;


public class EmailService
{

    private static final String FROM_EMAIL = "travelbuddyfam@gmail.com";
    private static final String PASSWORD   = "klgnzzeojgokgcpc";
    private static final String FROM_NAME  = "Travel Buddies Team";

    public static boolean sendPasswordResetEmail(User user, String token)
    {
        return send(user.getE_mail(), "Reset Password Request", "Hi " + user.getNameSurname() + ",\n\n" + "Use this code to reset your password: " + token + "\n\n" + "If you didn't request this, ignore the mail.\n\nTravel Buddies");
    }

    public static boolean sendWelcomeEmail(User user)
    {
        return send(user.getE_mail(), "Welcome to Travel Buddies!", "Hi " + user.getNameSurname() + ",\n\nWelcome aboard!\n\nTravel Buddies Team");
    }

    public static boolean verifyEmailToken(User user, String token, String input)
    {
        return input != null && input.equals(token);
    }

    private static boolean send(String to, String subject, String body)
    {
        try
        {
            Session session = createSession();
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
            System.out.println("Email sent successfully to " + to);
            return true;
        }
        catch (AuthenticationFailedException afe)
        {
            System.err.println("SMTP Auth failed. Check email and password.");
        }
        catch (MessagingException me)
        {
            System.err.println("SMTP error: " + me.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private static Session createSession()
    {
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.ssl.protocols", "TLSv1.2");
        p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");

        return Session.getInstance(p, new Authenticator()
        {
            @Override protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });
    }
}

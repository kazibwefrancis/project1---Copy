import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    //send email to school representative after a pupil has registered
    public static void notifySchoolRep(String schoolRegNo,String sender,String name){
        String recipient = Model.getSchoolRepEmail(schoolRegNo);

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.ssl.protocols","TLSv1.2");

        Session session = Session.getInstance(properties,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("jimisaac8082@gmail.com","wnza cduy pqch ydyw");
            }
        });
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipient));
            message.setSubject("New Pupil Registration");
            message.setText("A new pupil by the name "+name+" has registered in your school,,log into the system to confirm their registration");
            Transport.send(message);
            System.out.println("email sent successfully...");
    }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //send email to pupil after their registration has been confirmed
    public static void notifyPupil(String recipient){
        String sender = "jimisaac8082@gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.ssl.protocols","TLSv1.2");

        Session session = Session.getInstance(properties,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("jimisaac8082@gmail.com","wnza cduy pqch ydyw");
            }
        });
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipient));
            message.setSubject("Confirmation of Registration");
            message.setText("Your registration has been confirmed, you can now log into the system to take part in the available challenges");
            Transport.send(message);
            System.out.println("email sent succesfully...");
        }catch(MessagingException e){
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        notifyPupil("jimisaac8082@gmail.com");
    }
}

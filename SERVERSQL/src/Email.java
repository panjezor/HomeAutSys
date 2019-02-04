import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Email {
	public Email(String email, String input) {
		String to = email;
		String sender = "HomeAutomation@System.com";
		String host = "localhost";
		Properties prop = System.getProperties();
		
		prop.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(prop);
		
		try {
			//sets up the message
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("TEST");
			message.setText(input);
			
			
			
			//send the message
			Transport.send(message);
			System.out.println("Sending Message");
			
		}catch(MessagingException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Email email = new Email("16045694@stu.mmu.ac.uk", "TEST Email");
	}
}

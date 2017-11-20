package eu.giuseppeurso.utilities.networking;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	
	/**
	 * Send an Email via SMTP server using TLS connection
	 * @param username
	 * @param password
	 * @param smtpAuth
	 * @param starttls
	 * @param smtpHost
	 * @param smtpPort
	 * @param from
	 * @param to
	 * @param subject
	 * @param mailBody
	 */
	public static void sendMailTLS( final String username, final String password,
									Boolean smtpAuth,
									Boolean starttls,
									String smtpHost, String smtpPort,
									String from, String to, 
									String subject,	String mailBody) {
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", String.valueOf(smtpAuth));
		props.put("mail.smtp.starttls.enable", String.valueOf(starttls));
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {
			Message message = new MimeMessage(session);
			//message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(mailBody);
			Transport.send(message);

			System.out.println("Message sent FROM "+from+", TO "+to);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
		
	

}

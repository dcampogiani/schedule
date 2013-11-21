package util;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * Utility class to send mails using javamail and gmail
 * @author danielecampogiani
 *
 */

public class MailSender {

	/**
	 * Send mail according to given arguments
	 * @param username gmail username ex: "name.surname@gmail.com"
	 * @param password gmail password
	 * @param receivers list of receivers as mail ex: "name.surname@mail.com"
	 * @param subject mail subject
	 * @param body mail body
	 * @param attachmentName name of attachment 
	 * @param attachmentContent content of the attachment
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void  sendMail(String username, String password, List<String> receivers, String subject, String body, String attachmentName, String attachmentContent) throws AddressException, MessagingException, IOException{

		final String user = username;
		final String pass = password;


		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);
			}
		});

		for (String sendTo: receivers){

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(sendTo));
			message.setSubject(subject);


			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(body);

			MimeBodyPart messaMimeBodyPart2 = new MimeBodyPart();
			DataSource source = new ByteArrayDataSource(attachmentContent.getBytes("UTF-8"), "application/octet-stream");
			messaMimeBodyPart2.setDataHandler(new DataHandler(source));
			messaMimeBodyPart2.setFileName(attachmentName);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messaMimeBodyPart2);

			message.setContent(multipart);

			Transport.send(message);

		}




	}
}

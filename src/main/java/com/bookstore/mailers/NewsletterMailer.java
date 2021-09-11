package com.bookstore.mailers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.bookstore.model.Book;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class NewsletterMailer {
	public static final String defaultTo = "newsletter@bookstore.com";
	
	@Autowired
	private JavaMailSender mailSender;  
	
	@Autowired
	private FreeMarkerConfigurer freemarkerConfigurer;

	@Async
	public void newBookReleaseNotification(List<String> to, Book book) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(String.join(", ", to)));;
                mimeMessage.setFrom(new InternetAddress(defaultTo));
                mimeMessage.setSubject("New book released");
                mimeMessage.setContent(newBookReleaseNotificationHTML(objectWrapper(book)), "text/html");
			}
        };

        try {
            this.mailSender.send(preparator);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
		
	}
	
	@SuppressWarnings("serial")
	private Map<String, String> objectWrapper(Book book) {
		return new HashMap<String, String>() {{
			put("title", book.getTitle());
			put("author", book.getAuthor());
			put("cover_image_url", book.getCover_image_url());
		}};
	}
	
	private String newBookReleaseNotificationHTML(Map<String, String> model) throws IOException, TemplateException {
		Template freemarkerTemplate = freemarkerConfigurer.getConfiguration()
				  .getTemplate("newsletter_mailer/newBookReleaseNotification.ftl");

		return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, model);
	}

}

package com.webtech.blogpost.blogPost.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.webtech.blogpost.blogPost.exception.BlogPostException;
import com.webtech.blogpost.blogPost.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
class MailService {

	private final JavaMailSender mailSender;
	private final MailContentBuilder mailContentBuilder;

	@Async
	void sendMail(NotificationEmail notificationEmail) {
		log.info("Notification Email::" + notificationEmail);
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("karthikkalletla6@gmail.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(notificationEmail.getBody());
			log.info(messageHelper.toString());
		};
		try {
			mailSender.send(messagePreparator);
			log.info("Activation email sent!!");
		} catch (MailException e) {
			log.error("Exception occurred when sending mail", e);
			throw new BlogPostException("Exception occurred when sending mail to " + notificationEmail.getRecipient(),
					e);
		}
	}

}

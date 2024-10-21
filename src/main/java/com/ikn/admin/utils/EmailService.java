package com.ikn.admin.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
	@Slf4j
	@Component
	public class EmailService 
	{
		@Autowired
		private JavaMailSender sender;
		
		public boolean sendMail(String to, String subject, String textBody, String[] cc, String[] bcc, MultipartFile file,
				boolean isHtml) {
	 
			boolean flag = false;
			MimeMessage message = sender.createMimeMessage();
	 
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message, file != null ? true : false);
				helper.setTo(to);
				helper.setSubject(subject);
			    helper.setText(textBody);
				helper.setText(textBody, isHtml);
				if (cc != null) {
					helper.setCc(cc);
				}
				if (bcc != null) {
					helper.setBcc(bcc);
				}
				if (file != null) {
					helper.addAttachment(file.getOriginalFilename(), file);
				}
				sender.send(message);
				flag = true;
			} catch (Exception e) {
				
				flag = false;
				e.printStackTrace();
				System.out.println("the email exception is :"+e.getMessage());
				log.info(e.getMessage());
			}
			return flag;
	 
		}

		
	}
	 
		
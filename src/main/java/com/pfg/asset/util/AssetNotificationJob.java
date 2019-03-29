package com.pfg.asset.util;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.pfg.asset.core.AssetException;
import com.pfg.asset.dao.DAOFactory;
import com.pfg.asset.dto.AssetConfig;
import com.pfg.asset.dto.AssetInfo;
import com.pfg.asset.dto.FilterParam;

public class AssetNotificationJob implements Job{

	private static final Logger logger = Logger.getLogger(AssetNotificationJob.class.getName());

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.log(Level.INFO, "AssetNotificationJob: execute() entered" );
		try {
			AssetConfig assetConfig = (AssetConfig)context.getScheduler().getContext().get("assetConfig");
			sendRenewalNotification(assetConfig);
			/*
			Trigger newTrigger = TriggerBuilder
			.newTrigger()
			.withIdentity("AssetEmailNotificationTrigger", "ASSET_ADMIN")
			.withSchedule(CronScheduleBuilder.cronSchedule(assetConfig.getCronExpression()))
			.build();

			Trigger oldTrigger = context.getTrigger();
		    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		    scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
		    */
		}catch(SchedulerException se) {
			logger.log(Level.INFO, "Asset Notification Job failed while getting scheduler context" );
			logger.log(Level.SEVERE, se.getMessage(), se);
		}
		logger.log(Level.INFO, "AssetNotificationJob: execute() exited" );
	}

	private void sendRenewalNotification(AssetConfig assetConfig) {
		FilterParam filter = new FilterParam(assetConfig.getFilterType(), assetConfig.getFilterValue());		
		List<AssetInfo> filteredList = DAOFactory.getInstance().getAssetInfoDAO().filteredAssetInfo(filter);
		
		Properties props = new Properties();
		props.put("mail.smtp.host", assetConfig.getSmtpHost());
		props.put("mail.smtp.socketFactory.port", assetConfig.getSmtpPort());
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", assetConfig.getSmtpPort());
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(assetConfig.getSmtpUsername(), assetConfig.getSmtpPassword());
			}
		  });
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(assetConfig.getSmtpSender()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(assetConfig.getSmtpRecipient()));
			message.setSubject("Asset Renewal Notification");
			 
			String msg = "<h3>Asset Renewal Notification\n</h3>";
			msg += "<p>The enclosed assets are going to expire in 3 months.\n</p>";
			
			msg += "<p>Thanks.\n</p>";
			 
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html");
			 
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			 
			String exportContent = CSVUtils.write(filteredList);

			mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(exportContent.getBytes(),"text/csv")));
			mimeBodyPart.setFileName("export.csv");

		    multipart.addBodyPart(mimeBodyPart);

		    message.setContent(multipart);
			 
			Transport.send(message);			
			
			logger.log(Level.INFO, "Asset Notification Job successfully sent an email." );
		}catch(Exception e) {
			logger.log(Level.INFO, "Asset Notification Job failed to send an email." );
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
		}
	}
}

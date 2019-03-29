package com.pfg.asset.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.pfg.asset.dao.DAOFactory;
import com.pfg.asset.dto.AssetConfig;

public class AssetCronTrigger {
	private static final Logger logger = Logger.getLogger(AssetCronTrigger.class.getName());

	public AssetCronTrigger(){}
	
	public void schedule() throws SchedulerException {

		AssetConfig assetConfig  = DAOFactory.getInstance().getAssetConfigDAO().selectAssetConfig();
	    logger.log(Level.INFO, "{0}", assetConfig);

		JobDetail job = JobBuilder.newJob(AssetNotificationJob.class)
		.withIdentity("AssetNotificationJob", "ASSET_ADMIN").build();
	
		Trigger trigger = TriggerBuilder
		.newTrigger()
		.withIdentity("AssetEmailNotificationTrigger", "ASSET_ADMIN")
		.withSchedule(CronScheduleBuilder.cronSchedule(assetConfig.getCronExpression()))
		.build();
		
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.getContext().put("assetConfig", assetConfig);
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}
	
	public void unSchedule() throws SchedulerException {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.deleteJob(new JobKey("AssetNotificationJob"));
	}
}

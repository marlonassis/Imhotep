package br.com.ControleDispensacao.quartz;

import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class RunQuartz implements Runnable {
	@Override
	public void run() {
		try {
			
			// define the job and tie it to our HelloJob class
			JobDetail job = JobBuilder.newJob(JobBackUp.class).withIdentity("runBackUp").build();
			// Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger = newTrigger().withIdentity("triggerRunBackUp")
					.startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
					.build();
			// Tell quartz to schedule the job using our trigger
			StdSchedulerFactory.getDefaultScheduler().scheduleJob(job, trigger);
			StdSchedulerFactory.getDefaultScheduler().start();
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}
}

package br.com.ControleDispensacao.quartz;

import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class RunQuartz implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			// define the job and tie it to our HelloJob class
			JobDetail job = JobBuilder.newJob(JobBackUp.class).withIdentity("runBackUp").build();
			// Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger = newTrigger().withIdentity("triggerRunBackUp")
					.startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 * * * ?"))
					.build();
			// Tell quartz to schedule the job using our trigger
			StdSchedulerFactory.getDefaultScheduler().scheduleJob(job, trigger);
			StdSchedulerFactory.getDefaultScheduler().start();
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}
		
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

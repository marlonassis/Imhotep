package br.com.Imhotep.testes;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {
	public static void main(String[] args) {
		try {
			// Grab the Scheduler instance from the Factory
			// and start it off
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			
			// define the job and tie it to our HelloJob class
			JobDetail job = JobBuilder.newJob(HelloJob.class)
					.withIdentity("job1", "group1")
					.build();
			// Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
					.startNow()
					.withSchedule(simpleSchedule()
							.withIntervalInSeconds(40)
							.repeatForever())
							.build();
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);
			scheduler.start();
			scheduler.shutdown();
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
		
		
		
		
	}
}
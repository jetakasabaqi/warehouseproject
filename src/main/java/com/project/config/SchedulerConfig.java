package com.project.config;

import com.project.Autowiring.AutowiringSpringBeanJobFactory;
import com.project.Job.SampleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class SchedulerConfig {
    @Autowired
    private ApplicationContext context;
    private JobDetail jobDetail;
    private long frequency;

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(SampleJob.class)
            .storeDurably()
            .withIdentity("Qrtz_Job_Detail")
            .withDescription("Invoke Sample Job service...")
            .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
            .withIdentity("Qrtz_Trigger")
            .withDescription("Sample trigger")
            .withSchedule(simpleSchedule().repeatForever().withIntervalInHours(1))
            .build();
    }

    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job) throws SchedulerException, IOException {
        StdSchedulerFactory factory = new StdSchedulerFactory();
        factory.initialize(new ClassPathResource("quartz.properties").getInputStream());

        Scheduler scheduler = factory.getScheduler();
        scheduler.setJobFactory(springBeanJobFactory());
        scheduler.scheduleJob(job, trigger);

        scheduler.start();
        return scheduler;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(context);
        return jobFactory;
    }
}

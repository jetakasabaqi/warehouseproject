package com.project.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.scheduling.support.CronTrigger;

import javax.persistence.*;

@Entity
@Table(name = "cron_jobs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CronJobs  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;


    private static String cron ;



    public String CronJobs(String one) {
        cron=one;
        return one;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}

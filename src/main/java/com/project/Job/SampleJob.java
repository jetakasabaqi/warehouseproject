package com.project.Job;



import com.lowagie.text.DocumentException;
import com.project.service.SampleService;
import com.project.service.ShipmentService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by david on 2015-01-20.
 */
public class SampleJob implements Job {
    @Autowired
    private SampleService service;

    @Autowired
    private ShipmentService shipmentService;


    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            shipmentService.weeklyReport();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}

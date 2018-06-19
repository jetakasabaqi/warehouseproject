package com.project.service;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

@Service
public class SampleService {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(SampleService.class);

    public void hello() {
        LOG.info("Hello World!");
    }
}

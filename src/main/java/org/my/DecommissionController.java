package org.my;

import javax.annotation.PreDestroy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DecommissionController {

    private static final Logger LOG = LoggerFactory.getLogger(DecommissionController.class);

    private static final File DECOMMISSION_REQUEST_FILE = new File("/tmp/decommission-requested");
    private static final File DECOMMISSION_RESULT_FILE = new File("/dev/termination-log");
    private static final String DECOMMISSIONED_FLAG = "DECOMMISSIONED";


    @Value("${decommission.failure.rate}")
    private double decommissionFailureRate;

    @PreDestroy
    public void randomDecommission() throws IOException {
        if (DECOMMISSION_REQUEST_FILE.exists()) {
            LOG.info("Decommission of the current pod has been requested");
            if (Math.random() >= decommissionFailureRate) {
                LOG.info("Decommission will be completed successfully");
                try (PrintWriter out = new PrintWriter(new FileWriter(DECOMMISSION_RESULT_FILE))) {
                    out.println(DECOMMISSIONED_FLAG);
                }
            } else {
                LOG.warn("Decommission will fail. The controller will retry to decommission again later");
            }
        } else {
            LOG.info("Decommission of the current pod should not be done");
        }
    }


}

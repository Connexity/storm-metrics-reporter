package com.github.staslev.storm.metrics.yammer;

import com.github.staslev.storm.metrics.StormMetricProcessor;
import com.yammer.metrics.reporting.JmxReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SimpleJMXStormMetricProcessor extends SimpleStormMetricProcessor {

    public static final Logger LOG = LoggerFactory.getLogger(SimpleJMXStormMetricProcessor.class);

    public SimpleJMXStormMetricProcessor(final String topologyName,
                                         final Map config) {
        super(topologyName, config);
        new JmxReporter(StormMetricProcessor.metricsRegistry).start();
        LOG.info("Metrics JMXReport started");
    }
}

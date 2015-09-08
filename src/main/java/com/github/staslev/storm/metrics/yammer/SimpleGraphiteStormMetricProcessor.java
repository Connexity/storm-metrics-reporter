package com.github.staslev.storm.metrics.yammer;

import com.github.staslev.storm.metrics.Metric;
import com.github.staslev.storm.metrics.StormMetricProcessor;
import com.yammer.metrics.reporting.GraphiteReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SimpleGraphiteStormMetricProcessor extends SimpleStormMetricProcessor {

    public static final String REPORT_PERIOD_IN_SEC = "metric.reporter.graphite.report.period.sec";
    private static final int DEFAULT_REPORT_PERIOD_SEC = 30;

    public static final String METRICS_HOST = "metric.reporter.graphite.report.host";
    private static final String DEFAULT_METRICS_HOST = "localhost";

    public static final String METRICS_PORT = "metric.reporter.graphite.report.port";
    private static final int DEFAULT_METRICS_PORT = 8080;


    public static final Logger LOG = LoggerFactory.getLogger(SimpleGraphiteStormMetricProcessor.class);

    @SuppressWarnings("FieldCanBeLocal")
    private final GraphiteReporter graphiteReporter;

    public SimpleGraphiteStormMetricProcessor(final String topologyName,
                                              final Map config) {
        super(topologyName, config);

        try {
            graphiteReporter = new GraphiteReporter(StormMetricProcessor.metricsRegistry,
                    getGraphiteServerHost(config),
                    getGraphiteServerPort(config),
                    Metric.joinNameFragments("Storm", topologyName));

            graphiteReporter.start(getGraphiteReportPeriod(config), TimeUnit.SECONDS);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getGraphiteServerHost(final Map config) {
        return config.containsKey(METRICS_HOST) ?
                config.get(METRICS_HOST).toString() :
                DEFAULT_METRICS_HOST;
    }

    private int getGraphiteServerPort(final Map config) {
        return config.containsKey(METRICS_PORT) ?
                Integer.parseInt(config.get(METRICS_PORT).toString()) :
                DEFAULT_METRICS_PORT;
    }

    private int getGraphiteReportPeriod(final Map config) {
        return config.containsKey(REPORT_PERIOD_IN_SEC) ?
                Integer.parseInt(config.get(REPORT_PERIOD_IN_SEC).toString()) :
                DEFAULT_REPORT_PERIOD_SEC;
    }
}

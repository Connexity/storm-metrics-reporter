package com.github.staslev.storm.metrics;

import backtype.storm.metric.api.IMetricsConsumer;
import com.yammer.metrics.core.MetricsRegistry;

/**
 * Responsible for processing a metric reported by Storm.
 * <br/><br/>
 * NOTE: The implementing class must take into account that the reporting granularity is taskId,
 * that is, it should make sure it does not overwrite values by aggregating incoming value incorrectly (for instance,
 * aggregating per workerHost-port-componentId is wrong, since the various tasks might overwrite each other's values.
 */
public interface StormMetricProcessor {

  MetricsRegistry metricsRegistry = new MetricsRegistry();

  void process(final Metric metric, final IMetricsConsumer.TaskInfo taskInfo);
}

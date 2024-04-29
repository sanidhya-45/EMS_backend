package com.target.ems.metrics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.*;

import java.util.Random;

@RestController
public class MetricsController {

  private static final Meter sampleMeter =
  GlobalOpenTelemetry.getMeter("com.target.springreactform");

/**
* Counters will track the number of times something happened or the running total of
* the values of things that have happened, depending on how you use it.
* <p>
* One way you might query the counter below is as follows to get the sum of values
* for the numbers in every interval:
* {@code
*  SELECT sum("isum")
*    FROM "sample_counter"
*    WHERE "application" = 'springreactform' and $timeFilter
*    GROUP BY time($__interval)
* }
*/
private static final LongCounter sampleCounter =
  sampleMeter
      .counterBuilder("sample_counter")
      .setDescription("Counts the value of the numbers generated.")
      .setUnit("{number}")
      .build();

/**
* Histograms will give you information about the values of the numbers itself. You
* can use them to give you an average, display the histogram, generate approximate
* percentiles, etc.
* <p>
* One way you might query the histogram below is as follows to get the average values
* for the numbers in every interval:
* {@code
*  SELECT sum("isum") / sum("icount")
*    FROM "sample_histogram"
*    WHERE "application" = 'springreactform' and $timeFilter
*    GROUP BY time($__interval)
* }
*/
private static final DoubleHistogram sampleHistogram =
  sampleMeter
      .histogramBuilder("sample_histogram")
      .setDescription("Collects info about the distribution of the numbers generated.")
      .setUnit("{number}")
      .build();

private static int lastNumber;

/**
* Gauges will track the value of something in the system as it changes. Readings are
* typically recorded every metric interval.
* <p>
* One way you might query the gauge below is as follows to get latest value in every
* interval:
* {@code
*  SELECT last("last")
*    FROM "sample_gauge"
*    WHERE "application" = 'springreactform' and $timeFilter
*    GROUP BY time($__interval), *
* }
*/
private static final ObservableDoubleGauge sampleGauge =
  sampleMeter
      .gaugeBuilder("sample_gauge")
      .setDescription("Record the last number generated.")
      .setUnit("{number}")
      .buildWithCallback(m -> m.record(lastNumber));

private static final Random rng = new Random(System.nanoTime());

  @GetMapping("metrics")
  public String metrics() {
    int number = rng.nextInt(100);
    lastNumber = number;
    sampleCounter.add(number);
    sampleHistogram.record(number);
    return String.format("Hello New Number: %d!", number);
  }
}

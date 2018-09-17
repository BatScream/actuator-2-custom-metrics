# actuator-2-custom-metrics

Build status: [![build_status](https://travis-ci.org/BatScream/actuator-2-custom-metrics.svg?branch=master)](https://travis-ci.org/BatScream/actuator-2-custom-metrics)

Custom metrics for spring boot actuator 2

The intention of this project is to have a bunch of custom endpoints to extract and transform the required metric information from the metrcis registry. I felt this is required when our infrastructure is not yet ready to consume the newer version of the metrics API provided my actuator 2 which in turn uses micrometer.

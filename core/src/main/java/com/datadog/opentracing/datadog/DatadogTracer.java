package com.datadog.opentracing.datadog;

import datadog.opentracing.DDTracer;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

public final class DatadogTracer {

    private DatadogTracer(){}

    public static Tracer get(String serviceName) {
        Tracer tracer = new DDTracer(serviceName);
        if (!GlobalTracer.registerIfAbsent(tracer)) {
            return GlobalTracer.get();
        }
        return tracer;
    }

}

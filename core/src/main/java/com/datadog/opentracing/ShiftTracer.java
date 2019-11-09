package com.datadog.opentracing;

import com.datadog.opentracing.datadog.DatadogTracer;
import com.datadog.opentracing.jaeger.JaegerTracer;
import io.opentracing.Tracer;

import java.util.Optional;

public class ShiftTracer {

    public static final String ENV_TRACER_NAME = "TRACER_NAME";

    public static final String JAEGER = "JAEGER";
    public static final String DATADOG = "DATADOG";

    private static Optional<String> tracerName;

    static {
        tracerName = Optional.ofNullable(System.getenv(ENV_TRACER_NAME));
        if (!tracerName.isPresent()) tracerName = Optional.ofNullable(System.getProperty(ENV_TRACER_NAME, JAEGER));
    }

    private ShiftTracer(){}

    public static Tracer getTracer(String serviceName) {
        if (tracerName.get().equalsIgnoreCase(DATADOG)) {
            return DatadogTracer.get(serviceName);
        } else {
            return JaegerTracer.get(serviceName);
        }
    }

}

import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.trace.{Span, SpanKind, Tracer}

object OpenTelemetry {

  val tracer: Tracer = GlobalOpenTelemetry.tracerBuilder("test-trace").build()

  def createInternalSpan(name: String): Span = {
    val span = tracer
    .spanBuilder(name)
    .setSpanKind(SpanKind.INTERNAL)
    .startSpan()
    span.makeCurrent()
    span
  }

  //import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
  //import io.opentelemetry.context.propagation.ContextPropagators
  //import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
  //import io.opentelemetry.sdk.OpenTelemetrySdk
  //import io.opentelemetry.sdk.resources.Resource
  //import io.opentelemetry.sdk.trace.SdkTracerProvider
  //import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
  //import io.opentelemetry.semconv.resource.attributes.ResourceAttributes

  //def init(): Unit = {
  //  val resource = Resource.getDefault.toBuilder
  //    .put(ResourceAttributes.SERVICE_NAME, "scala-otel-java")
  //    .build()

  //  val spanExporter = OtlpGrpcSpanExporter.builder
  //    .setEndpoint("http://localhost:4317")
  //    .setCompression("gzip")
  //    .build

  //  val sdkTracerProvider = SdkTracerProvider.builder
  //    .addSpanProcessor(BatchSpanProcessor.builder(spanExporter).build)
  //    .setResource(resource)
  //    .setSampler(Sampler.alwaysOn())
  //    .build

  //  OpenTelemetrySdk.builder
  //    .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance))
  //    .setTracerProvider(sdkTracerProvider)
  //    .buildAndRegisterGlobal
  //}

}

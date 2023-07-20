import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.trace.{Span, SpanKind}
import io.opentelemetry.context.Context
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object FutureTestMain extends App {
  val logger = LoggerFactory.getLogger(getClass.getSimpleName)

  val tracer = GlobalOpenTelemetry.tracerBuilder("test-future-trace").build()
  val span = tracer
    .spanBuilder("init")
    .setSpanKind(SpanKind.SERVER)
    .startSpan()
  span.makeCurrent()
  val f = for {
    _ <- Future {
      logger.info("Context.current 1: {}", Context.current())
      val span = tracer
        .spanBuilder("future_1")
        .setSpanKind(SpanKind.INTERNAL)
        .startSpan()
      Thread.sleep(100)
      span.end()
    }
    _ = new Thread {
      override def run() = {
        logger.info("Context.current thread: {}:", Context.current())
        Thread.sleep(100)
      }
    }.start()
    _ <- Future {
      logger.info("Context.current 2: {}", Context.current())
      val span = tracer
        .spanBuilder("future_2")
        .setSpanKind(SpanKind.INTERNAL)
        .startSpan()
      Thread.sleep(20)
      span.end()
    }
  } yield {
    val span = Span.current()
    logger.info("Span.current : {}", span)
    logger.info("Top Span : {}", this.span)
    span.end()
  }
  Await.result(f, Duration.Inf)
}

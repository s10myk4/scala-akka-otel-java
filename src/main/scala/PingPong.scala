import OpenTelemetry.tracer
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import io.opentelemetry.api.trace.{Span, SpanKind}

object PingPong {
  sealed trait Cmd
  final case class Ping(msg: String, actorRef: ActorRef[Cmd]) extends Cmd
  final case class Pong(msg: String) extends Cmd

  def apply(): Behavior[Cmd] = Behaviors.setup { ctx =>
    Behaviors.receiveMessage {
      case Ping(msg, actorRef) =>
        val span = OpenTelemetry.createInternalSpan("receive_message:Ping")
        Thread.sleep(50)
        actorRef ! Pong(msg)
        ctx.log.info("Span.current: {}", Span.current())
        span.end()
        Behaviors.same
      case Pong(msg) =>
        val span = OpenTelemetry.createInternalSpan("receive_message:Pong")
        Thread.sleep(70)
        ctx.log.info("received msg: {}", msg)
        ctx.log.info("Span.current: {}", Span.current())
        span.end()
        Behaviors.same
    }
  }

}

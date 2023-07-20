import PingPong.Ping
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}
import io.opentelemetry.api.trace.SpanKind

import scala.io.Source.stdin

object ActorTestMain extends App {

  private val span = OpenTelemetry.tracer
    .spanBuilder("init-actor")
    .setSpanKind(SpanKind.SERVER)
    .startSpan()
  span.makeCurrent()

  private def init(): Behavior[Any] = Behaviors.setup { ctx =>
    val ping = ctx.spawn(PingPong(), "ping")
    val pong = ctx.spawn(PingPong(), "pong")
    for (line <- stdin.getLines()) {
      ping ! Ping("hoge:" + line, pong)
    }

    Behaviors.same
  }

  ActorSystem[Any](
    init(),
    "top-actor"
  )
  println("---- Please enter some text. ----")

  span.end()
}

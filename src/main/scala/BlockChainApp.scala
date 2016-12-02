import ExchangeRateTicker.StartTicking
import akka.actor.ActorSystem

/**
  * Created by marcel on 2-12-2016.
  */
object BlockChainApp extends App{

  override def main(args:Array[String]) = {
    val system = ActorSystem()
    val ticker = system.actorOf(ExchangeRateTicker.props)
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.duration._
    system.scheduler.schedule(
      0 milliseconds,
      3 seconds,
      ticker,
      StartTicking)
  }

}

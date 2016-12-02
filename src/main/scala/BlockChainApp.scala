import ExchangeRateActor.{ConvertEuroToBTC, SellEuroPrice}
import akka.actor.ActorSystem
import akka.util.Timeout
import scala.util.{Failure, Success}

/**
  * Created by marcel on 2-12-2016.
  */
object BlockChainApp extends App{



  override def main(args:Array[String]) = {

    val system = ActorSystem()
    val exchangeRate = system.actorOf(ExchangeRateActor.props, "exchangeRate")

    import scala.concurrent.duration._
    implicit val timeout = Timeout(5 seconds)

    import akka.pattern.ask
    import scala.concurrent.ExecutionContext.Implicits.global

    val euroPriceFuture = exchangeRate ? SellEuroPrice
    euroPriceFuture.onComplete {
      case Success(euroPrice) =>
        println(s"SELL 1 BTC == €$euroPrice")
      case Failure(e) => e.printStackTrace()
    }

    val btcOneEuroPriceFuture = exchangeRate ? ConvertEuroToBTC(java.math.BigDecimal.valueOf(1))
    btcOneEuroPriceFuture.onComplete {
      case Success(btcPrice) => println(s"BUY €1 == $btcPrice BTC")
      case Failure(e) => e.printStackTrace()
    }

  }

}

import ExchangeRateActor.{ConvertEuroToBTC, SellEuroPrice, SellPrice}
import akka.actor.{Actor, ActorLogging, Props}
import info.blockchain.api.exchangerates.ExchangeRates

/**
  * Created by marcel on 2-12-2016.
  */

object ExchangeRateActor {
  def props:Props = Props(new ExchangeRateActor())

  case class SellPrice(currency:String)
  case class SellEuroPrice(currency:String)
  case class ConvertEuroToBTC(euro:java.math.BigDecimal)
}

class ExchangeRateActor extends Actor {

  private val ticker = ExchangeRates.getTicker()
  private val euroCurrency = "EUR"

  override def receive: Receive = {
    case SellPrice(currency) => {
      sender() ! ticker.get(currency).getSell
    }
    case SellEuroPrice => self forward SellPrice(euroCurrency)
    case ConvertEuroToBTC(euro) => sender() ! ExchangeRates.toBTC(euroCurrency, euro)
  }
}

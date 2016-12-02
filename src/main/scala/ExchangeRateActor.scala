import ExchangeRateActor.{ConvertEuroToBTC, Price, SellEuroPrice, SellPrice}
import akka.actor.{Actor, Props}
import info.blockchain.api.exchangerates.ExchangeRates

class ExchangeRateActor extends Actor {

  private val ticker = ExchangeRates.getTicker()
  private val euroCurrency = "EUR"

  override def receive: Receive = {
    case SellPrice(currency) => {
      sender() ! Price(ticker.get(currency).getSell, currency)
    }
    case SellEuroPrice => self forward SellPrice(euroCurrency)
    case ConvertEuroToBTC(euro) => sender() ! ExchangeRates.toBTC(euroCurrency, euro)
  }
}

/**
  * Created by marcel on 2-12-2016.
  */

object ExchangeRateActor {
  def props: Props = Props(new ExchangeRateActor())

  case class SellPrice(currency: String)

  case class SellEuroPrice(currency: String)

  case class Price(value: java.math.BigDecimal, currency: String)

  case class ConvertEuroToBTC(euro: java.math.BigDecimal)

}

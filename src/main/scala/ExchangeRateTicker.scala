import java.util.concurrent.atomic.AtomicInteger

import ExchangeRateActor.{Price, SellEuroPrice}
import ExchangeRateTicker.StartTicking
import akka.actor.{Actor, ActorLogging, Props}

class ExchangeRateTicker extends Actor with ActorLogging {

  private val exchangeRate = context.actorOf(ExchangeRateActor.props)

  private val lastPrice = new AtomicInteger()

  override def receive: Receive = {
    case StartTicking => {
      exchangeRate ! SellEuroPrice
    }
    case Price(value, currency) => {
      val intValue = value.intValue()
      lastPrice.getAndSet(intValue) match {
        case 0 => log.info("First known price: {} {}", value, currency)
        case up if up > intValue => log.info("Up : {} {}", value, currency)
        case down if down < intValue => log.info("Down : {} {}", value, currency)
        case _ => log.info("Same : {} {}", value, currency)
      }
    }
  }
}

/**
  * Created by marcel on 2-12-2016.
  */

object ExchangeRateTicker {
  def props: Props = Props(new ExchangeRateTicker)

  case class StartTicking()

}

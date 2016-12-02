import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import ExchangeRateActor.{Price, SellEuroPrice}
import ExchangeRateTicker.StartTicking
import akka.actor.{Actor, Props}

class ExchangeRateTicker extends Actor {

  private val exchangeRate = context.actorOf(ExchangeRateActor.props)

  override def receive: Receive = {
    case StartTicking => {
      exchangeRate ! SellEuroPrice
    }
    case Price(value, currency) => val now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")); println(s"$now : BTC == $value $currency")
  }
}

/**
  * Created by marcel on 2-12-2016.
  */

object ExchangeRateTicker {
  def props: Props = Props(new ExchangeRateTicker)

  case class StartTicking()

}

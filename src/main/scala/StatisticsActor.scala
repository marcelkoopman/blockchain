import StatisticsActor.{BlocksSize, Difficulty}
import akka.actor.{Actor, Props}
import info.blockchain.api.statistics.Statistics

class StatisticsActor extends Actor {

  override def receive: Receive = {
    case Difficulty => {
      val statistics = Statistics.get();
      sender() ! statistics.getDifficulty
    }

    case BlocksSize => {
      val statistics = Statistics.get();
      sender() ! statistics.getBlocksSize
    }

  }
}

/**
  * Created by marcel on 2-12-2016.
  */

object StatisticsActor {
  def props: Props = Props(new StatisticsActor())

  case class Difficulty()

  case class BlocksSize()

}

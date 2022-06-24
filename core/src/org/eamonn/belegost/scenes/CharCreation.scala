package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.{Belegost, Geometry, Scene, Text, d}

class CharCreation extends Scene {
  var race: Option[playerRace] = None
  var pClass: Option[playerClass] = None
  var tStats: List[Int] = List.empty
  var stats: List[Int] = List.empty
  var done = false
  var quit = false
  for (i <- 0 until 6) {
    tStats = d(3, 6) :: tStats
  }

  override def init(): InputAdapter = new CharControl(this)

  override def update(delta: Float): Option[Scene] = {
    if (quit) Some(new Home)
    else {
      for {
        r <- race
        c <- pClass
        if (stats.nonEmpty)
      } yield new Game(r, c, stats)
    }

  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    if (race.isEmpty) {
      Text.mediumFont.setColor(Color.WHITE)
      Text.mediumFont.draw(
        batch,
        "a: Human",
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit
      )
    } else if (pClass.isEmpty) {

      Text.mediumFont.setColor(Color.WHITE)
      Text.mediumFont.draw(
        batch,
        "a: Fighter",
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit
      )
    } else if (stats.isEmpty) {
      Text.mediumFont.setColor(Color.WHITE)
      Text.mediumFont.draw(
        batch,
        s" ${tStats(0)} \n  ${tStats(1)} \n  ${tStats(2)} \n  ${tStats(
          3
        )} \n  ${tStats(4)} \n  ${tStats(5)} \n r: reroll, \n y: confirm",
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit
      )
    } else {

      Text.mediumFont.setColor(Color.WHITE)
      Text.mediumFont.draw(
        batch,
        "y: Begin \n n: Quit",
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit
      )
    }
  }
}
sealed trait playerRace {
  val name: String
}
object Races {
  case object Human extends playerRace {
    val name = "Human"
  }
}
sealed trait playerClass {
  val name: String
}
object Classes {
  case object Fighter extends playerClass {
    val name = "Fighter"
  }
}

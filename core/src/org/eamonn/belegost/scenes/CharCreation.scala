package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.{Belegost, Geometry, Scene, Text}

class CharCreation extends Scene {
  var race: Option[playerRace] = None
  var pClass: Option[playerClass] = None
  var done = false
  var quit = false

  override def init(): InputAdapter = new CharControl(this)

  override def update(delta: Float): Option[Scene] = {
    if (quit) Some(new Home)
    else {
      for {
        r <- race
        c <- pClass
      } yield new Game(r, c)
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

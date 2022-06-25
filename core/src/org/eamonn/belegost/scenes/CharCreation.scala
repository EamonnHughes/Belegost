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
    Text.mediumFont.setColor(Color.WHITE)

    if (race.isEmpty) {
      Text.mediumFont.setColor(Color.WHITE)
      Text.mediumFont.draw(
        batch,
        " a: Human \n b: Dwarf \n c: Elf",
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit
      )
    } else if (pClass.isEmpty) {

      Text.mediumFont.setColor(Color.WHITE)
      Text.mediumFont.draw(
        batch,
        " a: Fighter \n b: Wizard",
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit
      )
    } else if (stats.isEmpty) {
      Text.mediumFont.setColor(Color.WHITE)
      Text.mediumFont.draw(
        batch,
        s" ${tStats(0)} \n ${tStats(1)} \n ${tStats(2)} \n ${tStats(
          3
        )} \n ${tStats(4)} \n ${tStats(5)} \n r: reroll, \n y: confirm",
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit
      )
    } else {

      Text.mediumFont.setColor(Color.WHITE)
      Text.mediumFont.draw(
        batch,
        " y: Begin \n n: Quit",
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit
      )
    }
  }
}
sealed trait playerRace {
  val name: String
  val xpMod: Float
  val statBonus: List[Int]
}
object Races {
  case object Human extends playerRace {
    val name = "Human"
    val xpMod = 1
    val statBonus = List(0, 0, 0, 0, 0, 0)
  }
  case object Dwarf extends playerRace {
    val name = "Dwarf"
    val xpMod = 1.2f
    val statBonus = List(2, -1, 3, -1, 1, -1)
  }
  case object Elf extends playerRace {
    val name = "Elf"
    val xpMod = 1.5f
    val statBonus = List(-2, 1, -3, 4, 2, 1)
  }
}
sealed trait playerClass {
  val name: String
  val statBonus: List[Int]
  val caster: Boolean
  val hitDie: Int
}
object Classes {
  case object Fighter extends playerClass {
    val name = "Fighter"
    val statBonus = List(2, 1, 1, -1, 0, 0)
    val caster = false
    val hitDie = 10
  }
  case object Wizard extends playerClass {
    val name = "Wizard"
    val statBonus = List(-1, -1, -1, 3, 2, 1)
    val caster = true
    val hitDie = 5
  }
}

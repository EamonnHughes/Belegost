package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Location
import org.eamonn.belegost.{
  Belegost,
  Geometry,
  NavMenu,
  Scene,
  Text,
  d,
  menuItem
}

class CharCreation extends Scene {
  var race: Option[playerRace] = None
  var pClass: Option[playerClass] = None
  var tStats: List[Int] = List.empty
  var stats: List[Int] = List.empty
  var done = false
  var quit = false
  var rolls = 3
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

  var raceSelect = NavMenu(
    List(
      menuItem("Human", () => { race = Some(Races.Human) }),
      menuItem("Dwarf", () => { race = Some(Races.Dwarf) }),
      menuItem("Elf", () => { race = Some(Races.Elf) })
    ),
    Location(2, (Geometry.ScreenHeight / Belegost.screenUnit).toInt - 2),
    3,
    6
  )
  var classSelect = NavMenu(
    List(
      menuItem("Fighter", () => { pClass = Some(Classes.Fighter) }),
      menuItem("Wizard", () => { pClass = Some(Classes.Wizard) })
    ),
    Location(9, (Geometry.ScreenHeight / Belegost.screenUnit).toInt - 2),
    2,
    6
  )

  override def render(batch: PolygonSpriteBatch): Unit = {
    raceSelect.draw(batch)
    classSelect.draw(batch)
    raceSelect.update()
    classSelect.update()

    Text.mediumFont.setColor(Color.WHITE)
    Text.mediumFont.draw(
      batch,
      s" ${tStats(0)} \n ${tStats(1)} \n ${tStats(2)} \n ${tStats(
        3
      )} \n ${tStats(4)} \n ${tStats(5)} \n ${if (rolls > 0) "r: reroll"
      else "no more rerolls"} \n ENTER: confirm",
      Belegost.screenUnit * 16,
      Geometry.ScreenHeight - Belegost.screenUnit * 2
    )

  }
}
sealed trait playerRace {
  val name: String
  val xpMod: Float
  val statBonus: List[Int]
  val desc: String
}
object Races {
  case object Human extends playerRace {
    val name = "Human"
    val xpMod = 1
    val statBonus = List(0, 0, 0, 0, 0, 0)
    val desc =
      "A human, one of the most diverse and widespread race in the lands. No bonuses to stats, but levels up faster than other races."
  }
  case object Dwarf extends playerRace {
    val name = "Dwarf"
    val xpMod = 1.2f
    val statBonus = List(2, -1, 3, -1, 1, -1)
    val desc =
      "A dwarf, of the sturdy race that lives deep within the mountains and harbors grudges for many years. Greatly increased strength and constitution and an increase to wisdom, with the reduction of all other stats."
  }
  case object Elf extends playerRace {
    val name = "Elf"
    val xpMod = 1.5f
    val statBonus = List(-2, 1, -3, 4, 2, 1)
    val desc =
      "An elf, born into those reclusive people that live far longer than any mortal race. increased dexterity, and charisma, greatly increased intelligence and wisdom, and severe drawbacks to constitution and charisma. They level up slower than other races."
  }
}
sealed trait playerClass {
  val name: String
  val statBonus: List[Int]
  val caster: Boolean
  val hitDie: Int
  val desc: String
}
object Classes {
  case object Fighter extends playerClass {
    val name = "Fighter"
    val statBonus = List(2, 1, 1, -1, 0, 0)
    val caster = false
    val hitDie = 10
    val desc = "A warrior trained in the use of many weapons."
  }
  case object Wizard extends playerClass {
    val name = "Wizard"
    val statBonus = List(-1, -1, -1, 3, 2, 1)
    val caster = true
    val hitDie = 5
    val desc = "A scholar that has spent many years studying the arcane arts."
  }
}

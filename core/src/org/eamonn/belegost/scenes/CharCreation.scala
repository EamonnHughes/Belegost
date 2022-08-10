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
  var name: String = ""
  var rolls = 3
  for (i <- 0 until 6) {
    tStats = d(3, 6) :: tStats
  }
  var nameConfirmed = false

  override def init(): InputAdapter = new CharControl(this)

  override def update(delta: Float): Option[Scene] = {
    if (quit) Some(new Home)
    else {
      for {
        r <- race
        c <- pClass
        if stats.nonEmpty
        if nameConfirmed
      } yield new Game(r, c, stats, name)
    }

  }

  var classOptions: List[playerClass] =
    List(Classes.Fighter, Classes.Ranger, Classes.Wizard, Classes.Dev)
  var raceOptions: List[playerRace] =
    List(Races.Human, Races.Dwarf, Races.Elf, Races.Dev)

  var raceSelect: NavMenu = NavMenu(
    List(
      menuItem("Human", () => { race = Some(Races.Human) }),
      menuItem("Dwarf", () => { race = Some(Races.Dwarf) }),
      menuItem("Elf", () => { race = Some(Races.Elf) }),
      menuItem("", () => { race = Some(Races.Dev) })
    ),
    Location(2, (Geometry.ScreenHeight / Belegost.screenUnit).toInt - 2),
    3,
    6
  )
  var classSelect: NavMenu = NavMenu(
    List(
      menuItem("Fighter", () => { pClass = Some(Classes.Fighter) }),
      menuItem("Ranger", () => { pClass = Some(Classes.Ranger) }),
      menuItem("Wizard", () => { pClass = Some(Classes.Wizard) }),
      menuItem("", () => { pClass = Some(Classes.Dev) })
    ),
    Location(9, (Geometry.ScreenHeight / Belegost.screenUnit).toInt - 2),
    3,
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
      s" ${tStats.head} \n ${tStats(1)} \n ${tStats(2)} \n ${tStats(
        3
      )} \n ${tStats(4)} \n ${tStats(5)} \n ${if (rolls > 0) "r: reroll"
      else "no more rerolls"} \n ENTER: confirm",
      Belegost.screenUnit * 16,
      Geometry.ScreenHeight - Belegost.screenUnit * 2
    )
    Text.mediumFont.setColor(Color.WHITE)
    Text.mediumFont.draw(
      batch,
      raceOptions(raceSelect.selected).desc + " " + classOptions(
        classSelect.selected
      ).desc,
      Belegost.screenUnit / 2,
      Belegost.screenUnit * 10
    )
    Text.mediumFont.draw(
      batch,
      "Name: " + name,
      Belegost.screenUnit * 2,
      Belegost.screenUnit * 12
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
      "A human, one of the most diverse and widespread race in the lands. No bonuses to stats, but levels up faster \nthan other races."
  }
  case object Dwarf extends playerRace {
    val name = "Dwarf"
    val xpMod = 1.2f
    val statBonus = List(2, -1, 3, -1, 1, -1)
    val desc =
      "A dwarf, of the sturdy race that lives deep within the mountains and harbors grudges for many years. Greatly \nincreased strength and constitution and an increase to wisdom, with the reduction of all other stats."
  }
  case object Elf extends playerRace {
    val name = "Elf"
    val xpMod = 1.5f
    val statBonus = List(-2, 1, -3, 4, 2, 1)
    val desc =
      "An elf, born into those reclusive people that live far longer than any mortal race. increased dexterity, \nand charisma, greatly increased intelligence and wisdom, and severe drawbacks to constitution and strength. \nThey level up slower than other races."
  }
  case object Dev extends playerRace {
    val name = "Entity"
    val xpMod = 1f
    val statBonus = List(50, 50, 50, 50, 50, 50)
    val desc =
      "An Entity from another universe."
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
  case object Ranger extends playerClass {
    val name = "Ranger"
    val statBonus = List(1, 2, 1, 0, 1, -2)
    val caster = false
    val hitDie = 10
    val desc = "A trained survivalist, tracker, warrior, and huntsman."
  }
  case object Wizard extends playerClass {
    val name = "Wizard"
    val statBonus = List(-1, -1, -1, 3, 2, 1)
    val caster = true
    val hitDie = 5
    val desc = "A scholar that has spent many years studying the arcane arts."
  }
  case object Dev extends playerClass {
    val name = "Entity"
    val statBonus = List(50, 50, 50, 50, 50, 50)
    val caster = true
    val hitDie = 50
    val desc = "An Unknowable force from beyond."
  }
}

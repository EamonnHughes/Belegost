package org.eamonn.belegost.scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import org.eamonn.belegost.d
import org.eamonn.belegost.scenes.Classes.{Fighter, Wizard}
import org.eamonn.belegost.scenes.Races.{Dwarf, Elf, Human}
class CharControl(charCreation: CharCreation) extends InputAdapter {
  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {

    true
  }

  override def keyDown(keycode: Int): Boolean = {
    if (charCreation.race.isEmpty) {

      if (keycode == Keys.UP) {
        charCreation.raceSelect.up()
        charCreation.raceSelect.update()
      } else if (keycode == Keys.DOWN) {
        charCreation.raceSelect.down()
        charCreation.raceSelect.update()
      } else if (keycode == Keys.ENTER || keycode == Keys.RIGHT) {
        charCreation.raceSelect.used()
      }
    } else if (charCreation.pClass.isEmpty) {

      if (keycode == Keys.UP) {
        charCreation.classSelect.up()
        charCreation.classSelect.update()
      } else if (keycode == Keys.DOWN) {
        charCreation.classSelect.down()
        charCreation.classSelect.update()
      } else if (keycode == Keys.ENTER || keycode == Keys.RIGHT) {
        charCreation.classSelect.used()
      }
    } else if (charCreation.stats.isEmpty) {

      if (keycode == Keys.R && charCreation.rolls > 0) {
        charCreation.rolls -= 1
        charCreation.tStats = List.empty
        for (i <- 0 until 6) {
          charCreation.tStats =
            List(d(6), d(6), d(6), d(6)).sorted.tail.sum :: charCreation.tStats
        }
      }
      if (keycode == Keys.ENTER) {
        charCreation.pClass.foreach(pC => {
          charCreation.tStats = charCreation.tStats.sorted.reverse
          if (pC == Classes.Fighter) {
            charCreation.stats =
              charCreation.tStats(3) :: charCreation.stats //cha
            charCreation.stats =
              charCreation.tStats(4) :: charCreation.stats //wis
            charCreation.stats =
              charCreation.tStats(5) :: charCreation.stats //int
            charCreation.stats =
              charCreation.tStats(1) :: charCreation.stats //con
            charCreation.stats =
              charCreation.tStats(2) :: charCreation.stats //dex
            charCreation.stats =
              charCreation.tStats.head :: charCreation.stats //str
          } else if (pC == Classes.Wizard) {

            charCreation.stats =
              charCreation.tStats(3) :: charCreation.stats //cha
            charCreation.stats =
              charCreation.tStats(1) :: charCreation.stats //wis
            charCreation.stats =
              charCreation.tStats.head :: charCreation.stats //int
            charCreation.stats =
              charCreation.tStats(2) :: charCreation.stats //con
            charCreation.stats =
              charCreation.tStats(4) :: charCreation.stats //dex
            charCreation.stats =
              charCreation.tStats(5) :: charCreation.stats //str
          } else if (pC == Classes.Ranger) {

            charCreation.stats =
              charCreation.tStats(4) :: charCreation.stats //cha
            charCreation.stats =
              charCreation.tStats(3) :: charCreation.stats //wis
            charCreation.stats =
              charCreation.tStats(5) :: charCreation.stats //int
            charCreation.stats =
              charCreation.tStats(2) :: charCreation.stats //con
            charCreation.stats =
              charCreation.tStats.head :: charCreation.stats //dex
            charCreation.stats =
              charCreation.tStats(1) :: charCreation.stats //str
          } else {
            charCreation.stats = charCreation.tStats
          }
        })
      }
    } else if (!charCreation.nameConfirmed) {
      if (keycode == Keys.ENTER && charCreation.name.nonEmpty) {
        charCreation.nameConfirmed = true
      } else if (keycode == Keys.BACKSPACE) {
        charCreation.name = charCreation.name.dropRight(1)
      } else if (keycode == Keys.SPACE && charCreation.name.length < 20) {
        charCreation.name = charCreation.name + " "
      } else if (
        Keys.toString(keycode).length == 1 && charCreation.name.length < 20
      ) {
        charCreation.name = charCreation.name + Keys.toString(keycode)

      }
    }
    true
  }
}

package org.eamonn.belegost.scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import org.eamonn.belegost.d
import org.eamonn.belegost.scenes.Classes.Fighter
import org.eamonn.belegost.scenes.Races.{Dwarf, Human}
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

      if (keycode == Keys.A) {
        charCreation.race = Some(Human)
      }
      if (keycode == Keys.B) {
        charCreation.race = Some(Dwarf)
      }
    } else if (charCreation.pClass.isEmpty) {

      if (keycode == Keys.A) {
        charCreation.pClass = Some(Fighter)
      }
    } else if (charCreation.stats.isEmpty) {

      if (keycode == Keys.R) {
        charCreation.tStats = List.empty
        for (i <- 0 until 6) {
          charCreation.tStats =
            List(d(6), d(6), d(6), d(6)).sorted.tail.sum :: charCreation.tStats
        }
      }
      if (keycode == Keys.Y) {
        charCreation.pClass.foreach(pC => {
          charCreation.tStats = charCreation.tStats.sorted.reverse
          if (pC == Classes.Fighter) {
            charCreation.stats = charCreation.tStats(3) :: charCreation.stats
            charCreation.stats = charCreation.tStats(4) :: charCreation.stats
            charCreation.stats = charCreation.tStats(5) :: charCreation.stats
            charCreation.stats = charCreation.tStats(1) :: charCreation.stats
            charCreation.stats = charCreation.tStats(2) :: charCreation.stats
            charCreation.stats = charCreation.tStats(0) :: charCreation.stats
          } else {
            charCreation.stats = charCreation.tStats
          }
        })
      }
    } else {

      if (keycode == Keys.Y) {
        charCreation.done = true
      }
      if (keycode == Keys.N) {
        charCreation.quit = true
      }
    }
    true
  }
}
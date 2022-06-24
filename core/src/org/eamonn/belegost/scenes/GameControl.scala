package org.eamonn.belegost.scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import org.eamonn.belegost.Classes.FireBolt
import org.eamonn.belegost.{Belegost, Click, Geometry}
import org.eamonn.belegost.util.Location

class GameControl(game: Game) extends InputAdapter {
  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    if (button == 1) {
      game.player.destination = Location(
        (screenX / Belegost.screenUnit).toInt - Belegost.translationX,
        ((Geometry.ScreenHeight - screenY) / Belegost.screenUnit).toInt - Belegost.translationY
      )
    }

    true
  }
  override def touchUp(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    true
  }

  override def keyDown(keycode: Int): Boolean = {
    game.keysPressed = keycode :: game.keysPressed
    if (keycode == Keys.I) {
      game.player.inInventory = !game.player.inInventory
    }
    if (keycode == Keys.M && game.player.playerClass.caster) {
      game.player.inSpellList = !game.player.inSpellList
    }
    if (game.player.inInventory) {
      if (keycode == Keys.UP) {
        game.player.currentInventoryItem =
          (game.player.currentInventoryItem - 1) max 0
      }
      if (keycode == Keys.DOWN) {

        game.player.currentInventoryItem =
          (game.player.currentInventoryItem + 1) min game.player.inventory.length - 1
      }
    }
    if (game.player.inSpellList) {
      if (keycode == Keys.UP) {
        game.player.currentSpell = (game.player.currentSpell - 1) max 0
      }
      if (keycode == Keys.DOWN) {

        game.player.currentSpell =
          (game.player.currentSpell + 1) min game.player.spellList.length - 1
      }
      if (keycode == Keys.ENTER) {
        game.casting =
          game.player.spellList(game.player.currentSpell) :: game.casting
        game.player.spellSlots -= game.player
          .spellList(game.player.currentSpell)
          .cost
        game.player.moved = true
        game.player.inSpellList = false
      }
    }
    true
  }

  override def keyUp(keycode: Int): Boolean = {
    game.keysPressed = game.keysPressed.filterNot(i => i == keycode)
    true
  }

}

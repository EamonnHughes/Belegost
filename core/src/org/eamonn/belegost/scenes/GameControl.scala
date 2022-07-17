package org.eamonn.belegost.scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import org.eamonn.belegost.Classes.FireBolt
import org.eamonn.belegost.{Belegost, Click, Geometry, Menu}
import org.eamonn.belegost.util.Location

class GameControl(game: Game) extends InputAdapter {

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
    game.mx = (screenX / Belegost.screenUnit).toInt + Belegost.translationX
    game.my =
      ((Geometry.ScreenHeight.toInt - screenY) / Belegost.screenUnit).toInt + Belegost.translationY
    true
  }

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

      game.player.inEquip = false
      game.player.inSpellList = false
    }

    if (keycode == Keys.E) {
      game.player.inEquip = !game.player.inEquip
      game.player.inInventory = false
      game.player.inSpellList = false
    }
    if (keycode == Keys.M && game.player.playerClass.caster) {
      game.player.inSpellList = !game.player.inSpellList
      game.player.inInventory = false
      game.player.inEquip = false
    }
    if (game.player.inInventory && game.player.inventory.nonEmpty) {
      if (keycode == Keys.UP) { game.player.invMenu.up() }
      if (keycode == Keys.DOWN) { game.player.invMenu.down() }
      if (keycode == Keys.ENTER) {
        game.player.invMenu.update()
        game.player.invMenu.used()
        val (count, item) = game.player.inventory(game.player.invMenu.selected)
        item.use()
        if (count > 1) {
          game.player.inventory
            .update(game.player.invMenu.selected, (count - 1, item))
        } else {
          game.player.inventory.remove(game.player.invMenu.selected)
        }
        game.player.moved = true
        game.player.setInvMenu()
        game.player.equipMenuUpdate()
      }
    }
    if (game.player.inEquip) {
      if (keycode == Keys.UP) { game.player.equipMenu.up() }
      if (keycode == Keys.DOWN) { game.player.equipMenu.down() }
      if (keycode == Keys.ENTER) {
        game.player.equipMenu.update()
        game.player.equipMenu.used()
        game.player.setInvMenu()
        game.player.equipMenuUpdate()
        game.player.moved = true
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
        if (
          game.player.spellSlots - game.player
            .spellList(game.player.currentSpell)
            .cost >= 0
        ) {
          game.casting =
            game.player.spellList(game.player.currentSpell) :: game.casting
          game.player.spellSlots -= game.player
            .spellList(game.player.currentSpell)
            .cost
          game.player.moved = true
          game.player.inSpellList = false
        }
      }
    }
    if (game.shopIn.nonEmpty) {
      if (keycode == Keys.UP) {
        game.shopIn.foreach(shop => shop.invMenu.up())
      }
      if (keycode == Keys.DOWN) {
        game.shopIn.foreach(shop => shop.invMenu.down())
      }
      if (keycode == Keys.ENTER) {
        game.shopIn.foreach(shop => {
          if (game.player.money >= shop.inventory(shop.invMenu.selected)._1) {
            val index = game.player.inventory.indexWhere({ case (count, item) =>
              if (
                item == shop.inventory(shop.invMenu.selected)._2 && count < 40
              ) true
              else false
            })
            if (index < 0) {

              game.player.inventory
                .addOne((1, shop.inventory(shop.invMenu.selected)._2))
            } else {
              game.player.inventory(index) = (
                game.player.inventory(index)._1 + 1,
                game.player.inventory(index)._2
              )
            }
            game.player.money -= shop.inventory(shop.invMenu.selected)._1
            game.player.setInvMenu()
          }
        })
      }
      if (keycode == Keys.ESCAPE) {
        game.shopIn = None
      }
    }
    if (!game.player.moved && keycode == Keys.S) {
      game.player.target
    }
    true
  }

  override def keyUp(keycode: Int): Boolean = {
    game.keysPressed = game.keysPressed.filterNot(i => i == keycode)
    true
  }

}

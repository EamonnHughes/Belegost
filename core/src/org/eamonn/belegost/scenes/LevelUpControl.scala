package org.eamonn.belegost.scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import org.eamonn.belegost.d
class LevelUpControl(levelUp: LevelUp) extends InputAdapter {
  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    true
  }

  override def keyDown(keycode: Int): Boolean = {
    if (levelUp.statUpgrading) {
      if (keycode == Keys.UP) {
        levelUp.statBonuses.up
      } else if (keycode == Keys.DOWN) {
        levelUp.statBonuses.down

      }
      if (keycode == Keys.ENTER) {
        levelUp.statBonuses.used
        levelUp.statUpgrading = false
        levelUp.upgradingHealth = true
      }
    } else if (levelUp.upgradingHealth) {
      if (keycode == Keys.R && levelUp.hRolls > 0) {
        levelUp.healthUpgrade = d(levelUp.game.player.playerClass.hitDie)
        levelUp.hRolls -= 1
      }

      if (keycode == Keys.ENTER) {
        levelUp.upgradingHealth = false
      }
    }
    if (
      keycode == Keys.ENTER && !levelUp.upgradingHealth && !levelUp.statUpgrading
    ) {
      levelUp.gameBegin = true
    }
    true
  }
}

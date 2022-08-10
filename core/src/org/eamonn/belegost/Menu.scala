package org.eamonn.belegost

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.items.Item
import org.eamonn.belegost.util.Location

object Menu {
  def drawShop(shop: Shop, batch: PolygonSpriteBatch): Unit = {
    shop.invMenu.draw(batch)
  }
  def drawStats(batch: PolygonSpriteBatch, player: Player): Unit = {
    batch.setColor(Color.BLACK)
    batch.draw(
      Belegost.Square,
      -(Belegost.translationX) * Belegost.screenUnit,
      -(Belegost.translationY) * Belegost.screenUnit + Belegost.screenUnit,
      Belegost.screenUnit * 9,
      Belegost.screenUnit * 20
    )
    Text.smallFont.setColor(Color.WHITE)
    Text.smallFont.draw(
      batch,
      s" ${player.game.name} \n ${player.playerRace} ${player.playerClass} \n level: ${player.level} \n next: ${player.nextXP - player.XPvalue}" +
        s" \n str: ${player.strength} (${player.baseStr})\n dex: ${player.dexterity} (${player.baseDex})" +
        s"\n con: ${player.constitution} (${player.baseCon})\n int: ${player.intelligence} (${player.baseInt})" +
        s"\n wis: ${player.wisdom} (${player.baseWis})\n cha: ${player.charisma} (${player.baseCha})" +
        s"\n health: ${player.health} / ${player.maxHealth} ${if (player.playerClass.caster) {
          "\n spell slots:" + player.spellSlots + s"/ ${player.maxSS}"
        } else { "" }} \n AC: ${player.armorClass} " +
        s" \n speed: ${player.speed} \n GP: ${player.money} \n Light Level: ${player.lightDist} \n Fuel: ${if (player.lightS.nonEmpty) {
          player.lightS.head.mod
        } else "None"} \n Hunger: %${player.hunger / 2.5}" +
        s"${if (player.statusEffects.nonEmpty) {
          player.statusEffects
            .map(statE => {
              s"\n ${statE.name} ${statE.duration}"
            })
            .mkString
        } else ""}",
      -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit,
      -(Belegost.translationY) * Belegost.screenUnit + Belegost.screenUnit * 20
    )

  }
  def drawInRange(batch: PolygonSpriteBatch, player: Player): Unit = {
    player.inRange.draw(batch)
  }
  def drawSpellBook(batch: PolygonSpriteBatch, player: Player): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(
      Belegost.Square,
      -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit,
      -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * ((player.spellList.length + 1)),
      Geometry.ScreenWidth - Belegost.screenUnit * 2,
      (Belegost.screenUnit * player.spellList.length) max 1
    )
    if (player.spellList.nonEmpty) {
      batch.setColor(Color.RED)
      batch.draw(
        Belegost.Square,
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.currentSpell + 2),
        Geometry.ScreenWidth - Belegost.screenUnit * 2,
        Belegost.screenUnit
      )
    }
    player.spellList.zipWithIndex.foreach({ case (item, index) =>
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"${item.name}  (${item.cost})",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (index + 1) + Text.smallFont.getDescent
      )
    })
    if (player.spellList.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "no available spells to cast",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit + Text.smallFont.getDescent
      )
    }
  }
  def drawInventory(batch: PolygonSpriteBatch, player: Player): Unit = {

    player.invMenu.draw(
      batch
    )

  }
  def drawEquipment(batch: PolygonSpriteBatch, player: Player): Unit = {
    player.equipMenu.draw(
      batch
    )
  }
}

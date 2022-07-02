package org.eamonn.belegost

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch

object Menu {
  def drawStats(batch: PolygonSpriteBatch, player: Player): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(
      Belegost.Square,
      -(Belegost.translationX) * Belegost.screenUnit,
      -(Belegost.translationY) * Belegost.screenUnit + Belegost.screenUnit,
      Belegost.screenUnit * 8,
      Belegost.screenUnit * 20
    )
    Text.smallFont.setColor(Color.BLACK)
    Text.smallFont.draw(
      batch,
      s" ${player.playerRace} ${player.playerClass} \n level: ${player.level} \n next: ${player.nextXP - player.XPvalue}" +
        s" \n str: ${player.strength} (${player.baseStr})\n dex: ${player.dexterity} (${player.baseDex})" +
        s"\n con: ${player.constitution} (${player.baseCon})\n int: ${player.intelligence} (${player.baseInt})" +
        s"\n wis: ${player.wisdom} (${player.baseWis})\n cha: ${player.charisma} (${player.baseCha})" +
        s"\n health: ${player.health} / ${player.maxHealth} ${if (player.playerClass.caster) {
          "\n spell slots:" + player.spellSlots + s"/ ${player.maxSS}"
        } else { "" }} \n AC: ${player.armorClass} " +
        s" \n speed: ${player.speed} \n GP: ${player.money} \n Light Level: ${player.lightDist} \n Fuel: ${if (player.lightS.nonEmpty) {
          player.lightS.head.mod
        } else "None"}",
      -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit,
      -(Belegost.translationY) * Belegost.screenUnit + Belegost.screenUnit * 20
    )
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
    batch.setColor(Color.WHITE)
    batch.draw(
      Belegost.Square,
      -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit,
      -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * ((player.inventory.length + 8)),
      Geometry.ScreenWidth - Belegost.screenUnit * 2,
      Belegost.screenUnit * (player.inventory.length + 7)
    )
    if (player.inventory.nonEmpty) {
      batch.setColor(Color.RED)
      batch.draw(
        Belegost.Square,
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.currentInventoryItem + 2),
        Geometry.ScreenWidth - Belegost.screenUnit * 2,
        Belegost.screenUnit
      )
      batch.setColor(Color.BLACK)
      batch.draw(
        Belegost.Square,
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 1),
        Geometry.ScreenWidth - Belegost.screenUnit * 2,
        Belegost.screenUnit / 20
      )
    }
    player.inventory.zipWithIndex.foreach({ case (item, index) =>
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "x" + item._1 + "   " + item._2.name,
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (index + 1) + Text.smallFont.getDescent
      )
    })
    player.helmet.foreach(helm => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Helmet: ${helm.name}",
        (-Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        (-Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 1) + Text.smallFont.getDescent
      )
    })
    if (player.helmet.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Helmet: None",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 1) + Text.smallFont.getDescent
      )
    }
    player.bodyArmor.foreach(body => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Armor: ${body.name}",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 2) + Text.smallFont.getDescent
      )
    })
    if (player.bodyArmor.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Armor: None",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 2) + Text.smallFont.getDescent
      )
    }
    player.gloves.foreach(glove => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Gloves: ${glove.name}",
        (-Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        (-Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 3) + Text.smallFont.getDescent
      )
    })
    if (player.gloves.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Gloves: None",
        (-Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        (-Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 3) + Text.smallFont.getDescent
      )
    }
    player.boots.foreach(boot => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Boots: ${boot.name}",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 4) + Text.smallFont.getDescent
      )
    })
    if (player.boots.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Boots: None",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 4) + Text.smallFont.getDescent
      )
    }
    player.cloak.foreach(cloak => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Cloak: ${cloak.name}",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 5) + Text.smallFont.getDescent
      )
    })
    if (player.cloak.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Cloak: None",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 5) + Text.smallFont.getDescent
      )
    }
    player.weapon.foreach(weapon => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Weapon: ${weapon.name}",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 6) + Text.smallFont.getDescent
      )
    })
    if (player.weapon.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Weapon: None",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 6) + Text.smallFont.getDescent
      )
    }
    player.lightS.foreach(light => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Light Source: ${light.name}",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 7) + Text.smallFont.getDescent
      )
    })
    if (player.lightS.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Light Source: None",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 7) + Text.smallFont.getDescent
      )
    }
  }
}

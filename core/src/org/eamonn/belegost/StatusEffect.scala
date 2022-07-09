package org.eamonn.belegost

trait StatusEffect {
  def tick(): Unit
  var duration: Int
  def initial(): Unit
  def end(): Unit
  var name: String
}

case class Poisoned(var duration: Int, player: Player, damage: Int, save: Int)
    extends StatusEffect {
  def tick(): Unit = {
    player.health -= damage
    duration -= 1
    if (d(20) + ((player.constitution - 10) / 2).toInt > save) {
      duration = 0
    }
  }
  def initial(): Unit = {
    player.baseCon -= 1
  }
  def end(): Unit = {
    player.baseCon += 1
  }
  var name = "Poison " + damage
}

case class Burning(var duration: Int, player: Player, damage: Int)
    extends StatusEffect {
  def tick(): Unit = {
    player.health -= damage
    duration -= 1
  }
  def initial(): Unit = {}
  def end(): Unit = {}

  var name = "Burning " + damage
}

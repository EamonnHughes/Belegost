package org.eamonn.belegost

trait StatusEffect {
  def tick(): Unit
  val target: Entity
  var duration: Int
  def initial(): Unit
  def end(): Unit
  var name: String
}

case class Poisoned(
    val target: Entity,
    var duration: Int,
    player: Player,
    damage: Int,
    save: Int
) extends StatusEffect {
  def tick(): Unit = {
    target.health -= damage
    duration -= 1

  }
  def initial(): Unit = {
    player.baseCon -= 1
  }
  def end(): Unit = {
    player.baseCon += 1
  }
  var name = "Poison " + damage
}

case class Burning(
    val target: Entity,
    var duration: Int,
    player: Player,
    damage: Int
) extends StatusEffect {
  def tick(): Unit = {
    target.health -= damage
    duration -= 1
  }
  def initial(): Unit = {}
  def end(): Unit = {}

  var name = "Burning " + damage
}

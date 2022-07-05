package org.eamonn.belegost.skills

import org.eamonn.belegost.Player

trait Skill {
  var cur: Int
  val max: Int
  def affect: Unit

}

case class Accurate(player: Player) extends Skill {
  var cur: Int = 0
  val max: Int = 3
  def affect: Unit = {
    player.accurateBonus = cur
  }
}

case class Evasive(player: Player) extends Skill {
  var cur: Int = 0
  val max: Int = 3
  def affect: Unit = {
    player.evasiveBonus = cur
  }
}

package org.eamonn.belegost

import org.eamonn.belegost.Belegost.garbage
import org.eamonn.belegost.util.TextureWrapper

import scala.collection.mutable

object AssetLoader {

  private val images = mutable.Map.empty[String, TextureWrapper]

  def image(path: String): TextureWrapper =
    images.getOrElseUpdate(path, TextureWrapper.load(path))


  def clear(): Unit = {
    images.clear()
  }
}

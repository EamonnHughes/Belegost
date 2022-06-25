package org.eamonn.belegost

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.{
  BitmapFont,
  GlyphLayout,
  PolygonSpriteBatch
}
import org.eamonn.belegost.Geometry._
import org.eamonn.belegost.util.GarbageCan

object Text {
  def loadFonts()(implicit garbage: GarbageCan): Unit = {
    val generator = new FreeTypeFontGenerator(
      Gdx.files.internal("BIZUDGothic-Regular.ttf")
    )
    val parameter = new FreeTypeFontGenerator.FreeTypeFontParameter
    parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + CharExtras
    mediumFont = garbage.add(generator.generateFont(parameter))
    smallFont = garbage.add(generator.generateFont(parameter))
    tinyFont = garbage.add(generator.generateFont(parameter))
    generator.dispose()
  }

  private val CharExtras = ""

  var mediumFont: BitmapFont = _
  var smallFont: BitmapFont = _
  var tinyFont: BitmapFont = _

  def draw(
      batch: PolygonSpriteBatch,
      font: BitmapFont,
      color: Color,
      text: String,
      x: Float = 0f,
      y: Float,
      width: Float = Geometry.ScreenWidth
  ): Unit = {
    font.setColor(color)
    font.draw(batch, text, x, y, width, CenterAlign, false)
  }

  def draw(
      batch: PolygonSpriteBatch,
      font: BitmapFont,
      color: Color,
      text: String,
      position: GlyphLayout => (Float, Float)
  ): Unit = {
    font.setColor(color)
    val layout = new GlyphLayout(font, text)
    val (x, y) = position(layout)
    font.draw(batch, layout, x, y)
  }

}

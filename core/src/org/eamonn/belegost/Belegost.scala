package org.eamonn.belegost

import com.badlogic.gdx.Application.{ApplicationType, LOG_INFO}
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.{Pixmap, Texture}
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.{ApplicationAdapter, Gdx, Input}
import org.eamonn.belegost.scenes.Home
import org.eamonn.belegost.util.{GarbageCan, TextureWrapper}

class Belegost extends ApplicationAdapter {
  import Belegost.garbage

  private var batch: PolygonSpriteBatch = _
  private var scene: Scene = _

  override def create(): Unit = {

    Gdx.input.setCatchKey(Input.Keys.BACK, true)

    batch = garbage.add(new PolygonSpriteBatch())

    Belegost.player = TextureWrapper.load("Player.png")

    Belegost.pShadow = TextureWrapper.load("pShadow.png")

    Belegost.Platform = TextureWrapper.load("Platform.png")

//    CarnDum.sound = CarnDum.loadSound("triangle.mp3")

    Text.loadFonts()

    setScene(new Home)
  }

  override def render(): Unit = {
    val delta = Gdx.graphics.getDeltaTime
    scene.update(delta) foreach setScene
    ScreenUtils.clear(.8f, .8f, 0.6f, 1)
    batch.begin()
    scene.render(batch)
    batch.end()
  }

  override def dispose(): Unit = {
    garbage.dispose()
  }

  private def setScene(newScene: Scene): Unit = {
    scene = newScene
    Gdx.input.setInputProcessor(scene.init())
  }

}

object Belegost {
  implicit val garbage: GarbageCan = new GarbageCan

  val screenUnit = (Geometry.ScreenWidth min Geometry.ScreenHeight) / 20
  var player: TextureWrapper = _
  var pShadow: TextureWrapper = _
  var Platform: TextureWrapper = _

  var sound: Sound = _

  def mobile: Boolean = isMobile(Gdx.app.getType)

  private def isMobile(tpe: ApplicationType) =
    tpe == ApplicationType.Android || tpe == ApplicationType.iOS

  private def loadSound(path: String)(implicit garbage: GarbageCan): Sound =
    garbage.add(Gdx.audio.newSound(Gdx.files.internal(path)))

}

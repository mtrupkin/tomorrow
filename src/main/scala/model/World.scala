package model

import java.io._
import java.nio.file.{StandardOpenOption, Path, Paths, Files}

import me.mtrupkin.console.{ScreenChar, Colors, RGB, Screen}
import me.mtrupkin.core.{Points, Matrix, Point, Size}

import scala.collection.immutable.HashMap
import scala.collection.JavaConversions._


/**
 * Created by mtrupkin on 12/19/2014.
 */

class World extends GameMap with Agents {

  lazy val size = Size(80, 27)
  var time: Long = 0

  val home:Home = new Home(Point(size.width/2, size.height/2), 0)
  val sisterHome: Home = new Home(Point(size.width/3, size.height/3), 0)
  var sites: List[Site] = List()
  var workers: List[Worker] = List()

  size.foreach(x => this(x) = Square.floor())

  def update(elapsed: Int) {
    time += elapsed
    workers.foreach(_.update(elapsed))
  }

  def render(screen: Screen): Unit = {
    size.foreach(s => screen(s) = this(s).sc)
    for {
      worker <- workers
    } screen(worker.position) = 'E'

    for {
      site <- sites
    } screen(site.position) = 'S'

    screen(home.position) = 'M'
    screen(sisterHome.position) = 'L'
  }

  def addSite(point: Point) = {
    val site = new Site(point, 100)
    sites = site :: sites
    workers = new Worker(home.position, site) :: workers

  }
}
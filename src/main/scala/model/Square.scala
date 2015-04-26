package model

import me.mtrupkin.console.Colors._
import me.mtrupkin.console.{RGB, Colors, Screen, ScreenChar}
import me.mtrupkin.core.{Points, Matrix, Point, Size}

import scala.Array._
import scala.collection.mutable.HashMap
import scala.util.Random

/**
 * Created by mtrupkin on 12/14/2014.
 */
trait Square {
  def name: String
  def sc: ScreenChar
  def move: Boolean
  var cost: Int = 1
}

trait GameMap {
  def size: Size
  val squares = ofDim[Square](size.width, size.height)

  def apply(p: Point): Square = squares(p.x)(p.y)
  def update(p: Point, value: Square): Unit = squares(p.x)(p.y) = value
}

class Floor extends Square {
  val name = "Floor"
  val move = true
  cost = 100
  def color  = 240 * (100 - cost)/100 + 15
  def sc = ScreenChar('\u00B7', fg = RGB(color, color, color))

}

class Wall(val sc: ScreenChar) extends Square {
  val name = "Wall"
  val move = false
}



object Square {
  implicit def toTile(s: ScreenChar): Square = {
    s.c match {
      case ' ' | '.' | 'E' => new Floor
      case _ => new Wall(s)
    }
  }
  def floor(): Square = new Floor
  val unexplored = new Wall(' ')
  def wall(sc: ScreenChar) = new Wall(sc)
}

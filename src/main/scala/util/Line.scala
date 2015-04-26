package util

import me.mtrupkin.core.Point

/**
 * Created by mtrupkin on 4/23/2015.
 */
object Line {
  /**
   * Uses the Bresenham Algorithm to calculate all points on a line from p0 to p1.
   * The iterator returns all points in the interval [start, end).
   * @return the iterator containing all points on the line
   */
  def bresenham(p1: Point, p0: Point): Iterator[Point] = {
    import scala.math.abs
    val d = Point(abs(p1.x - p0.x), abs(p1.y - p0.y))

    val sx = if (p0.x < p1.x) 1 else -1
    val sy = if (p0.y < p1.y) 1 else -1

    new Iterator[Point] {
      var p = p0
      var err = d.x - d.y

      def next = {
        val e2 = 2 * err
        if (e2 > -d.y) {
          err -= d.y
          p = p.copy(x = p.x + sx)
        }
        if (e2 < d.x) {
          err += d.x
          p = p.copy(y = p.y + sy)
        }
        p
      }
      def hasNext = !(p == p1)
    }
  }

  def hasLineOfMovement(p: Point, p0: Point): Boolean = {
    true
//    bresenham(p, p0).forall(world.levelMap(_).move)
  }

  def wayPoints(p0: Point, path: Seq[Point]): Seq[Point] = {
    def wayPoints(p0: Point, path: Seq[Point], smoothed: List[Point]): Seq[Point] = {
      // returns smooth path up to point p0
      // finds the line from p0 to a point on previousPath that is not blocked
      // returns that point and the rest of the path
      // param next is the last point that had a line of sight to p0
      def smoothPath(p0: Point, next: Point, previousPath: List[Point]): List[Point] = {
        previousPath match {
          case p :: ps => if (hasLineOfMovement(p, p0))
            smoothPath(p0, p, ps)
          else
            next :: previousPath
          case Nil => next :: Nil
        }
      }

      path match {
        case p :: ps => wayPoints(p, ps, smoothPath(p, p0, smoothed))
        // XXX: try to remove reverse and tail call
        case Nil => smoothed.reverse.tail
      }
    }

    wayPoints(p0, path, Nil)
  }


}

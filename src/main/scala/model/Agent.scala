package model

import me.mtrupkin.core.{StateMachine, Point}
import play.api.libs.json.Json
import util.{AStar, Line}

/**
 * Created by mtrupkin on 4/23/2015.
 */

trait Agents {
  self: World =>
  val pathFinder = new AStar(this, size)

  trait Agent extends StateMachine {
    type StateType = AgentState

    var position: Vector
    def speed: Double
    def update(elapsed: Int): Unit = currentState.update(elapsed)

    trait AgentState extends State {
      def update(elapsed: Int): Unit
    }

    class Arrive(val destination: Vector, arrived: () => AgentState) extends AgentState {
      def update(elapsed: Int): Unit = {
        val desired = destination - position
        if (desired.magnitude < 0.5) changeState(arrived()) else {
          val costPercent = (100 - self(Vector.VectorToPoint(position)).cost) / 100.0 + 1
          update(desired.normal(((speed * elapsed ) / 1000)*costPercent))
        }
      }

      def update(v: Vector): Unit = {
        val newPosition = position + v
         if (Vector.VectorToPoint(newPosition) != Vector.VectorToPoint(position)) {
           if (  self(position).cost > 1) self(position).cost = self(position).cost -1
         }
        position = newPosition
      }
    }
  }

  class Worker(var position: Vector, val destination: Site) extends Agent {
    var maxPayload: Int = 5
    var payload: Int = 0
    val speed: Double = 0.2
    lazy val initialState: AgentState = new Arrive(destination.position, arrivedSite)

    def arrivedSite(): AgentState = {
      destination.store = destination.store - maxPayload
      payload = maxPayload
      new Arrive(home.position, arrivedHome)
    }

    def arrivedHome(): AgentState = {
      home.store = home.store + payload
      payload = 0

      new Arrive(destination.position, arrivedSite)
    }

  }
}

class Site(val position: Point, var store: Int)
class Home(val position: Point, var store: Int)


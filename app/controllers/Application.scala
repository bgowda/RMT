package controllers

import play.api.mvc._

import models._
import com.codahale.jerkson.Json
import play.api.libs.json.Json._
import Project.ProjectFormat
import scala.Some


object Application extends Controller {


  def createClient = Action(parse.json) {
    request =>

      try {
        val clientParsed: Client = request.body.as[Client]
        Client.getByName(clientParsed.name) match {

          case Some(clientFound) => sys.error("Client name '" + clientParsed.name + "' is already present")
          case None => {
            val result: Option[Client] = Client.create(clientParsed)
            result match {
              case Some(client) => {
                val json = Json.generate(client)
                Ok(json).as("application/json")
              }
              case None => BadRequest("Client is not created").as("application/json")
            }
          }
        }
      } catch {
        case e => BadRequest("Error processing request, verify posted json request body.\n[ " + e.getMessage + " ]").as("application/json")
      }
  }

  def allClients = Action {
    try {
      val all: List[Client] = Client.findAll
      val json = Json.generate(all)
      Ok(json).as("application/json")
    } catch {
      case e => BadRequest("Error occured while retrieving data.")
    }
  }

  def createProject = Action(parse.json) {
    request =>
      val project: Project = request.body.as[Project]
      val id: Long = Project.create(project)
      Ok("Success!! New Project created with id : " + id).as("application/json")
  }

  def allProjects = Action {
    try {
      val all: List[Project] = Project.findAll
      val json = Json.generate(all)
      Ok(json).as("application/json")
    } catch {
      case e => BadRequest("Error occured while retrieving data.")
    }
  }

  def addResource = Action(parse.json) {
    request =>
      try {
        val body: Resource = request.body.as[Resource]
        val result: Option[Resource] = Resource.addResource(body)
        result match {
          case Some(resource) => {
            val json = Json.generate(resource)
            Ok(json).as("application/json")
          }
          case None => BadRequest("Resource is not added").as("application/json")
        }
      } catch {
        case e => BadRequest("Error processing request, verify posted json request body." + e.getMessage).as("application/json")
      }
  }

  def bookResource = Action(parse.json) {
    request =>
      try {
        val bookings: List[Booking] = request.body.as[List[Booking]]
        val result: List[Long] = bookings map (x => Booking.bookResource(x))
        Ok("Bookings added :" + result.toString).as("application/json")
      } catch {
        case e => BadRequest("Error processing request, verify posted json request body." + e.getMessage).as("application/json")
      }
  }

  def getBookings(projectName: String) = Action {
    request =>
      val bookings: List[ResourcesBooking] = Booking.getResourceBookingForProject(projectName)
      val json: String = Json.generate(bookings)
      Ok(json).as("application/json")
  }


  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def sayHello = Action(parse.json) {
    request =>
      (request.body \ "name").asOpt[String].map {
        name =>
          Ok("Hello " + name)
      }.getOrElse {
        BadRequest("Missing parameter [name]")
      }
  }

  def sayResponse = Action(parse.json) {
    request =>
      (request.body \ "name").asOpt[String].map {
        name =>
          Ok(toJson(
            Map("status" -> "OK", "message" -> ("Hello " + name))
          ))
      }.getOrElse {
        BadRequest(toJson(
          Map("status" -> "KO", "message" -> "Missing parameter [name]")
        ))
      }
  }

}
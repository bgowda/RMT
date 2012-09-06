package controllers

import play.api.mvc._
import models.{Client, Project, Resource}
import com.codahale.jerkson.Json
import play.api.libs.json.Json._
import Project.ProjectFormat
import anorm.Id
import exception.DuplicateException


object Application extends Controller{

  def createClient = Action(parse.json) { request =>
    try {
        val clientParsed: Client = request.body.as[Client]
        Client.getByName(clientParsed.name) match {

           case Some(clientFound) => sys.error("Client name '"+clientParsed.name+"' is already present")
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
      }catch {
        case e =>  BadRequest("Error processing request, verify posted json request body.\n[ "+e.getMessage+" ]").as("application/json")
      }
  }

  def allClients= Action {
    try {
      val all: List[Client] = Client.findAll
      val json = Json.generate(all)
      Ok(json).as("application/json")
    }catch {
      case e => BadRequest("Error occured while retrieving data.")
    }
  }

  def createProject= Action(parse.json) { request =>
      val project: Project = request.body.as[Project]
      val id: Long = Project.create(project)
      Ok("Success!! New Project created with id : "+id).as("application/json")
  }

  def allProjects= Action {
    try {
      val all: List[Project] = Project.findAll
      val json = Json.generate(all)
      Ok(json).as("application/json")
    }catch {
      case e => BadRequest("Error occured while retrieving data.")
    }
  }

  def addResource = Action(parse.json) { request =>
     try {
        val body:Resource = request.body.as[Resource]
        val result: Option[Resource] = Resource.addResource(body)
        result match {
          case Some(resource) => {
            val json = Json.generate(resource)
            Ok(json).as("application/json")
          }
          case None => BadRequest("Resource is not added").as("application/json")
        }
    }catch {
      case e =>  BadRequest("Error processing request, verify posted json request body."+e.getMessage).as("application/json")
    }
  }








  def resourceBooking = Action {

    val resources:List[Resource] = List(
    Resource(Id(1),1000,"Bindiya", "Jinnappa", "TDM", "Technology"),
    Resource(Id(2),1000, "Andrew", "Smith","TA","Technology"),
    Resource(Id(3),1000,"Sebastian", "Wolf","SWD","Technology"))
    val json = Json.generate(resources)

    Ok(json).as("application/json")
  }




  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  //val json: JsValue = Json.parse(jsonString)

  def sayHello = Action(parse.json) { request =>
    (request.body \ "name").asOpt[String].map { name =>
      Ok("Hello " + name)
    }.getOrElse {
      BadRequest("Missing parameter [name]")
    }
  }

  def sayResponse = Action(parse.json) { request =>
    (request.body \ "name").asOpt[String].map { name =>
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
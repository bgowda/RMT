package models

import collection.mutable.ListBuffer
import play.api.libs.json._
import play.api.libs.json.JsObject
import play.api.libs.json.JsString
import play.api.db._
import anorm._
import anorm.SqlParser._
import play.api.Play.current

/**
 * User: bindiya.jinnappa
 * Date: 21/08/2012
 * Time: 11:33
 */

//ERROR "not enough arguments for constructor Project" - make sure to provide default parameter
case class Project(
                     id: Pk[Long] = NotAssigned,
                     clientId:Long = 0,
                     code: String = "",
                     name: String = "",
                     owner: String = "",
                     location: String = ""
                    ) {

  var resources = ListBuffer[Resource]()

  def addResource(resource: Resource) = {
    resources += resource
  }
}

object Project {
  /**
   * Anorm parser for Todo, used to map sql to object
   **/
  val mapper = {
      get[Pk[Long]]("id") ~
      get[Long]("client_id") ~
      get[String]("code") ~
      get[String]("name") ~
      get[String]("owner") ~
      get[String]("location") map {
      case id ~ clientId ~ code ~ name ~ owner ~ location => Project(id, clientId, code, name, owner, location)
    }
  }

  def create(project: Project) = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
          insert into project values(
          (select next value for project_seq),
          {clientId},{code},{name},{owner},{location})
          """
        ).on(
          'clientId -> project.clientId,
          'code -> project.code,
          'name -> project.name,
          'owner -> project.owner,
          'location -> project.location
        ).executeInsert().get // returns unique primary key
    }
  }

  def all = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
            select * from project
          """
        ).as(Project.mapper *).toList
    }
  }

  // For Json Serialization and Deserialization
  implicit object ProjectFormat extends Format[Project] {
    //marshalling
    def reads(json: JsValue): Project = Project(
      (json \ "id").asOpt[String].map(_.trim).filterNot(_.isEmpty).map {
        id =>
          Id(id.toLong)
      }.getOrElse {
        NotAssigned
      },
      (json \ "clientId").asOpt[String].map(_.trim).filterNot(_.isEmpty).map {
        clientId => clientId.toLong
      }.getOrElse {
        sys.error("ClientId is required.")
      },
      (json \ "code").as[String],
      (json \ "name").as[String],
      (json \ "owner").as[String],
      (json \ "location").as[String]
    )
    //(json \ "role").asOpt[List[Role]].getOrElse(List()))

    //unmarshaling

    def writes(p: Project): JsValue = JsObject(Seq(
      "id" -> JsString(p.id.map(_.toString).getOrElse("")),
      "clientId" -> JsString(p.clientId.toString),
      "code" -> JsString(p.code),
      "name" -> JsString(p.name),
      "owner" -> JsString(p.owner),
      "location" -> JsString(p.location)
      //  "role" -> JsArray(u.role.map(toJson(_)))
    ))
  }

}

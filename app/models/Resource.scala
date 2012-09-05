package models

import anorm._
import play.api.db.DB
import play.api.Play.current
import play.api.libs.json.{JsString, JsObject, JsValue, Format}

case class Resource(id: Pk[Long] = NotAssigned,
                    projectId: Long = 0,
                    firstName: String = "",
                    lastName: String = "",
                    role: String = "",
                    department: String = "")

object Resource {

  val mapper = {
    SqlParser.get[Pk[Long]]("id") ~
    SqlParser.get[Long]("project_id") ~
    SqlParser.get[String]("firstName") ~
    SqlParser.get[String]("lastName") ~
    SqlParser.get[String]("role") ~
    SqlParser.get[String]("department") map {
      case id ~ projectId ~ firstName ~lastName ~ role ~ department => Resource(id,projectId,firstName,lastName,role,department)
    }
  }

  def addResource(resource: Resource):Option[Resource] = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
            insert into resource values((select next value for resource_seq),
            {projectId},{firstName},{lastName},{role},{department})
          """).on(
          'projectId -> resource.projectId,
          'firstName -> resource.firstName,
          'lastName -> resource.lastName,
          'role -> resource.role,
          'department -> resource.department
        ).executeInsert()
    }match {
      case Some(id) => get(id)
      case None => None
    }
  }

  def get(id:Long):Option[Resource] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          select * from resource where id= {resourceId}
        """
      ).on(
        'resourceId -> id
      ).as(Resource.mapper *).toList.headOption
    }
  }

  def allResourcesPerProject(projectId:Long): List[Resource] = {
     DB.withConnection{
       implicit connection =>
         SQL(
           """
              select * from resource where project_id = {projectId}
           """).on(
         'projectId -> projectId
         ).as(Resource.mapper *).toList
     }
  }


  // For Json Serialization and Deserialization
  implicit object ResourceFormat extends Format[Resource] {
    //marshalling
    def reads(json: JsValue): Resource = Resource(
      (json \ "id").asOpt[String].map(_.trim).filterNot(_.isEmpty).map {
        id =>
          Id(id.toLong)
      }.getOrElse {
        NotAssigned
      },
      (json \ "projectId").asOpt[String].map(_.trim).filterNot(_.isEmpty).map {
        clientId => clientId.toLong
      }.getOrElse {
        sys.error("ProjectId is required.")
      },
      (json \ "firstName").asOpt[String].getOrElse("NOT AVAILABLE"),
      (json \ "lastName").asOpt[String].getOrElse("NOT AVAILABLE"),
      (json \ "role").as[String],
      (json \ "department").as[String]
    )

    //unmarshaling
    def writes(r: Resource): JsValue = JsObject(Seq(
      "id" -> JsString(r.id.map(_.toString).getOrElse("")),
      "projectId" -> JsString(r.projectId.toString),
      "firstName" -> JsString(r.firstName),
      "lastName" -> JsString(r.lastName),
      "role" -> JsString(r.role),
      "department" -> JsString(r.department)
    ))
  }


}



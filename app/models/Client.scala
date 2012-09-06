package models

import play.api.db._
import anorm._
import play.api.Play.current
import play.api.libs.json.{JsString, JsObject, JsValue, Format}

/**
 * Created with IntelliJ IDEA.
 * User: dasbh
 * Date: 02/09/2012
 * Time: 20:22
 * To change this template use File | Settings | File Templates.
 */
case class Client(id: Pk[Long] = NotAssigned, name:String = "")

object Client {

  val mapper = {
    SqlParser.get[Pk[Long]]("id") ~
    SqlParser.get[String]("name") map {
      case id ~ name => Client(id, name)
    }
  }

  def create(client:Client):Option[Client] = {
          DB.withConnection { implicit connection =>

            SQL(
              """
                insert into client values ((select next value for client_seq),{name})
              """).on(
            'name -> client.name
            ).executeInsert()
          } match {
            case Some(id) => get(id)
            case None => None
          }
  }

  def get(id:Long):Option[Client] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          select * from client where id= {clientId}
        """
      ).on(
      'clientId -> id
      ).as(Client.mapper *).toList.headOption
    }
  }

  def getByName(name:String):Option[Client] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          select * from client where lower(name)= lower({name})
        """
      ).on(
        'name -> name
      ).as(Client.mapper *).toList.headOption
    }
  }

  def findAll = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
            select * from client
          """
        ).as(Client.mapper *).toList
    }
  }

  // For Json Serialization and Deserialization
  implicit object ClientFormat extends Format[Client] {
    //marshalling
    def reads(json: JsValue): Client = Client(
      (json \ "id").asOpt[String].map(_.trim).filterNot(_.isEmpty).map {
        id =>
          Id(id.toLong)
      }.getOrElse {
        NotAssigned
      },
      (json \ "name").as[String]
    )

      //unmarshaling

    def writes(p: Client): JsValue = JsObject(Seq(
      "id" -> JsString(p.id.map(_.toString).getOrElse("")),
      "name" -> JsString(p.name)
    ))
  }

}

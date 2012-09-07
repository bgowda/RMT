package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import java.util.{Locale, Date}
import play.api.libs.json.{JsString, JsObject, JsValue, Format}
import java.text.SimpleDateFormat

/**
 * Created with IntelliJ IDEA.
 * User: bindiya.jinnappa
 * Date: 03/09/2012
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
object BookingStatus extends Enumeration("AWAITING","TENTATIVE", "REQUIRED"){
    type BookingStatusType = Value
    val AWAITING, TENTATIVE, REQUIRED = Value
}

case class Booking(id:Pk[Long]= NotAssigned,
                    resourceId:Long = 0,
                    hours:Int = 0,
                    bookingDate:Date ,
                    status:BookingStatus.BookingStatusType)


case class ResourcesBooking(resourceId:Long,
                             firstName:String,
                             lastName:String,
                             role:String,
                             department:String,
                             hours:Int,
                             bookingDate:Date,
                             status:String
                             )
object Booking {

  def bookResource(booking:Booking):Long = {

    DB.withConnection{ implicit connection =>
       SQL(
         """
           insert into booking values ((select next value for booking_seq),
            {resourceId},{hours},{bookingDate},{status})
         """).on(
       'resourceId -> booking.resourceId,
       'hours -> booking.hours,
       'bookingDate -> booking.bookingDate,
       'status -> booking.status.toString
       ).executeInsert().get
    }
  }

  def getResourceBookingForProject(projectName:String) ={
    DB.withConnection{ implicit connection =>

    val  resourceBookings:List[(Long, String, String, String, String,
      Option[Long], Option[Date], Option[String] )] =
      SQL(
      """
          select r.id, r.firstName,r.project_id,r.lastName,r.role,r.department, b.hours,b.bookingDate, b.status
          from Resource r
          left join project p on r.project_id = p.id
          left outer join Booking b
          on  b.resource_id = r.id
           where lower(p.name) = lower({projectName})
      """).on(
      'projectName -> projectName
      ).as(long("id") ~ str("firstName") ~ str("lastName") ~ str("role") ~ str("department")
        ~ get[Option[Long]]("hours") ~  get[Option[Date]]("bookingDate") ~  get[Option[String]]("status") map(flatten) *)

      println("List of resource bookings found - "+resourceBookings)
      resourceBookings
    }

  }

  // For Json Serialization and Deserialization
  implicit object BookingFormat extends Format[Booking] {
    //marshalling
    def reads(json: JsValue): Booking = Booking(
      (json \ "id").asOpt[String].map(_.trim).filterNot(_.isEmpty).map {
        id =>
          Id(id.toLong)
      }.getOrElse {
        NotAssigned
      },
      (json \ "resourceId").asOpt[String].map(_.trim).filterNot(_.isEmpty).map {
        resourceId => resourceId.toLong
      }.getOrElse {
        sys.error("resourceId is mandatory. Should be a Number.")
      },
      (json \ "hours").asOpt[String].map(_.trim).filterNot(_.isEmpty).map {
        hours => hours.toInt
      }.getOrElse {
        0
      },
      (json \ "bookingDate").asOpt[String].map(_.trim).filterNot(_.isEmpty).map {
        bookingDate => new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse(bookingDate)
      }.getOrElse {
        sys.error("'bookingDate' is mandatory")
      },
      (json \ "status").asOpt[String].map(_.trim).filterNot(_.isEmpty).map {
        status => BookingStatus.withName(status)
      }.getOrElse {
        sys.error("'status' is mandatory")
      }
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

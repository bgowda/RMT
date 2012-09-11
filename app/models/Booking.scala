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
object BookingStatus extends Enumeration("AWAITING", "TENTATIVE", "REQUIRED","UNKNOWN") {
  type BookingStatusType = Value
  val AWAITING, TENTATIVE, REQUIRED, UNKNOWN = Value
}

case class Booking(id: Pk[Long] = NotAssigned,
                   resourceId: Long = 0,
                   hours: String = "",
                   bookingDate: Date,
                   status: BookingStatus.BookingStatusType)


case class ResourcesBooking(resourceId: Long,
                            firstName: String,
                            lastName: String,
                            role: String,
                            department: String,
                            hours: Option[String],
                            bookingDate: Option[Date],
                            status: Option[String]
                             )

object Booking {

  def bookResource(booking: Booking): Long = {

    DB.withConnection {
      implicit connection =>
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

  def getResourceBookingForProject(projectName: String, startDate:String, endDate:String) = {
    if ((startDate isEmpty) && (endDate isEmpty))
      sys.error("StartDate and EndDate must not be empty")
    executeQuery(projectName, parseDate(startDate), parseDate(endDate)) map {
      x => ResourcesBooking(x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8)
    }
  }

  private def executeQuery(projectName: String, startDate:Date, endDate:Date) = {
    DB.withConnection {
      implicit connection =>

        val resourceBookings: List[(Long, String, String, String, String, Option[String], Option[Date], Option[String])] =
          SQL(
            """
          select r.id, r.firstName,r.project_id,r.lastName,r.role,r.department, b.hours,b.bookingDate, b.status
          from Resource r
          left join project p on r.project_id = p.id
          left outer join Booking b
          on  b.resource_id = r.id
          where lower(p.name) = lower({projectName} )
          and ((b.bookingDate between {startDate}  and {endDate} ) or  (b.bookingDate is null))
            """).on(
            'projectName -> projectName,
            'startDate -> startDate,
            'endDate -> endDate
          ).as(long("id") ~ str("firstName") ~ str("lastName") ~ str("role") ~ str("department")
            ~ get[Option[String]]("hours") ~ get[Option[Date]]("bookingDate") ~ get[Option[String]]("status") map (flatten) *)

        println("List of resource bookings found - " + resourceBookings)
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
      (json \ "hours").asOpt[String].getOrElse(""),
      (json \ "bookingDate").asOpt[String].map(_.trim).filterNot(_.isEmpty).map {
        bookingDate => parseDate(bookingDate)
      }.getOrElse {
        sys.error("'bookingDate' is mandatory")
      },
      (json \ "status").asOpt[String].map(_.trim).filterNot(_.isEmpty).map {
        status => BookingStatus.withName(status)
      }.getOrElse {
        sys.error("'status' is mandatory")
      }
    )

    //unmarshaling

    def writes(b: Booking): JsValue = JsObject(Seq(
      "id" -> JsString(b.id.map(_.toString).getOrElse("")),
      "resourceId" -> JsString(b.resourceId.toString),
      "hours" -> JsString(b.hours),
      "bookingDate" -> JsString(b.bookingDate.toString),
      "status" -> JsString(b.status.toString)
    ))
  }


  def parseDate(date: String): Date = {
    try{
      val parsedDate: Date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(date)
      parsedDate
    }catch {
      case e => sys.error("Unable to parse Date, format required is 'dd-MMM-yyyy'")
    }
  }
}

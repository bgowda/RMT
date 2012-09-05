package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import java.util.Date

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
                    bookingDate:Date = Nil,
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

    println(resourceBookings)
      resourceBookings
    }

  }

}

import anorm.NotAssigned
import java.text.SimpleDateFormat
import java.util.Locale
import models._

import org.h2.jdbc.JdbcSQLException
import org.specs2.execute.Result
import play.api.test.FakeApplication
import play.api.test.Helpers._
import org.specs2.mutable._

/**
 * Created with IntelliJ IDEA.
 * User: bindiya.jinnappa
 * Date: 24/08/2012
 * Time: 12:36
 * To change this template use File | Settings | File Templates.
 */


class ProjectSpecDb extends Specification {
  val bookingDate = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse("3-SEP-2012");


  object FakeApplicationWithDbData extends Around {
    def around[T](t: => T)(implicit evidence$1: (T) => Result) = {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
        prepareDbWithFakeData()
        t
      }
    }

    def prepareDbWithFakeData() {
      val client: Option[Client] = Client.create(Client(NotAssigned, "MINI"))
      val id: Long = Project.create(Project(NotAssigned, client.get.id.get, "CODE 1", "Initial Sample Project", "Bindiya Jinnappa", "London"))
    }
  }

  sequential

  "Client model" should {
    "able to add new client" in FakeApplicationWithDbData {
      val result: Option[Client] = Client.create(Client(NotAssigned, "NIKE"))
      result.get mustEqual Client(result.get.id, "NIKE")
    }

    "able to get by Name" in FakeApplicationWithDbData {
      val result: Option[Client] = Client.getByName("Mini")
      result.get mustEqual Client(result.get.id, "MINI")
    }

    "get all clients" in FakeApplicationWithDbData {
      val result: List[Client] = Client.findAll
      result.size must be greaterThan(0)
    }
  }

  "Project model" should {
    " get all available projects" in FakeApplicationWithDbData {
      val result: List[Project] = Project.findAll
      result.size must be greaterThan(0)
    }

    " be able to create new project" in FakeApplicationWithDbData {
      val result: Long = Project.create(Project(NotAssigned, 1,"CODE", "Sample Project in test", "Bindiya Jinnappa", "London"))
      result must not be null
    }

  }

  "Resource Model " should {

      " able to add resources" in FakeApplicationWithDbData {
        val result: Option[Resource] = Resource.addResource(Resource(projectId = 1, firstName = "Bindiya", lastName = "Jinnappa", role = "TDM", department = "Technology"))
        result.get mustEqual Resource(result.get.id,1,"Bindiya","Jinnappa","TDM","Technology")
      }

      " Throw exception when add resources for invalid project id" in FakeApplicationWithDbData {
        (Resource.addResource(Resource(projectId = 0, firstName = "Bindiya", lastName = "Jinnappa", role = "TDM", department = "Technology"))) must throwA[JdbcSQLException]
      }

     "get all resources for a given project" in FakeApplicationWithDbData {
      Resource.addResource(Resource(projectId = 1, firstName = "Bindiya", lastName = "Jinnappa", role = "TDM", department = "Technology"))

      val resources: List[Resource] = Resource.allResourcesPerProject(1)
      println("Resources result is .... "+resources)
      resources.size must be greaterThan(0)

    }
  }

  "Booking Model " should {

       "able to book resource" in FakeApplicationWithDbData {

         val resource:Option[Resource] = Resource.addResource(Resource(projectId = 1, firstName = "Bindiya", lastName = "Jinnappa", role = "TDM", department = "Technology"))


         val bookingId = Booking.bookResource(Booking(NotAssigned,resource.get.id.get , hours = "5", bookingDate = bookingDate, status = BookingStatus.AWAITING))
         bookingId mustEqual 1
       }

        "validate startDate and endDate while retrieving all booking" in FakeApplicationWithDbData{
          (Booking.getResourceBookingForProject("Test", "","")) must throwA[RuntimeException] // "StartDate and EndDate must not be empty"
        }

       "get all booking for given resource and Project" in FakeApplicationWithDbData {
         val id: Long = Project.create(Project(NotAssigned, 1, "CODE 1", "Test Project", "Bindiya Jinnappa", "London"))

         val resource1:Option[Resource]  = Resource.addResource(Resource(projectId = id, firstName = "Bindiya", lastName = "Jinnappa", role = "TDM", department = "Technology"))
          Booking.bookResource(Booking(NotAssigned,resource1.get.id.get, hours = "5", bookingDate = bookingDate, status = BookingStatus.AWAITING))

         val resourceId2:Option[Resource]  = Resource.addResource(Resource(projectId = id, firstName = "Andrew", lastName = "Smith", role = "TA", department = "Technology"))
          Booking.bookResource(Booking(NotAssigned,resourceId2.get.id.get , hours = "5", bookingDate = bookingDate, status = BookingStatus.REQUIRED))

         val resourceId3:Option[Resource]  = Resource.addResource(Resource(projectId = id, firstName = "Sebastian", lastName = "Wolf", role = "SWD", department = "Technology"))

         val project: List[ResourcesBooking] = Booking.getResourceBookingForProject(projectName = "Test Project","10-Sep-2012","15-Sep-2012")
         project must have length(3)
       }

      "get all booking for given resource and invalid project" in FakeApplicationWithDbData {
        val resource1:Option[Resource]  = Resource.addResource(Resource(projectId = 1, firstName = "Bindiya", lastName = "Jinnappa", role = "TDM", department = "Technology"))
        Booking.bookResource(Booking(NotAssigned,resource1.get.id.get, hours = "5", bookingDate = bookingDate, status = BookingStatus.AWAITING))

        val resourceId2:Option[Resource]  = Resource.addResource(Resource(projectId = 1, firstName = "Andrew", lastName = "Smith", role = "TA", department = "Technology"))
        Booking.bookResource(Booking(NotAssigned,resourceId2.get.id.get , hours = "5", bookingDate = bookingDate, status = BookingStatus.REQUIRED))

        val resourceId3:Option[Resource]  = Resource.addResource(Resource(projectId = 1, firstName = "Sebastian", lastName = "Wolf", role = "SWD", department = "Technology"))

        val project: List[ResourcesBooking]  = Booking.getResourceBookingForProject(projectName = "silly","10-Sep-2012","15-Sep-2012")
        println(project)
        project must have length(0)
      }
  }
}


import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import play.api.test.{FakeRequest, FakeApplication}

/**
 * Created with IntelliJ IDEA.
 * User: bindiya.jinnappa
 * Date: 05/09/2012
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
class ApplicationSpec extends FlatSpec with ShouldMatchers {

  /*"A request to the getBars Action" should "respond with data" in {
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("ApplicationTest"))) {
     // inTransaction(AppDB.barTable insert Bar(Some("foo")))

      val result = controllers.Application.createClient(FakeRequest())
      status(result) should equal (OK)
      contentAsString(result) should include ("foo")
    }
  } */
}

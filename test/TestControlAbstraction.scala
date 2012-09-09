/**
 * Created with IntelliJ IDEA.
 * User: dasbh
 * Date: 02/09/2012
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
object TestControlAbstraction extends App with ProfileSupport {
  implicit val g ="hgjggjgj"

  val result:Int = profile("Sample Code"){
    var i = 1
    Thread.sleep(1)
    1
  }

  println(result)
}


trait ProfileSupport {

  def profile[T](name: String)(block: => T) (implicit value:String)= {
    println("Profiling code block '" + name + "'")
    val t1 = System.currentTimeMillis()
    val result: T = block
    val t2 = System.currentTimeMillis()
    println("Completed block " + name + "' took " + (t2 - t1) + " ms.")
    result
  }
}
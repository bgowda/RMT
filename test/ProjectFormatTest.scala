import anorm.NotAssigned
import models.Project
import org.scalatest.FunSuite
import play.api.libs.json.JsValue

/**
 * Created with IntelliJ IDEA.
 * User: bindiya.jinnappa
 * Date: 24/08/2012
 * Time: 09:57
 * To change this template use File | Settings | File Templates.
 */

class ProjectFormatTest extends FunSuite {
    val expectedValue = """{"id":"","clientId":"1","code":"sfewr","name":"Sample Project","owner":"Bindiya Jinnappa","location":"London"}"""
    val project: Project = Project(NotAssigned, 1,"sfewr", "Sample Project", "Bindiya Jinnappa", "London")

  test("Serialize from object to json") {
    val json: JsValue = Project.ProjectFormat.writes(project)

    expect(expectedValue) (json.toString())

  }

  test("DeSerialize from json to object") {
    val json: JsValue = Project.ProjectFormat.writes(project)

    val actualProject: Project = Project.ProjectFormat.reads(json)

    expect(actualProject)(project)

  }


}

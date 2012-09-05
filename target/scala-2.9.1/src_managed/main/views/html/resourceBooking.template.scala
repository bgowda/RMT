
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import views.html._
/**/
object resourceBooking extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[List[Resource],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(resources:List[Resource]):play.api.templates.Html = {
        _display_ {import helper._

import models._


Seq[Any](format.raw/*1.28*/("""

"""),_display_(Seq[Any](/*5.2*/main("List resource")/*5.23*/{_display_(Seq[Any](format.raw/*5.24*/("""

    <h1>"""),_display_(Seq[Any](/*7.10*/resources/*7.19*/.size)),format.raw/*7.24*/(""" resources(s)</h1>

    <ul>
        """),_display_(Seq[Any](/*10.10*/resources/*10.19*/.map/*10.23*/ {  resource =>_display_(Seq[Any](format.raw/*10.38*/("""
                <li>
                    """),_display_(Seq[Any](/*12.22*/resource/*12.30*/.firstName)),format.raw/*12.40*/(""" """),_display_(Seq[Any](/*12.42*/resource/*12.50*/.lastName)),format.raw/*12.59*/("""
                </li>
        """)))})),format.raw/*14.10*/("""
    </ul>
""")))})))}
    }
    
    def render(resources:List[Resource]) = apply(resources)
    
    def f:((List[Resource]) => play.api.templates.Html) = (resources) => apply(resources)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Sep 05 10:03:32 BST 2012
                    SOURCE: /Users/bindiya.jinnappa/Dev/projects/ResourceManagementTool/app/views/resourceBooking.scala.html
                    HASH: 7ef9738e0b453b0438f44ca3772dc1d0469f1b0d
                    MATRIX: 523->1|659->27|696->64|725->85|763->86|809->97|826->106|852->111|926->149|944->158|957->162|1010->177|1089->220|1106->228|1138->238|1176->240|1193->248|1224->257|1288->289
                    LINES: 19->1|25->1|27->5|27->5|27->5|29->7|29->7|29->7|32->10|32->10|32->10|32->10|34->12|34->12|34->12|34->12|34->12|34->12|36->14
                    -- GENERATED --
                */
            
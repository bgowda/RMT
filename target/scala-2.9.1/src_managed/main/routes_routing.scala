// @SOURCE:/Users/bindiya.jinnappa/Dev/projects/ResourceManagementTool/conf/routes
// @HASH:5527279ebed3d12fb856c20431c1b834bfe736b5
// @DATE:Wed Sep 05 10:03:31 BST 2012

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString

object Routes extends Router.Routes {


// @LINE:6
val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart("/"))))
                    

// @LINE:9
val controllers_Assets_at1 = Route("GET", PathPattern(List(StaticPart("/assets/"),DynamicPart("file", """.+"""))))
                    

// @LINE:15
val controllers_Application_createClient2 = Route("POST", PathPattern(List(StaticPart("/client/create"))))
                    

// @LINE:16
val controllers_Application_createProject3 = Route("POST", PathPattern(List(StaticPart("/project/create"))))
                    

// @LINE:17
val controllers_Application_allProjects4 = Route("GET", PathPattern(List(StaticPart("/project/all"))))
                    

// @LINE:18
val controllers_Application_addResource5 = Route("POST", PathPattern(List(StaticPart("/resource/add"))))
                    

// @LINE:22
val controllers_Application_sayHello6 = Route("POST", PathPattern(List(StaticPart("/sayHello"))))
                    

// @LINE:23
val controllers_Application_sayResponse7 = Route("POST", PathPattern(List(StaticPart("/sayResponse"))))
                    
def documentation = List(("""GET""","""/""","""controllers.Application.index"""),("""GET""","""/assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""),("""POST""","""/client/create""","""controllers.Application.createClient"""),("""POST""","""/project/create""","""controllers.Application.createProject"""),("""GET""","""/project/all""","""controllers.Application.allProjects"""),("""POST""","""/resource/add""","""controllers.Application.addResource"""),("""POST""","""/sayHello""","""controllers.Application.sayHello"""),("""POST""","""/sayResponse""","""controllers.Application.sayResponse"""))
             
    
def routes:PartialFunction[RequestHeader,Handler] = {        

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.index, HandlerDef(this, "controllers.Application", "index", Nil))
   }
}
                    

// @LINE:9
case controllers_Assets_at1(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(_root_.controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String])))
   }
}
                    

// @LINE:15
case controllers_Application_createClient2(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.createClient, HandlerDef(this, "controllers.Application", "createClient", Nil))
   }
}
                    

// @LINE:16
case controllers_Application_createProject3(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.createProject, HandlerDef(this, "controllers.Application", "createProject", Nil))
   }
}
                    

// @LINE:17
case controllers_Application_allProjects4(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.allProjects, HandlerDef(this, "controllers.Application", "allProjects", Nil))
   }
}
                    

// @LINE:18
case controllers_Application_addResource5(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.addResource, HandlerDef(this, "controllers.Application", "addResource", Nil))
   }
}
                    

// @LINE:22
case controllers_Application_sayHello6(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.sayHello, HandlerDef(this, "controllers.Application", "sayHello", Nil))
   }
}
                    

// @LINE:23
case controllers_Application_sayResponse7(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.sayResponse, HandlerDef(this, "controllers.Application", "sayResponse", Nil))
   }
}
                    
}
    
}
                
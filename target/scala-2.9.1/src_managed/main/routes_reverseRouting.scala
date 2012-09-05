// @SOURCE:/Users/bindiya.jinnappa/Dev/projects/ResourceManagementTool/conf/routes
// @HASH:5527279ebed3d12fb856c20431c1b834bfe736b5
// @DATE:Wed Sep 05 10:03:31 BST 2012

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString


// @LINE:23
// @LINE:22
// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:9
// @LINE:6
package controllers {

// @LINE:23
// @LINE:22
// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:6
class ReverseApplication {
    


 
// @LINE:23
def sayResponse() = {
   Call("POST", "/sayResponse")
}
                                                        
 
// @LINE:16
def createProject() = {
   Call("POST", "/project/create")
}
                                                        
 
// @LINE:18
def addResource() = {
   Call("POST", "/resource/add")
}
                                                        
 
// @LINE:17
def allProjects() = {
   Call("GET", "/project/all")
}
                                                        
 
// @LINE:15
def createClient() = {
   Call("POST", "/client/create")
}
                                                        
 
// @LINE:6
def index() = {
   Call("GET", "/")
}
                                                        
 
// @LINE:22
def sayHello() = {
   Call("POST", "/sayHello")
}
                                                        

                      
    
}
                            

// @LINE:9
class ReverseAssets {
    


 
// @LINE:9
def at(file:String) = {
   Call("GET", "/assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                        

                      
    
}
                            
}
                    


// @LINE:23
// @LINE:22
// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:9
// @LINE:6
package controllers.javascript {

// @LINE:23
// @LINE:22
// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:6
class ReverseApplication {
    


 
// @LINE:23
def sayResponse = JavascriptReverseRoute(
   "controllers.Application.sayResponse",
   """
      function() {
      return _wA({method:"POST", url:"/sayResponse"})
      }
   """
)
                                                        
 
// @LINE:16
def createProject = JavascriptReverseRoute(
   "controllers.Application.createProject",
   """
      function() {
      return _wA({method:"POST", url:"/project/create"})
      }
   """
)
                                                        
 
// @LINE:18
def addResource = JavascriptReverseRoute(
   "controllers.Application.addResource",
   """
      function() {
      return _wA({method:"POST", url:"/resource/add"})
      }
   """
)
                                                        
 
// @LINE:17
def allProjects = JavascriptReverseRoute(
   "controllers.Application.allProjects",
   """
      function() {
      return _wA({method:"GET", url:"/project/all"})
      }
   """
)
                                                        
 
// @LINE:15
def createClient = JavascriptReverseRoute(
   "controllers.Application.createClient",
   """
      function() {
      return _wA({method:"POST", url:"/client/create"})
      }
   """
)
                                                        
 
// @LINE:6
def index = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"/"})
      }
   """
)
                                                        
 
// @LINE:22
def sayHello = JavascriptReverseRoute(
   "controllers.Application.sayHello",
   """
      function() {
      return _wA({method:"POST", url:"/sayHello"})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:9
class ReverseAssets {
    


 
// @LINE:9
def at = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"/assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                                                        

                      
    
}
                            
}
                    


// @LINE:23
// @LINE:22
// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:9
// @LINE:6
package controllers.ref {

// @LINE:23
// @LINE:22
// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:6
class ReverseApplication {
    


 
// @LINE:23
def sayResponse() = new play.api.mvc.HandlerRef(
   controllers.Application.sayResponse(), HandlerDef(this, "controllers.Application", "sayResponse", Seq())
)
                              
 
// @LINE:16
def createProject() = new play.api.mvc.HandlerRef(
   controllers.Application.createProject(), HandlerDef(this, "controllers.Application", "createProject", Seq())
)
                              
 
// @LINE:18
def addResource() = new play.api.mvc.HandlerRef(
   controllers.Application.addResource(), HandlerDef(this, "controllers.Application", "addResource", Seq())
)
                              
 
// @LINE:17
def allProjects() = new play.api.mvc.HandlerRef(
   controllers.Application.allProjects(), HandlerDef(this, "controllers.Application", "allProjects", Seq())
)
                              
 
// @LINE:15
def createClient() = new play.api.mvc.HandlerRef(
   controllers.Application.createClient(), HandlerDef(this, "controllers.Application", "createClient", Seq())
)
                              
 
// @LINE:6
def index() = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq())
)
                              
 
// @LINE:22
def sayHello() = new play.api.mvc.HandlerRef(
   controllers.Application.sayHello(), HandlerDef(this, "controllers.Application", "sayHello", Seq())
)
                              

                      
    
}
                            

// @LINE:9
class ReverseAssets {
    


 
// @LINE:9
def at(path:String, file:String) = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]))
)
                              

                      
    
}
                            
}
                    
                
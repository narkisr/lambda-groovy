<!SLIDE title-slide>

# Operators #

<!SLIDE  execute>
.notes Here we see an example of method ref, we can referecne object methods directly
    @@@groovy
       value = String.&valueOf 

       println value.class

       println value(true).class

<!SLIDE  execute>
.notes Elvis operator supplies default when value is null, the safe navigation operator saves us from all those ifs

    @@@groovy
      person = [name:"bob",last:"builder",birth:null]

      println person.birth ?: new Date() // elvis

      println person.birth?.toString() // safe nav

<!SLIDE title-slide>

# The Groovy JDK (GDK) #

<!SLIDE  execute>

.notes Groovy enriches Java types making it more Groovy!, both readLines and first don't exist in Java.

    @@@groovy
      path = "/home/ronen/.zshrc"

      println new File(path).readLines().first()

<!SLIDE  execute>
.notes Collection types have a slew of functional methods added to them

    @@@groovy
      multipleOfTwo = [1, 2, 3].collect{it * 2} 

      sum = multipleOfTwo.inject(0){acc, v ->
         acc +=v
      }

      println multipleOfTwo

      println sum

<!SLIDE title-slide>

# Groovy Builders #

<!SLIDE  execute>
.notes Groovy builders use the builder pattern to construct all sorts of hierarchal tree structures, in this case a json string.

    @@@groovy
      import groovy.json.*
       
      println new JsonBuilder().book{
           name("groovy in action")
           isbn("9781935182443")
      }

<!SLIDE  execute>
.notes Another example is constructing an http request.

    @@@groovy
     @Grab(group="org.codehaus.groovy.modules.http-builder",
           module="http-builder", version="0.5.1" ) 
      import groovyx.net.http.HTTPBuilder
      import static groovyx.net.http.Method.GET
      import static groovyx.net.http.ContentType.HTML
 
      def http = new HTTPBuilder("http://ajax.googleapis.com")

	http.request( GET, JSON ) {
	  uri.path = "/ajax/services/search/web"
	  uri.query = [v:"1.0", q:"Calvin and Hobbes"]
        headers."User-Agent" = "Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4"

       response.success = { resp, json ->
          println resp.status
          json.responseData.results.each { 
             println "${it.titleNoFormatting} : ${it.visibleUrl}"
          }
       }

       response.failure = { resp ->
         println "Unexpected error: ${resp.status} : ${resp.statusLine.reasonPhrase}" 
       }
    }


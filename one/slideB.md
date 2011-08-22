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

# Builders #

<!SLIDE  execute small>
.notes Groovy builders use the builder pattern to construct all sorts of hierarchal tree structures, in this case XML

    @@@groovy
    import groovy.xml.*
       
    writer = new StringWriter()

    new MarkupBuilder(writer).books{
      book(name:"groovy in action", isbn:"9781935182443")
      book(name:"Joy of Clojure", isbn:"1935182641")
    }

    println writer

<!SLIDE  execute smaller>
.notes Another use case is Swing UI

    @@@groovy
    import java.awt.FlowLayout 
    import javax.swing.* 
    import groovy.swing.SwingBuilder  

     builder = new SwingBuilder()

     builder.frame(title:"Swinging with Groovy!", size:[290,100]) { 
      panel(layout:new FlowLayout()) { 
        ["Groovy", "Ruby", "Python"].each{
          checkBox(text:it)
        } 
      } 
     }.show() 



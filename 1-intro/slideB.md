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

<!SLIDE smaller>
.notes In order to create our builder we need to extend BuilderSupport, this builder creates a nested nodes list.

    @@@groovy
     public class NodeBuilder extends BuilderSupport {
 
       protected void setParent(Object parent, Object child) {
       }

       protected Object createNode(Object name) {
           return new Node(getCurrentNode(), name, new ArrayList());
       }

       protected Object createNode(Object name, Object value) {
           return new Node(getCurrentNode(), name, value);
       }

       protected Object createNode(Object name, Map attributes) {
           return new Node(getCurrentNode(), name, attributes, new ArrayList());
       }

       protected Object createNode(Object name, Map attributes, Object value) {
           return new Node(getCurrentNode(), name, attributes, value);
       }

       protected Node getCurrentNode() {
           return (Node) getCurrent();
       }
     }

<!SLIDE title-slide>

# Syntax tidbits #

<!SLIDE  execute smaller>
.notes Easy initialization

    @@@groovy
     class Foo {
      def a
      def b
     }

     println new Foo(a:1, b:2).dump()

<!SLIDE  execute smaller>
.notes multiple assignment

    @@@groovy
      def multiReturn(){
         [1,2,3]
      }

      (a,b,c) = multiReturn()

      println a + b + c


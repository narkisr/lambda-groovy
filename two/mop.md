<!SLIDE title-slide>

# MOP #

<!SLIDE smaller>
.notes All Groovy object method calls are routed through MOP calls (including java objects called from within Groovy).

    @@@groovy
    public interface GroovyObject {

      public Object invokeMethod(String methodName, Object args);

      public Object getProperty(String propertyName);

      public void setProperty(String propertyName, Object newValue);

      public MetaClass getMetaClass();

      public void setMetaClass(MetaClass metaClass);

    }
   
<!SLIDE smaller>
.notes Default implementation of GroovyObject

    @@@groovy
    public abstract class GroovyObjectSupport implements GroovyObject {

      // never persist the MetaClass
      private transient MetaClass metaClass;

      public GroovyObjectSupport() {
         this.metaClass = InvokerHelper.getMetaClass(this.getClass());
      }

      public Object getProperty(String property) {
         return getMetaClass().getProperty(this, property);
      }

      public void setProperty(String property, Object newValue) {
         getMetaClass().setProperty(this, property, newValue);
      }

      public Object invokeMethod(String name, Object args) {
         return getMetaClass().invokeMethod(this, name, args);
      }
      // ... 
    } 

<!SLIDE smaller execute>
.notes Each object has a meta class (even interfaces), meta class holds introspection and dynamic invocation methods.

    @@@groovy
      [String,List,"hello"].each {
        println it.metaClass.name
      }
       
      objectMethods = Object.methods.collect{it.name}

      interestingMethods = "hello".metaClass.class.methods.grep {
         !objectMethods.contains(it.name)
      }
       
      println interestingMethods.collect {it.name}

<!SLIDE smaller execute>
.notes Default metaClass on String is the ExpandoMetaClass which expands upon assignment (there are other implementations).

    @@@groovy
      String.metaClass.capitelize = {// -1 is the one before 0 

          "${delegate[0].toUpperCase()}${delegate[1..-1]}"
      } 

      println "hello".capitelize()

      "hello".metaClass.bye = {println "bye"} // change per instance

      "hello".bye()
      
      "bye".bye() // wont work
     

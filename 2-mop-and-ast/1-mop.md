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
.notes Here we add a method/property to String, this is similar to ruby open classes notion
    @@@groovy
      String.metaClass.upper =  {-> toUpperCase() }
      String.metaClass.foo = 1 // property
    
      println "bla".upper()
      println "bla".foo


<!SLIDE smaller execute>
.notes Category offers us the ability to MOP on a limited scope (single thread scope).

    @@@groovy
      class StringCategory {
        static String lower(String string) {
          return string.toLowerCase()
        }
      }
  
      use(StringCategory) {
        println "TeSt".lower()
      } 
         

<!SLIDE smaller execute>
.notes We can manipulate classes also by using Mixins, note that mixin is not an is-a relationship.
    @@@groovy
      class Logable {
        def info(message) {println message}
      }
      
      class Chatty {
         def talk(){
           info("im alive!")
         }
      }

      @Mixin(Logable)
      class ChattyIntrusive {
         def talk(){
           info("im alive!")
         }
      }

      Chatty.mixin Logable
      new Chatty().talk()
      new ChattyIntrusive().talk() 
       


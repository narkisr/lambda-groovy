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

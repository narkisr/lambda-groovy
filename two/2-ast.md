<!SLIDE title>
# AST Transformations #

<!SLIDE bullets>

  * Local transformations.
  * Global transformations.
  * AST viewer.

<!SLIDE  execute smaller>
.notes This transformation adds toString hashCode equals and tuple constructor

    @@@groovy
     import groovy.transform.Canonical

     @Canonical class Foo {
       def lets,play
     }

     println new Foo("lets","play").equals(new Foo("lets","play"))


<!SLIDE  execute smaller>
.notes Lazy initialization of data members

    @@@groovy

      class Container {
        @Lazy def expensive = {println "Im so precious!"}
      }
      c =  new Container()
      println "nothing happend yet!"
      c.expensive() 


<!SLIDE  execute>
.notes The following enables us to use Groovy script as safe extension language in larger programms.

    @@@groovy
     @TimedInterrupt(value=1)
     import groovy.transform.TimedInterrupt

     while(true); 


  

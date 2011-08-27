<!SLIDE title>
# AST Transformations #

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

  
<!SLIDE smaller>
.notes Implementing a custom transformation requires us to implement an AST visitor, we are using a builder to construct the AST (or build from String/Code).

    @@@groovy
     @Retention (RetentionPolicy.SOURCE)
     @Target ([ElementType.METHOD])
     @GroovyASTTransformationClass (["PrinterTransformation"])
     public @interface Printer { }

     @GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
     class PrinterTransformation implements ASTTransformation {

      void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        if(!astNodes || !astNodes[0] || !(astNodes[1] instanceof MethodNode)){
           return
        }

        MethodNode annotatedMethod = astNodes[1]
        def printStatment = new AstBuilder().buildFromSpec {
          expression {
            methodCall {
              variable "this"
              constant "println"
              argumentList {
                constant "Hey!"
              }
            }
          }
        }

       List<Statement> statements = annotatedMethod.getCode().getStatements()
       statements.add(printStatment[0])
      }
     }

<!SLIDE execute smaller>
.notes And it works, at this point we can view it in AST viewer and see the changes
   
    @@@groovy
      class Bla {
        @Printer
        def action(){
        }
      }
       
      new Bla().action()
       

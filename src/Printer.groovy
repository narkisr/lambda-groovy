
import java.lang.annotation.*
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*

@Retention (RetentionPolicy.SOURCE)
@Target ([ElementType.METHOD])
@GroovyASTTransformationClass (["PrinterTransformation"])
public @interface Printer { }

@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class PrinterTransformation implements ASTTransformation {

  void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
    if(!astNodes || !astNodes[0] || !(astNodes[1] instanceof MethodNode)) return

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
 
 

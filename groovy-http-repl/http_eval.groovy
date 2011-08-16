import java.text.SimpleDateFormat

set 'port', 4999

get("/") {
    def ua = headers['user-agent']
    "Your user-agent: ${ua} ${Eval.me('new Date()')}" 
}
    
get("/eval") {
    // Eval.me(params.code)
    def shell = new GroovyShell()
    def res = shell.evaluate(params.code).toString()
    println res
    res
}
    
get("/person/:id") {
	"Person #${urlparams.id}"
}

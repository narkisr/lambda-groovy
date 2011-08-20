<!SLIDE title-slide>
# Groovy #

<!SLIDE bullets>

* Introduction 
* Syntax bits (Literals, Lambdas, Operators)
* Advanced Scoping
* Builders
* MOP
* AST transformations (Mixins, Delegation)


<!SLIDE bullets>
# Introduction

* Created in 2003
* Dynamic Language with optional typing
* Java super set
* Batteries included


<!SLIDE  execute>
# Literals #

    @@@groovy
        list = [1,2,3]

        map = [one:1, two:2]

        println "1234" ==~ /\d+/


<!SLIDE  execute>
# Lambdas #

    @@@groovy
       say = {m -> println m}
       say("hello")

<!SLIDE  execute>

    @@@groovy
       def apply(name,action){
         action(name)
       }

       apply("hello"){v -> println v }
       apply("hello"){println it}

<!SLIDE  execute>

    @@@groovy
        say = {}
        println say instanceof Closure


<!SLIDE  execute>

    @@@groovy
       class Owner {
          def say = {m -> println m}
       }

       def o = new Owner()

       println o.say.metaClass.properties.collect{
         it.name
       }

       println o.say.owner == o 
       println o.say.delegate 

<!SLIDE  execute>
 
    @@@groovy
       def say = {println m}
       say.delegate = [m:2]
       say()
         
<!SLIDE  execute>

    @@@groovy
       person = [name:"bob",last:"builder"]
       person.with {
	    println "${name}-${last}"
       }
       

<!SLIDE  execute>

# Operators #

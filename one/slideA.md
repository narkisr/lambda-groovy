<!SLIDE title-slide>
# Groovy #

<!SLIDE bullets>

* Introduction 
* Literals, Lambdas, Operators, Syntax tidbits.
* The GDK
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
.notes List map and regex literals
    @@@groovy
        list = [1,2,3]

        map = [one:1, two:2]

        println "1234" ==~ /\d+/

<!SLIDE title-slide>

# Lambdas #

<!SLIDE  execute>
.notes Closures are first class in Groovy, they are objects.

    @@@groovy
       say = {m -> println m}

       say("hello")

       println say instanceof Closure

<!SLIDE  execute>
.notes Two conventions: when closure is last it can be written out of parentheses (useful in builders), a single closure parameter is implicit "it".

    @@@groovy
       def apply(name,action){
         action(name)
       }

       apply("hello"){v -> println v }

       apply("hello"){println it}

<!SLIDE  execute>
.notes A closure has a couple of interesting properties, including delegate and owner

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
.notes We can manipulate the delegate, opening a set of interesting options (like in DSL's).
 
    @@@groovy
       def say = {println m}

       say.delegate = [m:2]

       say()
         
<!SLIDE  execute>
.notes Behind the scenes the delegate is manipulated
    @@@groovy
       person = [name:"bob",last:"builder"]

       person.with {// changing delegate
	    println "${name}-${last}"
       }


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

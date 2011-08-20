<!SLIDE title-slide>

# Operators #

<!SLIDE  execute>
.notes Here we see an example of method ref, we can referecne object methods directly
    @@@groovy
       value = String.&valueOf 

       println value.class

       println value(true).class

<!SLIDE  execute>
.notes Elvis operator supplies default when value is null, the safe navigation opertor saves us from all those if's

    @@@groovy
      person = [name:"bob",last:"builder",birth:null]

      println person.birth ?: new Date() // elvis

      println person.birth?.toString() // safe nav



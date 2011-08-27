<!SLIDE title-slide>
# GPars #

<!SLIDE bullets>

* Parallel collections
* Map reduce
* Dataflow
* Actors
* Agents


<!SLIDE smaller execute>
.notes The following uses jsr-166 fork join behind the scenes, making the collect and fold run in parallel
    @@@groovy
      import groovyx.gpars.GParsPool 
      GParsPool.withPool {

        def seq = (1..10).makeTransparent()
        def squareSum = seq.collect {
          println Thread.currentThread()
          it*it
         }.fold(0) {a,v-> a+v}
        println squareSum

      }


<!SLIDE smaller execute>
.notes Using Map/Reduce DSL might operate better when multiple steps are needed on a single collection.
    @@@groovy
       import static groovyx.gpars.GParsPool.withPool

       def words = "lets count"
       println count(words)

       def count(arg) { 
         withPool { 
           def grouped = arg.parallel.map{[it, 1]}.groupBy{it[0]}
           println grouped
           grouped.getParallel().map{it.value=it.value.size();it}.sort{-it.value}.collection 
         } 
       }

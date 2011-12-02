<!SLIDE title-slide>
# GPars #

<!SLIDE bullets>

* Parallel collections 
* Map reduce
* Dataflow
* Actors (will not cover)
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
           grouped.getParallel().map{
              it.value=it.value.size()
              it
           }.sort{-it.value}.collection 
         } 
       }

<!SLIDE title-slide>
# Dataflow #
Operations (in Dataflow programs) consist of "black boxes" with inputs and outputs, all of which are always explicitly defined.

 They run as soon as all of their inputs become valid, as opposed to when the program encounters them.

 Whereas a traditional program essentially consists of a series of statements saying "do this, now do this", a dataflow program is more like a series of workers on an assembly line, who will do their assigned task as soon as the materials arrive.

 This is why dataflow languages are inherently parallel; the operations have no hidden state to keep track of, and the operations are all "ready" at the same time.

<!SLIDE bullets >
*  No race-conditions
*  No live-locks
*  Deterministic deadlocks
*  Completely deterministic programs
*  BEAUTIFUL code.

<!SLIDE smaller execute>
.notes Three green thread (task) cooperating on 3 DataFlowVariable's.

    @@@groovy
     import groovyx.gpars.dataflow.DataFlowVariable as WAIT
     import static groovyx.gpars.dataflow.DataFlow.task

     WAIT<Integer> x = new WAIT() // each of these is a logical channel
     WAIT<Integer> y = new WAIT()
     WAIT<Integer> z = new WAIT()

     task {// a task is a logical work unit

        z << x.val + y.val // x.val,y.val blocks until they have been set

     }

     task { x << 40 }
     task { y << 2 }

     println "z=${z.val}"
     assert 42 == z.val


<!SLIDE smaller execute>
.notes Sieve of Eratosthenes mimicking golang style, flow is (generate nums 1, 2, 3, 4, 5, ...) -> (filter by mod 2) -> ... -> (caution! Primes falling out here).

    @@@groovy
      import static groovyx.gpars.dataflow.DataFlow.task 
      import groovyx.gpars.dataflow.DataFlowQueue

      def generate(ch) {
          {->
              for (i in (2..10000)) {
                  ch << i
              }
          }
      }

      def filter(inChannel, outChannel, int prime) {
          {->
              for (;;) {
                  def number = inChannel.val
                  if (number % prime != 0) {
                      outChannel << number
                  }
              }
          }
      }

      final DataFlowQueue origin = new DataFlowQueue() // multi assigned channel
      task generate(origin)
      10.times {
        int prime = origin.val
        println prime
        def multiFiltered = new DataFlowQueue()
        task filter(origin, multiFiltered, prime)
        origin = multiFiltered
      }

<!SLIDE title>
# Agents #

<!SLIDE smaller execute>
.notes inspired  by Clojure, an agent is like an actor that processes functions serially on mutable data.

    @@@groovy
     import groovyx.gpars.agent.Agent

     num = new Agent([0])  

     num.send {it[0] = 2}  

     println num.val // blocks until all messages consumed

<!SLIDE smaller execute>
.notes valAsync returns a snapshoot of the underlying state

    @@@groovy
      import groovyx.gpars.agent.Agent

      numbers = new Agent([])
      numbers.send {it.add 1} 

      t1 = Thread.start { numbers.send {it.add 2} }

      t2 = Thread.start { 
               numbers {it.add 3} 
               numbers {it.add 4} 
      }

      numbers.valAsync {println "Current members: $it"}
      [t1, t2]*.join() 
      println numbers.val 



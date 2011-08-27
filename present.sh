export SHOWOFF_EVAL_RUBY=1
groovyc src/Printer.groovy -d classes
/opt/groovyserv-0.9/bin/groovyclient 
../showoff/bin/showoff serve

requires java 8

a port of java 8 streams to kilim
* no parallel fork/join (it's simulated on a single thread, but don't use it - it's slow)
* allows for actor-based streams
* it works !



stream2 includes a fake Pausable
  for development, make it extend Exception (so you can see problems)
  for testing, extend RuntimeException

parallel stateful streams aren't supported
  because in general the constructor requires evaluation
  making that Pausable would infect everything (might be fine, but didn't want to do it)
  see: AbstractPipeline.checkLazy


some automated changes using sed, eg:
  for ii in *; do sed -i -n -e "/import .*java.util.Spliterator/ d; p" $ii; done



alias j2='java -cp "build/test/classes:dist/*" org.testng.TestNG -listener stream2.Log -testclass'
jdebug='-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005'

# run a single test:
j2 stream2.FlagOpTest


(cd build/test/classes; find * -name "*.class") | grep -v '\$' | sed -e "s/\.class$//g" -e "s_/_._g" > tests.txt


mkdir -p t1 t2 t3
for ii in $(< tests.txt); do j2 $ii > t1/$ii 2>t2/$ii; done
(cd t2; for ii in *; do [ -s $ii ] && grep "^\w" $ii | grep -v "^[0-9]" > ../t3/$ii; done)


all source files (src directory) are either Oracle GPL+classpath exception
or lytles/nqzero under the same terms or the MIT license at your choice
(some license terms got obfuscated during copying, but those have been verified manually
and the correct header has been pasted in, eg from:
http://grepcode.com/file_/repository.grepcode.com/java/root/jdk/openjdk/8u40-b25/java/util/Arrays.java/?v=source
)


test files (test directory) are either Oracle GPL or lytles/nqzero (GPL+cpe or MIT at your choice)



all tests pass: zero fails, zero skips
parallel is faked - everything runs sequentially


this work was done using the excellent netbeans IDE and project config files are included
in the netbeans directory. they provide a basic build environ outside of netbeans
(and even inside it don't provide any real magic)

from netbeans directory:
ant -q clean jar compile-test

i ran the tests from the command line as mentioned above


kilim.nb.jar should correspond to:
https://github.com/nqzero/kilim/commit/5fa04fed2b54cf7c9bae7a69b70d6c5a7d07fe5c
on:
https://github.com/nqzero/kilim/tree/srl.java8.formdata

(more changes could be forthcoming)


RUNNING:

Example.java
(cd ../libs; mvn dependency:copy-dependencies -DoutputDirectory=.)
ant -q jar
java -cp "build/classes:../libs/*" kilim.tools.Weaver  -d x1 build/classes/
java -cp "x1:build/classes:../libs/*" stream2.Example




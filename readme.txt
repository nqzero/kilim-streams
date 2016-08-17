requires java 8

a port of java 8 streams to kilim
* no parallel fork/join (it's simulated on a single thread, but don't use it - it's slow)
* allows for actor-based streams
* it works !



stream2 includes a fake Pausable in boottest
  for development, make it extend Exception (so you can see problems)
  for testing, extend RuntimeException

parallel stateful streams aren't supported
* because in general the constructor requires evaluation
* making that Pausable would infect everything (might be fine, but didn't want to do it)
* see: AbstractPipeline.checkLazy


all source files (src directory) are either Oracle GPL+classpath exception
or lytles/nqzero under the same terms or the MIT license at your choice
(some license terms got obfuscated during copying, but those have been verified manually
and the correct header has been pasted in, eg from:
http://grepcode.com/file_/repository.grepcode.com/java/root/jdk/openjdk/8u40-b25/java/util/Arrays.java/?v=source
)


test files (test directory) are either Oracle GPL or lytles/nqzero (GPL+cpe or MIT at your choice)



all tests pass: zero fails, zero skips
parallel is faked - everything runs sequentially
tests are all run without weaving
(it's possible that at this point they could be converted over)


# MAVEN BUILD
mvn package -DskipTests
java -cp target/\* kilim.tools.Kilim stream2.Example






# development and testing notes



some automated changes using sed, eg:
  for ii in *; do sed -i -n -e "/import .*java.util.Spliterator/ d; p" $ii; done



alias j2='java -cp "target/test-classes:target/*" org.testng.TestNG -listener stream2.Log -testclass'
jdebug='-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005'

# run a single test:
j2 stream2.FlagOpTest


(cd target/test-classes; find * -name "*.class") | grep -v '\$' | sed -e "s/\.class$//g" -e "s_/_._g" > tests.txt


# track down failed tests

mkdir -p t1 t2 t3
for ii in $(< tests.txt); do j2 $ii > t1/$ii 2>t2/$ii; done
(cd t2; for ii in *; do [ -s $ii ] && grep "^\w" $ii | grep -v "^[0-9]" > ../t3/$ii; done)


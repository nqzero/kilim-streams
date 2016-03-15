stream2 includes a fake Pausable
  for development, make it extend Exception (so you can see problems)
  for testing, extend RuntimeException

parallel stateful streams aren't supported
  because in general the constructor requires evaluation
  making that Pausable would infect everything (might be fine, but didn't want to do it)
  see: AbstractPipeline.checkLazy



alias j2='java -cp "build/test/classes:dist/*" org.testng.TestNG -listener stream2.Log -testclass'
jdebug='-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005'

# run a single test:
j2 stream2.FlagOpTest


(cd build/test/classes; find * -name "*.class") | grep -v '\$' | sed -e "s/\.class$//g" -e "s_/_._g" > tests.txt


mkdir -p t1 t2

for ii in $(< tests.txt); do j2 $ii > t1/$ii 2>t2/$ii; done

(cd t2; for ii in *; do [ -s $ii ] && grep "^\w" $ii | grep -v "^[0-9]" > ../t3/$ii; done)


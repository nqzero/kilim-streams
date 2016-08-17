netbeans build environment

provides a basic ant-based build environ outside of netbeans
(and even inside netbeans doesn't provide any real magic). 






RUNNING:

from netbeans directory:

# populate the dependencies and build
(cd ..; mvn dependency:copy-dependencies -DoutputDirectory=netbeans/lib)
ant -q jar compile-test


# using runtime weaving
java -cp "build/classes:lib/*" kilim.tools.Kilim stream2.Example


# alternative - using ahead-of-time weaving (woven files are writen to x1)
java -cp "build/classes:lib/*" kilim.tools.Weaver  -d x1 build/classes/
java -cp "x1:build/classes:lib/*" stream2.Example




note: i ran the tests from the command line as mentioned in the toplevel readme

BUILDDIR = build

COMPILEJARS = ./lib/junit-jupiter-api-5.4.2.jar:./lib/apiguardian-api-1.0.0.jar
TESTJARS = lib/junit-platform-console-standalone-1.7.1.jar

MAINCLASS = src/com/company/Main.java
LIBCLASS = src/com/company/CalcLib.java
TESTCLASS = src/tests/Tests.java

JC = javac -cp $(COMPILEJARS)

DEBUG = -g






DEFAULT: app test


app: 
		#Compile all application classes
	$(JC) $(MAINCLASS) $(LIBCLASS) -d $(BUILDDIR) $(DEBUG)

test:
		#Compile the tests class along with the library
	$(JC) $(LIBCLASS) $(TESTCLASS) -d $(BUILDDIR)
		#Run tests. Will return '1' if all tests don't pass
	-java -jar $(TESTJARS) -cp $(BUILDDIR) --scan-classpath 

profile:
		#Not implemented yet

clean:
	#Remove folders 'build' and 'out'
	rm -rf build out

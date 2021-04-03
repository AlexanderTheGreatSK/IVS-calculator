BUILDDIR = ../build
ZIPNAME = xkovac59_xokruc00_xskalo01_xfrybo01.zip
CLEANLIST = ../build ../out

COMPILEJARS = ../lib/junit-jupiter-api-5.4.2.jar:../lib/apiguardian-api-1.0.0.jar
TESTJARS = ../lib/junit-platform-console-standalone-1.7.1.jar

MAINCLASS = com/company/Main.java
LIBCLASS = com/company/CalcLib.java
TESTCLASS = tests/Tests.java
PROFILECLASS = profiling/Profile.java

JC = javac -cp $(COMPILEJARS)

DEBUG = -g


DEFAULT: all

all: #TODO ADD GUI CLASS + PROFILING PROGRAM
	$(JC) $(MAINCLASS) $(LIBCLASS) -d $(BUILDDIR) $(DEBUG)
	$(JC) $(LIBCLASS) $(TESTCLASS) -d $(BUILDDIR)

test: all #Returns 1 if a test fails
	-java -jar $(TESTJARS) -cp $(BUILDDIR) --scan-classpath 

profile: all
	javac $(PROFILECLASS) $(MAINCLASS) $(LIBCLASS) -d $(BUILDDIR)

doc: #TODO
	#Not implemented yet

run: all #TODO
	#Not implemented yet

clean:
	rm -rf $(CLEANLIST)
	
pack: clean
	cd ../../ && zip -r $(ZIPNAME) ./doc/ ./install/ ./repo/

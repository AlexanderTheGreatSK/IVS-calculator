#Where the compiled files will be stored
BUILDDIR = ../build

#How will the compressed project be called
PROJNAME = xkovac59_xokruc00_xskalo01_xfrybo01

#What to remove when cleaning
CLEANLIST = ../build ../out


#Classpaths for compiling
MAINCP = ../lib/javaFX/javafx.controls:../lib/javaFX/javafx.fxml:../lib/javaFX/jfxrt.jar
TESTSCP = ../lib/junit-jupiter-api-5.4.2.jar:../lib/apiguardian-api-1.0.0.jar

#Jar file needed to run tests in terminal
TESTJARS = ../lib/junit-platform-console-standalone-1.7.1.jar

#Modules (javaFX) needed to compile the app
JAVAFXPATH = ../lib/javaFX/
JAVAFXMODULES = javafx.controls,javafx.fxml

#Classes
MAINCLASS = com/company/Main.java
LIBCLASS = com/company/CalcLib.java
CONTROLLERCLASS = com/company/Controller.java
TESTCLASS = tests/Tests.java
PROFILECLASS = profiling/Profile.java


DEBUG = #-g  #Uncomment to enable compiling with -g and vice versa



default: all

all: tests profile
	#Compile main class with the library and controller
	javac $(MAINCLASS) $(LIBCLASS) $(CONTROLLERCLASS) -cp $(MAINCP) -p $(JAVAFXPATH) --add-modules $(JAVAFXMODULES) -d $(BUILDDIR) $(DEBUG) 
	cp com/company/calc.fxml ../build/com/company/
	cp com/company/logo.png ../build/com/company/

tests: all
	#Compile tests along with the library
	javac $(TESTCLASS) $(LIBCLASS) -cp $(TESTSCP) -d $(BUILDDIR)

profile: all
	#Compile the profiling app
	javac $(PROFILECLASS) $(LIBCLASS) -d $(BUILDDIR)

doc: 
	doxygen doxyfile

run: all
	cd ../build/ && java --module-path ../lib/javaFX --add-modules javafx.controls,javafx.fxml com.company.Main

test: all #Returns 1 if at least one test fails
	-java -jar $(TESTJARS) -cp $(BUILDDIR) --scan-classpath 

clean:
	rm -rf $(CLEANLIST)
	
pack: clean #To make sense of what is happening, read the echo messages...
	cd ../../ && \
	echo "Create a folder named after xlogins, copy data there and enter" && \
	mkdir -p $(PROJNAME) && \
	cp -r doc install $(PROJNAME) && \
	echo "Meanwhile, the repository is renamed to 'repo'" && \
	cp -r ./IVS-calculator/ ./$(PROJNAME)/repo/ && \
	cd ./$(PROJNAME)/repo/ && \
	echo "In repo/profiling remove everything except the .pdf and .csv files" && \
	rm -rf ./profiling/profile_command ./profiling/output/** ./profiling/report && \
	mv ./profiling/data/csv/** ./profiling/output/ && \
	rm -rf ./profiling/data && \
	echo "Go back above the repo directory, zip everything needed and remove the repo dir" && \
	cd ../../ && \
	zip -r $(PROJNAME).zip $(PROJNAME) && \
	:
	#rm -rf $(PROJNAME)
	



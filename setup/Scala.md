
# Setup Scala

## Setup Scala Project using Mill

```bash
$ cd PROJECT_DIR
$ ../setup/scripts/create_scala_project.sh PROJECT_NAME
#    where:
#        PROJECT_DIR is the new project directory
#        PROJECT_NAME is the new name of the project
```

## Mill Commands
```
Replace "myproject" with the name of your project

# Discover what commands are available to run
./mill resolve myproject._

# Compile and test
./mill myproject.compile
./mill myproject.test
./mill show myproject.compileClasspath
./mill show myproject.resources

# Build jar file
./mill myproject.jar

# Build jar file with dependencies (uber jar)
./mill myproject.assembly
./mill show myproject.assembly

# Run the uber jar
./out/myproject/assembly/dest/out.jar 

# Run this first before opening project in IntelliJ.
# Re-run each time you change build.sc
./mill mill.scalalib.GenIdea/idea

# Run a custom main class
./mill myproject.runMain synonym_matching.vocab.IndexDriver
./mill myproject.runMain synonym_matching.vocab.SearchDriver
./mill show myproject.runClasspath

```

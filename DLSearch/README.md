
Setup 
```

# Install and setup Mill project
curl -L https://github.com/lihaoyi/mill/releases/download/0.7.3/0.7.3-example-3.zip \\n  -o example-3.zip
unzip example-3.zip
rm -f *.zip; mv example-3/* .; rm -fr example-3
chmod +x mill

# Discover what commands are available to run
./mill resolve dlsearch._

# Compile and test
./mill dlsearch.compile
./mill dlsearch.test
./mill show dlsearch.compileClasspath
./mill show dlsearch.resources

# Build jar file
./mill dlsearch.jar

# Build jar file with dependencies (uber jar)
./mill dlsearch.assembly
./mill show dlsearch.assembly

# Run the uber jar
./out/dlsearch/assembly/dest/out.jar 

# Run this first before opening project in IntelliJ.
# Re-run each time you change build.sc
./mill mill.scalalib.GenIdea/idea

# Run a custom main class
./mill dlsearch.runMain synonym_matching.vocab.IndexDriver
./mill dlsearch.runMain synonym_matching.vocab.SearchDriver
./mill show dlsearch.runClasspath

```

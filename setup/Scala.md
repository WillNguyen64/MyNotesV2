
# Setup Scala

## Install Ammonite REPL

https://ammonite.io/

```bash
$ AMM=/opt/scala-tools/amm
$ curl -L https://github.com/lihaoyi/ammonite/releases/download/2.1.4/2.13-2.1.4-bootstrap > "$AMM" && chmod +x "$AMM"
```

## Setup Scala Project using Mill

http://www.lihaoyi.com/mill/index.html

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

## Ammonite Commands

```
// ================================================================================================
// Configuration
// ================================================================================================

~/.ammonite/predef.sc.   // or use --predef='...'

// Import commonly used libraries using predef.sc
$ cat ~/.ammonite/predef.sc
import $ivy.`com.lihaoyi::ammonite-ops:2.1.4`, ammonite.ops._

// Can pass JVM options to AMM
$ JAVA_OPTS="-Xmx1024m" amm

// ================================================================================================
// Customize the output manually
// ================================================================================================

// -- Show full output w/o truncation
@ show(Seq.fill(100)(100))

// -- Show full output w/ specific truncation
@ show(Seq.fill(100)(100), height = 3)

// Can also customize the output automatically with pprintConfig
// -- Add example here later

// ================================================================================================
// Import Scala scripts / libraries
// ================================================================================================

// -- Import local script
//    Assume there's a file named MyScript.sc containing some code
@ import $file.MyScript
@ MyScript.elite
res2: Int = 31337

// -- Import script in sub-dir
@ import $file.myfolder.MyScript

// -- Import script in outer dir
@ import $file.^.MyScript     // imports from ../MyScript.sc
@ import $file.^.^.MyScript   // imports from ../../MyScript.sc

// -- Import in one go
@ import $file.MyScript, MyScript._
@ elite
res4: Int = 31337

// -- Reset before re-importing script
@ sess.save()
@ import $file.MyScript
@ sess.load()
@ import $file.MyScript

// -- Importing multiple scripts
@ import $file.{MyScript, MyOtherScript{

// -- Bringing imports from scripts into the session
@ import $exec.MyScript

// -- Import ivy deps from Maven Central, or other repo
@ import $ivy.`org.scalaz::scalaz-core:7.2.27`, scalaz._, Scalaz._
@ import $ivy.`com.google.guava:guava:18.0`, com.google.common.collect._

@ import $ivy.`com.twitter::finagle-httpx:6.26.0`
@ import com.twitter.finagle._, com.twitter.util._

// -- Use autocomplete to find packages / versions in the repo
@ import $ivy.`com.lihaoyi::`<hit tab>
@ import $ivy.`com.lihaoyi::ammonite-ops`<hit tab>

// -- Import using custom resolvers w/o auth
@ interp.repositories() ++= Seq(coursierapi.IvyRepository.of(
    "https://ambiata-oss.s3-ap-southeast-2.amazonaws.com/[defaultPattern]"
))

// -- Import using custom resolvers w/ auth
@ import coursierapi.{Credentials, MavenRepository}

@ interp.repositories() ++= Seq(
    MavenRepository
        .of("https://nexus.corp.com/content/repositories/releases")
        .withCredentials(Credentials.of("user", "pass"))
)

// -- If need more control over import, e.g., w/ attributs, classifiers, exclusions
// use: interp.load.ivy(deps: coursierapi.Dependency*) function

@ import interp.load.module($printedScriptPath/"loadIvyAdvanced.sc")


// ================================================================================================
// Builtins
// ================================================================================================

// For a list of REPL built-ins and configuration, use `repl.<tab>`.

@ repl.imports
@ repl.fullImports
@ repl.typeOf(Seq(1,2,3))  // or typeOf
@ repl.show(Seq.fill(100)(100))  // or show
@ repl.lastException
// etc...

// Utilities

// Time how long code runs for
@ time(Thread.sleep(1000))

// Grep for strings in a collection
// !! Couldn't get this to work due to error with ||
@ sys.props || grep! "os|OS".r

// Browse large output in separate viewer tool (e.g., less, vim)
@ browse(sys.props)

// Desugar - See how compiler executes the code, useful for inspecting implicit params / conversions, macros, other 
// odd Scala features
@ desugar(List(1,2))

// ================================================================================================
// Sessions
// ================================================================================================

// checkpoint
@ repl.sess.save()
// ... do something
// restore to checkpoint
@ repl.sess.load()

// name your checkpoint
@ repl.sess.save("some checkpoint")
@ repl.sess.load("some checkpoint")

// ================================================================================================
// Embedding in Scala Program
// ================================================================================================

// Debugging

// -- Add dependency to build.sbt
libraryDependencies += "com.lihaoyi" % "ammonite" % "2.1.4" cross CrossVersion.full

// -- Place breakpoint in program. When the breakpoint is triggered, it opens up an Ammonite REPL
// with values you provided it bound to names you gave it.

// -- Option 1: Open an Ammonite REPL with Main()
package ammonite.integration

object TestMain {
    def main(args: Array[String]): Unit = {
        val hello = "Hello"
        // break into debug REPL 
        ammonite.Main(
            predefCode = "println(\"Starting Debugging!\")"
        ).run(
            "hello" -> hello,
            "fooValue" -> foo()
        )
    }
    def foo() = 1
}

// -- Option 2: Open an Ammonite REPL with debug
 ...
    import ammonite.repl.Repl._
    debug(
      "document" -> document,
      "src" -> src,
      "json" -> json
    )
 ...

// Remote REPL
// Leave an SSH server running in each process.

// -- Add dependency to build.sbt
libraryDependencies += "com.lihaoyi" % "ammonite-sshd" % "2.1.4" cross CrossVersion.full

// -- Add the repl server to application

import ammonite.sshd._
val replServer = new SshdRepl(
    SshServerConfig(
        address = "localhost", // or "0.0.0.0" for public-facing shells
        port = 22222, // Any available port
        passwordAuthenticator = Some(pwdAuth)  // or publicKeyAuthenticator
    )
)
replServer.start()

// -- Connect to the SSH server
$ ssh repl@localhost -p22222



// ================================================================================================
// Filesystem operations
// ================================================================================================

@ import $ivy.`com.lihaoyi::ammonite-ops:2.1.4`, ammonite.ops._



```
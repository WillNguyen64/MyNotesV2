
// Example of dynamic import
// This is referred to as a multi-stage script as the import is done in multiple stages
// It is not typically used as magic imports should be sufficient for most needs

// How to run:
// $ amm DynamicImport.sc

println("Welcome to the XYZ custom REPL!!")
val scalazVersion = "7.3.2"
// Must use this instead of "import $ivy"
interp.load.ivy("org.scalaz" %% "scalaz-core" % scalazVersion)

// This @ is needed for Ammonite to run interp.load.ivy before continuing with the
// rest of the code.
@

import scalaz._
import Scalaz._

// Use Scalaz

println(
  Apply[Option].apply2(some(1), some(2))((a, b) => a + b)
)

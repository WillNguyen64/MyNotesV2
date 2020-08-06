// build.sc
import mill._, scalalib._

object practice extends ScalaModule{
  def scalaVersion = "2.13.2"
  object test extends Tests{
    // utest example
    //def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.7.4")
    //def testFrameworks = Seq("utest.runner.Framework")
    // Scalatest example
    def ivyDeps = Agg(
      ivy"org.scalatest::scalatest:3.2.0",
      ivy"org.scalamock::scalamock:5.0.0",
      ivy"org.scalacheck::scalacheck:1.14.1",
      ivy"org.scalatestplus::scalacheck-1-14:3.2.0.0")
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}

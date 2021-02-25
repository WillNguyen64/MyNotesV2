// build.sc
import mill._, scalalib._

object dlsearch extends ScalaModule {
  def scalaVersion = "2.13.2"
  def ivyDeps = Agg(
    ivy"org.apache.lucene:lucene-core:8.5.2",
    ivy"org.apache.lucene:lucene-analyzers-common:8.5.2",
    ivy"org.apache.lucene:lucene-queryparser:8.5.2"
  )
  object test extends Tests {
    def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.7.4")
    def testFrameworks = Seq("utest.runner.Framework")
  }
}

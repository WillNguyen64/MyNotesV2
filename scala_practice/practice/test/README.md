
# ScalaTest

* General rules and guidelines for writing tests
* Ref: https://www.scalatest.org/user_guide/

## Standardizing Tests
* Choose one testing style for UT / Integration testing (e.g., FlatSpec), and one for acceptance testing (e.g., FeatureSpec)
* Styles:
  * `FunSuite` - similar to xUnit style
  * `FlatSpec` - for moving from xUnit to BDD, write test as specifications (e.g., X should Y, etc.)
  * `FunSpec` - similar to Ruby RSpec tool
  * `WordSpec` - similar to specs/specs2, very prescriptive in how text must be written
  * `FreeSpec` - no requirements on spec text, for teams with more BDD experience
  * `PropSpec` - tests written as property checks
  * `FeatureSpec` - mainly for acceptance testing, for communicating acceptance criteria with non-technical people. Specify tests with GivenWhenThen format.
* Enforce the style in build.sbt by specifying style-specific package (e.g., scalatest-flatspec instead of scalatest which contains all testing styles)
* `libraryDependencies += "org.scalatest" %% "scalatest-flatspec" % "3.2.0" % "test"`
* Create an ABC that mixes the traits used most often
  ```scala
    abstract class UnitSpec extends AnyFlatSpec with should.Matchers with
       OptionValues with Inside with Inspectors

     ...
    class MySpec extends UnitSpec {
      // Your tests here
    }
  ```
* Can have multiple ABCs, e.g., 1 integration test ABC requiring DB, 1 requiring actor system, 1 requiring both

## Running Tests
### Mill
* Running
  * `mill myproject.test`
  * TODO: how to customize running of tests?

### Sbt
* Running
  * `sbt test`
  * `sbt test-only org.acme.RedSuite org.acme.BlueSuite`
  * `sbt test-only *RedSuite`
  * `sbt test-quick`    -- only run tests affected by latest code chagne
* Debugging
  * `sbt test:full-classpath`
* Customize
  * Custom options
    * In build.sbt, `testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")`
    * Via CLI, `sbt test-only org.achme.RedSuite -- -oD`
    
## Writing Tests

Techniques to avoid test code duplication

* Scenario 1 - Diff tests need diff fixtures
    * `get-fixture` methods - use to create diff fixture objs for tests, no cleanup
    * `fixture-context` objects - use if tests need diff combos ofs mutable fixture objects, no cleanup
    * `loan-fixture` methods - use to create diff fixture objs for tests, cleanup afterwards
* Scenario 2 - Most or all tests need same fixture
    * `withFixture(NoArgTest)` - recommended approach, perform side effects at start/end of tests, transform test results, retry tests, make decisions based on test names, tags or test data.
    * `withFixture(OneArgTest)` - same, but pass same fixture obj as param into all tests
* Scenario 3 - Run fixture code before/after and abort suite (not test) if fixture fails
    * `BeforeAndAfter` - Perform side effects before/after each test
    * `BeforeAndAfterEach` - Put each fixture into a trait that can be composed, allows diff tests to use fixtures in diff combinations, and maybe initialed/cleaned up in diff order

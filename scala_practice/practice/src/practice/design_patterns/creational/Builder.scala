package practice.design_patterns.creational

// Approach 1 - Simple builder (preferred method)
// Validation is done at runtime

case class SimplePerson(
  firstName: String = "",
  lastName: String = "",
  age: Int = 0
) {
  require(firstName != "", "First name is required.")
  require(lastName != "", "Last name is required.")
}

object SimplePersonBuilderExample extends App {
  val person1 = SimplePerson(
    firstName = "Bill",
    lastName = "Gates",
    age = 60
  )
  println(s"Person 1: $person1")
  try {
    val person2 = SimplePerson(
      firstName = "John"
    )
    println(s"Person 2: $person2")
  } catch {
    case e: Throwable =>
      e.printStackTrace()
  }
}

// Approach 2 - A type-safe builder
// Uses a generalized type constraints to implement builder pattern
// This approach allows us to add compile-time validation and better manage dependencies between fields (e.g., if one field
// depends on another one)
// Disadvantages: complexity, mutability, predefined order of initialization, unhelpful error messages

// Example: Build a person that must have at least firstName and lastName

class Person(
  val firstName: String,
  val lastName: String,
  val age: Int) {
}

class PersonBuilder[PassedStep <: BuildStep] private(
    var firstName: String,
    var lastName: String,
    var age: Int
  ) {

  protected def this() = this("", "", 0)

  protected def this(pb: PersonBuilder[_]) = this(
    pb.firstName,
    pb.lastName,
    pb.age
  )

  def setFirstName(firstName: String): PersonBuilder[HasFirstName] = {
    this.firstName = firstName
    new PersonBuilder[HasFirstName](this)
  }

  // The =:= is a generalized type constraint
  def setLastName(lastName: String)(implicit ev: PassedStep =:= HasFirstName): PersonBuilder[HasLastName] = {
    this.lastName = lastName
    new PersonBuilder[HasLastName](this)
  }

  def setAge(age: Int): PersonBuilder[PassedStep] = {
    this.age = age
    this
  }

  // The =:= is a generalized type constraint
  def build()(implicit ev: PassedStep =:= HasLastName): Person = new Person(
    firstName,
    lastName,
    age
  )
}

object PersonBuilder {
  def apply() = new PersonBuilder()
}

sealed trait BuildStep
sealed trait HasFirstName extends BuildStep
sealed trait HasLastName extends BuildStep

object PersonBuilderTypeSafeExample extends App {
  val person = PersonBuilder()
    .setFirstName("Bill")
    .setLastName("Gates")
    .setAge(60)
    .build()
  println(s"Person: ${person.firstName} ${person.lastName}. Age: ${person.age}")
}
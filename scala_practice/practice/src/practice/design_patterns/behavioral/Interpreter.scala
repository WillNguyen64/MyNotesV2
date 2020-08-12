package practice.design_patterns.behavioral

import java.util.StringTokenizer

import scala.collection.mutable
import scala.jdk.CollectionConverters._

// Interpreter - evaluate domain language syntax using classes and syntax trees
// Do not confuse interpreter design pattern with parsing, though we need parsing to build our expressions.

// Uses composite pattern where everything is represented as an expression

// Example: evaluate simple numerical expressions using reverse Polish notation where
// both operands appear before the operator
//   e.g., '1 2 + 3 *' which represents '(1 + 2) * 3'
// An expression can be:
//   - Terminal expression: e.g., Number
//   - Non-terminal expression: e.g., Add, Subtract, Multiple
// Limitations:
//   - supports only integers, limited error reporting, supports 3 operations

trait Expression {
  def interpret(): Int
}

class Number(n: Int) extends Expression {
  override def interpret(): Int = n
}

class Add(right: Expression, left: Expression) extends Expression {
  override def interpret(): Int = left.interpret() + right.interpret()
}

class Subtract(right: Expression, left: Expression) extends Expression {
  override def interpret(): Int = left.interpret() - right.interpret()
}

class Multiply(right: Expression, left: Expression) extends Expression {
  override def interpret(): Int = left.interpret() * right.interpret()
}

// Factory to decide which expression to return
object Expression {
  def apply(operator: String, left: => Expression, right: => Expression): Option[Expression] =
    operator match {
      // the right param is access before the left param because the stack reverses the operand order
      // e.g., if you have '1 2', the popping order is 2 then 1.
      case "+" => Some(new Add(right, left))
      case "-" => Some(new Subtract(right, left))
      case "*" => Some(new Multiply(right, left))
      case i if i.matches("\\d+") => Some(new Number(i.toInt))
      case _ => None
    }
}

class RPNParser {
  def parse(expression: String): Expression = {
    val tokenizer = new StringTokenizer(expression)
    tokenizer.asScala.foldLeft(mutable.Stack[Expression]()) {
      case (result, token) =>
        // Since we use by-name parameters, result.pop() is deferred until we verify that token is
        // one of the supported operators
        val item = Expression(token.toString, result.pop(), result.pop())
        item.foreach(result.push)
        result
    }.pop()
  }
}

class RPNInterpreter {
  def interpret(expression: Expression): Int = expression.interpret()
}

object RPNExample extends App {
  val expr1 = "1 2 + 3 * 9 10 + -"  // (1 + 2) * 3 - (9 + 10) = -10
  val expr2 = "1 2 3 4 5 * * - +" // 1 + 2 - 3 * 4 * 5 = -57
  val expr3 = "12 -" // invalid
  val parser = new RPNParser
  val interpreter = new RPNInterpreter
  println(s"The result of '${expr1}' is: ${interpreter.interpret(parser.parse(expr1))}")
  println(s"The result of '${expr2}' is: ${interpreter.interpret(parser.parse(expr2))}")
  try {
    println(s"The result is: ${interpreter.interpret(parser.parse(expr3))}")
  } catch {
    case _: Throwable => println(s"'$expr3' is invalid'")
  }
}

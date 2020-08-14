package practice.design_patterns.scala_patterns

import java.security.MessageDigest

import org.apache.commons.codec.binary.Hex
import scalaz.Memo
import scala.collection.mutable.Map

// Memoization - records a function result based on its arguments in order to reduce
// computation in consecutive calls

// Alternative: Scalaz has support using Memo object
// val memoMd5Scalaz: String => String = Memo.immutableHashMapMemo {
//    md5
// }

// Example: hash millions of strings, use memoization to store and reuse previously hashed strings

trait Memoizer {

  def memo[X, Y](f: X => Y): (X => Y) = {
    val cache = Map[X, Y]()
    (x: X) => cache.getOrElseUpdate(x, f(x))
  }
}
class Hasher extends Memoizer {

  def md5(input: String) = {
    System.out.println(s"Calling md5 for $input.")
    new String(Hex.encodeHex(MessageDigest.getInstance("MD5").digest(input.getBytes)))
  }

  val memoMd5 = memo(md5)

  val memoMd5Scalaz: String => String = Memo.mutableHashMapMemo {
    md5
  }
}

object MemoizationExample {

  def main(args: Array[String]): Unit = {
    val hasher = new Hasher

    System.out.println(s"MD5 for 'hello' is '${hasher.memoMd5("hello")}'.")
    System.out.println(s"MD5 for 'bye' is '${hasher.memoMd5("bye")}'.")
    System.out.println(s"MD5 for 'hello' is '${hasher.memoMd5("hello")}'.")
    System.out.println(s"MD5 for 'bye1' is '${hasher.memoMd5("bye1")}'.")
    System.out.println(s"MD5 for 'bye' is '${hasher.memoMd5("bye")}'.")
  }
}

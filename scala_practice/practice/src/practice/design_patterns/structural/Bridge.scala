package practice.design_patterns.structural

import java.security.MessageDigest

import org.apache.commons.codec.binary.Hex

// Bridge is useful in cases where abstractions or implementations could vary often and independently
// Bridge is similar to adapter. Difference is that former is used on application code that can be changed, and
// the latter is used on legacy or third-party code

// Abstraction side

abstract class PasswordConverterBase {
  self: Hasher =>
  def convert(password: String): String
}

class SimplePasswordConverter extends PasswordConverterBase {
  self: Hasher =>
  override def convert(password: String): String =
    hash(password)
}

class SaltedPasswordConverter(salt: String) extends PasswordConverterBase {
  self: Hasher =>
  override def convert(password: String): String =
    hash(s"$salt:$password")
}

// Implementation side

trait Hasher {
  def hash(data: String) : String

  protected def getDigest(algorithm: String, data: String): MessageDigest = {
    val crypt = MessageDigest.getInstance(algorithm)
    crypt.reset()
    crypt.update(data.getBytes("UTF-8"))
    crypt
  }
}

trait Sha1Hasher extends Hasher {
  override def hash(data: String): String =
    new String(Hex.encodeHex(getDigest("SHA-1", data).digest()))
}

trait Sha256Hasher extends Hasher {
  override def hash(data: String): String =
    new String(Hex.encodeHex(getDigest("SHA-256", data).digest()))
}

trait Md5Hasher extends Hasher {
  override def hash(data: String): String =
    new String(Hex.encodeHex(getDigest("MD5", data).digest()))
}

// Client

object BridgeExample extends App {
  val p1 = new SimplePasswordConverter with Sha256Hasher
  val p2 = new SimplePasswordConverter with Md5Hasher
  println(s"'password' in SHA-256 is: ${p1.convert("password")}")
  println(s"'password' in MD5 is: ${p2.convert("1234567890")}")
}

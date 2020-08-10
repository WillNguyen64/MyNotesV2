package practice.design_patterns.creational

import scala.collection.concurrent.TrieMap

object AppRegistry {
  println("Registry init block called.")
  private val users: TrieMap[String, String] = TrieMap.empty[String, String]

  def addUser(id: String, name: String): Unit = {
    users.put(id, name)
  }

  def removeUser(id: String): Unit = {
    users.remove(id)
  }

  def isUserRegistered(id: String): Boolean = users.contains(id)

  def getAllUserNames(): List[String] = users.map[String](_._2).toList

}

object AppRegistryExample extends App {
  println("Sleeping for 5 sec")
  Thread.sleep(5000)
  println("I woke up.")
  AppRegistry.addUser("1", "Ivan")
  AppRegistry.addUser("2", "John")
  AppRegistry.addUser("3", "Martin")
  println(s"Is user with ID=1 registered? ${AppRegistry.isUserRegistered("1")}")
  AppRegistry.removeUser("2")
  println(s"Is user with ID=2 registered? ${AppRegistry.isUserRegistered("2")}")
  println(s"All users registered are: ${AppRegistry.getAllUserNames().mkString(",")}")
}

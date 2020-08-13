package practice.design_patterns.behavioral2

import scala.collection.mutable.ListBuffer

// Iterator - iterator a collection of objects w/o knowing internal representation
// Pattern has two parts:
//  - Iterable (the collection that supports iteration)
//  - Iterator (an object that knows how to iterate the collection)

// Caution: in multithreading case, a thread may add/remove objects from original collection which is not
// reflected by the iterator

case class Student(name: String, age: Int)

class StudentIterator(students: Array[Student]) extends Iterator[Student] {
  var currentPos = 0
  override def hasNext: Boolean = currentPos < students.size
  override def next(): Student = {
    val result = students(currentPos)
    currentPos += 1
    result
  }
}

class ClassRoom extends Iterable[Student] {
  val students: ListBuffer[Student] = ListBuffer[Student]()

  def add(student: Student): Unit = {
    students.prepend(student)
    //student +=: students
  }

  // Could also use students.iterator() here
  override def iterator: Iterator[Student] = new StudentIterator(students.toArray)
}

object ClassRoomExample extends App {
  val classRoom = new ClassRoom
  classRoom.add(Student("Ivan", 26))
  classRoom.add(Student("Maria", 26))
  classRoom.add(Student("John", 25))
  classRoom.foreach(println)
}

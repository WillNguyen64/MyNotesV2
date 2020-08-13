package practice.design_patterns.behavioral2

// Visitor pattern - helps us add new operations to existing object structures w/o modifying them

abstract class Element(text: String) {
  def accept(visitor: Element => Unit): Unit = {
    visitor(this)
  }
}

case class Title(text: String) extends Element(text)
case class Text(text: String) extends Element(text)
case class Hyperlink(text: String, val url: String) extends Element(text)

class Document(parts: List[Element]) {

  def accept(visitor: Element => Unit): Unit = {
    parts.foreach(p => p.accept(visitor))
  }
}

object VisitorExample {
  val line = System.getProperty("line.separator")

  def htmlExporterVisitor(builder: StringBuilder): Element => Unit = element => element match {
    case Title(text) =>
      builder.append(s"<h1>${text}</h1>").append(line)
    case Text(text) =>
      builder.append(s"<p>${text}</p>").append(line)
    case Hyperlink(text, url) =>
      builder.append(s"""<a href=\"${url}\">${text}</a>""").append(line)
  }

  def plainTextExporterVisitor(builder: StringBuilder): Element => Unit = element => element match {
    case Title(text) =>
      builder.append(text).append(line)
    case Text(text) =>
      builder.append(text).append(line)
    case Hyperlink(text, url) =>
      builder.append(s"${text} (${url})").append(line)
  }

  def main(args: Array[String]): Unit = {
    val document = new Document(
      List(
        Title("The Visitor Pattern Example"),
        Text("The visitor pattern helps us add extra functionality without changing the classes."),
        Hyperlink("Go check it online!", "https://www.google.com/"),
        Text("Thanks!")
      )
    )

    val html = new StringBuilder
    System.out.println(s"Export to html:")
    document.accept(htmlExporterVisitor(html))
    System.out.println(html.toString())

    val plain = new StringBuilder
    System.out.println(s"Export to plain:")
    document.accept(plainTextExporterVisitor(plain))
    System.out.println(plain.toString())
  }
}
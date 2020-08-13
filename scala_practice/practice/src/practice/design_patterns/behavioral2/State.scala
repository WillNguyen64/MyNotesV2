package practice.design_patterns.behavioral2

// State pattern allows us to choose different behavior of an object based on its internal state
// It is similar to strategy design pattern

trait State[T] {
  def press(context: T)
}

case class MediaPlayer() {
  private var state: State[MediaPlayer] = new Paused

  def pressPlayOrPauseButton(): Unit = {
    state.press(this)
  }

  def setState(state: State[MediaPlayer]): Unit = {
    this.state = state
  }
}

class Playing extends State[MediaPlayer] {
  override def press(context: MediaPlayer): Unit = {
    System.out.println("Pressing pause.")
    context.setState(new Paused)
  }
}

class Paused extends State[MediaPlayer] {
  override def press(context: MediaPlayer): Unit = {
    System.out.println("Pressing play.")
    context.setState(new Playing)
  }
}


object MediaPlayerExample {
  def main(args: Array[String]): Unit = {
    val player = MediaPlayer()

    player.pressPlayOrPauseButton()
    player.pressPlayOrPauseButton()
    player.pressPlayOrPauseButton()
    player.pressPlayOrPauseButton()
  }
}

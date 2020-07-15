
// How to run:
// amm MainFunction.sc foobar 5
// amm MainFunction.sc --str foobar --num 5
// amm MainFunction.sc --str foobar --num 5 --greeting 'Have a very nice day!'

// To use a custom arg parser use:
// @main
// def entrypoint(args: String*) = {
//   ...
// }

@main
def main(str: String, num: Int, greeting: String = "Have a nice day!") = {
  s"Hello ${str * num}!!! $greeting"
}

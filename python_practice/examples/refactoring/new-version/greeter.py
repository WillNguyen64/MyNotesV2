import datetime


def day():
    return datetime.datetime.now().strftime('%A')


def part_of_day():
    curr_hour = datetime.datetime.now().hour
    if curr_hour < 12:
        return "morning"
    elif curr_hour < 17:
        return "afternoon"
    else:
        return "evening"


class Greeter:
    def __init__(self, name):
        self.name = name

    def greet(self, store):
        print(f"Hi, my name is {self.name}, and welcome to {store}!")
        print(f"How's your {day()} {part_of_day()} going?")
        print(f"Here's a coupon for 20% off!")


greeter = Greeter("Bob")
greeter.greet("Superstore")

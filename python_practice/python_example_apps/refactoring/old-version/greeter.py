import datetime

class Greeter:
    def _day(self):
        return datetime.datetime.now().strftime('%A')
    
    def _part_of_day(self):
        curr_hour = datetime.datetime.now().hour
        if curr_hour < 12:
            return "morning"
        elif curr_hour < 17:
            return "afternoon"
        else:
            return "evening"

    def greet(self, store):
        print(f"Hi, welcome to {store}")
        print(f"How's your {self._day()} {self._part_of_day()} going?")
        print(f"Here's a coupon for 20% off!")

    
greeter = Greeter()
greeter.greet("Superstore")


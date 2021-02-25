import random

# Separate the original extract_functions.py into different functions,
# then refactor to a class

OPTIONS = ['rock', 'paper', 'scissors']

class RockPaperScissorsGame:

    def __init(self):
        self.human_choice = ''
        self.computer_choice = ''

    def get_choices(self):
        print('(1) Rock\n(2) Paper\n(3) Scissors')
        human_choice = OPTIONS[int(input('Enter the number of your choice: ')) - 1]
        print(f'You chose {human_choice}')
        computer_choice = random.choice(OPTIONS)
        print(f'The computer chose {computer_choice}')
        self.human_choice = human_choice
        self.computer_choice = computer_choice

    def is_tie(self):
        return self.human_choice == self.computer_choice

    def is_human_winner(self):
        map_choices_to_outcome = {
            ('rock','paper'): False,
            ('rock','scissors'): True,
            ('paper', 'scissors'): False,
            ('paper', 'rock'): True,
            ('scissors', 'rock'): False,
            ('scissors', 'paper'): True
        }
        return map_choices_to_outcome[(self.human_choice, self.computer_choice)]
        
    def print_tie_message(self):
        print('Draw!')

    def print_winning_msg(self):
        print(f'Yes, {self.human_choice} beat {self.computer_choice}!')

    def print_losing_msg(self):
        print(f'Sorry, {self.computer_choice} beat {self.human_choice}')

    def simulate(self):
        self.get_choices()

        if self.is_tie():
            self.print_tie_message()
        elif self.is_human_winner():
            self.print_winning_msg()
        else:
            self.print_losing_msg()


game = RockPaperScissorsGame()
game.simulate()



from functools import partial

# Partial functions - create new functions out of existing ones
def pow(x, power=1):
    return x ** power

square = partial(pow, power=2)
cube = partial(pow, power=3)

print(square(5))
print(cube(5))


from functools import reduce

# Map/reduce/filter using functools
squares = list(map(lambda i: i * i, [1, 2, 3, 4, 5]))
should = reduce(lambda x, y: x and y, [True, True, False])
evens = list(filter(lambda x: x % 2 == 0, [1, 2, 3, 4, 5]))

print(squares)
print(should)
print(evens)

# Map/reduce/filter using Python (preferred approach)
squares = [x * x for x in [1, 2, 3, 4, 5]]
should = all([True, True, False])
evens = [x for x in [1, 2, 3, 4, 5] if x % 2 == 0]

print(squares)
print(should)
print(evens)





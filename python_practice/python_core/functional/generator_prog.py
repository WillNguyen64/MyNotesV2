
# Generator
# Prefer them over lists when you don't need all the values at once to
# save on memory usage
def range(*args):
    if len(args) == 1:
        start = 0
        stop = args[0]
    else:
        start = args[0]
        stop = args[1]
    
    current = start

    while current < stop:
        yield current
        current += 1

# Convert generator values to a list
print(list(range(1, 10)))

# Can iterate generator values using next()
nums = range(1, 10)
try:
    while True:
        print(next(nums))
except StopIteration:
    # next() throws StopIteration
    pass

# Can compose generators
def squares(items):
    for i in items:
        yield i * i

print(list(squares(range(1, 6))))


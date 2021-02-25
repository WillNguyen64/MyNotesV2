
# Run timeit module from cmdline
# $ python -m timeit "total = sum(range(1000))"
# 10000 loops, best of 5: 21.6 usec per loop
#
# timeit runs code many times, the code takes 21.6 microseconds to run (on average)

# Run timeit from code
# Use 'setup' to avoid timing the setup code

from timeit import timeit

setup = 'from datetime import datetime'
statement = 'datetime.now()'
result = timeit(setup=setup, stmt=statement)
print(f"Took an average of {result:.5f}ms")

# Comparing if checking item in set is faster than list
set_result = timeit(setup='my_set = set(range(10000))',
                    stmt='300 in my_set')
print(f"Set: Took an average of {set_result:.5f}ms")

list_result = timeit(setup='my_list = list(range(10000))',
                     stmt='300 in my_list')
print(f"List: Took an average of {list_result:.5f}ms")


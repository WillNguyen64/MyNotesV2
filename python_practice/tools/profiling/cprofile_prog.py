
# CPU profiling using cProfile module
# see which parts of code runs expensive calcs, and how often they're called
#
# Example 1:
# python -m cProfile --sort cumtime cprofile_prog.py sleep_test
#         3777 function calls (3750 primitive calls) in 8.816 seconds
#
# Ordered by: cumulative time
#
# ncalls  tottime  percall  cumtime  percall filename:lineno(function)
#   3/1   0.000    0.000    8.816    8.816 {built-in method builtins.exec}
#     1   0.008    0.008    8.815    8.815 cprofile_test.py:7(<module>)
#  1000   0.010    0.000    8.805    0.009 cprofile_test.py:10(an_expensive_function)
#  1000   8.793    0.009    8.793    0.009 {built-in method time.sleep}
# ...
#
# ncalls = # times call occurred
# tottime = total time spent in that call alone, not incl. things it calls
# percall = avg time spent in that call alone, across ncalls times it was called
# cumtime = cumulative time spent in that call, incl. time in subcalls
# percall = avg time spent in that, incl. time in subcalls
#
# Example 2:
# python -m cProfile --sort cumtime cprofile_prog.py sort_test
#
# ./cprofile_ex.py sort_test_with_timeit


import random
import time
import sys


def an_expensive_function():
    execution_time = random.random() / 100
    time.sleep(execution_time)


def sort_expensive():
    the_list = random.sample(range(1_000_000), 1_000)
    the_list.sort()


def sort_cheap():
    the_list = random.sample(range(1_000), 10)
    the_list.sort()


if __name__ == "__main__":
    if sys.argv[1] == "sleep_test":
        for _ in range(1000):
            an_expensive_function()
    elif sys.argv[1] == "sort_test":
        sort_expensive()
        for i in range(1000):
            sort_cheap()
    elif sys.argv[1] == "sort_test_with_timeit":
        # output:
        # sort_expensive: avg call = 0.986327ms
        # sort_cheap: avg call = 14.0617624ms

        # notes:
        # - timeit CLI interface auto-determines # repetitions
        # - timeit Python interface uses 1_000_000 as default, must set to lower value
        from timeit import timeit
        sort_expensive_time = timeit(
            setup='from __main__ import sort_expensive',
            stmt='sort_expensive()', number=1000)
        print(f"sort_expensive: avg call = {sort_expensive_time}ms")
        sort_cheap_time = timeit(
            setup='from __main__ import sort_cheap',
            stmt=(
                f'for i in range(1000):'
                f'    sort_cheap()'
            ), number=1000)
        print(f"sort_cheap: avg call = {sort_cheap_time}ms")

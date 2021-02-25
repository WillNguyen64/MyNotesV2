from collections import defaultdict, Counter

def get_number_with_highest_count(counts):
    max_count = 0
    for number, count in counts.items():
        if count > max_count:
            max_count = count
            number_with_highest_count = number
    return number_with_highest_count 

# Even better version
def get_number_with_highest_count_v2(counts):
    return max(
        counts,
        key=lambda number: counts[number]
    )

def most_frequent(numbers):
    # Default type of each value in defaultdict should be int
    counts = defaultdict(int)
    for number in numbers:
        counts[number] += 1
    return get_number_with_highest_count(counts)

# Even better version
def most_frequent_v2(numbers):
    counts = Counter(numbers)
    return get_number_with_highest_count_v2(counts)


print(most_frequent(
    [1, 2, 3, 3, 5, 5, 5, 1]
))

print(most_frequent_v2(
    [1, 2, 3, 3, 5, 5, 5, 1]
))

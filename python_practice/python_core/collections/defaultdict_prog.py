
from collections import defaultdict

# defaultdict
# Define default value for dict of the specified type

counts = defaultdict(int)
words = ["larry", "curly", "moe", "larry", "curly"]

for word in words:
    counts[word] += 1

print(counts)


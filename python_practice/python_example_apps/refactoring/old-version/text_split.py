import re

product_review = '''This is a fine milk, but the product
line appears to be limited in available colors. I
could only find white.'''

# The DOTALL flag matches any character including a newline
sentence_pattern = re.compile(r'(.*?\.)(\s|$)', re.DOTALL)
matches = sentence_pattern.findall(product_review)
sentences = [match[0] for match in matches]

print(sentences)

word_pattern = re.compile(r"([\w\-']+)([\s,.])?")
for sentence in sentences:
    matches = word_pattern.findall(sentence)
    words = [match[0] for match in matches]
    print(words)


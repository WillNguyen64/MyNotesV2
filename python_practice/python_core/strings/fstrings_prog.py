
########## f-strings ##########

# Multiline
name = 'John Doe'
age = 32
occupation = 'gardener'

msg = (
    f'Name: {name}\n'
    f'Age: {age}\n'
    f'Occupation: {occupation}'
)

print(msg)

msg2 = f'''
Name: {name}
Age: {age}
Occupation: {occupation}
'''

print(msg2)

# Formatting floats
val = 12.3
print(f'{val:.2f}')

# Format widths for value
# Value may be filled with spaces or other chars if value shorter than specified width
for x in range(1, 11):
    print(f'{x:02} {x*x:3} {x*x*x:4}')

# Justify strings
# Use the '>' char to right justify values
s1 = 'a'; s2 = 'ab';  s3 = 'abc'; s4 = 'abcd'
print(f'{s1:>10}')
print(f'{s2:>10}')
print(f'{s3:>10}')
print(f'{s4:>10}')

# Format using numeric notations (hex, oct, scientific)
a = 300
## hex
print(f'{a:x}')
## octal
print(f'{a:o}')
## scientific
print(f'{a:e}')

# Formatting datetime
import datetime
now = datetime.datetime.now()
# 2020-04-07 11:18
print(f'{now:%Y-%m-%d %H:%M}')
# 11:25:18 AM
print(f'{now:%I:%M:%S %p}')
# Tuesday April 07 2020
print(f'{now:%A %B %d %Y}')

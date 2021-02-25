# General rules for inheritence
# 1. Use inheritence to represent true is-a relationships. Subclasses should
#    specialize behavior.
# 2. Use composition for has-a relationships.  (code reuse)

# Sandy Metz's tests for good inheritence
#
# 1. Shallow, narrow object hierarchy. 
#      i.e., one level of hierarchy deep, small of subclasses
# 2. Subclasses don't know about other objects
# 3. Subclasses use or specialize all of the functionality from their superclass

# Rules for Multiple Inheritence
# 
# In cooperative multiple inheritance, each class commits to having the same
# method signatures (substitutability) and to calling super().some_method()
# from within its own some_method().
# The presence of super() in each method means Python will keep going through
# the method resolution order even after it finds a method.

class BigCat:
    def eats(self):
        return ['rodents']

class Lion(BigCat):
    def eats(self):
        return super().eats() + ['wildebeest']

class Tiger(BigCat):
    def eats(self):
        return super().eats() + ['water buffalo']


class Liger(Lion, Tiger):
    def eats(self):
        return super().eats() + ['rabbit', 'cow', 'pig', 'chicken']

if __name__ == '__main__':
    lion = Lion()
    print('The lion eats', lion.eats())
    tiger = Tiger()
    print('The tiger eats', tiger.eats())
    liger = Liger()
    print('The liger eats', liger.eats())
    # Method resolution order (<class '__main__.Liger'>, <class '__main__.Lion'>,
    # <class '__main__.Tiger'>, <class '__main__.BigCat'>, <class 'object'>)
    print(f'Method resolution order {Liger.__mro__}')

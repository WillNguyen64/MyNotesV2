
# Run unit tests
# $ pytest
#
# Test discovery:
# 1. Looks for tests in ./tests for classes named Test*
# 2. Look for methods starting with test_

# Notes:
# - Add __init__.py to each subdirectory under ./tests to make tests discoverable
# - For more about Pytest, read this book:
#   Python Testing with pytest: Simple, Rapid, Effective,and Scalable

from tests.examples.product import Product

class TestProduct:
    def test_working(self):
        pass

    def test_transform_name_for_sku(self):
        small_black_shoes = Product('shoes', 'S', 'black')
        expected_value = 'SHOES'
        actual_value = small_black_shoes.transform_name_for_sku()
        assert expected_value == actual_value

    def test_transform_color_for_sku(self):
        assert 'BLACK' == Product('shoes', 'S', 'black').transform_color_for_sku()

    def test_generate_sku(self):
        assert 'SHOES-S-BLACK' == Product('shoes', 'S', 'black').generate_sku()

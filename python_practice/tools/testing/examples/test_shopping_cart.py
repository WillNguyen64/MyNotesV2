
from tests.examples.shopping_cart import ShoppingCart
from tests.examples.product import Product


class TestShoppingCart:
    def test_add_and_remove_product(self):
        cart = ShoppingCart()
        product = Product('shoes', 'S', 'blue')

        cart.add_product(product)
        cart.remove_product(product)

        assert {} == cart.products

    def test_cart_initially_empty(self):
        cart = ShoppingCart()
        assert {} == cart.products

    def test_add_product(self):
        cart = ShoppingCart()
        product = Product('shoes', 'S', 'blue')
        cart.add_product(product)
        assert {'SHOES-S-BLUE': {'quantity': 1}} == cart.products

    # etc....

    def test_remove_too_many_products(self):
        cart = ShoppingCart()
        product = Product('shoes', 'S', 'blue')
        cart.add_product(product)
        cart.remove_product(product, quantity=2)
        assert {} == cart.products

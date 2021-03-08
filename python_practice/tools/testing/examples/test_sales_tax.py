
import io
from unittest import mock

from tests.examples.sales_tax import add_sales_tax

# Test doubles:
# - Faking: create a system that imitates the real one
# - Stubbing: Return predetermined values as a response
# - Mocking: Use a system with the same interface as the real one, but also
#            records interactions for later inspection and assertions

class TestSalesTax:

    @mock.patch('tests.examples.sales_tax.urlopen')
    def test_get_sales_tax_returns_proper_value_from_api(
        self,
        mock_urlopen
    ):
        test_tax_rate = 1.06
        mock_urlopen.return_value = io.BytesIO(
            str(test_tax_rate).encode('utf-8')
        )

        expected = 5 * test_tax_rate
        actual = add_sales_tax(5, 'USA', 'MI')
        assert expected == actual

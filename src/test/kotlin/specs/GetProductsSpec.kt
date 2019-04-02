package specs

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject
import io.kotlintest.inspectors.forAll
import io.kotlintest.matchers.collections.shouldBeSortedWith
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.FeatureSpec
import io.swagger.client.models.Product
import models.nongenerated.Products
import java.util.*

class GetProductsSpec : FeatureSpec({
    // Cases are taken from http://localhost:3030/queries
    feature("get queries") {
        FuelManager.instance.basePath = "http://localhost:3030"
        val PRODUCTS = "/products"
        val allProducts = mutableListOf<Product>()

        scenario("get all products") {
            val (_, response, result) = PRODUCTS.httpGet().responseObject<Products>()
            val (products, fuelError) = result

            response.statusCode shouldBe 200
            fuelError shouldBe null
            // I'd prefer to start with empty db, generate products, add them their, and save them to Products object
            // and compare objects. Because I don't have chance to do it, let's check at least for total and limit
            products?.total shouldBe 51957
            products?.limit shouldBe products?.data?.size
        }

        scenario("get product with id 9132294") {
            val ID = 9132294
            val EXPRECTED_PRODUCT = Product(
                name = "Yamaha - P32D Pianica - Brown/White",
                type = "HardGood",
                upc = "086792895093",
                description = "Keyboard wind instrument; designed for general music education; mouthpiece, anticorrosive reed and blowing pipe; 32-note design; shock-resistant, double-walled, blow-molded case",
                model = "EN033P32D",
                price = 59.99.toBigDecimal(),
                shipping = 0.toBigDecimal(),
                manufacturer = "Yamaha",
                url = "http://www.bestbuy.com/site/yamaha-p32d-pianica-brown-white/9132294.p?id=1218990144149&skuId=9132294&cmp=RMXCC",
                image = "http://img.bbystatic.com/BestBuy_US/images/products/9132/9132294_sa.jpg"
            )

            val (_, response, result) = "$PRODUCTS/$ID".httpGet().responseObject<Product>()
            val (product, fuelError) = result

            response.statusCode shouldBe 200
            fuelError shouldBe null
            product shouldBe EXPRECTED_PRODUCT
        }

        scenario("get all products, limit to 1 result") {
            val LIMIT = 1
            val (_, response, result) = "$PRODUCTS?\$limit=$LIMIT".httpGet().responseObject<Products>()
            val (products, fuelError) = result

            response.statusCode shouldBe 200
            fuelError shouldBe null
            products?.total shouldBe 51957
            products?.limit shouldBe LIMIT
            products?.limit shouldBe products?.data?.size
        }

        scenario("get all products, skip to the 30,001th result") {
            val SKIP = 30_000
            val (_, response, result) = "$PRODUCTS?\$skip=$SKIP".httpGet().responseObject<Products>()
            val (products, fuelError) = result

            response.statusCode shouldBe 200
            fuelError shouldBe null
            products?.total shouldBe 51957
            products?.skip shouldBe SKIP
            // Here we should get all products from our database and check that skip works
            // products?.data shouldBe allProducts.subList(SKIP + 1, SKIP + 11)
        }

        scenario("max limit is 25") {
            val MAX_LIMIT = 25
            val BIG_LIMIT = 30000
            val (_, response, result) = "$PRODUCTS?\$limit=$BIG_LIMIT".httpGet().responseObject<Products>()
            val (products, fuelError) = result

            response.statusCode shouldBe 200
            fuelError shouldBe null
            products?.total shouldBe 51957
            products?.limit shouldNotBe BIG_LIMIT
            products?.limit shouldBe MAX_LIMIT
            products?.data?.size shouldBe MAX_LIMIT
        }

        scenario("get all products, sort by highest price (descending)") {
            val (_, response, result) = "$PRODUCTS?\$sort[price]=-1".httpGet().responseObject<Products>()
            val (products, fuelError) = result
            val prices = products?.data?.map { it.price }

            response.statusCode shouldBe 200
            fuelError shouldBe null
            products?.total shouldBe 51957
            prices?.shouldBeSortedWith(Comparator.reverseOrder())
        }

        scenario("get all products, sort by lowest price (ascending)") {
            val (_, response, result) = "$PRODUCTS?\$sort[price]=1".httpGet().responseObject<Products>()
            val (products, fuelError) = result
            val prices = products?.data?.map { it.price }

            response.statusCode shouldBe 200
            fuelError shouldBe null
            products?.total shouldBe 51957
            prices?.shouldBeSortedWith(Comparator.naturalOrder())
        }

        scenario("get all products, but only show the name and price in the result") {
            val (_, response, result) = "$PRODUCTS?\$select[]=name&\$select[]=price".httpGet().responseObject<Products>()
            val (products, fuelError) = result
            val EXPECTED_PRODUCT = Product(
                name = "Duracell - AAA Batteries (4-Pack)",
                price = 5.49.toBigDecimal()
            )

            response.statusCode shouldBe 200
            fuelError shouldBe null
            products?.total shouldBe 51957
            products?.data?.get(0) shouldBe EXPECTED_PRODUCT
        }

        scenario("get products of certain type") {
            val HARDGOOD_TYPE = "HardGood"
            val (_, response, result) = "$PRODUCTS?type=$HARDGOOD_TYPE".httpGet().responseObject<Products>()
            val (products, fuelError) = result

            response.statusCode shouldBe 200
            fuelError shouldBe null
            products?.total shouldBe 46496
            products?.data?.forAll { it.type shouldBe HARDGOOD_TYPE }
        }
    }
})
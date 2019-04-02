package models.nongenerated

import io.swagger.client.models.Product

data class Products(
    val total: Int,
    val limit: Int,
    val skip: Int,
    val data: List<Product>
)
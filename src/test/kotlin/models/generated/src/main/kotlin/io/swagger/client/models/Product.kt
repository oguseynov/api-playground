/**
* Best Buy API Playground
* A sample dataset and API for you to experiment with.
*
* OpenAPI spec version: 1.1.0
* Contact: developer@bestbuy.com
*
* NOTE: This class is auto generated by the swagger code generator program.
* https://github.com/swagger-api/swagger-codegen.git
* Do not edit the class manually.
*/
package io.swagger.client.models


/**
 * 
 * @param name 
 * @param type 
 * @param price The Price Of the Product
 * @param shipping 
 * @param upc 
 * @param description 
 * @param manufacturer 
 * @param model 
 * @param url 
 * @param image 
 */
data class Product (
    val name: kotlin.String,
    // According JSON schema this field is required, can not be null, make required when data with null is fixed
    val type: kotlin.String? = null,
    // According JSON schema this field is required, can not be null, make required when data with null is fixed
    val upc: kotlin.String? = null,
    // According JSON schema this field is required, can not be null, make required when data with null is fixed
    val description: kotlin.String? = null,
    // According JSON schema this field is required, can not be null, make required when data with null is fixed
    val model: kotlin.String? = null
,
    /* The Price Of the Product */
    val price: java.math.BigDecimal? = null,
    val shipping: java.math.BigDecimal? = null,
    val manufacturer: kotlin.String? = null,
    val url: kotlin.String? = null,
    val image: kotlin.String? = null
) {
}
# What I have done
1. Created kotlin project for writing tests on API
1. Generated models for tests from http://localhost:3030/swagger.json by swagger-codegen
1. Wrote some tests for /products. 

# A list of test cases proposed for automation
Because of the fact that it is api tests, they run fast and easy to write,
I would say that we should write test for all positive, edge and corner
cases, for all endpoints and their functionality. 

# Please automate proposed test cases
I have automated some get products cases, I should mention that they are
not full test suit even for that functionality. Please concentrate not
on the number of test, but on solutions used and on how they are written.

# A short explanation of the provided solution
* Tests are written in Kotlin.
* Data models are generated form swagger.json by swagger-codegen. 
* KotlinTest is used as test runner because the fact that it allows to write
Gherkin style tests, has a big number of matchers and can be easily tuned.
* Faker library for generating data, not used it tests, but I use it a lot
in everyday life.
* Fuel as http client, easy to use, has a lot of integrations from box,
for instance with Jackson.

# How to run tests
1. Make sure you have NodeJS installed (we require version 4 or newer).
1. Make sure you have node-gyp, python2, gcc installed
1. git clone https://github.com/bestbuy/api-playground/
1. cd api-playground
1. npm install, if you have problems with sqlite3 installation - uninstall it
clean npm cash, and re-install sqlite3
1. npm start
1. Clone my project
1. Open it in Intellij Idea
1. Run GetProductsSpec that is in src/test/kotlin/specs or just from terminal:
``./gradlew test``


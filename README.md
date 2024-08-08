# Warehouse
Backend application for warehouse management which intends to provide basic functionalities like
viewing all the products and articles with quantities available in the inventory, selling products
and updating inventory accordingly.

### Assignment details
This assignment is to implement a simple warehouse application.This software can loads artcile details from a inventory.json file and hold
articles with id, a name and available stock. The warehouse application, similarly also loads product details from product.json file and hold product details.
It provides the following functionalities:
- Get all the products along with their quantities that is available in current inventory.
- Remove or sell a product by name from the inventory and update it accordingly.
- Get list of all articles from inventory along with their present stock.
- Get a particular article from inventory with its details.

### Approach
Expose an interactive REST API to manipulate inventory of the warehouse

### Usage

1. Get this repository in local
   ```shell
   git clone 
   ```
2. Enter into folder created from git clone
   ```shell
   cd warehouse
   ```
3. install `maven` dependencies
   ```shell
   mvn clean install
   ``` 
3. run application
   ```shell
   mvn spring-boot:run
   ```
### Prerequisite
1. Java 11 or higher version
2. PostgresSQL 13.6 or higher version
3. Maven 3.6.3 or higher
> Update Database connection information in application.properties
### Database migration
> For database migration we are using `Flyway` an open-source database migration tool

Things to follow for migration scripts:
- All migration scripts (`.sql`) should go in `resources/db/migration` location
- Script should follow `V<version-number>__<description>.sql` this format.
    - Example, `V1.0.0__initial_script.sql`
- Once migration script is applied you should not change anything in existing script. _If you do so, it will throw an error_.
- Create new migration file with different version number for any further DB operations.

### API Details
<details>
<summary>

#### 1.`POST /api/v1/import/inventoryfile` _import articles from json file_
</summary>
<p>

###### Request
```curl
curl --location --request POST 'http://localhost:8080/api/v1/import/inventoryfile' --form 'file=@"<filepath>/inventory.json";type=application/json'
```
> Kindly change file path as per file location
</p>

<p>

###### Response
```
{
    "responseCode": 200,
    "responseMessage": "Record successfully imported",
    "responseData": [
        {
            "name": "leg",
            "stock": 12,
            "art_id": "1"
        },
        {
            "name": "screw",
            "stock": 17,
            "art_id": "2"
        },
        {
            "name": "seat",
            "stock": 2,
            "art_id": "3"
        },
        {
            "name": "table top",
            "stock": 1,
            "art_id": "4"
        }
    ]
}
```
</p>
</details>
<details>
<summary>

#### 2. `POST /api/v1/import/productfile` _import product from json file_
</summary>
<p>

##### Request
```curl
curl --location --request POST 'http://localhost:8080/api/v1/import/productfile' --form 'file=@"<filepath>/products.json";type=application/json'
```
> Kindly change file path as per file location
>
</p>
<p>

###### Response
```
{
    "responseCode": 200,
    "responseMessage": "Record successfully imported",
    "responseData": [
        {
            "name": "Dining Chair",
            "contain_articles": [
                {
                    "art_id": "1",
                    "amount_of": 4
                },
                {
                    "art_id": "2",
                    "amount_of": 8
                },
                {
                    "art_id": "3",
                    "amount_of": 1
                }
            ]
        },
        {
            "name": "Dinning Table",
            "contain_articles": [
                {
                    "art_id": "1",
                    "amount_of": 4
                },
                {
                    "art_id": "2",
                    "amount_of": 8
                },
                {
                    "art_id": "4",
                    "amount_of": 1
                }
            ]
        }
    ]
}
```
</p>
</details>


<details>
<summary>

#### 3. `GET /api/v1/articles` _Get all articles from inventory_
</summary>
<p>

###### Request
```curl
curl http://localhost:8080/api/v1/articles
```
</p>
<p>

###### Response
```
{
    "responseCode": 200,
    "responseMessage": "Record found successfully",
    "responseData": [
        {
            "name": "leg",
            "stock": 12,
            "art_id": "1"
        },
        {
            "name": "screw",
            "stock": 17,
            "art_id": "2"
        },
        {
            "name": "seat",
            "stock": 2,
            "art_id": "3"
        },
        {
            "name": "table top",
            "stock": 1,
            "art_id": "4"
        }
    ]
}
```
</p>
</details>

<details>
<summary>

#### 4. `GET /api/v1/products` _Get all products_
</summary>
<p>

###### Request
```curl
curl http://localhost:8080/api/v1/products
```
</p>
<p>

###### Response
```
{
    "responseCode": 200,
    "responseMessage": "Record found successfully",
    "responseData": [
        {
            "name": "Dining Chair",
            "contain_articles": [
                {
                    "art_id": "1",
                    "amount_of": 4
                },
                {
                    "art_id": "2",
                    "amount_of": 8
                },
                {
                    "art_id": "3",
                    "amount_of": 1
                }
            ]
        },
        {
            "name": "Dinning Table",
            "contain_articles": [
                {
                    "art_id": "1",
                    "amount_of": 4
                },
                {
                    "art_id": "2",
                    "amount_of": 8
                },
                {
                    "art_id": "4",
                    "amount_of": 1
                }
            ]
        }
    ]
}
```
</p>
</details>

<details>
<summary>

#### 5. `GET /api/v1/available/products` _Get all products and quantity of each that is an available with the current inventory_
</summary>
<p>

###### Request
```curl
curl http://localhost:8080/api/v1/available/products
```
</p>
<p>

###### Response
```
{
    "responseCode": 200,
    "responseMessage": "Record found successfully",
    "responseData": [
        {
            "name": "Dining Chair",
            "isAvailable": true,
            "quantityInStock": 2,
            "contain_articles": [
                {
                    "stock": 12,
                    "art_id": "1",
                    "amount_of": 4
                },
                {
                    "stock": 17,
                    "art_id": "2",
                    "amount_of": 8
                },
                {
                    "stock": 2,
                    "art_id": "3",
                    "amount_of": 1
                }
            ]
        },
        {
            "name": "Dinning Table",
            "isAvailable": true,
            "quantityInStock": 1,
            "contain_articles": [
                {
                    "stock": 12,
                    "art_id": "1",
                    "amount_of": 4
                },
                {
                    "stock": 17,
                    "art_id": "2",
                    "amount_of": 8
                },
                {
                    "stock": 1,
                    "art_id": "4",
                    "amount_of": 1
                }
            ]
        }
    ]
}
```
</p>
</details>

<details>
<summary>

#### 6. `DELETE /api/v1/sell/product` _Remove(Sell) a product and update the inventory accordingly_
</summary>
<p>

###### Request
```curl
curl --location --request DELETE 'http://localhost:8080/api/v1/sell/product?productName=Dinning Table'
```
</p>
<p>

###### Response
```
{
    "responseCode": 200,
    "responseMessage": "Product has been sold",
    "responseData": {
        "name": "Dinning Table",
        "contain_articles": [
            {
                "art_id": "1",
                "amount_of": 4
            },
            {
                "art_id": "2",
                "amount_of": 8
            },
            {
                "art_id": "4",
                "amount_of": 1
            }
        ]
    }
}
```
</p>
</details>


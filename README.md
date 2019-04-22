Travel Backend API
======================
Clone this repo and start the service:
1. cd \<path to\>\travel-backend-service
2. Buid using "mvn package"
3. Run using "java -jar target\travel-backend-0.0.1-SNAPSHOT.jar

Service will start on Port: 9000

Resource endpoints:
===================
**Retrieve a list of airports**
```sh
http://localhost:9000/airports 
```

Query Params:
  * lang: the language, supported ones are nl and en
  * term: A search term that searches through code, name and description
  
Example
```sh
Request:
GET http://localhost:9000/airports

Response:
[
    {
        "code": "YOW",
        "name": "Ottawa International",
        "description": "Ottawa - Ottawa International (YOW), Canada",
        "coordinates": {
            "latitude": 45.32083,
            "longitude": -75.67278
        }
    },
    {
        "code": "BBA",
        "name": "location.airport.BBA.long",
        "description": "location.city.BBA.long - location.airport.BBA.long (BBA), Chile",
        "coordinates": {
            "latitude": -45.916389,
            "longitude": -71.686944
        }
    },
    
    .....
]
```


**Retrieve a specific airport**
```sh
http://localhost:9000/airports/{code}
```

Query Params:
  * lang: the language, supported ones are nl and en
  
Example
```sh
Request:
GET http://localhost:9000/airports/SDK

Response:
{
    "code": "SDK",
    "name": "Sandakan",
    "description": "Sandakan - Sandakan (SDK), Malaysia",
    "coordinates": {
        "latitude": 5.9,
        "longitude": 118.05972
    }
}
```


**Retrieve a fare offer**
```sh
http://localhost:9000/fare/{origin_code}/{destination_code}
```

Query Params:
  * currency:  the requested resulting currency, supported ones are EUR and USD


Example
```sh
Request:
GET http://localhost:9000/fare/YOW/BBA

Response:
{
    "amount": 1157.19,
    "currency": "EUR",
    "origin": {
        "code": "YOW",
        "name": "Ottawa International",
        "description": "Ottawa - Ottawa International (YOW), Canada",
        "coordinates": {
            "latitude": 45.32083,
            "longitude": -75.67278
        }
    },
    "destination": {
        "code": "BBA",
        "name": "location.airport.BBA.long",
        "description": "location.city.BBA.long - location.airport.BBA.long (BBA), Chile",
        "coordinates": {
            "latitude": -45.916389,
            "longitude": -71.686944
        }
    }
}
```


**Retrieve statistics**
```sh
http://localhost:9000/metric
```

Example
```sh
Request:
GET http://localhost:9000/metric

Response:
{
    "totalRequest": 6,
    "totalOk": 4,
    "total4xx": 2,
    "total5xx": 0,
    "avgResponseTimeOk": 3448,
    "minResponseTimeOk": 29,
    "maxResponseTimeOk": 8939
}
```

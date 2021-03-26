# payrollengine-organigram
**Allow cluster connectivity**
This step is necessary if you want to run the Spring-boot app standalone and/or to be able to execute Integration tests.
Modify your /etc/hosts file and add the following 3 entries:
# INSTALLATION
##1. Backend
##1.1. Modify your /etc/hosts file and add the following 3 entries:
```
127.0.0.1 mongo1
127.0.0.1 mongo2
127.0.0.1 mongo3
```

##1.2. Run the Spring-boot app non-dockerized
```
docker-compose up
```

##1.3. Load sample data
Organizational Unit:
```
docker-compose exec mongo1 mongoimport --host mongo1 --db test --collection nodes --type json --file /tmp/organizational_unit.json --jsonArray
```

Organizational Position:
```
docker-compose exec mongo1 mongoimport --host mongo1 --db test --collection nodes --type json --file /tmp/organizational_position.json --jsonArray
```

Organizational Employee:
```
docker-compose exec mongo1 mongoimport --host mongo1 --db test --collection nodes --type json --file /tmp/organizational_employee.json --jsonArray
```

##2. FrontEnd
```
npm install
```

```
npm run dev
```

##3. Access application
```
npm install
```

##Notes
MongoDB connect: 
```
127.0.0.1:27020
```
If you want to create manually data from PostMan, please download here

# API LIST
## 1. Get All Organization

GET /payroll-organigram-service/{rootId} HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Accept: application/json
Accept-Charset: utf-8

## 2. Get Organization Unit

GET /payroll-organigram-service/{rootId}/st/{orgUnitId} HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Accept: application/json
Accept-Charset: utf-8

## 3. Create new Organization Unit

POST /payroll-organigram-service/{rootId}/{orgUnitId} HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Accept: application/json
Accept-Charset: utf-8


- Create root node
```
{
  "orgUnitId": -1,
  "name": "Root",
  "rootId": 1001,
  "parentOrgUnitId": [
  ],
  "entityType": "type_1"
}
```

- Create next node is child of root:
```
{
  "name": "Node1",
  "rootId": 1001,
  "parentOrgUnitId": [
      -1
  ],
  "entityType": "type_2"
}
```
```
{
  "name": "Node2",
  "rootId": 1001,
  "parentOrgUnitId": [
      -1
  ],
  "entityType": "type_2"
}
```

## 4. Move Organization Unit

PUT /payroll-organigram-service/{rootId}/{orgUnitId} HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Accept: application/json
Accept-Charset: utf-8

**API LIST:**
1. Get All Organization

GET /payroll-organigram-service/{rootId} HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Accept: application/json
Accept-Charset: utf-8

2. Get Organization Unit

GET /payroll-organigram-service/{rootId}/st/{orgUnitId} HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Accept: application/json
Accept-Charset: utf-8

3. Create new Organization Unit

POST /payroll-organigram-service/{rootId}/{orgUnitId} HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Accept: application/json
Accept-Charset: utf-8


- Create root node
{
  "orgUnitId": -1,
  "name": "Root",
  "rootId": 1001,
  "parentOrgUnitId": [
  ],
  "entityType": "type_1"
}


- Create next node is child of root:
{
  "name": "Node1",
  "rootId": 1001,
  "parentOrgUnitId": [
      -1
  ],
  "entityType": "type_2"
}

{
  "name": "Node2",
  "rootId": 1001,
  "parentOrgUnitId": [
      -1
  ],
  "entityType": "type_2"
}

4. Move Organization Unit

PUT /payroll-organigram-service/{rootId}/{orgUnitId} HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Accept: application/json
Accept-Charset: utf-8



# payrollengine-organigram
**Allow cluster connectivity**
This step is necessary if you want to run the Spring-boot app standalone and/or to be able to execute Integration tests.
Modify your /etc/hosts file and add the following 3 entries:
```
127.0.0.1 mongo1
127.0.0.1 mongo2
127.0.0.1 mongo3
```



**Bring up the MongoDB cluster with:**
```
docker-compose up
```

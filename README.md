# channels

## Prerequirements

* check if ```rso1920``` exist in docker

```docker run -d --name rso1920-pg-catalog-api --network rso1920 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=catalog -p 5435:5432 postgres:12 ```

## RUN

``````docker run -d --name rso1920-channels-api --network rso1920 -e KUMULUZEE_CONFIG_ETCD_HOSTS=http://etcd:2379 -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://rso1920-pg-catalog-api:5432/channels  -p 8080:8080 rso1920/channels:latest``````

## OpenAPI
```
http://localhost:8080/api-specs/v1/openapi.json
```

```
http://localhost:8080/api-specs/ui
```
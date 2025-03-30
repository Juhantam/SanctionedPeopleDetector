# Sanctioned People Detector

## Configurations

### Local database configuration

In order to run the local database: Navigate to `etc/local/docker/db` and run `docker-compose up -d`

### Code repository configurations

- Use JDK22
- Turn on Annotation Processing
- Run the application with gradle task `gradle bootRun`

## Example requests

### SAVE
localhost:8800/api/web/sanctioned-names/save with body  
{  
"names":["Pimm Pomm"]  
}

### GET
localhost:8800/api/web/sanctioned-names/name-suspicion-level?name=Osama Bin Laden

### DELETE
localhost:8800/api/web/sanctioned-names/delete?ids=1&ids=2
@cls
@echo ================

@echo Generating client lib
cmd /c openapi-generator-cli generate -i http://localhost:8080/swagger.json -g python-legacy -o python --global-property=apiTests=false,modelTests=false,modelDocs=false,apiDocs=false --additional-properties=usePromises=true

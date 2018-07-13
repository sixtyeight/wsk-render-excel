# OpenWhisk #

n.b: Using this on MiniShift using the openwhisk-template will *NOT* work out of the box due to the memory limits. Template: https://raw.githubusercontent.com/projectodd/openwhisk-openshift/master/template.yml [(persistent version)](https://raw.githubusercontent.com/projectodd/openwhisk-openshift/master/persistent-template.yml)

* [Running OpenWhisk on OpenShift](https://github.com/projectodd/openwhisk-openshift)
* [Apache OpenWhisk](https://openwhisk.apache.org/)
* [OpenShift Origin](https://github.com/openshift/origin)
* [IBM Cloud Functions Shell](https://github.com/ibm-functions/shell)

## Deploy ##
``
$ wsk -i -v action create excel -m 1024 --main com.example.FunctionApp target/wsk-action-render-excel.jar
``
    
## Invoke blocking ##
This will invoke the action and display only the result.
```
    $ wsk -i action invoke excel -P example.json --result
    {
        "report": "UEsDBBQACAgIAM2o7EwAAAAAAAAAAAAAAAALAAAAX3JlbHM...
```
Or, even more fancy, use jq and base64 to decode the report back to an XLS file:
```
    $ wsk -i action invoke excel -P example.json --result | jq '.report' -r | base64 -d - > result.xls
```

## Invoke asynchronous ##
This will invoke the action and display only the activation id.
```
    $ wsk -i action invoke excel -P example.json 
    ok: invoked /_/excel with id d2606a504a204f99a06a504a20cf9918
```
Use the activation id to get the full response (including the result).
```
    $ wsk -i activation get d2606a504a204f99a06a504a20cf9918
    ok: got activation d2606a504a204f99a06a504a20cf9918
    {
        "namespace": "whisk.system",
        "name": "excel",
        "version": "0.0.1",
        "subject": "whisk.system",
        "activationId": "d2606a504a204f99a06a504a20cf9918",
        "start": 1531429290770,
        "end": 1531429293369,
        "duration": 2599,
        "response": {
            "status": "success",
            "statusCode": 0,
            "success": true,
            "result": {
                "report": "UEsDBBQACAgIADCo7EwAAAAAAAAAAAAAAAALAAAAX3JlbHM...
...
        },
        "logs": [
            ...... stdout logging .....
        ],
        "annotations": [
            {
                ....
```

wso2registry-extensions
=======================

This repository hosts extensions supporting API management with wso2 governance registry.

There are three modules types:
* A web application (WAR) deployed to the WSO2 carbon platform in order to support integration 
  with other systems (based on Apache Camel).
* A maven plugin to publish artifacts to the registry during the build process.
* Handlers deployed to the service registry, in order to extend the core functionality.



Upgrade recipe from 4.5.2 to 4.5.3:
***********************************
* update service.rxt via Management UI!
* update custom droplist (from overlay) => just verify, should be in build
* Configure Roles: add service, schema, wsdls permissions to the roles (these 
  are generic now!)
* DB Connection for API-Store (based on H2) is added
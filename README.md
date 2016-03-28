# AEM /Conf demo

The project is a simple demonstration of Adobe Experience Manager's new `/conf` node and the `com.adobe.granite.confmgr.ConfMgr` service.

See [http://www.nateyolles.com/blog/2016/03/aem-slash-conf-and-confmgr](http://www.nateyolles.com/blog/2016/03/aem-slash-conf-and-confmgr) for a detailed description of `/conf` and `com.adobe.granite.confmgr.ConfMgr`.

## Description

### Sites

The demonstration is a multi-tenant project containing 3 companies and a sub project:

* Pied Piper: [http://localhost:4502/content/piedpiper/en.html](http://localhost:4502/content/piedpiper/en.html)
* Aviator: [http://localhost:4502/content/aviato/en.html](http://localhost:4502/content/aviato/en.html)
* Hooli: [http://localhost:4502/content/hooli/en.html](http://localhost:4502/content/hooli/en.html)
* Hooli XYZ subproject: [http://localhost:4502/content/hooli/hoolixyz/en.html](http://localhost:4502/content/hooli/hoolixyz/en.html)

### Component

All sites use one custom component that displays the correct Twitter and Facebook links as per the sites corresponding configuration.

* Sling Model: /core/src/main/java/com/nateyolles/aem/slashconfdemo/core/models/SocialMediaModel.java
* Sightly Component: /ui.apps/src/main/content/jcr_root/apps/slashconfdemo/components/content/socialmedia/socialmedia.html

### Configurations

The global settings disable both sites:

````
/conf/global/settings/socialmedia/facebook/jcr:content/@enabled = false
/conf/global/settings/socialmedia/twitter/jcr:content/@enabled = false
````

Hooli enables and configures both sites:
````
/conf/tenants/hooli/settings/socialmedia/twitter
/conf/tenants/hooli/settings/socialmedia/facebook
````

Hooli XYZ inherits from Hooli but overwrites Twitter:
````
/conf/tenants/hooli/hoolixyz/settings/socialmedia/twitter
````

Pied Piper configures both sites. Aviato does not have a configuration and thus falls back to the Global settings.

### Linking to configurations

The sites Pied Piper and Hooli, and well as the subsite Hooli XYZ, are linked to their perspective configurations:

````
/content/piedpiper/jcr:content/@cq:conf = /conf/tenants/piedpiper
/content/hooli/jcr:content/@cq:conf = /conf/tenants/hooli
/content/hooli/hoolixyz/jcr:content/@cq:conf = /conf/tenants/hooli/hoolixyz
````

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality in Sling Models
* ui.apps: contains the /apps and /etc parts of the project containing clientlibs, components, and templates
* ui.content: contains sample content using the components from the ui.apps
* ui.conf: contains configurations for our sample companies

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with  

    mvn clean install -PautoInstallPackage

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html

## Other

### Dependencies

Reference the `com.adobe.granite.confmgr` dependency by using the uber-jar:

````
<dependency>
    <groupId>com.adobe.aem</groupId>
    <artifactId>uber-jar</artifactId>
    <version>6.1.0</version>
    <classifier>obfuscated-apis</classifier>
    <scope>provided</scope>
</dependency>
````

Alternatively, you can reference the dependency specifically:

````
<dependency>
    <groupId>com.adobe.granite</groupId>
    <artifactId>com.adobe.granite.confmgr</artifactId>
    <version>1.0.0</version>
    <scope>provided</scope>
</dependency>
````

The jar can be obtained from your local AEM instance and installed into your local repository:

````
wget --user admin --password admin http://localhost:4502/libs/cq/commons/install/com.adobe.granite.confmgr-1.0.0.jar &&
mvn install:install-file -Dfile=com.adobe.granite.confmgr-1.0.0.jar -DgroupId=com.adobe.granite -DartifactId=com.adobe.granite.confmgr -Dversion=1.0.0 -Dpackaging=jar
````

### Archetype

````
mvn archetype:generate \
 -DarchetypeGroupId=com.adobe.granite.archetypes \
 -DarchetypeArtifactId=aem-project-archetype \
 -DarchetypeVersion=10 \
 -DarchetypeRepository=https://repo.adobe.com/nexus/content/groups/public/ \
 -DgroupId=com.nateyolles.aem \
 -DartifactId=slash-conf-demo \
 -Dversion=1.0-SNAPSHOT \
 -Dpackage=com.nateyolles.aem.slashconfdemo \
 -DappsFolderName=slashconfdemo \
 -DartifactName="Slash Conf Demo" \
 -DcomponentGroupName="Slash Conf Demo" \
 -DcontentFolderName=slashconfdemo \
 -DcssId=slashconfdemo \
 -DpackageGroup=slashconfdemo \
 -DsiteName="Slash Conf Demo"
 ````

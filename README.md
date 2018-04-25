# java-keyid-client

A Java 8 library to allow you to peform second factor authentication using Intensity Analytics TickStream.KeyID. Designed and tested with OpenJDK.

## Install

Include the JAR file or use the Intensity Analytics public Maven repository. You will also need GSON to process results.

Maven pom.xml example:

```
<repositories>
...
    <repository>
        <id>intensityanalytics-public</id>
        <name>Intensity Analytics Public Release Repository</name>
        <url>https://www.myget.org/F/intensityanalytics-public/maven/</url>
    </repository>
...
</repositories>

<dependencies>
...
    <dependency>
        <groupId>com.intensityanalytics</groupId>
        <artifactId>keyid</artifactId>
        <version>1.3</version>
    </dependency>
...
</dependencies>
```

## Usage

**For detailed usage see the included tests.**

```java
KeyIDSettings settings = new KeyIDSettings();
settings.setLicense("license");
settings.setPassiveEnrollment(true);
settings.setUrl("http://yourkeyidserver");
KeyIDClient client = new KeyIDClient(settings);

client.RemoveProfile("javatest1","","").get();
client.Login("javatest1","tsData from userinput", "").get();
```

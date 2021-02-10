# codeforces-api
[![Language](https://img.shields.io/badge/language-java-green.svg)](https://github.com/CovarianceMomentum/codeforces-api)
[![GitHub license](https://img.shields.io/github/license/CovarianceMomentum/codeforces-api.svg)](https://github.com/CovarianceMomentum/codeforces-api/blob/master/LICENSE)
[![](https://jitpack.io/v/CovarianceMomentum/codeforces-api.svg)](https://jitpack.io/#CovarianceMomentum/codeforces-api)

codeforces-api is a [Codeforces](https://codeforces.com/) API wrapper library written in Java.
Checks the official design doc [here](https://codeforces.com/apiHelp).

[Codeforces](https://codeforces.com/) is a website that hosts competitive programming contests. 
It has almost 100000 registered users, and more than 35000 of them participate in at least one contest during last month.

Codeforces API was introduced [over 7 years ago](https://codeforces.com/blog/entry/12520), 
but to this day there were no wrappers for it written in Java.

## Installation

Currently, [jitpack](https://jitpack.io/) is used for the installation process.

### Gradle
1. Add the JitPack repository in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        // other repositories //
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency (`TAG` is representing current version):
```groovy
dependencies {
    implementation 'com.github.CovarianceMomentum:codeforces-api:TAG'
}
```
### Maven
1. Add the JitPack repository to your pom.xml:
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```
2. Add the dependency (`TAG` is representing current version):
```xml
<dependency>
  <groupId>com.github.CovarianceMomentum</groupId>
  <artifactId>codeforces-api</artifactId>
  <version>TAG</version>
</dependency>
```


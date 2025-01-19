# BypassLogic
Bypass logic ia a gradle plugin that removes code from any JVM based languages based on feature flag.

# Usage:
#### The .java file with the business logic:
```java
@BypassLogic
public static double sum(double a, double b) {
  return a+b;
}
```
#### The decompiled .class file without the business logic:
```java
@BypassLogic
public static double sum(double a, double b) {
  return 0.0;
}
```
# Use Cases:
* Feature Flag - The ability to remove features without the fear of it to appear on production.
* Business Plans - Allow plans for on permiss products if certain features are paid plans other then the client's plan they won't appear anywhere.
* Performance Benchmarknig - The ability to remove heavy features to test the performances of complex logic.
* Running Demos - Make sure running demos won't have any unpredicted behaviour.

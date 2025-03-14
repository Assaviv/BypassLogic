# BypassLogic
The bypass logic project is an open source gradle plugin to remove code from JVM based languages using an annotation and controlled by gradle flag.

The precompiled java class:
```java
@BypassLogic
public void processPaidPlanRequest(String userId) {
  if (!isValidUser(userId)) {
    return;
  }

  handlePaidPlanUserRequest();
}

private boolean isValidUser(String userId) {
  return ...;
}
```

The decompiled .class file:
```java
public void processPaidPlanRequest(String userId) {
  return;
}
```
Easier than ever to control multiple clients with different tiers in on premises softwares, one source code for multiple tiers!

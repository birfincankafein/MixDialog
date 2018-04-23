
[![Release](https://img.shields.io/badge/release-1.0.0-blue.svg?style=flat)](https://bintray.com/birfincankafein/com.birfincankafein/permission-util/_latestVersion)  [![API](https://img.shields.io/badge/API-14+-green.svg?style=flat)]()

What is Permission-Util  
------------   
 Permission-Util is a utility that simplifies Android runtime permission management.
*  It can request permissions in a single method and triggers given callback when permission status is ready.
* It splits all requested permissions as grantedPermissions and deniedPermissions, so you can easily check which permission granted or which one denied by user.
* It returns sum result so you don't need to check all permissions separately. If all permissions granted by user, Permission-util let you to keep your application logic simpler, you don'tt need to manage your permissions-sensitive code inside the `onRequestPermissionsResult`.

Usage
-----
In order to use the library, there are 3 different options:

**1. Gradle dependency**

  -  Add this to your app `build.gradle`:
 ```gradle
dependencies {
	implementation 'com.birfincankafein:permission-util:1.0.0'
}
```

**2. Maven**
- Add the following to the `<dependencies>` section of your `pom.xml`:
 ```xml
<dependency>
        <groupId>com.birfincankafein</groupId>
        <artifactId>permission-util</artifactId>
        <version>1.0.0</version>
</dependency>
```

**3. Ivy**
- Add the following to the `<dependencies>` section of your `ivy.xml`:
```xml
<dependency org='com.birfincankafein' name='permission-util' rev='1.0.0'> <artifact name='permission-util' ext='pom' /> </dependency>
```

How to Use
-----
Before starting, you should override the `onRequestedPermissionsResult` callback from `Activity` or `Fragment` in each of your Activities from which you make permissions requests, trigger the `PermissionUtil.onRequestPermissionsResult`. Single line code, easy.

```java
@Override  
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {  
    PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);  
}
```

You can request permissions from Activity or Fragment using  `requestPermission` for single permission, `requestPermissions` for multiple permissions. 
* Requesting single permission from Activity:
```java
PermissionUtil.requestPermission(activityInstance, Manifest.permission.ACCESS_NETWORK_STATE, new PermissionUtil.onPermissionResultListener() {  
    @Override  
    public void onPermissionResult(boolean isSuccess, int requestCode, ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions) {  
        // checking permission granted from isSuccess
        if(isSuccess){
            // permission granted
            someFunction();
        }
        else{
	        // permission denied. Toast that this permission is required.
	        Toast.makeText(activityInstance, "This permission is required for this operation", Toast.SHORT).show();
        }
    }
});  
break;
```
* Requesting multiple permissions from Activity:
```java
PermissionUtil.requestPermissions(activityInstance, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.READ_CONTACTS}, new PermissionUtil.onPermissionResultListener() {  
	@Override  
    public void onPermissionResult(boolean isSuccess, int requestCode, ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions) {  
        // checking all permissions granted from isSuccess
        if(isSuccess){
            // permission granted
            someFunction();
        }
        else{
	        // permissions denied. Toast that this permission is required.
	        if(deniedPermissions.contains(Manifest.permission.READ_CONTACTS)){
	            Toast.makeText(activityInstance, "Read Contact permission is required for this operation", Toast.SHORT).show();
	        }
	        else if(deniedPermissions.contains(Manifest.permission.READ_CALENDAR)){
	            Toast.makeText(activityInstance, "Read Calendar permission is required for this operation", Toast.SHORT).show();
	        }
        }
    }  
});
```
* Requesting single permission from Fragment:
```java
PermissionUtil.requestPermission(fragmentInstance, Manifest.permission.ACCESS_NETWORK_STATE, new PermissionUtil.onPermissionResultListener() {  
    @Override  
    public void onPermissionResult(boolean isSuccess, int requestCode, ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions) {  
        // checking permission granted from isSuccess
        if(isSuccess){
            // permission granted
            someFunction();
        }
        else{
	        // permission denied. Toast that this permission is required.
	        Toast.makeText(fragmentInstance.getActivity(), "This permission is required for this operation", Toast.SHORT).show();
        }
    }
});  
break;
```
* Requesting multiple permissions from Fragment:
```java
PermissionUtil.requestPermissions(fragmentInstance, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.READ_CONTACTS}, new PermissionUtil.onPermissionResultListener() {  
	@Override  
    public void onPermissionResult(boolean isSuccess, int requestCode, ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions) {  
        // checking all permissions granted from isSuccess
        if(isSuccess){
            // permission granted
            someFunction();
        }
        else{
	        // permissions denied. Toast that this permission is required.
	        if(deniedPermissions.contains(Manifest.permission.READ_CONTACTS)){
	            Toast.makeText(fragmentInstance.getActivity(), "Read Contact permission is required for this operation", Toast.SHORT).show();
	        }
	        else if(deniedPermissions.contains(Manifest.permission.READ_CALENDAR)){
	            Toast.makeText(fragmentInstance.getActivity(), "Read Calendar permission is required for this operation", Toast.SHORT).show();
	        }
        }
    }  
});
```

That's it. Now you can remove untraceable logic and result handling.

Demo
----
You can find the demo application at [app](https://github.com/birfincankafein/permission-util/blob/master/app)  directory. Demo app includes requesting from Activity and Fragment samples.
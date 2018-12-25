# SimpliMVVM

SimpliMVVM is an effort to create a simple and understandable MVVM Library. Idea here is to create a basic code structure that can be further extend based on the project needs.

<br/>

#### Why SimpliMVVM?
The SimpliMvvm concept simplifies the use of MVVM architecture by,
1. Hiding view provider boiler plate code.
2. Provides clean implementation for using view models efficiently.
3. Provides you View models plugged with lifecycle.

#### Implementation

By referring to the sample ```KotlinApp``` application, one can understand the implementation way.

<br/>

##### 1. Add SimpliMvvm library to application
Update application's gradle file with SimpliMvvm dependencies.

```
implementation "com.trayis:simpliKMvvm:2.0.0"
implementation "com.trayis:simpliKMvvmAnnotation:2.0.0"

kapt "com.trayis:simpliKMvvmAnnotation:2.0.0"
annotationProcessor "com.trayis:simpliKMvvmAnnotation:2.0.0"

testAnnotationProcessor "com.trayis:simpliKMvvmAnnotation:2.0.0"
```

<br/>

##### 2. Application class will initialise the ```SimpliVmmvProvider``` component
You just need to add, ```SimpliProviderUtil.setProvider(SimpliMvvmProviderImpl(this))``` in ```onCreate()``` method of application class.

<br/>

##### 3. View classes must be a ```SimpliViewComponent```

1. Your activities and fragments must extend ```SimpliActivity```/```SimpliFragment```
2. These activities / fragments must have ```SimpliViewComponent``` as class level annotation.
3. Generated layout data-binding class should be passed as specification for the class.

```
@SimpliViewComponent
class MainActivity : SimpliActivity<ActivityMainBinding>() {

    ...

}
```

<br/>

##### 4. View models will be 'injected' members of these view classes

1. The view model parameters should have ```SimpliInject``` annotation
2. The view models can have a constructor class or a parameterised constructor but not both.

```
@SimpliInject
var viewModel: MainViewModel? = null
```

##### 5. Parameters of View Model constructor are resources.

1. The constructor parameter is a resource which is could be a network resource, data resource or any asset resource.
2. Application must have a ```SimpliResourcesProvider```. A class which provides instances of resources (refer ```SampleResourceProvider``` sample class).

```
@SimpliResourcesProvider
class SampleResourceProvider {

...

}
```

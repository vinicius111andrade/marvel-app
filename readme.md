# Marvel App Decisions

## Creating App Project
- Chose to use Views instead of Compose, because its much more common in companies currently.
- Chose to use Kotlin DSL for the gradle configuration files, because its recommended by Google, and is the most recent one.


## Configuring App SDK Versions
1. Minimum SDK: the minimum Android API level on which your app can run.
2. Target SDK: is the SDK version that your app was created to run on. It is used to indicate awareness of specific behaviour changes introduced in newer Android versions. You make sure that your app behaves fine at this SDK level, considering its particular behaviour.
3. Compile SDK: determines which API level your app will be compiled with. Using the latest API allows us developers to levarege the latest features.

### Bonus:
1. The compileSDK can not me lower than the targetSDK.
2. The targetSDK can be lower than the compileSDK.
3. You should always use the latest version for the compile and target SDKs.
4. To choose the minimum SDK you should think about and balance: compatibility issues, user base size. Newer versions will have less compatibility issues but fewer users.

### What did I do?
- Chose to set Minimum SDK to API 27, Oreo, Android 8.1, because its the most recent API that will run on more than 90% of devices.
- Chose the Target SDK to be 34, since its the last one available.
- Chose to use compile SDK 34 also, so it would be the same as the target SDK.

## Project Architecture
### MVVM
I will use MVVM, because its the recommended architecture by Google and it is used in most companies.

The main advantage of MVVM is its ease to preserve states, which is perfect for mobile where the OS can choose to kill the activity at anytime when it is on background, and also because rotating the screen kills the activity to rebuild it. MVVM uses the Observable design pattern, in which when an object changes its subscribers updte accordingly.

It makes use of the ViewModel which is lifecycle aware, meaning it responds to changes in Views lifecycle states, making it easier to preserve its state.

### Clean Architecture
I will also follow the Clean Architecture principles, in order to showcase its power, even though it wouldnt be needed in an app so small that has no intent on growing to a giant. Clean Architecture is most useful on applications that intend to last many many years and be really really big, it has the trade off of making the development process slower at first, to make it faster to make change to the code base later on. If your app is small and it wont be maintained for long, you may go without using it. If your app is intended to be a big and long project, you will need to use it, or some other architecture with a similar goal. For Android apps the standard is MVVM + Clean, and that is what I will showcase.


## Dependency Injection
### Koin
I chose to use Koin because it is the tool I have used the most, another possible choice would be Hilt. Koin is very easy to use and implement. The attention point is that if you mess up and do not inject a module correctly you will only discover it while running the application, and it will crash. That is one of the reasons it is a good idea to implement Koin Modules tests. If you use Hilt you won't have this problem, you will get an error during compilation time. But Hilt is more complex, I have less experience with it, and there's simply no good reason to use it in this project. 

## Network
### Retrofit
Retrofit is the standard library for making requests on Android, it is a HTTP client. Retrofit is a higher-level library that simplifies the process of making HTTP request by offering annotation-based API interfaces and built-in response parsing capabilities.
### OkHttp
OkHttp is another standard for Android, it is a HTTP client that is efficient by default. It is a lower-level library that provides greater flexibility and customization options for HTTP requests. We use it as client while building the retrofit service instance.
### Gson
Gson is a Java serialization and deserialization library. It converts objects into JSON and converts JSON into objects.

## UI
### Structure
I decided to use the single activity architecture, using Fragments for each screen, and Jetpack Navigation to navigate between them. It is a simple and powerful way to reuse code. Since I will be able to access the Characters screen from the Home screen and from the Favorites screen. Everything done in a very standard way, and using one of the most used Android libraries.
### ViewBinding
I'm going to use ViewBinding in this project so I have minimum boilerplate code, and a very easy way to access View declared on my XML files.

# How to run the project
### API Keys
You will need to configure your local.properties file with a valid pair of API Keys. It is easy to get one, just request it here: https://developer.marvel.com/

Then, on your local.properties file you need to add these lines, where abcde is your Public Api Key and xyz is your Private Key:
PUBLIC_API_KEY="abcde"
PRIVATE_API_KEY="xyz"

Afterwards just rebuild your project.

# Dependencies
##### Personal Library
- Common - Module inside project with some useful extensions and some unit tests for them.

##### Dependency Injection
- Koin - Very popular and efficient dependency injection manager.

##### Network
- Retrofit - Builds the service MarvelApi.
- Gson - Parser for and from Json to Kotlin Objects.
- OkHttp 3 - Builds the client used in Retrofit.

##### Data Persistence
- Room - Android Jetpack persistence library that provides an abstraction layer over SQLite. [Read more about it here.](https://developer.android.com/jetpack/androidx/releases/room)


##### Pagination
- Paging 3 - A library that helps you load and display pages of data from a larger dataset from local storage or over a network. I used with Room, so that all paginated data is persisted until refreshed. [Read more about it here.](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)


##### Image Loading
- Picasso - A library for loading images, in this case from the internet using URLs.

##### Testing
- JUnit
- Mockk

##### Basic Functionalities
- View Binding
- Navigation

# Problem Analysis
#### Specifications
- App must show a characters list
- When a character in the list is clicked it must navigate to a details screen
- The characters must be able to flagged as a favorite, either on the list or in the details screen
- Favorite characters must be persisted on the device in order to be accessed offline, and shown in a exclusive screen
- There must be an search bar to search characters by name.
- Use the characters endpoint from the Marvel API: [documentation](https://developer.marvel.com/docs).
#### Analysis
1. The most obvious solution is to use a paginated infinite scroll list. This way all characters will be displayed.
2. Must find a way to persist the favorites in the device, it must be able to notify me everytime the data set is changed, by a new favorite being added or another one being removed. It should not be cleared. I must be able to access and modify it from any fragment or activity.
3. I must have at least 3 screens, one for the main characters list, one exclusive list for favorites, and one for characters details.

# Solution
## Architecture
I chose to use MVVM coupled with Clean, the industry standard for Native Android development. MVVM, the recommended view architecture by Google, allow us to decouple business logic from the view, keeping it inside the view model and exposing observables to the view. One interesting point to make is that ViewModels are aware of the Views lifecycle, which make it a lot easier to handle it.

Clean Archtecture on the other hand allows us to have clear separation between data, domain, and presentation layers. Making it easier to change each of these layers. I used dependency injection and dependency inversion in order to keep the correct dependency graph, and in order to allow easier testing.

- [Clean Architecture](https://proandroiddev.com/clean-architecture-data-flow-dependency-rule-615ffdd79e29)
- [MVVM with Clean](https://medium.com/@ami0275/mvvm-clean-architecture-pattern-in-android-with-use-cases-eff7edc2ef76)
- [Google guide for app architecture](https://developer.android.com/topic/architecture)
## Modularization
I created a single module apart from the app module, it is called Common, it has reusable code, like string extensions, and can be used in any other Android project. It is currently local, so its code is in the project, but it could have its own git repository, just like it's done in many companies. I could have created modules for each feature, a module for network, but I didn't since this a very small project and it would add unecessary complexity. We as software engineers should add complexity when needed and not just for the sake of adding it.
## Gradle and Build Configuration
## Pagination
## Favorites
## Sharing Image
## Checking Internet Status

# Marvel App Decisions
## Creating App Project
- Chose to use Views instead of Compose, because its much more common in companies currently.
- Chose to use Kotlin DSL for the gradle configuration files, because its recommended by Google, and is the most recent one.

## Configuring App SDK Versions
1. Minimum SDK: the minimum Android API level on which your app can run.
2. Target SDK: is the SDK version that your app was created to run on. It is used to indicate awareness of specific behaviour changes introduced in newer Android versions. You make sure that your app behaves fine at this SDK level, considering its particular behaviour.
3. Compile SDK: determines which API level your app will be compiled with. Using the latest API allows us developers to leverage the latest features.

##### To keep in mind:
- The compileSDK can not be lower than the targetSDK.
- The targetSDK can be lower than the compileSDK.
- You should always use the latest version for the compile and target SDKs.
- To choose the minimum SDK you should think about and balance: compatibility issues, user base size. Newer versions will have less compatibility issues but fewer users.

##### What did I set?
- Chose to set Minimum SDK to API 27, Oreo, Android 8.1, because its the most recent API that will run on more than 90% of devices.
- Chose the Target SDK to be 34, since its the last one available.
- Chose to use compile SDK 34 also, so it would be the same as the target SDK.

## Patterns
### Orchestrator Pattern
I was looking for a pattern and a name for a class that would receive data from two use cases and have some business logic in it to decide how the data should be delivered to the ViewModel, I didn't want to use another use case, just due to the naming differentiation. After some research found this Orchestrator Pattern usually used in the backend, but I found it fit quite well in this case. Article: https://medium.com/gbtech/orchestration-pattern-3d8f5abc3be3

## UI

## Why Entity is sent to view model?
Entity to domain, removed old domain model, due to need of paging data source of getting data from database and sending it directly to view model. The model at the data base needs to be the one received at the view model paging source. It is a limitation of paging 3.

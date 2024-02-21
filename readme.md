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
- Use the charcters endpoint from the Marvel API: [documentation](https://developer.marvel.com/docs).
#### Analysis
1. The most obvious solution is to use a paginated infinite scroll list. This way all characters will be displayed.
2. Must find a way to persist the favorites in the device, it must be able to notify me everytime the data set is changed, by a new favorite being added or another one being removed. It should not be cleared. I must be able to access and modify it from any fragment or activity.
3. I must have at least 3 screens, one for the main characters list, one exclusive list for favorites, and one for characters details.

# Solution
### Architecture
### Gradle and Build Configuration
### Pagination
### Favorites
### Sharing Image
### Checking Internet Status

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

## Project Architecture
### MVVM
I will use MVVM, because its the recommended architecture by Google and it is used in most companies.

The main advantage of MVVM is its ease to preserve states, which is perfect for mobile where the OS can choose to kill the activity at anytime when it is on background, and also because rotating the screen kills the activity to rebuild it. MVVM uses the Observable design pattern, in which when an object changes its subscribers updte accordingly.

It makes use of the ViewModel which is lifecycle aware, meaning it responds to changes in Views lifecycle states, making it easier to preserve its state.

### Clean Architecture
I will also follow the Clean Architecture principles, in order to showcase its power, even though it wouldn't be needed in an app so small that has no intent on growing to a giant. Clean Architecture is most useful on applications that intend to last many many years and be really really big, it has the trade off of making the development process slower at first, to make it faster to make change to the code base later on. If your app is small and it wont be maintained for long, you may go without using it. If your app is intended to be a big and long project, you will need to use it, or some other architecture with a similar goal. For Android apps the standard is MVVM + Clean, and that is what I will showcase.

### Modularization
Why I did not create different modules for features? Because I have only 3 screens. I did create modules for code that don't have any buissiness logic, one for Commons, and one for Network //TODO

## Patterns
### Orchestrator Pattern
I was looking for a pattern and a name for a class that would receive data from two use cases and have some business logic in it to decide how the data should be delivered to the ViewModel, I didn't want to use another use case, just due to the naming differentiation. After some research found this Orchestrator Pattern usually used in the backend, but I found it fit quite well in this case. Article: https://medium.com/gbtech/orchestration-pattern-3d8f5abc3be3

## UI

## Why Entity is sent to view model?
Entity to domain, removed old domain model, due to need of paging data source of getting data from database and sending it directly to view model. The model at the data base needs to be the one received at the view model paging source. It is a limitation of paging 3.

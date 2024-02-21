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
I created a single module apart from the app module, it is called Common, it has reusable code, like string extensions, and can be used in any other Android project. It is currently local, so its code is in the project, but it could have its own git repository, just like it's done in many companies. I could have created modules for each feature, a module for network, but I didn't since this a very small project and it would add unnecessary complexity. We as software engineers should add complexity when needed and not just for the sake of adding it.
## Gradle and Build Configuration
I chose to use the API 27, Oreo, Android 8.1 as the Minimum SDK, since it's the most recent API that will run on more than 90% of devices. For the Target SDK and the Compile SDK I chose the API 34, since it's the latest one available. In general we should use the latest one for Target and Compile SDK. For the Minimum SDK we have to consider our user base, and choose the version that allows the bigger number of users to install and run our app.

Just as a reminder:
1. Minimum SDK: the minimum Android API level on which your app can run.
2. Target SDK: is the SDK version that your app was created to run on. It is used to indicate awareness of specific behaviour changes introduced in newer Android versions. You make sure that your app behaves fine at this SDK level, considering its particular behaviour.
3. Compile SDK: determines which API level your app will be compiled with. Using the latest API allows us developers to leverage the latest features.
- The compileSDK can not be lower than the targetSDK.
- The targetSDK can be lower than the compileSDK.
## Network - API and Interceptor
I used OkHttp to build my client and Retrofit to build the service. The base URL for this Marvel API is "https://gateway.marvel.com/". I had to create an account on their web site and was provided a pair of API keys in order to consume the API, a public and a private one. They are a requirement for this API. Then, on my http request, I had to pass 3 values as query parameters.
1. Public API Key - provided by Marvel
2. Time Stamp - generated in run time, using the System clock
3. Hash - generated from a string composed of the time stamp, the private key and the public key. To generate the hash the [MD5 message-digest algorithm](https://en.wikipedia.org/wiki/MD5) is used, which produces a 128-bit hash and can be used as a checksum.

Here we have a documentation about the [Marvel API authorization process](https://developer.marvel.com/documentation/authorization).

I stored both my public and private API keys on the local.properties files and exposed it in the code using build variables. This allowed me to include the local.properties files on the .gitignore file, resulting in my keys not being tracked by Git and being hidden from the internet. An attacker could still find these files if he had my apk, but this was not an issue for this project. The attacker job could be made harder by allowing obfuscation of the code, by setting isMinifyEnabled to true on the build.gradle from the app module.
## Pagination
I decided to used Paging 3 for implementing pagination. It's designed to be able to consume both from remote and local sources, so it made perfect sense to use it. I implemented a RemoteMediator which make the remote requests as needed and stores data on the device using Room. Inside the RemoteMediator I also checked if a specific item was a favorite or not, already flagging it and enabling my paginated list to be updated. I will talk more about this on the next section. A very simple DataSource was implemented, consuming data from Room. This meant that I had a single source of truth for my paginated data.

For the view layer I used three instances of adapters, one items adapters for the paginated data, and two adapters for displaying error and loading states at the top and the bottom of the list. The data layer sent to the presentation layer a Flow of PagingData. A series of Flows were used to control the screen state and request triggering.

There is still work that can be done here to improve the separation of responsibilities from the HomeActivity and the HomeViewModel, I followed the [Paging 3 Code Lab implementation](https://developer.android.com/codelabs/android-paging#0) and it made really hard to decouple it. I will work on it in the future, because it's a great challenge for handling Flows.
## Favorites
The design I chose allowed me to add and remove favorites from anywhere in the app, and anywhere else would be notified and updated accordingly. I created a exclusive database for favorite characters, allowing me to clean the database for the paginated data without losing my favorites. If I were to maintain only one database I would be forced to paginate the data locally since the RemoteKeys would become a mess when I cleared the database partially. Another solution would be to not clean the paginated data from my database unless the user requested it, which would also get rid of the favorites, but then my data would never be in sync with the remote data. So the best solution I found was to create two databases, never clean the favorites database and use it to set the isFavorite variable on each of the paginated items.

In the end I'm able to add and remove favorites from every screen, the UI updates accordingly and I am able to persist my favorites and access them offline. The favorites screen does not need paginated data, so it access the favorites database directly to populate its list.
## Sharing Image
To share an image from an URL we need to first download it, then generate a file, and then call an Android Intent. I was already using Picasso to download the image from the internet. So I just used the downloaded image that was set to the ImageView. I used the MediaStore to create a sharable file, and then created an Intent that allows the used to pick which app to use to open the file.
## Checking Internet Status
The internet connection status is monitored in the MainActivity using the MainViewModel to store a LiveData with the connection status which is observed to update the UI. OnResume I register a callback to the ConnectivityManager, this callback updates my ViewModel.
Then, onPaused I unregister the callback.
- [Monitor connectivity status](https://developer.android.com/training/monitoring-device-state/connectivity-status-type)
- [UnregisteringNetworkCallbacks](https://developer.android.com/develop/connectivity/network-ops/reading-network-state#additional-networks)
## Tests
I've written unit tests for every ViewModel I have, covering most of it's logic. I used JUnit and Mockk. It was need to add a MainDispatcherRule in order to the coroutines inside the ViewModels to run properly.

The Repository was mocked with Mockk, with relaxed set to true, which means the object created has no specific behaviour and its unstubbed methods (methods with no behaviour specified) won't throw when called.

I had to use coEvery and coVerify on some tests in order to stub and check for calls of some repository methods. These are the every and verify variations used for suspend functions, it is its "coroutines version".

For testing pagination I did not add anything for now, but in the future I will add UI tests, mocking the api call and the DB, in order to test the behaviour of the whole. And it would be nice to implement unit tests for the RemoteMediator.
## Patterns Used
### Adapter Pattern

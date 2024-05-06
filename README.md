<p>
  <a href="https://android-arsenal.com/api?level=26"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
</p>

# Architecture description

I've decided to implement this project using the Clean Architecture approach, primarily for its ability to separate concerns and facilitate testing of individual layers without significant impact on each other. However, to keep the app simple, I've opted to house all the architecture within the app module, dividing it into distinct packages.

Here's a brief overview of the layers involved in this architecture:

- **Domain package**: This is where our business logic resides. It includes the definition of repositories, models, and use cases (or interactors). Notably, this layer is self-contained and doesn't depend on any other layers.

- **Data package**: This layer is responsible for implementing the definitions set forth in the domain layer, such as repositories. It accommodate data source implementations, like local database caching or remote data retrieval via an API. This layer also handles the task of mapping data between domain and data models.

- **Presentation or UI package**: This package is built around the MVVM (Model-View-ViewModel) design pattern. It houses the ViewModels and composables

# Libraries

Exhaustive list of libraries helped me to achieve this project.

### Network
[Retrofit](https://square.github.io/retrofit/) was an obvious choice for the simplicity that it can offer to perform API calls. in addition 
the API call has no custom requirements in terms of caching, request prioritization or retries.

### Network response deserialization
 [Moshi](https://github.com/square/moshi) was introduced as a small, efficient and safer alternative to Gson. Using moshi codegen will create compile time adapters to model classes, which will remove the usage of reflection in runtime. In terms of size, Moshi+Okio combined is more light-weight than Gson.

### Dependency injection with Hilt
I used [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) library backed by Google, It was built on top of ```Dagger``` and inherit many benefits like runtime performance, compile time validation and faster build. Hilt provides a standardized way to manage dependency injection in particular for ***Android*** with scoping component, extension for viewModels and tooling support.

### Display Images
[Coil](https://coil-kt.github.io/coil/) provides a straightforward and easy-to-use API for loading images. It utilizes modern Android technologies like Kotlin coroutines, OkHttp, and Android's new bitmap and image loading APIs, resulting in faster image loading and less memory usage.

### Caching
Data persistence was done with the help of [Room](https://developer.android.com/jetpack/androidx/releases/room?gclsrc=ds&gclsrc=ds), which is JetPack compatible, easy to test, and check queries at compile time (expect ```RawQuery```). 

### Async with coroutines and flows
The asynchronous operations are performed with kotlin coroutines, which are light-weight thread that suspend instead of blocking a thread. Similarly to Rx Observables or Flowables, Kotlin Flows are used to handle streams of data, and rely on coroutines to operate.

### Paging3

- For a performance and memory usage issue, the contact list is paginated to limit the number of
  contacts kept in memory. The pagination is made with the Jetpack [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview). The
  paging library also supports the pagination of both remote and local data sources using [**RemoteMediator**](https://developer.android.com/topic/libraries/architecture/paging/v3-network-db).

- In the past, I've used version 2, but there are many differences compared to version 3. Nevertheless, it's still beneficial to use version 3 for quickly paging without worrying about local or remote issues and memory problems.
  
- The contact caching is made with Room, partly because it supports pagination with the Jetpack paging
  library, but also because it verifies at build-time the validity of the sqlite queries written in
  the DAOs (https://developer.android.com/training/data-storage/room).


# Testing

I wrote tests for RemoteMediator, mappers and use cases

Here are the libraries that I used for unit testing:

- Coroutines-test: necessary to unit-test functions that launch coroutines
- Mockk: can be used to generate mocks and spies for writing unit-tests
- Junit: for the test assertions

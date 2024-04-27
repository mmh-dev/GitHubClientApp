# Demo app, which works with GitHub API, written according to Clean Architecture on MVVM pattern.

Android app which allows to search for GitHub users, see and download selected user's repositories. Clicking on user 
repository allows to open it with browser. The data is obtained from GitHub API, downloaded repositories are stored in Downloads folder and remembered on local database with Room database

## Used libraries and features

### Android Jetpack

* [ViewBinding](https://developer.android.com/topic/libraries/view-binding) View binding is a
  feature that makes it easier for you to write code that interacts with views.

* [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) An interface
  that automatically responds to lifecycle events.

* [Kotlin flows](https://developer.android.com/kotlin/flow) In coroutines, a flow is a type that can
  emit multiple values sequentially, as opposed to suspend functions that return only a single
  value. For example, you can use a flow to receive live updates from a database.

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) Data related to
  the user interface that is not destroyed when the application is rotated. Easily schedule
  asynchronous tasks for optimal execution.

* [Room](https://developer.android.com/training/data-storage/room) The Room persistence library
  provides an abstraction layer over SQLite to allow fluent database access while harnessing the
  full power of SQLite

### DI

* [Koin](https://insert-koin.io/) Koin is a pragmatic and lightweight dependency injection framework for Kotlin developers.

### HTTP

* [Retrofit2](https://github.com/square/retrofit) Type-safe HTTP client for Android and Java.

### Coroutines

* [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines) A coroutine is a concurrency
  design pattern that you can use on Android to simplify code that executes asynchronously.

## Author
This project is maintained by:
* [Murod Khodzhaev](https://github.com/mmh-dev)

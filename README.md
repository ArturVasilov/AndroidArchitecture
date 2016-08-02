# AndroidArchitecture

Most of us already know principles of [Clean Architecture](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)
But to me these principles are more theoretical than practical. If we follow this principles directly we'll have about 6-9 classes 
for each screen (model, view, presenter, interactor, router, repository and so on). 

##### But why it's problem?

1. You can test only presenters and they are in fact very small piece of code in this architecture and don't cover all your sensitive logic
2. More classes in your app - harder to understand where to write your code. When we have only view and presenter - this is obvious.

These are the main reasons, why we should consider another ways. 

##### My suggestion is to use only these classes:

* **Model** is our POJO from server
* **View** is Activity or Fragment which interacts with the user and display all information
* **Presenter** is our most important object, which handles all business logic, handles lifecycle and manipulates view. Presenter uses repository
to get data from the server or database.
* **Repository** is main layer between network and presenter
  * Sends requests directly to the remote API
  * Handles response, saves it to persistent storage and delivers to the presenter
  * Handles server error and trying to fix them (such as session expired) or deliver to the presenter in more readable way

In this architecutre presenter covers all the business logic and it's the only thing we need to test. 

We should be able to mock repository using DI principles, e.g. static field and setter or [Dagger2](http://google.github.io/dagger/).

Many developers believe that in MVP Presenter should be absolutely free from any Android dependencies in order to test it.
I don't think so. Using something like [Mockito](http://mockito.org/) could solve all these dependencies without a great efforts from developer. 

So, our final architecture will look like this:

![Architecture](/images/architecture.png)

#### Easy start

In this repository you can find samples for the described model. If you're new to Android Architecture, 
I suggest you to check the __PopularMovies__ project, since it's very simple and easy to understand. 

#### Complicated large sample with described architecture

If you're looking for more complicated architecture take a look at __StackExchangeClient__. It's rather large sample, with many screens
and more - all presenters are tested with 100% coverage!

#### MVVM

MVVM for Android became more possible after release of [DataBinding](https://developer.android.com/topic/libraries/data-binding/index.html).
Google also suggest [architecture variant](https://github.com/googlesamples/android-architecture/tree/todo-databinding/) with DataBinding<
but again - it's very complicated and doesn't make sense. 

The only variant when MVVM is acceptable is this architecture:

![MVVM](/images/mvvm.png)

This architecture was implemented in __PopularMoviesDataBinding__ sample. You can take a look on it and decide for yourself - is it good or not :)
Personally I believe that MVP is much better. 

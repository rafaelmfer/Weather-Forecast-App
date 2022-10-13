# Weather Forecast App

Study application made to take advantage of the best programming practices using weather public api.
Shows a 3 day city's weather forecast (temperature in fahrenheit, wind mph, humidity and min/max
temperature). You can do city search for look your city's forecast

[APK](https://github.com/rafaelmfer/Weather-Forecast-App/blob/master/apk/app-debug.apk?raw=true)
|| [VIDEO](https://github.com/rafaelmfer/Weather-Forecast-App/blob/master/github_assets/videos/screen_recording_app.mp4?raw=true)

<table>
    <thead>
        <tr>
            <th>BASE</th>
            <th>Architecture</th>
            <th>IU</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>AppCompat</td>
            <td>ViewBinding</td>
            <td>Material Components</td>
        </tr>
        <tr>
            <td>Android KTX</td>
            <td>Lifecycles</td>
        </tr>
        <tr>
            <td>Android Arch</td>
            <td>LiveData</td>
        </tr>
        <tr>
            <td></td>
            <td>ViewModel</td>
        </tr>
    </tbody>
</table>


**Screens**
<table>  
    <th>Home</th>
    <tr>
        <td>
            <img src="https://github.com/rafaelmfer/Weather-Forecast-App/blob/master/github_assets/images/HomeWeatherForecastViewModelTest.png"/>
        </td>
    </tr>
</table>

## Base project

- **Dependency injection:**
  With Koin, a practical dependency injection library, the code will not be coupled and it'll still
  be easy to resolve automatically the dependencies on the runtime and mock them during the tests.

- **Coroutines:**
  With coroutines it is possible to perform asynchronous tasks without changing the code flow of the
  application. Simplifies code by abstracting all the complexity of using threads

- **Room:**
  Room Database is one of the existing libraries within the “Android JetPack” suite, it helps
  developers creating an abstraction of database layers (SQLite) to store information.

- **Kotlin KTS:**
  Using Kotlin KTS we can take advantage of the application configuration using the kotlin language
  in our gradle file. This makes our configuration even easier

## Tests

- **Unit Tests**:

<table>
    <th>HomeWeatherForecastViewModelTest</th>
    <tr>
        <td>
            <img src="https://github.com/rafaelmfer/Weather-Forecast-App/blob/master/github_assets/images/HomeWeatherForecastViewModelTest.png"/>
        </td>
    </tr>
</table>

## Quick start

1. Clone the repository with `git clone https://github.com/rafaelmfer/Weather-Forecast-App.git`
2. Create a free account on the [WeatherAPI](https://www.weatherapi.com/) to get your API KEY
3. Go to the app's `build.gradle` file and put your API KEY in the
   variable `def API_KEY = "INPUT_YOUR_API_KEY_HERE"`
4. Run the application and be happy

## CODE

- **IDE - Android Studio Dolphin 2021.3.1**

- **Gradle 7.3.0**

- **Kotlin 1.7.10**

- **AAC Android Architecture Components** *using guide Google JetPack*

- **MVVM Architecture** *for apply SOLID*

- **ViewBinding** *bind view*

- **Retrofit** *for make the communication to API*

- **Coroutines** *for asynchronous calls and operations*

- **ViewModel** *for interact view with business rules*

- **JUnit** *for unit tests*

## API

Weather's API Documentation: https://www.weatherapi.com/

## DESIGN

**Material Components**

https://github.com/material-components

- RecyclerView
- MaterialButton
- TextInputLayout
- TextInputEditText
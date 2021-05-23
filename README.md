# Sample Movie App
## _This App is built to Accomplish Following Requirements._

## Screen 1

- Form contains two text fields : one is email and other one is password
- Email must be valid email address
- Password size limitation between 8 - 15 characters
- Submit button to be enabled only in case of email & password are valid otherwise it will be disabled
- Once Submit button is active move to next screen without any Remote API Call.

## Screen 2

- Using Restful Web APIs load the list of popular Movies on this screen in a grid view with number of columns as 2.
- Each Item contains the name and image. Name to be taken from the “title” and image from “poster_path” from the response.

> Note: As the API key used for TMDB is secret and confidential, You need to update [secure.properties] with contents as below for this project to run successfully.
  
  ```sh
TMDB_API_KEY = <YourApiKey>
```

## Run Unit Tests

To run unit test cases, use following command.

```sh
gradlew test
```


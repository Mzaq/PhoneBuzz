# PhoneBuzz

PhoneBuzz is a twist on the classic [Fizz Buzz](https://en.wikipedia.org/wiki/Fizz_buzz) game. Now, you play Fizz Buzz through your phone! You can either call PhoneBuzz, or have it call you. Once connected with it, you enter a number and then PhoneBuzz will reply with the proper Fizz Buzz output. And if you want, you can see all your recent calls and replay any of them at any time!

PhoneBuzz was created for a [LendUp](https://www.lendup.com/) coding challenge.

### Technology stack

Java, Twilio, Spark, Apache Velocity, Gradle, HTML, CSS.

## Test it out
~~Try the app [here](#). It is currently running on a [DigitalOcean](https://www.digitalocean.com/) droplet~~

App not hosted anymore as of Jan 27, 2018.

Note: You will only be able to call PhoneBuzz directly. Because my Twilio account is only a trial account, only numbers that I have whitelisted will be able to get called.


## How to run on your machine
If you want to get the full functionality of PhoneBuzz, you will need to run it on your own server and connect your own Twilio account to it.

### Requirements

You will need the [Java Development Kit (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/index.html). The Gradle wrapper is included with the repository so Gradle installation is not necessary and furthermore, it will take care of downloading the other dependencies.

### Steps

1. Download Java (if you haven't yet)

2. Either clone or download the repository

3. Browse to 'src/main/java/util' and open up Config.java.

4. Change `ACCOUNT_SID` and `AUTH_TOKEN` to your Twilio account sid and auth token. Either set them as system variables or hard code them in. (Make sure not to upload these to the internet!) Change `TWILIO_NUMBER` to your Twilio number. Change `RECEIVE_CALL_URL` to "http://yourwebsite:4567/receive-call". Change `RECEIVE_REPLAY_URL` to "http://yourwebsite:4567/create-replay". Finally, in your Twlio Dashboard, make sure to point the voice URL to your application's endpoint.

5. Run `./gradlew run` in the root directory and connect to the application at `http://yourwebsite:4567`. Have fun!



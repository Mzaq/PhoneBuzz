package util;

public class Config {
    //Set as environment variables
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    //Twilio number
    public static final String TWILIO_NUMBER = "+16082344458";

    //URL of twiml for calls
    public static final String RECEIVE_CALL_URL = "http://138.197.26.93:4567/receive-call";
    public static final String RECEIVE_REPLAY_URL = "http://138.197.26.93:4567/create-call";
}

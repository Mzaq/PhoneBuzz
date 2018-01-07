/* Controls the flow of outgoing calls */

package controllers;

import util.Config;
import util.Helper;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

import spark.Route;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class SendCall {

    //Prepares necessary data to create outgoing call.
    public static Route handlePhone = ((request, response) -> {
        String inputNumber = request.queryParams("phoneNumber");
        String inputDelay = request.queryParams("delay");
        int delay = 0;

        //Check for proper phone number
        if (inputNumber.length() != 10 || !inputNumber.matches("[0-9]+") ){
            return inputNumber + " is not valid. Please go back and try again.";
        }

        //Convert delay string to integer. Will default to 0 with invalid input
        try {
            delay = Integer.parseInt(inputDelay);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (delay < 0){
            delay = 0;
        }

        //Create delay and create callable number
        TimeUnit.SECONDS.sleep(delay);
        String phoneNumber = "+1" + inputNumber;
        makeCall(phoneNumber, delay);

        return "Call to " + phoneNumber + " with " + delay + " second delay was successful.";
    });

    //Create outgoing replay call.
    public static Route handleReplay = ((request, response) -> {
        String oldSid = request.queryParams("sid");
        String to = Helper.getPhoneCall(oldSid).getPhoneNumber();
        String delay = Helper.getPhoneCall(oldSid).getDelay();
        String count = Helper.getPhoneCall(oldSid).getCount();

        TimeUnit.SECONDS.sleep(Integer.parseInt(delay));

        //Create call
        Call call = Call.creator(
                new PhoneNumber(to),
                new PhoneNumber(Config.TWILIO_NUMBER),
                new URI(Config.RECEIVE_REPLAY_URL))
                .create();

        //Map replay data to sid
        Helper.mapCall(count, delay, to, call.getSid());
        return "Replay call to " + to + " with " + delay + " second delay and counting up to " + count + " was successful.";
    });

    //Create outgoing call (non replay).
    private static void makeCall(String toNumber, int delay) throws URISyntaxException, IOException {
        Call call = Call.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(Config.TWILIO_NUMBER),
                new URI(Config.RECEIVE_CALL_URL))
                .create();

        //Map call info to sid
        Helper.mapCall(null, Integer.toString(delay), toNumber, call.getSid());
    }
}

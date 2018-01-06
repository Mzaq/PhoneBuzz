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

    public static Route handlePhone = ((request, response) -> {
        String inputNumber = request.queryParams("phoneNumber");
        String inputDelay = request.queryParams("delay");
        int delay = 0;

        if (inputNumber.length() != 10 || !inputNumber.matches("[0-9]+") ){
            return inputNumber + " is not valid. Please go back and try again.";
        }

        try {
            delay = Integer.parseInt(inputDelay);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (delay < 0){
            delay = 0;
        }

        TimeUnit.SECONDS.sleep(delay);
        String phoneNumber = "+1" + inputNumber;
        makeCall(phoneNumber, delay);

        return "Call to " + phoneNumber + " with " + delay + " second delay was successful.";
    });

    public static Route handleReplay = ((request, response) -> {
        String oldSid = request.queryParams("sid");
        System.out.println(oldSid);
        String to = Helper.getPhoneCall(oldSid).getPhoneNumber();
        String delay = Helper.getPhoneCall(oldSid).getDelay();
        String count = Helper.getPhoneCall(oldSid).getCount();

        TimeUnit.SECONDS.sleep(Integer.parseInt(delay));

        Call call = Call.creator(
                new PhoneNumber(to),
                new PhoneNumber(Config.TWILIO_NUMBER),
                new URI(Config.RECEIVE_REPLAY_URL))
                .create();

        System.out.println("Here: Route handleReplay");
        Helper.mapCall(count, delay, to, call.getSid());
        return "Replay call to " + to + " with " + delay + " second delay and counting up to " + count + " was successful.";
    });

    private static void makeCall(String toNumber, int delay) throws URISyntaxException, IOException {
        Call call = Call.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(Config.TWILIO_NUMBER),
                new URI(Config.RECEIVE_CALL_URL))
                .create();

        System.out.println("Here: after call creator");
        Helper.mapCall(null, Integer.toString(delay), toNumber, call.getSid());
    }
}

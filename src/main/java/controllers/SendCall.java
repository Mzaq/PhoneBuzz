package controllers;

import util.Config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

import spark.Route;

import java.net.URISyntaxException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class SendCall {

    public static Route handlePhone = ((request, response) -> {
        String inputNumber = request.queryParams("phoneNumber");
        String inputDelay = request.queryParams("delay");
        int delay = 0;

        if (inputNumber.length() != 10 || !inputNumber.matches("[0-9]+") ){
            return "Phone number not valid. Please go back and try again.";
        }

        try {
            delay = Integer.parseInt(inputDelay);
        } catch (NumberFormatException e) {
            delay = 0;
        }

        if (delay < 0){
            delay = 0;
        }

        TimeUnit.SECONDS.sleep(delay);
        String phoneNumber = "+1" + inputNumber;
        makeCall(phoneNumber);

        return "Success";
    });

    private static void makeCall(String toNumber) throws URISyntaxException {
        Twilio.init(Config.ACCOUNT_SID, Config.AUTH_TOKEN);

        Call call = Call.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(Config.TWILIO_NUMBER),
                new URI(Config.RECEIVE_CALL_URL))
                .create();

        System.out.println("SID: " + call.getSid());
    }
}

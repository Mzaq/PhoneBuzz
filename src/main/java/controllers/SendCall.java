package controllers;

import utility.Config;

import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

import spark.Route;

import java.net.URISyntaxException;
import java.net.URI;

public class SendCall {

    public static Route handlePhone = ((request, response) -> {
        String phoneNumber = "+1" + request.queryParams("phonenumber");
        makeCall(phoneNumber);

        return "test";
    });

    private static void makeCall(String toNumber) throws URISyntaxException{
        Twilio.init(Config.ACCOUNT_SID, Config.AUTH_TOKEN);

        Call call = Call.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(Config.TWILIO_NUMBER),
                new URI("response.xml"))
                .create();

        System.out.println("SID: " + call.getSid());
    }
}

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
        String phoneNumber = "1" + request.queryParams("phonenumber");
        makeCall(phoneNumber);
        //Say message = Helper.fizzBuzz(number);
        //VoiceResponse twiml = new VoiceResponse.Builder().say(message).build();
        //return twiml.toXml();
        return null;
    });

    private static void makeCall(String toNumber) throws URISyntaxException{
        Twilio.init(Config.ACCOUNT_SID, Config.AUTH_TOKEN);


    }
}

package controllers;

import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Say;
import com.twilio.twiml.*;
import spark.Route;
import utility.Helper;

public class ReceiveCall {

    public static Route call = (request, response) -> {
        System.out.println(request.attributes());
        System.out.println(request.params());

        Say sayMessage = new Say.Builder("Hello! Please enter a number for Fizz Buzz. Enter # when finished.").build();
        Gather input = new Gather.Builder().timeout(3).say(sayMessage).action("/handle-number").build();
        VoiceResponse twiml = new VoiceResponse.Builder().gather(input).build();

        return twiml.toXml();
        };

    public static Route fizzBuzz = ((request, response) -> {
        System.out.println("test: " + request.queryParams());

        String digit = request.queryParams("Digits");
        System.out.println(digit);

        int number = Integer.parseInt(digit);

        Say message = Helper.fizzBuzz(number);
        VoiceResponse twiml = new VoiceResponse.Builder().say(message).build();
        return twiml.toXml();
    });
}

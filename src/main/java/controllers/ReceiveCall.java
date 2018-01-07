/*Controls the flow of any incoming calls*/

package controllers;

import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Say;
import com.twilio.twiml.*;
import spark.Route;
import util.Config;
import util.Helper;

public class ReceiveCall {

    //Input prompt. Routes to /handle-number.
    public static Route call = (request, response) -> {
        Say sayMessage = new Say.Builder("Hello! Please enter a number for Fizz Buzz. Enter # when finished.").build();
        Gather input = new Gather.Builder().timeout(3).say(sayMessage).action("/handle-number").build();
        VoiceResponse twiml = new VoiceResponse.Builder().gather(input).build();

        return twiml.toXml();
        };

    //Process fizzbuzz input and create message twiml.
    public static Route fizzBuzz = (request, response) -> {
        String digit = request.queryParams("Digits");
        String toNumber = request.queryParams("To");
        String sid = request.queryParams("CallSid");
        VoiceResponse twiml;

        //Check for valid fuzz buzz input
        try {
            int number = Integer.parseInt(digit);
            Say message = Helper.fizzBuzz(number);
            twiml = new VoiceResponse.Builder().say(message).build();
            if (!toNumber.equals(Config.TWILIO_NUMBER)){
                Helper.addCallToLog(sid, digit);
            }
        } catch (NumberFormatException e) {
            twiml = new VoiceResponse.Builder().
                    say(new Say.Builder("Sorry that is not a valid input.").build()).
                    build();
            e.printStackTrace();
        }

        return twiml.toXml();
    };

    //Create replay message twiml.
    public static Route createReplay = ((request, response) -> {
        String sid = request.queryParams("CallSid");
        String digit = Helper.getPhoneCall(sid).getCount();
        int number = Integer.parseInt(digit);
        Say message = Helper.fizzBuzz(number);
        VoiceResponse twiml = new VoiceResponse.Builder().say(message).build();
        Helper.addCallToLog(sid, null);
        return twiml.toXml();
    });
}

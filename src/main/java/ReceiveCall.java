import static spark.Spark.post;

import com.twilio.twiml.*;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Say;
import spark.Route;

public class ReceiveCall {

    public static Route call = (request, response) -> {
        Say sayMessage = new Say.Builder("Hello! Please enter a number for Fizz Buzz. Enter # when finished.").build();
        Gather input = new Gather.Builder().timeout(3).say(sayMessage).build();
        VoiceResponse twiml = new VoiceResponse.Builder().gather(input).build();
        System.out.println(twiml.toXml());
        return twiml.toXml();
        };

    public static Route fizzBuzz = ((request, response) -> {

        VoiceResponse twiml = new VoiceResponse.Builder().build();
        return twiml.toXml();
    });
}

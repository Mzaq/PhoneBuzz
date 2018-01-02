import static spark.Spark.post;

import com.twilio.twiml.*;
import com.twilio.twiml.voice.Say;
import spark.Route;

public class ReceiveCall {

    public static Route call = (request, response) -> {
        Say sayMessage = new Say.Builder("Hello! Please enter a number for Fizz Buzz.").build();

        VoiceResponse twiml = new VoiceResponse.Builder().say(sayMessage).build();

        return twiml.toXml();
        };
}

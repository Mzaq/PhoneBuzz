import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Say;
import com.twilio.twiml.*;
import spark.Route;

public class ReceiveCall {

    public static Route call = (request, response) -> {
        System.out.println(request.attributes());
        System.out.println(request.params());

        Say sayMessage = new Say.Builder("Hello! Please enter a number for Fizz Buzz. Enter # when finished.").build();
        Gather input = new Gather.Builder().timeout(3).say(sayMessage).action("/handle-number").build();
        VoiceResponse twiml = new VoiceResponse.Builder().gather(input).build();

        System.out.println(response.body());
        response.type("application/xml");
        response.body(twiml.toXml());
        return response;
        //return twiml.toXml();
        };

    public static Route fizzBuzz = ((request, response) -> {
        System.out.println(request.attributes());
        System.out.println(request.params());
        System.out.println("test: " + request.queryParams());

        String digit = request.params("Digits");
        System.out.println(digit);

        int number = Integer.parseInt(digit);

        Say message = Helper.fizzBuzz(number);
        VoiceResponse twiml = new VoiceResponse.Builder().say(message).build();
        return twiml.toXml();
    });
}

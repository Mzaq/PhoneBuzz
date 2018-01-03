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

        Say message = fizzBuzz(number);
        VoiceResponse twiml = new VoiceResponse.Builder().say(message).build();
        return twiml.toXml();
    });

    public static Route handlePhone = ((request, response) -> {
        String phoneNumber = request.queryParams("phonenumber");
        System.out.println("1" + phoneNumber);

        int number = Integer.parseInt(phoneNumber);

        Say message = fizzBuzz(number);
        VoiceResponse twiml = new VoiceResponse.Builder().say(message).build();
        return twiml.toXml();
    });

    private static Say fizzBuzz(int number){
        String message = "";

        for (int i = 1; i <= number; i++){
            if (i % 3 == 0 && i % 5 == 0) {
                message += "Fizz Buzz";
            } else if (i % 3 == 0) {
                message += "Fizz";
            } else if (i % 5 == 0) {
                message += "Buzz";
            } else {
                message += Integer.toString(i);
            }
            message += "...";
        }

        Say fizzBuzzMessage = new Say.Builder(message).build();
        return fizzBuzzMessage;
    }
}

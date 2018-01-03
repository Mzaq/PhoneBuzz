import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import spark.Route;

public class SendCall {

    public static Route handlePhone = ((request, response) -> {
        String phoneNumber = request.queryParams("phonenumber");
        System.out.println("1" + phoneNumber);

        int number = Integer.parseInt(phoneNumber);

        Say message = Helper.fizzBuzz(number);
        VoiceResponse twiml = new VoiceResponse.Builder().say(message).build();
        return twiml.toXml();
    });
}

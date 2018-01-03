import com.twilio.twiml.voice.Say;

public class Helper {
    public static Say fizzBuzz(int number){
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

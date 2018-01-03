import com.twilio.twiml.voice.Gather;

import static spark.Spark.*;

public class App {
    public static void main (String[] args){
        post("/receive-call", ReceiveCall.call);
        post("/handle-number", ReceiveCall.fizzBuzz);
    }

}

package util;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.twiml.voice.Say;
import obj.BasicPhoneCall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
        message += "....Thank you for playing Phone Buzz!";
        Say fizzBuzzMessage = new Say.Builder(message).build();
        return fizzBuzzMessage;
    }

    public static ResourceSet<Call> retrieveCallLog(){
        Twilio.init(Config.ACCOUNT_SID, Config.AUTH_TOKEN);

        ResourceSet<Call> calls = Call.reader().read();

        for (Call call : calls) {
            System.out.println(call.toString());
        }

        return calls;
    }

    public static List<BasicPhoneCall> packageLog() throws FileNotFoundException {
        List<BasicPhoneCall> phoneCalls = new ArrayList<>();
        Scanner fileScanner = new Scanner(new File("src/main/resources/call_log.dat"));
        while (fileScanner.hasNextLine()){
            String nextCall = fileScanner.nextLine();
            List<String> callInfo = Arrays.asList(nextCall.split(","));
            BasicPhoneCall phoneCall = new BasicPhoneCall(callInfo.get(0), callInfo.get(1), callInfo.get(2), callInfo.get(3));
            phoneCalls.add(phoneCall);
        }
        Collections.reverse(phoneCalls);
        return phoneCalls;
    }
}

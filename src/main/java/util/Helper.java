package util;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.twiml.voice.Say;
import obj.BasicPhoneCall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    public static void updateLog(Map<String, Object> model) throws FileNotFoundException {
        int numLines = 1;
        Scanner scanner = new Scanner(new File("src/main/resources/call_log.dat"));
        while (scanner.hasNextLine() && numLines <= 5){
            String nextCall = scanner.nextLine();
            List<String> callInfo = Arrays.asList(nextCall.split(","));
            BasicPhoneCall phoneCall = new BasicPhoneCall(callInfo.get(0), callInfo.get(1), callInfo.get(2), callInfo.get(3));
            model.put("call" + numLines, phoneCall);
            numLines++;
        }
    }
}

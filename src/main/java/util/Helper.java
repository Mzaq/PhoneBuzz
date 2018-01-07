/* Contains useful methods used throughout the program */

package util;

import com.twilio.security.RequestValidator;
import com.twilio.twiml.voice.Say;
import obj.BasicPhoneCall;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Helper {
    private static Map<String, BasicPhoneCall> loggedCalls = new HashMap<>();
    private static final String logFilePath = "src/main/resources/call_log.dat";

    //Fizz buzz message creator.
    public static Say fizzBuzz(int number){
        StringBuilder message = new StringBuilder();

        for (int i = 1; i <= number; i++){
            if (i % 3 == 0 && i % 5 == 0) {
                message.append("Fizz Buzz");
            } else if (i % 3 == 0) {
                message.append("Fizz");
            } else if (i % 5 == 0) {
                message.append("Buzz");
            } else {
                message.append(Integer.toString(i));
            }
            message.append("...");
        }
        message.append("....Thank you for playing Phone Buzz!");
        return new Say.Builder(message.toString()).build();
    }

    //Parses phone calls and creates BasicPhoneCall's to store data.
    public static List<BasicPhoneCall> parseLog() throws FileNotFoundException {
        List<BasicPhoneCall> phoneCalls = new ArrayList<>();
        Scanner fileScanner = new Scanner(new File(logFilePath));
        while (fileScanner.hasNextLine()){
            String nextCall = fileScanner.nextLine();
            if (nextCall.equals("")){
                continue;
            }
            List<String> callInfo = Arrays.asList(nextCall.split(","));
            BasicPhoneCall phoneCall = new BasicPhoneCall(
                    callInfo.get(0),
                    callInfo.get(1),
                    callInfo.get(2),
                    callInfo.get(3),
                    callInfo.get(4));
            phoneCalls.add(phoneCall);
            mapCall(phoneCall);
        }
        Collections.reverse(phoneCalls);
        return phoneCalls;
    }

    //Map new BasicPhoneCall to sid.
    public static void mapCall(String count, String delay, String phoneNumber, String sid){
        BasicPhoneCall phoneCall = new BasicPhoneCall(
                getFormattedCurrentDateAndTime(),
                count,
                delay,
                phoneNumber,
                sid);

        loggedCalls.put(sid, phoneCall);
    }

    //Map existing BasicPhoneCall to sid.
    public static void mapCall(BasicPhoneCall phoneCall){
        loggedCalls.put(phoneCall.getSid(), phoneCall);
    }

    public static void addCallToLog(String sid, String digit) throws IOException {
        BasicPhoneCall phoneCall = loggedCalls.get(sid);

        if (digit == null){
            digit = phoneCall.getCount();
        }

        StringBuilder newLine = new StringBuilder();
        newLine.append(phoneCall.getTime()).append(",").
                append(digit).append(",").
                append(phoneCall.getDelay()).append(",").
                append(phoneCall.getPhoneNumber()).append(",").
                append(sid).append("\n");

        Writer output = new BufferedWriter(new FileWriter(logFilePath, true));
        output.append(newLine);
        output.close();
    }

    //Creates readable current date and time.
    private static String getFormattedCurrentDateAndTime(){
        StringBuilder dateAndTime = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();

        String hour = Integer.toString(now.getHour());
        String minute = Integer.toString(now.getMinute());

        if (hour.length() == 1){
            hour = "0" + hour;
        }
        if (minute.length() == 1){
            minute = "0" + minute;
        }

        //Creates "XX/XX/XX @ XX:XX" format (24 hours)
        dateAndTime.append(now.getMonthValue()).append("/").
                append(now.getDayOfMonth()).append("/").
                append(now.getYear()).append(" @ ").
                append(hour).append(":").
                append(minute);

        return dateAndTime.toString();
    }

    //Get BasicPhoneCall by sid.
    public static BasicPhoneCall getPhoneCall(String sid){
        return loggedCalls.get(sid);
    }

    //NOTE: is not working correctly
    public static boolean validateRequest(String sid, String caller, String digits, String from, String to) {
        // Initialize the validator
        RequestValidator validator = new RequestValidator(Config.AUTH_TOKEN);
        String url = Config.RECEIVE_CALL_URL;

        Map<String, String> params = new HashMap<>();
        params.put("CallSid", sid);
        params.put("Caller", caller);
        params.put("Digits", digits);
        params.put("From", from);
        params.put("To", to);

        // The X-Twilio-Signature header attached to the request
        String twilioSignature = Config.RECEIVE_CALL_URL +
                "CallSid" + sid +
                "Caller" + caller +
                "Digits" + digits +
                "From" + from +
                "To" + to;

        return validator.validate(url, params, twilioSignature);
    }
}

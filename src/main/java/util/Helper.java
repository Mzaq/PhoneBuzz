package util;

import com.twilio.twiml.voice.Say;
import obj.BasicPhoneCall;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Helper {
    private static Map<String, BasicPhoneCall> loggedCalls = new HashMap<>();
    private static final String logFilePath = "src/main/resources/call_log.dat";

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

    public static void mapCall(String count, String delay, String phoneNumber, String sid){
        BasicPhoneCall phoneCall = new BasicPhoneCall(
                getFormattedCurrentDateAndTime(),
                count,
                delay,
                phoneNumber,
                sid);

        loggedCalls.put(sid, phoneCall);
    }

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

    private static String getFormattedCurrentDateAndTime(){
        StringBuilder dateAndTime = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        dateAndTime.append(now.getMonthValue()).append("/").
                append(now.getDayOfMonth()).append("/").
                append(now.getYear()).append(" @ ").
                append(now.getHour()).append(":").
                append(now.getMinute());

        return dateAndTime.toString();
    }

    public static BasicPhoneCall getPhoneCall(String sid){
        return loggedCalls.get(sid);
    }
}

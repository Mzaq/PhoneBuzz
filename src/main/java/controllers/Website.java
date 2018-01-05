package controllers;

import spark.ModelAndView;
import spark.TemplateViewRoute;
import util.Config;
import util.Helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Website {
    //public static TemplateViewRoute init = (request, response) -> {new ModelAndView(model, "templates/index.vtl");

    public static TemplateViewRoute init = ((request, response) -> {
        Map<String, String> model = new HashMap<>();
        int numLines = 0;
        model.put("twilioNumber", Config.TWILIO_NUMBER);
        Scanner scanner = new Scanner("../resources/call_log.dat");
        while (scanner.hasNextLine() && numLines < 5){
            String nextLine = scanner.nextLine();
            //model.put("call" + numLines, nextLine);
            System.out.println(nextLine);
        }
        return new ModelAndView(model, "templates/index.vtl");
    });
}

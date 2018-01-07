/* Responsible for mapping variables to Velocity template */

package controllers;

import com.twilio.Twilio;
import spark.ModelAndView;
import spark.TemplateViewRoute;
import util.Config;
import util.Helper;

import java.util.HashMap;
import java.util.Map;

public class Website {

    //Map model to view and map relevant data to display on webpage.
    public static TemplateViewRoute init = ((request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("twilioNumber", Config.TWILIO_NUMBER);
        model.put("phoneCalls", Helper.parseLog());
        Twilio.init(Config.ACCOUNT_SID, Config.AUTH_TOKEN);

        return new ModelAndView(model, "templates/index.vtl");
    });
}

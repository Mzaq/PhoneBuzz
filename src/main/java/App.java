import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;
import spark.template.velocity.VelocityTemplateEngine;

import controllers.*;

public class App {
    public static void main (String[] args){
        //Resources location
        staticFileLocation("/public");

        //Website template
        get("/", Website.init, new VelocityTemplateEngine());

        //Call to web application
        post("/receive-call", ReceiveCall.call);
        post("/handle-number", ReceiveCall.fizzBuzz);
        //get("/handle-number", ReceiveCall.fizzBuzz);

        //Call from web application
        get("/handle-phone", SendCall.handlePhone);
    }
}

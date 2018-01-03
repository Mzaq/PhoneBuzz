import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import spark.template.velocity.VelocityTemplateEngine;

public class App {
    public static void main (String[] args){
        staticFileLocation("/public");

        get("/", Website.init, new VelocityTemplateEngine());

        post("/receive-call", ReceiveCall.call);
        post("/handle-number", ReceiveCall.fizzBuzz);
        get("/handle-number", ReceiveCall.fizzBuzz);
        get("/handle-phone", ReceiveCall.handlePhone);
    }
}

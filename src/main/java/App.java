/* PhoneBuzz by Harish Veeramani
Created for a LendUp coding challenge.

Initializes all HTTP GET and POST routes.
 */

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
        get("/receive-call", ReceiveCall.call);

        //Handle fizz buzz number
        post("/handle-number", ReceiveCall.fizzBuzz);

        //Call from web application
        get("/handle-phone", SendCall.handlePhone);

        //Replay call
        get("/handle-replay", SendCall.handleReplay);
        post("/create-replay", ReceiveCall.createReplay);
    }
}

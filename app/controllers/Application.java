package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.data.validation.Constraints.*;

import views.html.*;

import pt.fcup.bioinformatics.sequencealignment.AlignmentResult;

public class Application extends Controller {
    
    /**
     * Describes the hello form.
     */
    public static class AlignmentData {
        @Required public String sequenceA;
        @Required public String sequenceB;
        
        public String alignmentType;
        public String matrixCost;
    } 
    
    // -- Actions
  
    /**
     * Home page
     */
    public static Result index() {
        return ok(
            index.render(form(AlignmentData.class))
        );
    }
  
    /**
     * Handles the form submission.
     */
    public static Result align() {
        Form<AlignmentData> form = form(AlignmentData.class).bindFromRequest();
        if(form.hasErrors()) {

            System.out.println("error");

            return badRequest(index.render(form));


        } else {
            System.out.println("sucesso!!");

            AlignmentData data = form.get();

            AlignmentResult result = new services.AlignmentService().apply(data);
            String output = result.getAlignment().replace("\n","</br>");

            return ok(
                hello.render(   data.sequenceA, 
                                data.sequenceB, 
                                data.alignmentType, 
                                data.matrixCost,
                                result.getScore(),
                                output)
            );
        }
    }
  
}

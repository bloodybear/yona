package controllers;

import controllers.annotation.AnonymousCheck;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.devfarm.*;

@AnonymousCheck
public class DfMemberApp extends Controller {

    public static Result members() {
        return ok(members.render(DevFarm.ORGANIZATION_NAME));
    }
}

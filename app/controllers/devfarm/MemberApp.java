package controllers.devfarm;

import controllers.annotation.AnonymousCheck;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.devfarm.member.*;

@AnonymousCheck(requiresLogin = true, displaysFlashMessage = true)
public class MemberApp extends Controller {

    public static Result members() {
        return ok(memberList.render());
    }
}

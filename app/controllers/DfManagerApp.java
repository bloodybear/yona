package controllers;

import actions.DevFarmManagerAuthAction;
import com.avaje.ebean.Page;
import controllers.annotation.AnonymousCheck;
import models.DfMember;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.devfarm.*;

@With(DevFarmManagerAuthAction.class)
@AnonymousCheck(requiresLogin = true)
public class DfManagerApp extends Controller {

    public static Result members(int pageNum, String query) {
        Page<DfMember> dfMembers = DfMember.findDfMembers(pageNum -1, query);
        return ok(members.render("devfarm.mng.sidebar.member", dfMembers, query));
    }

    public static Result groups() {
        return ok(groups.render("devfarm.mng.sidebar.group"));
    }
}
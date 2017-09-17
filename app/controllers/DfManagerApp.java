package controllers;

import actions.DevFarmManagerAuthAction;
import com.avaje.ebean.Page;
import controllers.annotation.AnonymousCheck;
import models.DfGroup;
import models.DfMember;
import models.enumeration.DfGroupState;
import models.enumeration.UserState;
import org.apache.commons.lang3.StringUtils;
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

    public static Result groups(int pageNum, String query) {
        String state = StringUtils.defaultIfBlank(request().getQueryString("state"), DfGroupState.READY.name());
        DfGroupState groupState = DfGroupState.valueOf(state);
        Page<DfGroup> dfGroups = DfGroup.findDfGroups(pageNum -1, groupState, query);
        return ok(groups.render("devfarm.mng.sidebar.group", dfGroups, groupState, query));
    }
}
package controllers;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import controllers.annotation.AnonymousCheck;
import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.board.list;
import views.html.devfarm.*;

import java.util.List;

@AnonymousCheck
public class DevFarm extends Controller {

    public static String ORGANIZATION_NAME = play.Configuration.root().getString("application.devfarm.organization.name", "DevFarm");
    public static String NOTICE_PROJECT_NAME = play.Configuration.root().getString("application.devfarm.project.notice.name");
    public static String TECH_PROJECT_NAME = play.Configuration.root().getString("application.devfarm.project.tech.name");
    public static String BBS_PROJECT_NAME = play.Configuration.root().getString("application.devfarm.project.bbs.name");
    public static String QNA_PROJECT_NAME = play.Configuration.root().getString("application.devfarm.project.qna.name");
    public static final int DAYS_AGO = 7;
    public static final int ITEMS_LATEST = 10;

    public static Organization getOrganization() {
        return Organization.findByName(ORGANIZATION_NAME);
    }

    public static boolean isAdmin(User user) {
        return user.isAdminOf(getOrganization());
    }

    public static boolean isMember(User user) {
        return user.isMemberOf(getOrganization());
    }

    public static Result latests() {
        return ok(latests.render());
    }
}

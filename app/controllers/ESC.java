package controllers;

import controllers.annotation.AnonymousCheck;
import models.Organization;
import models.User;
import play.mvc.Controller;

@AnonymousCheck(requiresLogin = true)
public class ESC extends Controller {

    public static String ORGANIZATION_NAME = play.Configuration.root().getString("esc.organization.name", "ESC");
    public static Boolean WELCOME_SHOW = play.Configuration.root().getBoolean("esc.welcome.show", false);
    public static Boolean WELCOME_IN_PERIOD = play.Configuration.root().getBoolean("esc.welcome.inPeriod", false);

    public static Organization getOrganization() {
        return Organization.findByName(ORGANIZATION_NAME);
    }

    public static boolean isAdmin(User user) {
        return user.isAdminOf(getOrganization());
    }

    public static boolean isMember(User user) {
        return user.isMemberOf(getOrganization());
    }
}

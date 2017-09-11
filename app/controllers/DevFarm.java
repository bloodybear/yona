package controllers;

import models.Organization;
import models.User;
import play.mvc.Controller;

public class DevFarm extends Controller {

    public static String ORGANIZATION_NAME = play.Configuration.root().getString("application.devfarm.organization.name", "DevFarm");

    public static Organization getOrganization() {
        return Organization.findByName(ORGANIZATION_NAME);
    }

    public static boolean isManager(User user) {
        return user.isAdminOf(getOrganization());
    }
}

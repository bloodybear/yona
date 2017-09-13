package controllers;

import models.Organization;
import models.User;
import play.mvc.Controller;

public class DevFarm extends Controller {

    public static String ORGANIZATION_NAME = play.Configuration.root().getString("application.devfarm.organization.name", "DevFarm");
    public static String NOTICE_PROJECT_NAME = play.Configuration.root().getString("application.devfarm.project.notice.name");
    public static String TECH_PROJECT_NAME = play.Configuration.root().getString("application.devfarm.project.tech.name");
    public static String BBS_PROJECT_NAME = play.Configuration.root().getString("application.devfarm.project.bbs.name");
    public static String QNA_PROJECT_NAME = play.Configuration.root().getString("application.devfarm.project.qna.name");
    public static final int DAYS_AGO = 7;

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

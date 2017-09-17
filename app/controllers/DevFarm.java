package controllers;

import com.avaje.ebean.ExpressionList;
import controllers.annotation.AnonymousCheck;
import models.Organization;
import models.Posting;
import models.Project;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.devfarm.latests;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@AnonymousCheck(requiresLogin = true)
public class DevFarm extends Controller {

    public static String ORGANIZATION_NAME = play.Configuration.root().getString("application.devfarm.organization.name", "DevFarm");
    public static String NOTICE_PROJECT_NAME = play.Configuration.root().getString("application.devfarm.project.notice.name");
    public static String TECH_PROJECT_NAME = play.Configuration.root().getString("application.devfarm.project.tech.name");
    public static String BBS_PROJECT_NAME = play.Configuration.root().getString("application.devfarm.project.bbs.name");
    public static String QNA_PROJECT_NAME = play.Configuration.root().getString("application.devfarm.project.qna.name");
    public static final int DAYS_AGO = 3;
    public static final int NOTICES_LATEST = 5;
    public static final int POSTS_LATEST = 15;

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

    private static List<String> getVisibleProjectIds(@Nonnull Organization organization, @Nonnull List<Project> excludeProjects) {
        List<Project> projects = organization.getVisibleProjects(UserApp.currentUser());
        List<String> projectsIds = new ArrayList<String>();
        for (Project project : projects) {
            if (excludeProjects.contains(project)) continue;
            projectsIds.add(project.id.toString());
        }
        return projectsIds;
    }

    public static List<Posting> getLatestPosts() {
        Organization org = getOrganization();
        if (org == null) return new ArrayList<>();

        List<Project> excludeProjects = new ArrayList<>();
        Project noticeProject = Project.findByOwnerAndProjectName(ORGANIZATION_NAME, NOTICE_PROJECT_NAME);
        if (noticeProject != null) {
            excludeProjects.add(noticeProject);
        }

        ExpressionList<Posting> el = Posting.finder.where();
        el.in("project.id", getVisibleProjectIds(getOrganization(), excludeProjects));
        el.order().desc("createdDate");
        return el.findPagingList(POSTS_LATEST).getPage(0).getList();
    }
}

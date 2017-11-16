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

    public static String ORGANIZATION_NAME = play.Configuration.root().getString("devfarm.organization.name", "DevFarm");
    public static String REQUEST_PROJECT_NAME = play.Configuration.root().getString("devfarm.project.request.name");
    public static String NOTICE_PROJECT_NAME = play.Configuration.root().getString("devfarm.project.notice.name");
    public static String TECH_PROJECT_NAME = play.Configuration.root().getString("devfarm.project.tech.name");
    public static String BBS_PROJECT_NAME = play.Configuration.root().getString("devfarm.project.bbs.name");
    public static String QNA_PROJECT_NAME = play.Configuration.root().getString("devfarm.project.qna.name");
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

    private static List<String> getBoardProjectIds() {
        Project techProject = Project.findByOwnerAndProjectName(ORGANIZATION_NAME, TECH_PROJECT_NAME);
        Project bbsProject = Project.findByOwnerAndProjectName(ORGANIZATION_NAME, BBS_PROJECT_NAME);
        Project qnaProject = Project.findByOwnerAndProjectName(ORGANIZATION_NAME, QNA_PROJECT_NAME);
        List<String> projectsIds = new ArrayList<String>();
        if (techProject != null) projectsIds.add(techProject.id.toString());
        if (bbsProject != null) projectsIds.add(bbsProject.id.toString());
        if (qnaProject != null) projectsIds.add(qnaProject.id.toString());
        return projectsIds;
    }

    public static List<Posting> getLatestPosts() {
        Organization org = getOrganization();
        if (org == null) return new ArrayList<>();

        ExpressionList<Posting> el = Posting.finder.where();
        el.in("project.id", getBoardProjectIds());
        el.order().desc("createdDate");
        return el.findPagingList(POSTS_LATEST).getPage(0).getList();
    }
}

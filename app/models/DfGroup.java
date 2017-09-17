/**
 * Yona, 21st Century Project Hosting SW
 * <p>
 * Copyright Yona & Yobi Authors & NAVER Corp.
 * https://yona.io
 **/
package models;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import controllers.DevFarm;
import models.enumeration.DfGroupState;
import models.enumeration.UserState;
import org.apache.commons.lang3.StringUtils;
import play.data.format.Formats;
import play.db.ebean.Model;
import utils.JodaDateUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DfGroup extends Model {
    private static final long serialVersionUID = 1L;

    public static final Finder<Long, DfGroup> find = new Finder<>(Long.class, DfGroup.class);

    public static final int COUNT_PER_PAGE = 30;

    @Id
    public Long id;

    @ManyToOne
    public DfGroup currentGroup;

    @ManyToOne
    public Project project;

    @Enumerated(EnumType.STRING)
    public DfGroupState stateCode;

    public String name;

    @Formats.DateTime(pattern = "yyyy-MM-dd")
    public Date startDate;

    @Formats.DateTime(pattern = "yyyy-MM-dd")
    public Date endDate;

    @Formats.DateTime(pattern = "yyyy-MM-dd")
    public Date createDatetime;

    public static void addGroup(Project project) {
        if (project.organization == null || !project.organization.equals(DevFarm.getOrganization())) {
            return;
        }
        if (DevFarm.isReservedProject(project.name)) {
            return;
        }

        DfGroup group = new DfGroup();
        group.project = project;
        group.stateCode = DfGroupState.READY;
        group.name = project.name;
        group.startDate = JodaDateUtil.now();
        group.endDate = JodaDateUtil.now();
        group.createDatetime = JodaDateUtil.now();
        group.save();
    }

    public static Page<DfGroup> findDfGroups(int pageNum, DfGroupState state, String query) {
        ExpressionList<DfGroup> el = find.where();
        el.isNull("currentGroup").eq("stateCode", state);

        if (StringUtils.isNotBlank(query)) {
            el = el.disjunction();
            el = el.icontains("name", query);
            el.endJunction();
        }

        return el.order().desc("createDatetime").findPagingList(COUNT_PER_PAGE).getPage(pageNum);
    }
}

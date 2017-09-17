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
import models.enumeration.UserState;
import org.apache.commons.lang3.StringUtils;
import play.data.format.Formats;
import play.db.ebean.Model;
import utils.JodaDateUtil;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class DfMember extends Model {
    private static final long serialVersionUID = 1L;

    public static final Finder<Long, DfMember> find = new Finder<>(Long.class, DfMember.class);

    public static final int COUNT_PER_PAGE = 30;

    @Id
    public Long id;

    @ManyToOne
    public User user;

    @ManyToOne
    public DfMember currentMember;

    public String teamName;

    @Formats.DateTime(pattern = "yyyy-MM-dd")
    public Date createDatetime;

    public DfMember() {
    }

    public static void addMember(OrganizationUser organizationUser) {
        if (!organizationUser.organization.equals(DevFarm.getOrganization())) {
            return;
        }

        DfMember member = new DfMember();
        member.user = organizationUser.user;
        member.createDatetime = JodaDateUtil.now();
        member.save();
    }

    public static Page<DfMember> findDfMembers(int pageNum, String query) {
        ExpressionList<DfMember> el = find.where();
        el.isNull("currentMember");

        if (StringUtils.isNotBlank(query)) {
            el = el.disjunction();
            el = el.icontains("user.loginId", query)
                    .icontains("user.name", query)
                    .icontains("user.email", query)
                    .icontains("teamName", query);
            el.endJunction();
        }

        return el.order().desc("createDatetime").findPagingList(COUNT_PER_PAGE).getPage(pageNum);
    }
}

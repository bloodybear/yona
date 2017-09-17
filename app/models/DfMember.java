/**
 * Yona, 21st Century Project Hosting SW
 * <p>
 * Copyright Yona & Yobi Authors & NAVER Corp.
 * https://yona.io
 **/
package models;

import controllers.DevFarm;
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

    @Id
    public Long id;

    @ManyToOne
    public User user;

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
}

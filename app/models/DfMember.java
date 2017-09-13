/**
 * Yona, 21st Century Project Hosting SW
 * <p>
 * Copyright Yona & Yobi Authors & NAVER Corp.
 * https://yona.io
 **/
package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import com.avaje.ebean.RawSqlBuilder;
import controllers.DevFarm;
import controllers.UserApp;
import models.enumeration.ResourceType;
import models.enumeration.RoleType;
import models.enumeration.UserState;
import models.resource.GlobalResource;
import models.resource.Resource;
import models.resource.ResourceConvertible;
import models.support.UserComparator;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.ValidateWith;
import play.db.ebean.Model;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import utils.CacheStore;
import utils.GravatarUtil;
import utils.JodaDateUtil;
import utils.ReservedWordsValidator;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static utils.HtmlUtil.defaultSanitize;

@Entity
public class DfMember extends Model {
    private static final long serialVersionUID = 1L;

    public static final Finder<Long, DfMember> find = new Finder<>(Long.class, DfMember.class);

    @Id
    public Long memberSn;

    @ManyToOne
    public User user;

    public String teamName;

    @Formats.DateTime(pattern = "yyyy-MM-dd")
    public Date createdDate;

    public DfMember() {
    }

    public static void addMember(OrganizationUser organizationUser) {
        if (!organizationUser.organization.equals(DevFarm.getOrganization())) {
            return;
        }

        DfMember member = new DfMember();
        member.user = organizationUser.user;
        member.save();

    }
}

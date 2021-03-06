package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.contact.mail.MailAddressValidator;
import com.qinyuan.lib.database.hibernate.HibernateDeleter;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.security.SimpleUserDao;
import com.qinyuan.lib.mvc.security.UserRole;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Dao of User
 * Created by qinyuan on 15-6-29.
 */
public class UserDao extends SimpleUserDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserDao.class);
    public final static int SERIAL_KEY_LENGTH = 100;

    @Deprecated
    @Override
    public void setIgnoreCase(boolean ignoreCase) {
        throw new RuntimeException("not support method");
    }

    private Integer add(String username, String password, String role, String email, String tel,
                        Integer spreadUserId, String spreadWay, String qqOpenId) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setEmail(email);
        user.setTel(tel);
        user.setSpreadUserId(spreadUserId);
        user.setSpreadWay(spreadWay);
        user.setQqOpenId(qqOpenId);

        // set default value
        user.setActive(true);
        user.setSerialKey(RandomStringUtils.randomAlphanumeric(SERIAL_KEY_LENGTH));
        user.setReceiveMail(false);

        return HibernateUtils.save(user);
    }

    public boolean hasUsername(String username) {
        return super.getInstanceByName(username) != null;
    }

    public boolean hasEmail(String email) {
        return getInstanceByEmail(email) != null;
    }

    public boolean hasTel(String tel) {
        return getInstanceByTel(tel) != null;
    }

    public boolean hasQqOpenId(String qqOpenId) {
        return StringUtils.isNotBlank(qqOpenId) &&
                new HibernateListBuilder().addEqualFilter("qqOpenId", qqOpenId).count(User.class) > 0;
    }

    /**
     * Query user by username, email or tel
     *
     * @param usernameFromLoginForm username from login from, including username, email or tel
     * @return user whose username, email or tel is usernameFromLoginForm
     */
    @Override
    public User getInstanceByName(String usernameFromLoginForm) {
        if (StringUtils.isBlank(usernameFromLoginForm)) {
            return null;
        }

        User user = (User) super.getInstanceByName(usernameFromLoginForm);
        if (user != null) {
            return user;
        }

        user = getInstanceByEmail(usernameFromLoginForm);
        if (user != null) {
            return user;
        }

        return getInstanceByTel(usernameFromLoginForm);
    }

    public User getInstanceByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return null;
        }

        return new HibernateListBuilder().addEqualFilterIgnoreCase("email", email).getFirstItem(User.class);
    }

    public User getInstanceByTel(String tel) {
        if (StringUtils.isBlank(tel)) {
            return null;
        }
        return new HibernateListBuilder().addEqualFilter("tel", tel).getFirstItem(User.class);
    }

    public User getInstanceByQqOpenId(String qqOpenId) {
        if (StringUtils.isBlank(qqOpenId)) {
            return null;
        }
        return new HibernateListBuilder().addEqualFilter("qqOpenId", qqOpenId).getFirstItem(User.class);
    }

    public Integer addAdmin(String username, String password) {
        return add(username, password, UserRole.ADMIN, null, null, null, null, null);
    }

    public void activate(Integer id) {
        User user = getInstance(id);
        user.setActive(true);
        HibernateUtils.update(user);
    }

    public Integer addNormal(String username, String password, String email) {
        return addNormal(username, password, email, null, null);
    }

    public Integer addNormal(String username, String password, String email, String qqOpenId) {
        return addNormal(username, password, email, null, null, qqOpenId);
    }

    public Integer addNormal(String username, String password, String email, Integer spreadUserId, String spreadWay) {
        //return add(username, password, UserRole.NORMAL, email, null, spreadUserId, spreadWay, null);
        return addNormal(username, password, email, spreadUserId, spreadWay, null);
    }

    public Integer addNormal(String username, String password, String email, Integer spreadUserId, String spreadWay, String qqOpenId) {
        return add(username, password, UserRole.NORMAL, email, null, spreadUserId, spreadWay, qqOpenId);
    }

    public void updateTel(Integer id, String tel) {
        User user = getInstance(id);
        if (user != null) {
            user.setTel(tel);
            HibernateUtils.update(user);
        }
    }

    public void updateEmail(Integer id, String email) {
        if (!new MailAddressValidator().validate(email)) {
            LOGGER.error("invalid email: {}", email);
            return;
        }

        User user = getInstance(id);
        if (user != null) {
            user.setEmail(email);
            HibernateUtils.update(user);
        }
    }

    public void deleteNormal(Integer id) {
        if (IntegerUtils.isPositive(id)) {
            new HibernateDeleter().addEqualFilter("id", id).addEqualFilter("role", UserRole.NORMAL).delete(User.class);
        } else {
            LOGGER.error("id is not positive: {}", id);
        }
    }

    public User getInstance(Integer id) {
        return HibernateUtils.get(User.class, id);
    }

    public List<User> getInstances() {
        return new HibernateListBuilder().build(User.class);
    }

    public int countAllUsers() {
        return new HibernateListBuilder().count(User.class);
    }

    public int countNormalUsers() {
        return getNormalUserListBuilder().count(User.class);
    }

    public int countActiveNormalUsers() {
        return getNormalUserListBuilder().addEqualFilter("active", true).count(User.class);
    }

    public int countDirectlyRegisterNormalUsers() {
        return getNormalUserListBuilder().addFilter("spreadUserId IS NULL").count(User.class);
    }

    public int countInvitedRegisterNormalUsers() {
        return getNormalUserListBuilder().addFilter("spreadUserId IS NOT NULL").count(User.class);
    }

    private HibernateListBuilder getNormalUserListBuilder() {
        return new HibernateListBuilder().addEqualFilter("role", UserRole.NORMAL);
    }

    public void updatePassword(Integer id, String password) {
        User user = getInstance(id);
        if (user != null) {
            user.setPassword(password);
            HibernateUtils.update(user);
        }
    }

    public void updateSerialKeyIfNecessary(User user) {
        if (user != null && user.getSerialKey() == null) {
            user.setSerialKey(RandomStringUtils.randomAlphanumeric(SERIAL_KEY_LENGTH));
            HibernateUtils.update(user);
        }
    }

    public void updateAdditionalInfo(Integer id, String gender, String birthday, String constellation,
                                     String hometown, String residence, Boolean lunarBirthday) {
        User user = getInstance(id);
        if (user != null) {
            user.setGender(gender);
            user.setBirthday(birthday);
            user.setConstellation(constellation);
            user.setHometown(hometown);
            user.setResidence(residence);
            user.setLunarBirthday(lunarBirthday);
            HibernateUtils.update(user);
        }
    }

    public String getNameById(Integer id) {
        User user = getInstance(id);
        return user == null ? null : user.getUsername();
    }

    public Integer getIdBySerialKey(String serialKey) {
        if (StringUtils.isBlank(serialKey)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Integer userId = (Integer) new HibernateListBuilder().addEqualFilter("serialKey", serialKey)
                .getFirstItem("SELECT id FROM User");
        return userId;

    }

    public User getInstanceBySerialKey(String serialKey) {
        if (StringUtils.isBlank(serialKey)) {
            return null;
        }

        return new HibernateListBuilder().addEqualFilter("serialKey", serialKey).getFirstItem(User.class);
    }
}

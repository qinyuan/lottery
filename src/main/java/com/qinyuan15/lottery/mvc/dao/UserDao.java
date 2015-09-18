package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateDeleter;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.security.SimpleUserDao;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Dao of User
 * Created by qinyuan on 15-6-29.
 */
public class UserDao extends SimpleUserDao {
    public final static int SERIAL_KEY_LENGTH = 100;

    private Integer add(String username, String password, String role, String email, String tel,
                        Integer spreadUserId, String spreadWay) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setEmail(email);
        user.setTel(tel);
        user.setSpreadUserId(spreadUserId);
        user.setSpreadWay(spreadWay);

        // set default value
        //user.setActive(false);
        user.setActive(true);
        //user.setSerialKey(RandomStringUtils.randomAlphanumeric(SERIAL_KEY_LENGTH));
        user.setReceiveMail(true);
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

    /**
     * Query user by username, email or tel
     *
     * @param usernameFromLoginForm username from login from, including username, email or tel
     * @return user whose username, email or tel is usernameFromLoginForm
     */
    @Override
    public User getInstanceByName(String usernameFromLoginForm) {
        User user = getInstanceByUsername(usernameFromLoginForm);
        if (user != null) {
            return user;
        }

        user = getInstanceByEmail(usernameFromLoginForm);
        if (user != null) {
            return user;
        }

        return getInstanceByTel(usernameFromLoginForm);
    }

    public User getInstanceByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return null;
        }
        return new HibernateListBuilder().addEqualFilter("username", username)
                .getFirstItem(User.class);
    }

    public User getInstanceByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return null;
        }
        return new HibernateListBuilder().addFilter("LOWER(email)=:email")
                .addArgument("email", email.toLowerCase())
                .getFirstItem(User.class);
    }

    public User getInstanceByTel(String tel) {
        return new HibernateListBuilder().addFilter("tel=:tel")
                .addArgument("tel", tel)
                .getFirstItem(User.class);
    }

    public Integer addAdmin(String username, String password) {
        return add(username, password, User.ADMIN, null, null, null, null);
    }

    public void activate(Integer id) {
        User user = getInstance(id);
        user.setActive(true);
        HibernateUtils.update(user);
    }

    public Integer addNormal(String username, String password, String email) {
        return addNormal(username, password, email, null, null);
    }

    public Integer addNormal(String username, String password, String email, Integer spreadUserId, String spreadWay) {
        return add(username, password, User.NORMAL, email, null, spreadUserId, spreadWay);
    }

    public void updateTel(Integer id, String tel) {
        User user = getInstance(id);
        user.setTel(tel);
        HibernateUtils.update(user);
    }

    public void delete(Integer id) {
        HibernateDeleter.deleteById(User.class, id);
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
        return new HibernateListBuilder().addEqualFilter("role", User.NORMAL);
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
            user.setSerialKey(RandomStringUtils.randomAlphanumeric(UserDao.SERIAL_KEY_LENGTH));
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
        if (!IntegerUtils.isPositive(id)) {
            return null;
        }

        User user = getInstance(id);
        return user == null ? null : user.getUsername();
    }

    public Integer getIdBySerialKey(String serialKey) {
        if (!StringUtils.hasText(serialKey)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Integer userId = (Integer) new HibernateListBuilder().addEqualFilter("serialKey", serialKey)
                .getFirstItem("SELECT id FROM User");
        return userId;

    }
}

package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateDeleter;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.security.SimpleUserDao;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Dao of User
 * Created by qinyuan on 15-6-29.
 */
public class UserDao extends SimpleUserDao {
    private Integer add(String username, String password, String role, String email, String tel) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setEmail(adjustEmail(email));
        user.setTel(tel);
        return HibernateUtils.save(user);
    }

    public boolean hasUsername(String username) {
        return super.getInstanceByName(username) != null;
    }

    public boolean hasEmail(String email) {
        return getInstanceByEmail(email) != null;
    }

    @Override
    public com.qinyuan15.utils.security.User getInstanceByName(String usernameFromLoginForm) {
        com.qinyuan15.utils.security.User user = super.getInstanceByName(usernameFromLoginForm);
        if (user != null) {
            return user;
        }

        user = getInstanceByEmail(usernameFromLoginForm);
        if (user != null) {
            return user;
        }

        return getInstanceByTel(usernameFromLoginForm);
    }

    private String adjustEmail(String email) {
        if (StringUtils.hasText(email)) {
            return email.toLowerCase();
        } else {
            return email;
        }
    }

    public User getInstanceByEmail(String email) {
        return new HibernateListBuilder().addFilter("email=:email")
                .addArgument("email", adjustEmail(email))
                .getFirstItem(User.class);
    }

    public User getInstanceByTel(String tel) {
        return new HibernateListBuilder().addFilter("tel=:tel")
                .addArgument("tel", adjustEmail(tel))
                .getFirstItem(User.class);
    }

    public Integer addAdmin(String username, String password) {
        return add(username, password, User.ADMIN, null, null);
    }

    public Integer addNormal(String username, String password, String email, String tel) {
        return add(username, password, User.NORMAL, email, tel);
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
}

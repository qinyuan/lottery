package com.qinyuan15.lottery.mvc.controller;

import com.google.common.collect.Lists;
import com.qinyuan.lib.contact.mail.MailAccountDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.DatabaseTable;
import com.qinyuan.lib.mvc.controller.TableController;
import com.qinyuan.lib.mvc.security.UserRole;
import com.qinyuan15.lottery.mvc.account.RegisterLocationCounter;
import com.qinyuan15.lottery.mvc.dao.*;
import com.qinyuan15.lottery.mvc.mail.NormalMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class AdminUserListController extends TableController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminUserListController.class);

    private final static String MIN_LIVENESS_SESSION_KEY = "admin-user-list-list-mode-min-liveness";
    private final static String SEARCH_WORD_KEY = "admin-user-list-list-mode-search-word";
    private final static String FILTER_LOTTERY_ACTIVITY_IDS_SESSION_KEY = "admin-user-list-filter-lottery-activity-ids";

    @RequestMapping("/admin-user-list")
    public String index(@RequestParam(value = "displayMode", required = false) String displayMode,
                        @RequestParam(value = "minLiveness", required = false) Integer minLiveness,
                        @RequestParam(value = "keyword", required = false) String searchWord) {
        IndexHeaderUtils.setHeaderParameters(this);

        if (displayMode != null && displayMode.equals("table")) {
            setAttribute("displayMode", "table");
            getTableUtil().addIndexAttributes(getTable());
        } else {
            setAttribute("displayMode", "list");
            UserDao userDao = new UserDao();
            setAttribute("userCount", userDao.countNormalUsers());
            setAttribute("activeUserCount", userDao.countActiveNormalUsers());
            setAttribute("directlyRegisterUserCount", userDao.countDirectlyRegisterNormalUsers());
            setAttribute("invitedRegisterUserCount", userDao.countInvitedRegisterNormalUsers());

            if (IntegerUtils.isNotNegative(minLiveness)) {
                session.setAttribute(MIN_LIVENESS_SESSION_KEY, minLiveness);
            }
            setAttribute("minLiveness", getMinLiveness());

            if (searchWord != null) {
                session.setAttribute(SEARCH_WORD_KEY, searchWord);
            }
            setAttribute("keyword", getSearchWord());

            List<LotteryActivity> selectedActivities = new ArrayList<>();
            List<LotteryActivity> unselectedActivities = new ArrayList<>();
            Set<Integer> filterLotteryActivityIds = getFilterLotteryActivityIds();
            for (LotteryActivity activity : new LotteryActivityDao().getInstances()) {
                if (filterLotteryActivityIds.contains(activity.getId())) {
                    selectedActivities.add(activity);
                } else {
                    unselectedActivities.add(activity);
                }
            }
            setAttribute("selectedLotteryActivities", selectedActivities);
            setAttribute("unselectedLotteryActivities", unselectedActivities);

            setAttribute("users", getSimpleTable(filterLotteryActivityIds));
            setAttribute("locationCounts", new RegisterLocationCounter().count());

            addCss("resources/js/lib/font-awesome/css/font-awesome.min", false);
            addCss("resources/js/lib/buttons/buttons.min", false);
        }

        setAttribute("mailAccounts", new MailAccountDao().getInstances());

        // bootstrap switch
        //addJs("lib/bootstrap/js/bootstrap-switch", false);
        //addCss("resources/js/lib/bootstrap/css/bootstrap-switch.min", false);

        // icheck
        addCss("resources/js/lib/icheck/skins/all", false);
        addJs("lib/icheck/icheck.min", false);

        setTitle("用户列表");
        addJs("lib/ckeditor/ckeditor", false);
        addCss("admin-form");
        addCssAndJs("admin-user-list");
        return "admin-user-list";
    }

    private String getSearchWord() {
        Object searchWordFromSession = session.getAttribute(SEARCH_WORD_KEY);
        return searchWordFromSession == null ? null : searchWordFromSession.toString();
    }

    private int getMinLiveness() {
        Object minLivenessFromSession = session.getAttribute(MIN_LIVENESS_SESSION_KEY);
        if (minLivenessFromSession != null && minLivenessFromSession instanceof Integer) {
            return (Integer) minLivenessFromSession;
        } else {
            return 0;
        }
    }

    @RequestMapping(value = "/admin-user-list-delete-lottery-activity-filter.json", method = RequestMethod.POST)
    @ResponseBody
    public String deleteLotteryActivityFilter(@RequestParam(value = "filterLotteryActivityId", required = false)
                                              Integer filterLotteryActivityId) {
        if (IntegerUtils.isPositive(filterLotteryActivityId)) {
            getFilterLotteryActivityIds().remove(filterLotteryActivityId);
            return success();
        } else {
            return failByInvalidParam();
        }
    }

    @RequestMapping(value = "/admin-user-list-add-lottery-activity-filters.json", method = RequestMethod.POST)
    @ResponseBody
    public String addLotteryActivityFilters(@RequestParam(value = "filterLotteryActivityIds", required = true)
                                            Integer[] filterLotteryActivityIds) {
        if (filterLotteryActivityIds != null && filterLotteryActivityIds.length > 0) {
            getFilterLotteryActivityIds().addAll(Lists.newArrayList(filterLotteryActivityIds));
        }
        return success();
    }

    @SuppressWarnings("unchecked")
    private Set<Integer> getFilterLotteryActivityIds() {
        Object idsObject = session.getAttribute(FILTER_LOTTERY_ACTIVITY_IDS_SESSION_KEY);
        if (idsObject == null || !(idsObject instanceof Set)) {
            Set<Integer> ids = new TreeSet<>();
            session.setAttribute(FILTER_LOTTERY_ACTIVITY_IDS_SESSION_KEY, ids);
            return ids;
        } else {
            return (Set) idsObject;
        }
    }

    @RequestMapping(value = "/admin-user-list-send-mail.json", method = RequestMethod.POST)
    @ResponseBody
    public String sendMail(@RequestParam(value = "mailAccountIds[]", required = true) Integer[] mailAccountIds,
                           @RequestParam(value = "userIds[]", required = true) Integer[] userIds,
                           @RequestParam(value = "subject", required = true) String subject,
                           @RequestParam(value = "content", required = true) String content) {
        if (mailAccountIds == null || mailAccountIds.length == 0) {
            return fail("发件箱帐户不能为空！");
        }

        if (userIds == null || userIds.length == 0) {
            return fail("收件人不能为空！");
        }

        if (!StringUtils.hasText(subject)) {
            return fail("邮件标题不能为空！");
        }

        if (!StringUtils.hasText(content)) {
            return fail("邮件正文不能为空！");
        }

        List<Integer> mailAccountList = Lists.newArrayList(mailAccountIds);
        List<Integer> userList = Lists.newArrayList(userIds);
        try {
            new NormalMailSender().send(mailAccountList, userList, subject, content);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to send normal email, mailAccounts: {}, users: {}, subject: {}, content: {}, info: {}",
                    mailAccountIds, userList, subject, content, e);
            return fail("邮件发送失败！");
        }
    }

    @RequestMapping(value = "/admin-user-list-send-system-info.json", method = RequestMethod.POST)
    @ResponseBody
    public String sendSystemInfo(@RequestParam(value = "userIds[]", required = true) Integer[] userIds,
                                 @RequestParam(value = "content", required = true) String content) {
        if (userIds == null || userIds.length == 0) {
            return fail("接受者不能为空！");
        }

        if (!StringUtils.hasText(content)) {
            return fail("消息内容不能为空！");
        }

        List<Integer> userList = Lists.newArrayList(userIds);
        try {
            new SystemInfoSendRecordDao().add(userList, content);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to send system information, users: {}, content: {}, info: {}",
                    userList, content, e);
            return fail("系统消息发送失败！");
        }
    }

    @RequestMapping(value = "/admin-user-list-distinct-values.json", method = RequestMethod.GET)
    @ResponseBody
    public String getDistinctValues() {
        return super.getDistinctValues();
    }

    @RequestMapping(value = "/admin-user-list-filter.json", method = RequestMethod.POST)
    @ResponseBody
    public String addFilter() {
        return super.addFilter();
    }

    @RequestMapping(value = "/admin-user-list-filter-remove.json", method = RequestMethod.POST)
    @ResponseBody
    public String removeFilter() {
        return super.removeFilter();
    }

    private String getBaseTableName() {
        /*String livenessTable = "SELECT spread_user_id,SUM(liveness) AS sum_liveness FROM lottery_liveness " +
                "WHERE activity_id=(SELECT MAX(id) FROM lottery_activity WHERE expire=false) GROUP BY spread_user_id";*/
        String livenessTable = "SELECT spread_user_id,SUM(liveness) AS sum_liveness " +
                "FROM lottery_liveness GROUP BY spread_user_id";
        return "user AS u LEFT JOIN (" + livenessTable + ") AS l ON u.id=l.spread_user_id";
    }

    private List<Object[]> getSimpleTable(Set<Integer> activityIds) {
        HibernateListBuilder listBuilder = new HibernateListBuilder();
        String loginRecordTable = "SELECT MAX(login_time) AS last_login_time,user_id FROM login_record GROUP BY user_id";
        String sql = "SELECT u.id,u.username,l.sum_liveness,DATE_FORMAT(lr.last_login_time,'%Y-%m-%d %T') FROM " +
                getBaseTableName() + " LEFT JOIN (" + loginRecordTable + ") AS lr ON u.id=lr.user_id";

        String whereClause = " WHERE u.role='" + UserRole.NORMAL + "'";

        // filtered by min liveness
        int minLiveness = getMinLiveness();
        if (minLiveness > 0) {
            whereClause += " AND l.sum_liveness>=" + minLiveness;
        }

        // filtered by search word
        String keyword = getSearchWord();
        if (StringUtils.hasText(keyword)) {
            whereClause += " AND (username LIKE :keyword OR email LIKE :keyword OR tel LIKE :keyword)";
            listBuilder.addArgument("keyword", "%" + keyword + "%");
        }

        for (Integer activityId : activityIds) {
            if (!IntegerUtils.isPositive(activityId)) {
                continue;
            }
            whereClause += " AND u.id IN (SELECT user_id FROM lottery_lot WHERE activity_id=" + activityId + ")";
        }
        sql += whereClause + " ORDER BY u.id ASC";

        //return new HibernateListBuilder().buildBySQL(sql);
        return listBuilder.buildBySQL(sql);
    }

    protected DatabaseTable getTable() {
        String tableName = getBaseTableName();

        String lotTable = "SELECT user_id,MAX(lot_time) AS last_lot_time FROM lottery_lot GROUP BY user_id";
        tableName += " LEFT JOIN (" + lotTable + ") AS lot ON u.id=lot.user_id";

        tableName += " LEFT JOIN user AS u2 ON u.id=u2.spread_user_id";

        String invitedUserTable = "SELECT u.id,u.spread_user_id,group_concat(u2.username) AS invited_users " +
                "FROM user AS u JOIN user AS u2 ON u.id=u2.spread_user_id GROUP BY u.id";
        tableName += " LEFT JOIN (" + invitedUserTable + ") AS idu ON u.id=idu.id";

        tableName += " LEFT JOIN user AS iu ON u.spread_user_id=iu.id";

        String loginRecordTable = "SELECT user_id,location FROM login_record WHERE id IN " +
                "(SELECT MAX(id) FROM login_record GROUP BY user_id)";
        tableName += " LEFT JOIN (" + loginRecordTable + ") AS lr ON u.id=lr.user_id";

        String mailRecordTable = "SELECT MAX(send_time) AS last_send_time,user_id FROM mail_send_record GROUP BY user_id";
        tableName += " LEFT JOIN (" + mailRecordTable + ") AS mr ON u.id=mr.user_id";

        DatabaseTable table = new DatabaseTable(tableName, "u.id", DatabaseTable.QueryType.SQL);
        table.addField("用户名", "u.username", "username");
        table.addField("邮箱", "u.email", "email");
        table.addField("地区", "lr.location", "location");
        table.addField("活跃度", "l.sum_liveness", "liveness");
        table.addField("最后一封邮件时间", "mr.last_send_time", "last_send_time");
        table.addField("最近一次抽奖", "lot.last_lot_time", "lot_time");
        table.addField("邀请了谁", "idu.invited_users", "invited_users");
        table.addField("被请邀请", "iu.username", "invite_user");
        table.addEqualFilter("u.role", UserRole.NORMAL);
        return table;
    }
}

package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan15.lottery.mvc.RichHelpGroup;
import com.qinyuan15.lottery.mvc.dao.HelpGroup;
import com.qinyuan15.lottery.mvc.dao.HelpGroupDao;
import com.qinyuan15.lottery.mvc.dao.HelpItemDao;
import com.qinyuan15.lottery.mvc.dao.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminHelpController extends HelpController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminHelpController.class);

    @RequestMapping("/admin-help")
    @Override
    public String index() {
        if (SecurityUtils.hasAuthority(User.ADMIN)) {
            setAttribute("editMode", true);
        }

        addJs("lib/ckeditor/ckeditor", false);
        addCssAndJs("admin-help");
        setTitle("编辑帮助中心");

        return getHelpView();
    }

    @RequestMapping("/admin-add-help-group.json")
    @ResponseBody
    public String addGroup(@RequestParam(value = "title", required = true) String title) {
        try {
            Integer id = new HelpGroupDao().add(title);
            return success(String.valueOf(id));
        } catch (Exception e) {
            LOGGER.error("Fail to add help group, info {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-edit-help-group.json")
    @ResponseBody
    public String editGroup(@RequestParam(value = "id", required = true) Integer id,
                            @RequestParam(value = "title", required = true) String title) {
        if (!StringUtils.hasText(title)) {
            return getEditGroupFailResult(id, "分组名称不能为空");
        }

        try {
            new HelpGroupDao().update(id, title);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to edit help group, info {}", e);
            return getEditGroupFailResult(id, "数据库操作失败");
        }
    }

    private String getEditGroupFailResult(Integer id, String detail) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("detail", detail);
        result.put("oldTitle", new HelpGroupDao().getInstance(id).getTitle());
        return toJson(result);
    }

    @RequestMapping("/admin-delete-help-group.json")
    @ResponseBody
    public String deleteGroup(@RequestParam(value = "id", required = true) Integer id) {
        try {
            new HelpGroupDao().delete(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to edit help group, id: {}, info {}", id, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-rank-up-help-group.json")
    @ResponseBody
    public String rankUpGroup(@RequestParam(value = "id", required = true) Integer id) {
        try {
            new HelpGroupDao().rankUp(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to rank up help group, id: {}, info: {}", id, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-rank-down-help-group.json")
    @ResponseBody
    public String rankDownGroup(@RequestParam(value = "id", required = true) Integer id) {
        try {
            new HelpGroupDao().rankDown(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to rank down help group, id: {}, info: {}", id, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-add-or-edit-help-item")
    public String addOrEditItem(@RequestParam(value = "id", required = true) Integer id,
                                @RequestParam(value = "groupId", required = true) Integer groupId,
                                @RequestParam(value = "icon", required = true) String icon,
                                @RequestParam(value = "iconFile", required = true) MultipartFile iconFile,
                                @RequestParam(value = "title", required = true) String title,
                                @RequestParam(value = "content", required = true) String content) {
        final String index = "admin-help";
        String iconPath = null;
        if (StringUtils.hasText(icon) || isUploadFileNotEmpty(iconFile)) {
            try {
                iconPath = getSavePath(icon, iconFile);
            } catch (Exception e) {
                LOGGER.error("Fail to deal with icon, icon {}, iconFile{}, error info: {}", icon, iconFile, e);
                return redirect(index, "图标文件处理失败");
            }
        }

        try {
            HelpItemDao dao = new HelpItemDao();
            if (IntegerUtils.isPositive(id)) {
                dao.update(id, groupId, iconPath, title, content);
            } else {
                dao.add(groupId, iconPath, title, content);
            }
            return redirect(index);
        } catch (Exception e) {
            LOGGER.error("Fail to add help item, info: {}", e);
            return redirect(index, "数据库操作失败");
        }
    }

    @RequestMapping("/admin-edit-help-item-content.json")
    @ResponseBody
    public String editItemContent(@RequestParam(value = "id", required = true) Integer id
            , @RequestParam(value = "content", required = true) String content) {
        new HelpItemDao().updateContent(id, content);
        return success();
    }

    @RequestMapping("/admin-rank-up-help-item.json")
    @ResponseBody
    public String rankUpItem(@RequestParam(value = "id", required = true) Integer id) {
        try {
            new HelpItemDao().rankUp(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to rank up help item, info {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-rank-down-help-item.json")
    @ResponseBody
    public String rankDownItem(@RequestParam(value = "id", required = true) Integer id) {
        try {
            new HelpItemDao().rankDown(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to rank down help item, info {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-delete-help-item.json")
    @ResponseBody
    public String deleteItem(@RequestParam(value = "id", required = true) Integer id) {
        try {
            new HelpItemDao().delete(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to edit help group, info {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/query-help-item.json") // this url should not start with 'admin'
    @ResponseBody
    public String queryItem(@RequestParam(value = "id", required = true) Integer id) {
        return toJson(adaptHelpItem(new HelpItemDao().getInstance(id)));
    }

    @RequestMapping("/query-help-items.json") // this url should not start with 'admin'
    @ResponseBody
    public String queryItems(@RequestParam(value = "id", required = false) Integer groupId) {
        HelpGroup group;
        HelpGroupDao dao = new HelpGroupDao();
        if (IntegerUtils.isPositive(groupId)) {
            group = dao.getInstance(groupId);
        } else {
            group = dao.getFirstInstance();
        }
        if (group == null) {
            return null;
        }
        return toJson(adaptRichHelpGroup(RichHelpGroup.getInstance(group)));
    }
}

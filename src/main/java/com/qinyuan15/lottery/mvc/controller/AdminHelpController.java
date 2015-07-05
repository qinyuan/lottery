package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.RichHelpGroup;
import com.qinyuan15.lottery.mvc.dao.HelpGroupDao;
import com.qinyuan15.lottery.mvc.dao.HelpItemDao;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminHelpController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminHelpController.class);

    @RequestMapping("/admin-help")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);
        setAttribute("helpGroups", RichHelpGroup.getInstances());

        addJs("resources/js/lib/handlebars.min-v1.3.0", false);
        setTitle("编辑帮助中心");
        addCss("help");
        addJs("admin-help");
        return "help";
    }

    @RequestMapping("/admin-add-help-group.json")
    @ResponseBody
    public String addGroup(@RequestParam(value = "title", required = true) String title) {
        try {
            Integer id = new HelpGroupDao().add(title);
            return success(String.valueOf(id));
        } catch (Exception e) {
            LOGGER.error("Fail to add help group, info {}", e);
            return fail("数据库操作失败");
        }
    }

    @RequestMapping("/admin-edit-help-group.json")
    @ResponseBody
    public String editGroup(@RequestParam(value = "id", required = true) Integer id,
                            @RequestParam(value = "title", required = true) String title) {
        try {
            new HelpGroupDao().update(id, title);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to edit help group, info {}", e);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("detail", "数据库操作失败");
            result.put("oldTitle", new HelpGroupDao().getInstance(id).getTitle());
            return toJson(result);
        }
    }

    @RequestMapping("/admin-delete-help-group.json")
    @ResponseBody
    public String deleteGroup(@RequestParam(value = "id", required = true) Integer id) {
        try {
            new HelpGroupDao().delete(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to edit help group, info {}", e);
            return fail("数据库操作失败");
        }
    }

    @RequestMapping("/admin-rank-up-help-group.json")
    @ResponseBody
    public String rankUpGroup(@RequestParam(value = "id", required = true) Integer id) {
        try {
            new HelpGroupDao().rankUp(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to rank up help group, info {}", e);
            return fail("数据库操作失败");
        }
    }

    @RequestMapping("/admin-rank-down-help-group.json")
    @ResponseBody
    public String rankDownGroup(@RequestParam(value = "id", required = true) Integer id) {
        try {
            new HelpGroupDao().rankDown(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to rank down help group, info {}", e);
            return fail("数据库操作失败");
        }
    }

    @RequestMapping("/admin-add-help-item")
    public String addItem(@RequestParam(value = "groupId", required = true) Integer groupId,
                          @RequestParam(value = "icon", required = true) String icon,
                          @RequestParam(value = "iconFile", required = true) MultipartFile iconFile,
                          @RequestParam(value = "title", required = true) String title,
                          @RequestParam(value = "content", required = true) String content) {
        final String index = "admin-help";
        String iconPath;
        try {
            iconPath = getSavePath(icon, iconFile);
        } catch (Exception e) {
            LOGGER.error("Fail to deal with icon, icon {}, iconFile{}, error info: {}", icon, iconFile, e);
            return redirect(index, "图标文件处理失败");
        }

        try {
            new HelpItemDao().add(groupId, iconPath, title, content);
            return redirect(index);
        } catch (Exception e) {
            LOGGER.error("Fail to add help item, info: {}", e);
            return redirect(index, "数据库操作失败");
        }
    }

    @RequestMapping("/admin-edit-help-item")
    public String editItem(@RequestParam(value = "id", required = true) Integer id,
                           @RequestParam(value = "groupId", required = true) Integer groupId,
                           @RequestParam(value = "icon", required = true) String icon,
                           @RequestParam(value = "iconFile", required = true) MultipartFile iconFile,
                           @RequestParam(value = "title", required = true) String title,
                           @RequestParam(value = "content", required = true) String content) {
        final String index = "admin-help";
        String iconPath;
        try {
            iconPath = getSavePath(icon, iconFile);
        } catch (Exception e) {
            LOGGER.error("Fail to deal with icon, icon {}, iconFile{}, error info: {}", icon, iconFile, e);
            return redirect(index, "图标文件处理失败");
        }

        try {
            new HelpItemDao().update(id, groupId, iconPath, title, content);
            return redirect(index);
        } catch (Exception e) {
            LOGGER.error("Fail to edit help item, info: {}", e);
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
        new HelpItemDao().rankUp(id);
        return success();
    }

    @RequestMapping("/admin-rank-down-help-item.json")
    @ResponseBody
    public String rankDownItem(@RequestParam(value = "id", required = true) Integer id) {
        new HelpItemDao().rankDown(id);
        return success();
    }
}

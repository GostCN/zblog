
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.Tool;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.ToolConditionVO;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工具管理
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/tool")
public class BizToolController {
    @Autowired
    private BizToolService bizToolService;

    @PostMapping("/list")
    public PageResult list(ToolConditionVO vo) {
        PageInfo<Tool> pageInfo = bizToolService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping(value = "/add")
    public ResponseVO add(Tool toolType) {
        bizToolService.insert(toolType);
        return ResultUtil.success("工具添加成功！新工具 - " + toolType.getName());
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            bizToolService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个标签");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.bizToolService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(Tool tool) {
        try {
            bizToolService.updateSelective(tool);
        } catch (Exception e) {
            return ResultUtil.error("工具修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    @PostMapping("/listAll")
    public ResponseVO listType() {
        return ResultUtil.success(null, bizToolService.listAll());
    }

}

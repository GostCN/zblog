
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.ToolType;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.ToolTypeConditionVO;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizToolTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工具类型管理
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/toolType")
public class BizToolTypeController {
    @Autowired
    private BizToolTypeService bizToolTypeService;

    @PostMapping("/list")
    public PageResult list(ToolTypeConditionVO vo) {
        PageInfo<ToolType> pageInfo = bizToolTypeService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping(value = "/add")
    public ResponseVO add(ToolType toolType) {
        bizToolTypeService.insert(toolType);
        return ResultUtil.success("工具类型添加成功！新工具类型 - " + toolType.getName());
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            bizToolTypeService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个标签");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.bizToolTypeService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(ToolType toolType) {
        try {
            bizToolTypeService.updateSelective(toolType);
        } catch (Exception e) {
            return ResultUtil.error("工具类型修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    @PostMapping("/listAll")
    public ResponseVO listType() {
        return ResultUtil.success(null, bizToolTypeService.listAll());
    }

}

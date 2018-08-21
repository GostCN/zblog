package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.NavType;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.NavTypeConditionVO;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizNavTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网址导航类型管理
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/navType")
public class BizNavTypeController {
    @Autowired
    private BizNavTypeService bizNavTypeService;

    @PostMapping("/list")
    public PageResult list(NavTypeConditionVO vo) {
        PageInfo<NavType> pageInfo = bizNavTypeService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping(value = "/add")
    public ResponseVO add(NavType navType) {
        bizNavTypeService.insert(navType);
        return ResultUtil.success("网址导航类型添加成功！新网址导航类型 - " + navType.getName());
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            bizNavTypeService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个标签");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.bizNavTypeService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(NavType navType) {
        try {
            bizNavTypeService.updateSelective(navType);
        } catch (Exception e) {
            return ResultUtil.error("网址导航类型修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    @PostMapping("/listAll")
    public ResponseVO listType() {
        return ResultUtil.success(null, bizNavTypeService.listAll());
    }

}

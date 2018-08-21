
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.Resources;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.ResourceConditionVO;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.SysResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统资源管理
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/resources")
public class SysResourcesController {

    @Autowired
    private SysResourcesService resourcesService;

    @PostMapping("/list")
    public PageResult getAll(ResourceConditionVO vo) {
        PageInfo<Resources> pageInfo = resourcesService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping("/resourcesWithSelected")
    public ResponseVO<List<Resources>> resourcesWithSelected(Long rid) {
        return ResultUtil.success(null, resourcesService.queryResourcesListWithSelected(rid));
    }

    @PostMapping(value = "/add")
    public ResponseVO add(Resources resources) {
        resourcesService.insert(resources);
        return ResultUtil.success("成功");
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            resourcesService.removeByPrimaryKey(id);
        }

        //更新权限
        return ResultUtil.success("成功删除 [" + ids.length + "] 个资源");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.resourcesService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(Resources resources) {
        try {
            resourcesService.updateSelective(resources);
        } catch (Exception e) {
            return ResultUtil.error("资源修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }
}

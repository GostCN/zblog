
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.Role;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.RoleConditionVO;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.SysRoleResourcesService;
import com.cchcz.blog.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统角色管理
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/roles")
public class SysRoleController {
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysRoleResourcesService roleResourcesService;

    @PostMapping("/list")
    public PageResult getAll(RoleConditionVO vo) {
        PageInfo<Role> pageInfo = roleService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping("/rolesWithSelected")
    public ResponseVO<List<Role>> rolesWithSelected(Integer uid) {
        return ResultUtil.success(null, roleService.queryRoleListWithSelected(uid));
    }

    @PostMapping("/saveRoleResources")
    public ResponseVO saveRoleResources(Long roleId, String resourcesId) {
        if (StringUtils.isEmpty(roleId)) {
            return ResultUtil.error("error");
        }
        roleResourcesService.addRoleResources(roleId, resourcesId);
        // 重新加载所有拥有roleId的用户的权限信息
        return ResultUtil.success("成功");
    }

    @PostMapping(value = "/add")
    public ResponseVO add(Role role) {
        roleService.insert(role);
        return ResultUtil.success("成功");
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            roleService.removeByPrimaryKey(id);
            roleResourcesService.removeByRoleId(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个角色");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.roleService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(Role role) {
        try {
            roleService.updateSelective(role);
        } catch (Exception e) {
            return ResultUtil.error("角色修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

}

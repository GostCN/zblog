package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.OSProject;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.OSProjectConditionVO;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizOSProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开源项目管理
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/osproject")
public class BizOSProjectController {
    @Autowired
    private BizOSProjectService bizOSProjectService;

    @PostMapping("/list")
    public PageResult list(OSProjectConditionVO vo) {
        PageInfo<OSProject> pageInfo = bizOSProjectService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping(value = "/add")
    public ResponseVO add(OSProject osproject) {
        bizOSProjectService.insert(osproject);
        return ResultUtil.success("开源项目添加成功！新开源项目 - " + osproject.getName());
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            bizOSProjectService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个标签");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.bizOSProjectService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(OSProject osproject) {
        try {
            bizOSProjectService.updateSelective(osproject);
        } catch (Exception e) {
            return ResultUtil.error("开源项目修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    @PostMapping("/listAll")
    public ResponseVO listType() {
        return ResultUtil.success(null, bizOSProjectService.listAll());
    }

    @PostMapping("/update/{type}")
    public ResponseVO update(@PathVariable("type") String type, Long id) {
        OSProject project = bizOSProjectService.getByPrimaryKey(id);
        project.setStatus(((Integer.valueOf(project.getStatus()) + 1) % 2) + "");
        bizOSProjectService.updateSelective(project);
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }
}

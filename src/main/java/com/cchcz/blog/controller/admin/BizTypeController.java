
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.Type;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.TypeConditionVO;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文章类型管理
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/type")
public class BizTypeController {
    @Autowired
    private BizTypeService typeService;

    @PostMapping("/list")
    public PageResult list(TypeConditionVO vo) {
        PageInfo<Type> pageInfo = typeService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping("/listNodes")
    public PageResult listNodes(TypeConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber() - 1, vo.getPageSize());
        if (vo.getPid() == null) {
            return ResultUtil.tablePage(null);
        }
        PageInfo<Type> pageInfo = typeService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping(value = "/add")
    public ResponseVO add(Type type) {
        typeService.insert(type);
        return ResultUtil.success("文章类型添加成功！新类型 - " + type.getName());
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            typeService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个文章类型");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.typeService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(Type type) {
        try {
            typeService.updateSelective(type);
        } catch (Exception e) {
            return ResultUtil.error("文章类型修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    @PostMapping("/listAll")
    public ResponseVO listType() {
        List<Type> types = typeService.listAll();
        if (CollectionUtils.isEmpty(types)) {
            return null;
        }
        Set<Long> set = types.stream()
                .filter(x -> !(CollectionUtils.isEmpty(x.getNodes())))
                .map(x -> x.getId())
                .collect(Collectors.toSet());
        List<Type> subTypes = types.stream()
                .filter(x -> !set.contains(x.getId()))
                .collect(Collectors.toList());
        return ResultUtil.success(null, subTypes);
    }

}

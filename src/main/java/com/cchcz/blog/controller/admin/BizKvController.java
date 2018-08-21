
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.Kv;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.KvConditionVO;
import com.cchcz.blog.service.BizKvService;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统参数管理
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/kv")
public class BizKvController {
    @Autowired
    private BizKvService bizKvService;

    @PostMapping("/list")
    public PageResult list(KvConditionVO vo) {
        PageInfo<Kv> pageInfo = bizKvService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping(value = "/add")
    public ResponseVO add(Kv kv) {
        bizKvService.insert(kv);
        return ResultUtil.success("系统参数添加成功！新系统参数 - " + kv.getPkey());
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            bizKvService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个标签");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.bizKvService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(Kv kv) {
        try {
            bizKvService.updateSelective(kv);
        } catch (Exception e) {
            return ResultUtil.error("系统参数修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    @PostMapping("/listAll")
    public ResponseVO listType() {
        return ResultUtil.success(null, bizKvService.listAll());
    }

}


package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.UpdateRecorde;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.UpdateRecordeConditionVO;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.SysUpdateRecordeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统更新日志
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/update")
public class SysUpdateRecordController {
    @Autowired
    private SysUpdateRecordeService updateRecordeService;

    @PostMapping("/list")
    public PageResult list(UpdateRecordeConditionVO vo) {
        PageInfo<UpdateRecorde> pageInfo = updateRecordeService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping(value = "/add")
    public ResponseVO add(UpdateRecorde updateRecorde) {
        updateRecordeService.insert(updateRecorde);
        return ResultUtil.success("成功");
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            updateRecordeService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个更新记录");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.updateRecordeService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(UpdateRecorde updateRecorde) {
        try {
            updateRecordeService.updateSelective(updateRecorde);
        } catch (Exception e) {
            return ResultUtil.error("更新记录修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

}

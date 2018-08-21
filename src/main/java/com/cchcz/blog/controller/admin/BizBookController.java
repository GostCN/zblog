
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.Book;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.BookConditionVO;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 书籍管理
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/book")
public class BizBookController {
    @Autowired
    private BizBookService bizBookService;

    @PostMapping("/list")
    public PageResult list(BookConditionVO vo) {
        PageInfo<Book> pageInfo = bizBookService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping(value = "/add")
    public ResponseVO add(Book book) {
        bizBookService.insert(book);
        return ResultUtil.success("书籍添加成功！新书籍 - " + book.getTitle());
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            bizBookService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个标签");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.bizBookService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(Book book) {
        try {
            bizBookService.updateSelective(book);
        } catch (Exception e) {
            return ResultUtil.error("书籍修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    @PostMapping("/listAll")
    public ResponseVO listType() {
        return ResultUtil.success(null, bizBookService.listAll());
    }

    @PostMapping("/update/{type}")
    public ResponseVO update(@PathVariable("type") String type, Long id) {
        Book book = bizBookService.getByPrimaryKey(id);
        book.setStatus(((Integer.valueOf(book.getStatus()) + 1) % 2) + "");
        bizBookService.updateSelective(book);
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

}

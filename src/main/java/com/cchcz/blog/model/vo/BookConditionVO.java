package com.cchcz.blog.model.vo;

import com.cchcz.blog.model.entity.Book;
import com.cchcz.blog.model.object.BaseConditionVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <ClassName>BookConditionVO</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月03日 07:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BookConditionVO extends BaseConditionVO {
    private Book book;
}

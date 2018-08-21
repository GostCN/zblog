
package com.cchcz.blog.controller;

import com.cchcz.blog.constant.CommonConstant;
import com.cchcz.blog.exception.*;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.util.ResultUtil;
import com.cchcz.blog.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * 统一异常处理类<br>
 * 捕获程序所有异常，针对不同异常，采取不同的处理方式
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandleControllerAdvice {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseVO handle(Throwable e) {
        if (e instanceof BlogArticleException
                || e instanceof BlogCommentException
                || e instanceof BlogFileException
                || e instanceof BlogLinkException
                || e instanceof BlogException) {
            return ResultUtil.error(e.getMessage());
        }
        if (e instanceof UndeclaredThrowableException) {
            e = ((UndeclaredThrowableException) e).getUndeclaredThrowable();
        }
        ResponseStatus responseStatus = ResponseStatus.getResponseStatus(e.getMessage());
        if (responseStatus != null) {
            log.error(responseStatus.getMessage());
            return ResultUtil.error(responseStatus.getCode(), responseStatus.getMessage());
        }
        log.error("统一异常处理:", e);// 打印异常栈
        return ResultUtil.error(CommonConstant.DEFAULT_ERROR_CODE, ResponseStatus.ERROR.getMessage());
    }

}

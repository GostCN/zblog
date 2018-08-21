
package com.cchcz.blog.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
* @author cchcz

 * @date 2018/6/6 16:34
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserPwd {
    @NotNull(message = "用户ID不可为空")
    private Long id;
    @NotEmpty(message = "原密码不可为空")
    private String password;
    @NotEmpty(message = "新密码不可为空")
    @Length(max = 20, min = 6, message = "新密码长度建议保持在6~20个字符以内")
    private String newPassword;
    @NotEmpty(message = "新密码不可为空")
    @Length(max = 20, min = 6, message = "新密码长度建议保持在6~20个字符以内")
    private String newPasswordRepeat;
}

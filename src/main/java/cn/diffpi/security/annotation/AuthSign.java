package cn.diffpi.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.diffpi.security.credential.CredentialType;

/***
 * 签名注解类，在类或方法上面加入注解
 *  Created by one__l on 2016年4月25日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface AuthSign {
    /***
     * 权限验证等级
     * @return
     */
    CredentialType valiLevel() default CredentialType.IS_AUTH;//默认只需验证身份

}

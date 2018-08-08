package cn.diffpi.security.credential;


/***
 * 凭证类型 Created by one__l on 2016年4月25日
 */
public enum CredentialType {
    NO,//无需任何验证
    IS_AUTH, // 只需要验证用户是否有权限
    REQS_SIGN,// 请求需要验证签名
    REPS_SIGN,// 响应需要签名
    TOP_SECRET; // 绝密级别代表必须验证请求跟响应都需加密
}

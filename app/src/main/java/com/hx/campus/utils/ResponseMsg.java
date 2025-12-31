

package com.hx.campus.utils;
/**
 * 通用返回提示信息常量类
 * 包含各类业务场景下的常用提示文案，可直接引用
 */
public class ResponseMsg{
    // ===================== 基础操作提示 =====================
    /** 操作成功 */
    public static final String SUCCESS = "操作成功";
    /** 操作失败 */
    public static final String FAIL = "操作失败";
    /** 请求成功 */
    public static final String REQUEST_SUCCESS = "请求成功";
    /** 请求失败 */
    public static final String REQUEST_FAIL = "请求失败";

    // ===================== 参数相关提示 =====================
    /** 参数错误 */
    public static final String PARAM_ERROR = "参数格式错误或缺失";
    /** 参数不能为空 */
    public static final String PARAM_NOT_EMPTY = "参数不能为空";
    /** 参数格式错误 */
    public static final String PARAM_FORMAT_ERROR = "参数格式错误";
    /** 参数超出范围 */
    public static final String PARAM_OUT_OF_RANGE = "参数超出允许范围";

    // ===================== 权限/认证相关提示 =====================
    /** 未登录或登录过期 */
    public static final String UNAUTHORIZED = "未登录或登录已过期，请重新登录";
    /** 权限不足 */
    public static final String FORBIDDEN = "暂无权限执行该操作";
    /** 账号被禁用 */
    public static final String ACCOUNT_DISABLED = "账号已被禁用，请联系管理员";
    /** 账号或密码错误 */
    public static final String ACCOUNT_PWD_ERROR = "手机号或密码错误";
    /** 验证码错误 */
    public static final String CAPTCHA_ERROR = "验证码错误或已过期";

    // ===================== 资源相关提示 =====================
    /** 资源不存在 */
    public static final String RESOURCE_NOT_FOUND = "请求的资源不存在";
    /** 资源已存在 */
    public static final String RESOURCE_EXIST = "数据已存在，请勿重复提交";
    /** 资源已被删除 */
    public static final String RESOURCE_DELETED = "资源已被删除";
    /** 资源被占用 */
    public static final String RESOURCE_OCCUPIED = "资源正在被使用，无法操作";

    // ===================== 数据相关提示 =====================
    /** 暂无数据 */
    public static final String DATA_EMPTY = "暂无数据";
    /** 数据更新失败 */
    public static final String DATA_UPDATE_FAIL = "数据更新失败";
    /** 数据删除失败 */
    public static final String DATA_DELETE_FAIL = "数据删除失败";
    /** 数据新增失败 */
    public static final String DATA_ADD_FAIL = "数据新增失败";

    // ===================== 系统相关提示 =====================
    /** 系统繁忙 */
    public static final String SYSTEM_BUSY = "系统繁忙，请稍后重试";
    /** 请求超时 */
    public static final String REQUEST_TIMEOUT = "请求超时，请稍后重试";
    /** 服务暂不可用 */
    public static final String SERVICE_UNAVAILABLE = "服务暂不可用";
    /** 接口调用失败 */
    public static final String INTERFACE_CALL_FAIL = "第三方接口调用失败";

    // ===================== 业务通用提示 =====================
    /** 操作过于频繁 */
    public static final String OPERATE_TOO_FREQUENT = "操作过于频繁，请稍后再试";
    /** 超出限制 */
    public static final String EXCEED_LIMIT = "超出最大限制，无法继续操作";
    /** 操作已取消 */
    public static final String OPERATE_CANCELLED = "操作已取消";
    /** 操作超时 */
    public static final String OPERATE_TIMEOUT = "操作超时，请重新尝试";
}

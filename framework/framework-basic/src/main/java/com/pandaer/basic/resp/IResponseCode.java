package com.pandaer.basic.resp;

/**
 * 顶级状态相应码接口
 * 1. HTTP的状态响应码
 * 2. 业务状态码
 * 3. 业务状态描述
 */
public interface IResponseCode {

    /**
     * 业务状态码
     * @return
     */
    Integer getCode();

    /**
     * 业务状态描述
     * @return
     */
    String getMessage();
}

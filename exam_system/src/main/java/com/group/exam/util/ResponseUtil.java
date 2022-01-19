package com.group.exam.util;


import com.alibaba.fastjson.JSON;
import com.group.exam.model.cusEnum.ResponseCode;
import com.group.exam.model.responseModel.ResponseModel;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class ResponseUtil {
    public static ResponseModel success(Object object, String msg){
        ResponseModel result = new ResponseModel();
        result.setStatus(ResponseCode.SUCCESS.getCode());
        result.setMessage(msg == null?ResponseCode.SUCCESS.getMsg():msg);
        result.setData(object);
        return result;
    }
    /**成功但不带数据**/
    public static ResponseModel success(){

        return success(null,null);
    }

    public static ResponseModel success(Object object){

        return success(object,null);
    }

    public static ResponseModel success(String msg){

        return success(null, msg);
    }

    public static ResponseModel empty(String msg){
        ResponseModel result = new ResponseModel();
        result.setStatus(ResponseCode.DATA_IS_EMPTY.getCode());
        result.setMessage(msg == null?ResponseCode.DATA_IS_EMPTY.getMsg():msg);
        return result;
    }
    /**失败**/
    public static ResponseModel error(String msg){
        ResponseModel result = new ResponseModel();
        result.setStatus(ResponseCode.UNKNOWN_ERROR.getCode());
        result.setMessage(msg == null?ResponseCode.UNKNOWN_ERROR.getMsg():msg);
        return result;
    }

    public static ResponseModel error(){
        return error(null);
    }

    /**token验证失败**/
    public static ResponseModel tokenError(String msg){
        ResponseModel result = new ResponseModel();
        result.setStatus(ResponseCode.TOKEN_ERROR.getCode());
        result.setMessage(msg == null?ResponseCode.TOKEN_ERROR.getMsg():msg);
        return result;
    }

    public static ResponseModel tokenError(){
        return error(null);
    }

    public static void ok(HttpServletResponse response, ResponseModel result){
        out(response, result, 200);
    }

    public static void denied(HttpServletResponse response, ResponseModel result){
        out(response, result, 401);
    }

    private static void out(HttpServletResponse response, ResponseModel result, int status) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setStatus(status);
            out = response.getWriter();
            out.println(JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}

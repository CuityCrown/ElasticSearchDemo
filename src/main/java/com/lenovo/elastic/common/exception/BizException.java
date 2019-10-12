package com.lenovo.elastic.common.exception;

/**
 * description:业务异常Exception
 *
 * @author 刘一博
 * @version V1.0
 * @date 2019/10/12 18:02
 */
public class BizException extends RuntimeException{

    public BizException(String message) {
        super(message);
    }

}

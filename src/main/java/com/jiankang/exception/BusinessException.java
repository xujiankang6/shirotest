package com.jiankang.exception;

/**
 * BusinessException
 *
 * @author jiankang.xu
 * @date 2020/3/9
 */

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException(Object Obj) {
        super(Obj.toString());
    }

}

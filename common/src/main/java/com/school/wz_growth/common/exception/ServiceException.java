package com.school.wz_growth.common.exception;


import com.school.wz_growth.common.response.ResponseVoResultCode;

import java.io.Serializable;


public class ServiceException extends Exception implements Serializable {

    private static final long serialVersionUID = 211943645297464984L;

    private int code = ResponseVoResultCode.CODE_SUCCESS;


    public ServiceException () {

    }


    public ServiceException (String message, int code) {
        super(message);
        this.code = code;
    }

    public ServiceException (String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }


    public int getCode() {
        return code;
    }

    public ServiceException setCode(int code) {
        this.code = code;
        return this;
    }
}

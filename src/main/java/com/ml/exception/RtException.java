package com.ml.exception;

/**
 * @author panda.
 * @since 2017-09-06 16:08.
 */
public class RtException extends RuntimeException {

    private int code = 0;
    private String msg;

    public RtException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RtException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RtException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RtException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}

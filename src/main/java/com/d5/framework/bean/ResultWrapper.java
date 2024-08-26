//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.d5.framework.bean;

import com.d5.framework.exception.BusinessException;
import java.io.Serializable;
import java.util.Objects;

public class ResultWrapper<T> implements Serializable {
    public static final Integer SUCCESS_CODE = 0;
    public static final String SUCCESS_MSG = "success";
    private Integer code;
    private String msg;
    private T data;

    public ResultWrapper(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultWrapper(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResultWrapper<T> success() {
        return new ResultWrapper(SUCCESS_CODE, "success");
    }

    public static <T> ResultWrapper<T> success(T data) {
        return new ResultWrapper(SUCCESS_CODE, "success", data);
    }

    public static <T> ResultWrapper<T> error(int code, String msg) {
        return new ResultWrapper(code, msg);
    }

    /** @deprecated */
    @Deprecated
    public boolean isSuccess(Integer code) {
        return Objects.equals(code, SUCCESS_CODE);
    }

    public boolean isSuccess() {
        return Objects.equals(this.code, SUCCESS_CODE);
    }

    public static <T> ResultWrapper<T> error(BusinessException exception) {
        return error(exception.getCode(), exception.getMessage());
    }

    public static <T> ResultWrapper<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getValue());
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResultWrapper)) {
            return false;
        } else {
            ResultWrapper<?> other = (ResultWrapper)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label47;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label47;
                    }

                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResultWrapper;
    }

//    public int hashCode() {
//        int PRIME = true;
//        int result = 1;
//        Object $code = this.getCode();
//        result = result * 59 + ($code == null ? 43 : $code.hashCode());
//        Object $msg = this.getMsg();
//        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
//        Object $data = this.getData();
//        result = result * 59 + ($data == null ? 43 : $data.hashCode());
//        return result;
//    }

    public String toString() {
        return "ResultWrapper(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
    }

    public ResultWrapper() {
    }
}

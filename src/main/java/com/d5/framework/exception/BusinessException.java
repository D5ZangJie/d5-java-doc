//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.d5.framework.exception;

import com.d5.framework.bean.ErrorCode;

public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;

    public BusinessException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getValue();
    }

    public String getMessage() {
        return this.message;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "BusinessException(code=" + this.getCode() + ", message=" + this.getMessage() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BusinessException)) {
            return false;
        } else {
            BusinessException other = (BusinessException)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BusinessException;
    }

//    public int hashCode() {
//        int PRIME = true;
//        int result = super.hashCode();
//        Object $code = this.getCode();
//        result = result * 59 + ($code == null ? 43 : $code.hashCode());
//        Object $message = this.getMessage();
//        result = result * 59 + ($message == null ? 43 : $message.hashCode());
//        return result;
//    }
}

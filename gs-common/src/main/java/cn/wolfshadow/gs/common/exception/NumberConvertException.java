package cn.wolfshadow.gs.common.exception;

/**
 * 主键为空异常
 */
public class NumberConvertException extends RuntimeException {

    private static final String MSG = "数值转换出错！";


    public NumberConvertException() {
        super(MSG);
    }

    public NumberConvertException(String message) {
        super(message);
    }
}

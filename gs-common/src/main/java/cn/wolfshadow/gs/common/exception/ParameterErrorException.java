package cn.wolfshadow.gs.common.exception;

/**
 * 主键为空异常
 */
public class ParameterErrorException extends RuntimeException {

    private static final String MSG = "参数错误！";


    public ParameterErrorException() {
        super(MSG);
    }

    public ParameterErrorException(String message) {
        super(message);
    }
}

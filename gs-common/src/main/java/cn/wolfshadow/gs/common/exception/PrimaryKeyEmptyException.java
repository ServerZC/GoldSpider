package cn.wolfshadow.gs.common.exception;

/**
 * 主键为空异常
 */
public class PrimaryKeyEmptyException extends RuntimeException {

    private static final String MSG = "主键不能为空！";


    public PrimaryKeyEmptyException() {
        super(MSG);
    }

    public PrimaryKeyEmptyException(String message) {
        super(message);
    }
}

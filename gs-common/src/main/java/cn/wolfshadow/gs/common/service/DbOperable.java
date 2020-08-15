package cn.wolfshadow.gs.common.service;

import java.util.List;

/**
 * 操作数据库的接口
 * @param <D>
 */
public interface DbOperable<D> {

    public static final String DATA_VALID = "1";
    public static final String DATA_INVALID = "0";

    D insert(D data);

    boolean update(D data);

    boolean delete(D data);

    D get(String pk);

    List<D> list(D condition);
}

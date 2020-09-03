package cn.wolfshadow.gs.common.service.impl;

import cn.wolfshadow.gs.common.entity.TaskListEntity;
import cn.wolfshadow.gs.common.exception.PrimaryKeyEmptyException;
import cn.wolfshadow.gs.common.service.DbOperable;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

/**
 * MongoDb操作的抽象类，实现简单DB操作
 * @param <D>
 */
public abstract class MongoDbOperator<D> implements DbOperable<D> {


    public D insert(D data, MongoOperations mongoOperations) throws IllegalAccessException{
        return mongoOperations.insert(data);
    }

    public int insertBatch(List<D> datas, MongoOperations mongoOperations) throws IllegalAccessException{
        Collection<D> ds = mongoOperations.insertAll(datas);
        return ds.size();
    }

    public boolean update(D data, MongoOperations mongoOperations) throws IllegalAccessException{
        //反射获取data的所有属性及值
        Class<?> aClass = data.getClass();
        Field[] fields = aClass.getDeclaredFields();
        String idValue = null;
        Update update = new Update();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(data);

            if (value == null) continue;

            if ("id".equals(name)) idValue = value.toString();
            else update.set(name,value);

        }

        if (idValue == null) throw new PrimaryKeyEmptyException();

        //修改条件
        Query query = new Query(Criteria.where("id").is(idValue));

        //修改
        mongoOperations.updateFirst(query,update,aClass);

        return true;
    }

    public boolean delete(D data, MongoOperations mongoOperations) {
        DeleteResult result = mongoOperations.remove(data);
        return result.getDeletedCount() > 0;
    }

    public D get(String pk, MongoOperations mongoOperations) {
        Class <D> entityClass = (Class <D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Query query = new Query(Criteria.where("id").is(pk));
        List<D> ds = mongoOperations.find(query, entityClass);
        return ds.isEmpty() ? null : ds.get(0);
    }

    public List<D> list(D condition, MongoOperations mongoOperations) {
        return null;
    }


}

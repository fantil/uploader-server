package org.fanti.uploader.server.mapper.base;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.entity.Example;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/9/17
 */

public abstract interface IMapperService<T> {
    public abstract int insertSelective(T paramT);

    public abstract int insert(T paramT);

    public abstract int deleteByPrimaryKey(Object paramObject);

    public abstract int delete(T paramT);

    public abstract int deleteByExample(Example paramExample);

    public abstract int updateByExample(T paramT, Example paramExample);

    public abstract int updateByExampleSelective(T paramT, Example paramExample);

    public abstract int updateByPrimaryKey(T paramT);

    public abstract int updateByPrimaryKeySelective(T paramT);

    public abstract List<T> selectAll();

    public abstract T selectOne(T paramT);

    public abstract List<T> select(T paramT);

    public abstract T selectByPrimaryKey(Object paramObject);

    public abstract int selectCount(T paramT);

    public abstract int selectCount(Example paramExample);

    public abstract List<T> selectByExample(Object paramObject);

    public abstract List<T> selectByRowBounds(T paramT, RowBounds paramRowBounds);

    public abstract boolean existsWithPrimaryKey(Object paramObject);
}

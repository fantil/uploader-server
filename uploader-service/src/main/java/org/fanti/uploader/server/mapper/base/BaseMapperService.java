package org.fanti.uploader.server.mapper.base;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/9/17
 */

public abstract class BaseMapperService<T>
        implements IMapperService<T> {
    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return this.mapper;
    }

    public int insertSelective(T record) {
        return this.mapper.insertSelective(record);
    }

    public int insert(T record) {
        return this.mapper.insert(record);
    }

    public int deleteByPrimaryKey(Object key) {
        return this.mapper.deleteByPrimaryKey(key);
    }

    public int delete(T record) {
        return this.mapper.delete(record);
    }

    public int deleteByExample(Example example) {
        return this.mapper.deleteByExample(example);
    }

    public int updateByExample(T record, Example example) {
        return this.mapper.updateByExample(record, example);
    }

    public int updateByExampleSelective(T record, Example example) {
        return this.mapper.updateByExampleSelective(record, example);
    }

    public int updateByPrimaryKey(T record) {
        return this.mapper.updateByPrimaryKey(record);
    }

    public int updateByPrimaryKeySelective(T record) {
        return this.mapper.updateByPrimaryKeySelective(record);
    }

    public List<T> selectAll() {
        return this.mapper.selectAll();
    }

    public T selectOne(T record) {
        return this.mapper.selectOne(record);
    }

    public List<T> select(T record) {
        return this.mapper.select(record);
    }

    public T selectByPrimaryKey(Object key) {
        return this.mapper.selectByPrimaryKey(key);
    }

    public int selectCount(T record) {
        return this.mapper.selectCount(record);
    }

    public int selectCount(Example example) {
        return this.mapper.selectCountByExample(example);
    }

    public List<T> selectByExample(Object example) {
        return this.mapper.selectByExample(example);
    }

    public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
        return this.mapper.selectByRowBounds(record, rowBounds);
    }

    public boolean existsWithPrimaryKey(Object key) {
        return this.mapper.existsWithPrimaryKey(key);
    }
}


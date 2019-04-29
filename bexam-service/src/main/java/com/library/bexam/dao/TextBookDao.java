package com.library.bexam.dao;

import com.library.bexam.entity.TextBookEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教材册别操作数据库类
 * @author caoqian
 * @date 20181219
 */
public interface TextBookDao {
    /**
     * 添加教材册别
     * @param textBookEntity
     * @return
     */
    boolean addTextBook(TextBookEntity textBookEntity);

    /**
     * 批量保存教材册别
     * @param textBookEntity
     */
    boolean addTextBookList(List<TextBookEntity> textBookEntity);

    /**
     * 保存教材版本与教材册别的关系
     * @param textBookEntity
     * @return
     */
    boolean addVersion2TextBook(List<TextBookEntity> textBookEntity);

    /**
     * 根据学段ids获取教材册别列表
     * @param periodIdArr 学段ids
     * @return
     */
    List<TextBookEntity> searchTextBookList(@Param("periodIdArr") String[] periodIdArr);

    /**
     * 根据教材版本id查询教材册别
     * @param versionId
     * @return
     */
    List<TextBookEntity> searchTextBookListByVersionId(@Param("versionId") int versionId);
}

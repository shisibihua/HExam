package com.library.bexam.dao;

import com.library.bexam.entity.NodeBookEntity;
import com.library.bexam.form.NodeBookForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教材章节操作数据库类
 * @author caoqian
 * @date 20181222
 */
public interface NodeBookDao {
    /**
     * 添加教材章节
     * @param nodeBookEntity
     * @return
     */
    boolean addNodeBook(NodeBookEntity nodeBookEntity);

    /**
     * 批量保存教材章节
     * @param nodeBookEntity
     */
    boolean addNodeBookList(List<NodeBookForm> nodeBookEntity);

    /**
     * 保存教材册别与教材章节的关系
     * @param nodeBookEntityList
     * @return
     */
    boolean addTextBook2NodeBook(List<NodeBookForm> nodeBookEntityList);

    /**
     * 获取教材章节列表
     * @param firstIndex  起始页
     * @param pageSize    每页大小
     * @return
     */
    List<NodeBookEntity> searchNodeBookPageList(@Param("firstIndex") int firstIndex, @Param("pageSize") int pageSize);

    /**
     * 根据教材册别id查询章节
     * @param bookId
     * @return
     */
    List<NodeBookEntity> searchNodeListByBookId(@Param("bookId") int bookId);


    /**
     * 根据科目id获取章节列表
     * @param subjectId 科目id
     * @return
     */
    List<NodeBookEntity> searchNodeListBySubId(int subjectId);

    /**
     * 根据父级章节查询子级章节
     * @param nodeBookList
     * @return
     */
    List<NodeBookEntity> searchChildNodeBookList(List<NodeBookEntity> nodeBookList);
}

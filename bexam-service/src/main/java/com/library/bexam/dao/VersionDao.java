package com.library.bexam.dao;

import com.library.bexam.entity.VersionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教材版本操作数据库类
 * @author caoqian
 * @date 20181219
 */
public interface VersionDao {
    /**
     * 添加教材版本
     * @param versionEntity
     * @return
     */
    boolean addVersion(VersionEntity versionEntity);

    /**
     * 批量保存教材版本
     */
    boolean addVersionList(List<VersionEntity> versionEntity);

    /**
     * 保存科目与教材版本的关系
     * @param versionEntity
     * @return
     */
    boolean addSubject2Version(List<VersionEntity> versionEntity);

    /**
     * 获取教材版本列表
     * @return
     */
    List<VersionEntity> searchVersionList();

    /**
     * 根据科目id查询教材版本
     * @param subjectId
     * @return
     */
    List<VersionEntity> getVersionListBySubId(@Param("subjectId") int subjectId);
}

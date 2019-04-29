package com.library.bexam.dao;

import com.library.bexam.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户操作数据库类
 * @author caoqian
 * @date 20181213
 */
public interface UserDao {
    /**
     * 添加用户
     * @param user
     * @return
     */
    boolean addUser(UserEntity user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    boolean updateUser(UserEntity user);

    /**
     * 删除用户，支持批量删除（逻辑删除）
     * @param userIds  用户id，多个","分割
     * @return
     */
    boolean deleteUser(@Param("userIds") String[] userIds);

    /**
     * 分页查询用户信息，支持模糊查询
     * @param type       用户类型，传空时，查询全部；1为查询管理员；2为查询教师
     * @param search     用户名或手机号，可为空
     * @param firstIndex 起始条数
     * @param pageSize   每页条数
     * @return
     */
    List<UserEntity> searchUserList(@Param("type") String type, @Param("search") String search ,@Param("firstIndex") int firstIndex,
                                    @Param("pageSize") int pageSize);

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    UserEntity searchUserById(int userId);

    /**
     * 验证是否存在用户
     * @param userEntity
     * @return
     */
    int exitUser(UserEntity userEntity);
    int exitUserMap(Map<String,Object> userEntity);

    /**
     * 根据用户名或手机号查询用户
     * @param userEntity
     * @return
     */
    UserEntity searchUser(UserEntity userEntity);

    /**
     * 查询用户总条数
     * @return
     */
    int getUserTotalCount();

    /**
     * 根据用户名查询用户信息
     * @param userName  用户名
     * @return
     */
    UserEntity searchUserByName(String userName);

    /**
     * 分配老师到班级
     * @param classId  班级id
     * @param userIds   用户id，多个","分割，如“1，2,3”
     * @return
     */
    boolean allotUserToClass(@Param("classId") int classId,@Param("userIdsArr") String[] userIds);

    /**
     * 根据班级id查询用户信息
     * @param classId  用户id
     * @return
     */
    List<UserEntity> searchUsersByClassId(@Param("classId") int classId);

    /**
     * 根据班级ids删除班级与用户的关系
     * @param classIdList
     * @return
     */
    boolean deleteCla2UserByClassId(@Param("list") List<Integer> classIdList);

    /**
     * 根据用户ids删除班级与用户的关系
     * @param userId  多个","分割
     * @return
     */
    boolean deleteCla2UserByUserId(@Param("userIdsArr") String[] userId);

    /**
     * 删除用户与班级的关系
     * @param userId    用户id
     * @param classId   班级id
     * @return
     */
    boolean deleteClass2User(@Param("userId") int userId,@Param("classId") int classId);

    /**
     * 根据用户id查询用户所在的班级id集合
     * @param userId
     * @return
     */
    List<String> searchClassListByUId(@Param("userId") int userId);

    /**
     * 批量保存用户
     * @param userList
     * @return
     */
    boolean addBatchUsers(List<Map<String,Object>> userList);
}

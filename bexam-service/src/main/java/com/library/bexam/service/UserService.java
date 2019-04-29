package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 用户业务处理层
 * @author  caoqian
 * @date 20181213
 */
public interface UserService {
    /**
     * 添加用户
     * @param userEntity
     * @return
     */
    Result addUser(UserEntity userEntity);

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    Result searchUserById(int userId);

    /**
     * 分页查询用户信息
     * @param type
     * @param search
     * @param page
     * @param pageSize
     * @return
     */
    Result getUserListByPage(String type, String search, int page, int pageSize);

    /**
     * 删除用户信息
     * @param userEntity
     * @return
     */
    Result deleteUserByIds(Map<String,String> userEntity);

    /**
     * 修改用户信息
     * @param userEntity
     * @return
     */
    Result updateUserInfo(UserEntity userEntity);

    /**
     * 修改用户密码
     * @param userEntity
     * @return
     */
    Result updatePwd(Map<String,Object> userEntity);

    /**
     * 用户登录
     * @param userEntity
     * @return
     */
    Result login(UserEntity userEntity);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    Result getUserByToken(String token);

    /**
     * 分配老师到班级
     * @param params
     * @return
     */
    Result allotUserToClass(Map<String, Object> params);

    /**
     * 根据班级id查询用户信息
     * @param classId  班级id
     * @return
     */
    Result searchUsersByClassId(int classId);

    /**
     * 删除班级与用户的关系
     * @param userId
     * @param classId
     * @return
     */
    Result deleteClass2User(int userId,int classId);

    /**
     * 下载用户模板
     * @return
     */
    Result downLoadUserTemplate(HttpServletResponse res);

    /**
     * 下载错误模板
     * @param fileName 文件名
     * @param res
     * @return
     */
    Result downLoadErrorUserTemplate(String fileName,HttpServletResponse res);

    /**
     * 导入用户模板
     * @param fileName
     * @author caoqian
     * @return
     */
    Result importUsers(String fileName);
}

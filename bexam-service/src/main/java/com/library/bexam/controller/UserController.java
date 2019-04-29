package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.UserEntity;
import com.library.bexam.service.ClassService;
import com.library.bexam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 用户处理表现层
 * @author caoqina
 * @date 20181213
 */
@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;
    /**
     * 用户登录
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Result login(@RequestBody UserEntity userEntity){
        return userService.login(userEntity);
    }
    /**
     * 新增用户
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Result addUser(@RequestBody UserEntity userEntity){
       return userService.addUser(userEntity);
    }

    /**
     * 根据用户id查询用户信息
     * @param userId  用户id
     * @return
     */
    @RequestMapping(value = "getUserById",method = RequestMethod.GET)
    public Result getUserById(int userId){
        return userService.searchUserById(userId);
    }

    /**
     * 分页查询用户信息，支持模糊查询
     * @param type       用户类型，传空时，查询全部；1为查询管理员；2为查询教师
     * @param search     用户名或手机号，可为空
     * @param page       页数
     * @param pageSize   每页条数
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Result getUserListByPage(String type,String search,int page,int pageSize){
        return userService.getUserListByPage(type,search,page,pageSize);
    }

    /**
     * 根据用户ids删除用户信息，支持批量删除（逻辑删除）
     * @param params
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public Result deleteUserByIds(@RequestBody Map<String,String> params){
        return userService.deleteUserByIds(params);
    }

    /**
     * 修改用户信息
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Result updateUser(@RequestBody UserEntity userEntity){
        return userService.updateUserInfo(userEntity);
    }

    /**
     * 修改密码
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "updatePwd",method = RequestMethod.POST)
    public Result updatePwd(@RequestBody Map<String,Object> userEntity){
        return userService.updatePwd(userEntity);
    }

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    @RequestMapping(value = "getUserByToken",method = RequestMethod.GET)
    public Result getUserByToken(String token){
        return userService.getUserByToken(token);
    }

    /**
     * 给用户分配班级
     * @param params  {"userId":1,"classId":"1,2,3"}
     * @return
     */
    @RequestMapping(value="allotClassToUser",method = RequestMethod.POST)
    public Result allotClassToUser(@RequestBody Map<String,Object> params){
        return classService.allotClassToUser(params);
    }

    /**
     * 根据班级id查询用户信息
     * @param classId  班级id
     * @return
     */
    @RequestMapping(value = "searchUsersByClassId",method = RequestMethod.GET)
    public Result searchUsersByClassId(int classId){
        return userService.searchUsersByClassId(classId);
    }

    /**
     * 删除班级与用户的关系
     * @param userId
     * @param classId
     * @return
     */
    @RequestMapping(value = "deleteClass2User",method = RequestMethod.GET)
    public Result deleteClass2User(int userId,int classId){
        return userService.deleteClass2User(userId,classId);
    }

    /**
     * 下载用户模板
     * @author caoqian
     * @date 20180106
     * @return
     */
    @RequestMapping(value = "downLoadUserTemplate",method = RequestMethod.GET)
    public Result downLoadUserTemplate(HttpServletResponse res){
        return userService.downLoadUserTemplate(res);
    }

    /**
     * 下载错误模板
     * @param fileName
     * @param res
     * @return
     */
    @RequestMapping(value = "downLoadErrorUserTemplate",method = RequestMethod.GET)
    public Result downLoadErrorUserTemplate(String fileName,HttpServletResponse res){
        return userService.downLoadErrorUserTemplate(fileName,res);
    }

    /**
     * 导入用户模板
     * @param fileName 文件名
     * @author caoqian
     * @date 20180106
     * @return
     */
    @RequestMapping(value = "importUsers",method = RequestMethod.GET)
    public Result importUsers(String fileName){
        return userService.importUsers(fileName);
    }

}

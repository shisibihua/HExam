package com.library.bexam.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.library.bexam.common.pojo.model.Page;
import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.common.util.*;
import com.library.bexam.common.util.DateUtil;
import com.library.bexam.dao.ClassDao;
import com.library.bexam.dao.SchoolDao;
import com.library.bexam.dao.UserDao;
import com.library.bexam.entity.SchoolEntity;
import com.library.bexam.entity.UserEntity;
import com.library.bexam.form.ClassForm;
import com.library.bexam.form.GradeForm;
import com.library.bexam.service.UserService;
import com.library.bexam.util.ConvertResult;
import com.library.bexam.util.RedisUtil;
import jodd.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private SchoolDao schoolDao;
    @Autowired
    private ClassDao classDao;
    private final static String DEFAULT_USER_PWD="123456";
    private final static String USER_FILE_NAME="user_template.xls";

    @Override
    public Result addUser(UserEntity userEntity) {
        if(userEntity==null ){
            return ConvertResult.getParamErrorResult("用户信息不能为空");
        }
        if(StringUtil.isEmpty(userEntity.getUserName())){
            return ConvertResult.getParamErrorResult("用户名不能为空");
        }
        if(userEntity.getType()==0){
            return ConvertResult.getParamErrorResult("用户类型不能为空");
        }
        if (!StringUtil.isEmpty(userEntity.getMobile()) && !ValidatorUtil.isMobile(userEntity.getMobile()) &&
                !ValidatorUtil.checkTelephone(userEntity.getMobile())) {
            return ConvertResult.getParamErrorResult("手机号格式非法");
        }
        //验证是否存在用户
        int exitUser=userDao.exitUser(userEntity);
        if(exitUser>0){
            Result errorResult=new Result(TipsMessage.FAILED_CODE);
            return ConvertResult.getErrorResult("用户名或手机号重复",errorResult);
        }
        try {
            if(StringUtil.isEmpty(userEntity.getUserPwd())){
                userEntity.setUserPwd(MD5Util.MD5(DEFAULT_USER_PWD));
            }
            userEntity.setStatus(0);
            userEntity.setCreateTime(DateUtil.currentDatetime());
            boolean result = userDao.addUser(userEntity);
            if(result){
                List<ClassForm> classFormList=userEntity.getClassList();
                if(classFormList!=null && !classFormList.isEmpty()){
                    //分配班级给用户
                    classDao.allotClassToUser(classFormList,userEntity.getUserId());
                }
            }
            return ConvertResult.getSuccessResult(result);
        }catch (Exception e){
            logger.error("保存用户信息异常");
            return ConvertResult.getErrorResult("保存用户异常！");
        }
    }

    @Override
    public Result searchUserById(int userId) {
        if(userId==0){
            return ConvertResult.getParamErrorResult("用户id不能为空");
        }
        try {
            UserEntity userEntity = userDao.searchUserById(userId);
            if(userEntity!=null){
                userEntity.setSubjectName(userEntity.getSubjectEntity()==null?"":userEntity.getSubjectEntity().getSubjectName());
                userEntity.setPeriodName(userEntity.getPeriodEntity()==null?"":userEntity.getPeriodEntity().getPeriodName());
                return ConvertResult.getSuccessResult(userEntity);
            }else{
                return ConvertResult.getErrorResult("该用户不存在");
            }
        }catch (Exception e){
            logger.error("根据用户id查询用户信息异常,userId="+userId,e);
            return ConvertResult.getErrorResult("查询用户信息异常");
        }
    }

    @Override
    public Result getUserListByPage(String type, String search, int page, int pageSize) {
        if(page==0 || pageSize==0){
            return ConvertResult.getParamErrorResult("参数错误");
        }
        int firstIndex=(page-1)*pageSize;
        try {
            List<UserEntity> searchUserList = userDao.searchUserList(type, search, firstIndex, pageSize);
            for(UserEntity user:searchUserList){
                user.setSubjectName(user.getSubjectEntity()==null?"":user.getSubjectEntity().getSubjectName());
                user.setPeriodName(user.getPeriodEntity()==null?"":user.getPeriodEntity().getPeriodName());
                List<ClassForm> classFormList=user.getClassList();
                if(classFormList!=null && !classFormList.isEmpty()){
                    for(ClassForm cla:classFormList){
                        GradeForm gradeForm=cla.getGradeEntity();
                        if(gradeForm!=null){
                            cla.setGradeId(gradeForm.getGradeId());
                            cla.setGradeName(gradeForm.getGradeName());
                        }
                    }
                }
            }
            int totalCount = userDao.getUserTotalCount();
            Page userPage = new Page(searchUserList, page, pageSize, totalCount);
            return ConvertResult.getSuccessResult(userPage);
        }catch (Exception e){
            logger.error("分页查询用户信息异常",e);
            return ConvertResult.getErrorResult("分页查询用户信息异常");
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result deleteUserByIds(Map<String,String> userEntity) {
        if(userEntity==null || userEntity.isEmpty() || !userEntity.containsKey("userId") ||
                StringUtil.isEmpty(userEntity.get("userId"))){
            return ConvertResult.getParamErrorResult("用户ids不能为空");
        }
        String userIds=userEntity.get("userId");
        boolean result = userDao.deleteUser(userIds.split(","));
        if(result){
            //删除用户与班级的关系
            userDao.deleteCla2UserByUserId(userIds.split(","));
        }
        return ConvertResult.getSuccessResult(result);
    }

    @Override
    public Result updateUserInfo(UserEntity userEntity) {
        if(userEntity==null || userEntity.getUserId()==0){
            return ConvertResult.getParamErrorResult("用户id不能为空");
        }else if(!StringUtil.isEmpty(userEntity.getMobile()) && !ValidatorUtil.isMobile(userEntity.getMobile()) &&
                !ValidatorUtil.checkTelephone(userEntity.getMobile())){
            return ConvertResult.getParamErrorResult("手机号格式非法");
        }else{
            try {
                UserEntity exitUser = userDao.searchUser(userEntity);
                if (exitUser != null && exitUser.getUserId() != userEntity.getUserId()) {
                    return ConvertResult.getErrorResult("用户名或手机号重复");
                } else {
                    return ConvertResult.getSuccessResult(userDao.updateUser(userEntity));
                }
            }catch (Exception e){
                logger.error("修改用户信息异常",e);
                return ConvertResult.getErrorResult("修改用户信息异常");
            }
        }
    }

    @Override
    public Result updatePwd(Map<String,Object>  userEntity){
        if(userEntity==null || userEntity.isEmpty() || !userEntity.containsKey("userId") ||
                StringUtil.isEmpty(userEntity.get("oldPwd").toString()) || StringUtil.isEmpty(userEntity.get("newPwd").toString())){
            return ConvertResult.getParamErrorResult("用户id或密码不能为空");
        }
        int userId=Integer.parseInt(String.valueOf(userEntity.get("userId")));
        UserEntity oldUser=userDao.searchUserById(userId);
        if(oldUser!=null) {
            //数据库原密码
            String oldPwd = oldUser.getUserPwd();
            //输入的旧密码
            String oldPwd1 = userEntity.get("oldPwd").toString();
            //输入的新密码
            String newPwd = userEntity.get("newPwd").toString();
            if (oldPwd.equals(oldPwd1)) {
                UserEntity user = new UserEntity();
                user.setUserId(userId);
                user.setUserPwd(newPwd);
                return ConvertResult.getSuccessResult(userDao.updateUser(user));
            } else {
                return ConvertResult.getParamErrorResult("旧密码输入不正确");
            }
        }else {
            return ConvertResult.getParamErrorResult("用户不存在");
        }
    }

    @Override
    public Result login(UserEntity userEntity) {
        if(userEntity==null || StringUtil.isEmpty(userEntity.getUserName()) || StringUtil.isEmpty(userEntity.getUserPwd())){
            return ConvertResult.getParamErrorResult("用户信息不能为空");
        }
        UserEntity user=userDao.searchUserByName(userEntity.getUserName());
        if(user!=null) {
            //数据库密码
            String oldPwd = user.getUserPwd();
            //输入的密码
            String userPwd=user.getUserPwd();
            if(oldPwd.equals(userPwd)){
                //获取token
                String userToken = IDUtil.getToken("user", user.getUserName());
                if (userToken == null) {
                    return ConvertResult.getErrorResult("token生成失败");
                }else{
                    try {
                        user.setToken(userToken);
                        user.setSubjectName(user.getSubjectEntity()==null?"":user.getSubjectEntity().getSubjectName());
                        user.setPeriodName(user.getPeriodEntity()==null?"":user.getPeriodEntity().getPeriodName());
                        //token存入redis
                        if (!RedisUtil.setString(userToken, RedisUtil.KEYTIMEOUT, JSON.toJSONString(user))) {
                            return ConvertResult.getErrorResult("redis保存token失败");
                        }
                    }catch (Exception e){
                        logger.error("redis操作异常!",e);
                        return ConvertResult.getErrorResult("redis保存token失败");
                    }
                }
                return ConvertResult.getSuccessResult(user);
            }else{
                Result errorResult=new Result(Result.Code.UnKnowError.value());
                return ConvertResult.getErrorResult("登录失败",errorResult);
            }
        }else{
            return ConvertResult.getParamErrorResult("用户不存在");
        }
    }

    @Override
    public Result getUserByToken(String token) {
        /*if(StringUtil.isEmpty(token)){
            return ConvertResult.getParamErrorResult("token不能为空");
        }*/
        String userInfo=RedisUtil.getString(token);
        if(!StringUtil.isEmpty(userInfo)) {
            JSONObject user=JSONObject.parseObject(userInfo);
            SchoolEntity schoolEntity=schoolDao.searchSchool();
            if(schoolEntity!=null){
                user.put("schoolId",schoolEntity.getSchoolId());
                user.put("schoolName",schoolEntity.getSchoolName());
            }else{
                Result errorResult=new Result(TipsMessage.FAILED_CODE);
                return ConvertResult.getErrorResult("没有学校信息",errorResult);
            }
            return ConvertResult.getSuccessResult(user);
        }else{
            Result errorResult=new Result(TipsMessage.FAILED_CODE);
            return ConvertResult.getErrorResult("用户不存在",errorResult);
        }
    }

    @Override
    public Result allotUserToClass(Map<String, Object> params) {
        if(params==null || params.isEmpty() || !params.containsKey("classId") || !params.containsKey("userId")){
            return ConvertResult.getParamErrorResult("班级id或用户id不能为空");
        }
        int classId=(int)params.get("classId");
        String userId=(String)params.get("userId");
        return ConvertResult.getSuccessResult(userDao.allotUserToClass(classId,userId.split(",")));
    }

    @Override
    public Result searchUsersByClassId(int classId) {
        if(classId==0){
            return ConvertResult.getParamErrorResult("班级id不能为空");
        }
        List<UserEntity> userList=userDao.searchUsersByClassId(classId);
        if(userList!=null && !userList.isEmpty()){
            for(UserEntity user:userList){
                user.setSubjectName(user.getSubjectEntity()==null?"":user.getSubjectEntity().getSubjectName());
                user.setPeriodName(user.getPeriodEntity()==null?"":user.getPeriodEntity().getPeriodName());
            }
        }
        return ConvertResult.getSuccessResult(userList);
    }

    @Override
    public Result deleteClass2User(int userId,int classId) {
        if(classId==0 || userId==0){
            return ConvertResult.getParamErrorResult("用户id或班级id不能为空");
        }
        return ConvertResult.getSuccessResult(userDao.deleteClass2User(userId,classId));
    }

    @Override
    public Result downLoadUserTemplate(HttpServletResponse res) {
        String path=PathUtil.getPath(PathUtil.PathType.TEMPLATE);
        return ConvertResult.getSuccessResult(ExcelToolUtil.getInstance().downLoadFile(path,USER_FILE_NAME,res));
    }

    @Override
    public Result downLoadErrorUserTemplate(String fileName,HttpServletResponse res) {
        String path=PathUtil.getPath(PathUtil.PathType.UPLOAD);
        return ConvertResult.getSuccessResult(ExcelToolUtil.getInstance().downLoadFile(path,fileName,res));
    }

    @Override
    public Result importUsers(String fileName) {
        if(StringUtil.isEmpty(fileName)){
            return ConvertResult.getParamErrorResult("文件名不能为空");
        }
        return ConvertResult.getSuccessResult(importUserTemplate(fileName));
    }

    /**
     * 导入用户模板
     * @param fileName  文件名
     * @return
     */
    private String importUserTemplate(String fileName) {
        InputStream stream = null;
        Workbook workbook = null;
        String filePath=System.getProperty("user.dir")+File.separator+PathUtil.PathType.UPLOAD.value()+File.separator+fileName;
        File file=new File(filePath);
        if(!file.exists()){
            return "upload file failed , file is empty";
        }
        String result="";
        try {
            // 文件流指向excel文件
            stream = new FileInputStream(file);
            //HSSF只能打开2003，XSSF只能打开2007，WorkbookFactory.create兼容以上两个版本
            workbook = WorkbookFactory.create(stream);

            //设置CELL格式为文本格式 防止科学计数问题
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("@"));
            Sheet sheet = workbook.getSheetAt(0);// 得到工作表
            Map<String,Object> addBatchUserResult=importHandler(sheet);
            if(addBatchUserResult!=null && !addBatchUserResult.isEmpty()) {
                List<Map<String,Object>> failedResult=(List<Map<String,Object>>)addBatchUserResult.get("failedList");
                if(failedResult!=null && !failedResult.isEmpty()) {
                    return exportExcel(failedResult);
                }else{
                    //导入成功时删除模板文件
                    FileUtils.forceDelete(file);
                    result = "success";
                }
            }else{
                return "file is empty!!";
            }
        } catch (Exception e) {
            logger.error("导入用户模板异常",e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }
    /**
     * 将导入失败的数据回写到excel文件中
     *
     * @param errorDataList   插入数据库异常数据
     * @return String
     * @throws IllegalArgumentException
     */
    private String exportExcel(List<Map<String,Object>> errorDataList) throws IllegalArgumentException {
        // 生成表头
        String[] headers = {"姓名*", "用户名*", "用户类型","手机号","年龄","性别","授课科目","授课学段","错误原因"};
        String fileName = "错误用户列表";
        List<String[]> excelList = new ArrayList<>();
        // 插入数据
        for (Map<String,Object> data:errorDataList) {
            String[] strList = new String[headers.length];
            strList[0] = StringUtil.isEmpty(data.get("userRealName").toString()) ? "" : data.get("userRealName").toString();
            strList[1] = StringUtil.isEmpty(data.get("userName").toString())? "" : data.get("userName").toString();
            strList[2] = StringUtil.isEmpty(data.get("userType").toString()) ? "" : data.get("userType").toString();
            strList[3] = StringUtil.isEmpty(data.get("userMobile").toString())?"":data.get("userMobile").toString();
            strList[4] = StringUtil.isEmpty(data.get("userAge").toString())?"":data.get("userAge").toString();
            strList[5] = StringUtil.isEmpty(data.get("userGender").toString())?"":data.get("userGender").toString();
            strList[6] = StringUtil.isEmpty(data.get("subjectId").toString())?"":data.get("subjectId").toString();
            strList[7] = StringUtil.isEmpty(data.get("periodId").toString())?"":data.get("periodId").toString();
            strList[8] = StringUtil.isEmpty(data.get("errorMsg").toString())?"":data.get("errorMsg").toString();
            excelList.add(strList);
        }
        Date date = new Date();
        String filePath = System.getProperty("user.dir") + File.separator + "upload_dir"+ File.separator;
        String name = "user_failed_" + date.getTime();
        try {
            filePath = ExcelToolUtil.getInstance().exportTableByFile(fileName, headers, excelList, filePath, name)[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        //文件上传到upload_dir
        return name + ".xls";
    }

    /**
     * 导入excel表
     *
     */
    public Map<String,Object> importHandler(Sheet sheet) {
        Row row;// 对应excel的行
        int totalRow = sheet.getLastRowNum();// 得到excel的总记录条数
        Map<String,Object> resultMap=new HashMap<>();
        if(!checkExcelIsTrue(sheet.getRow(1))){
            return null;
        }
        List<Map<String,Object>> userList=new ArrayList<>();
        List<Map<String,Object>> errorMsgList=new ArrayList<>();
        for (int i = 2; i <= totalRow; i++) {
            row = sheet.getRow(i);//获取单行数据
            String userRealName = row.getCell(0) == null ? "" : row.getCell(0).toString();
            String userName = row.getCell(1) == null ? "" : row.getCell(1).toString();
            String userType = row.getCell(2) == null ? "1" : row.getCell(2).toString();
            String userMobile = row.getCell(3) == null ? "" : row.getCell(3).toString();
            String age = row.getCell(4) == null ? null : row.getCell(4).toString();
            String gender = row.getCell(5) == null ? null : row.getCell(5).toString();
            String subject=row.getCell(6) == null ? null : row.getCell(6).toString();
            String period=row.getCell(7) == null ? null : row.getCell(7).toString();
            //处理Excel空行
            if ("".equals(userRealName) && "".equals(userName)) {
                continue;
            }
            Map<String,Object> user=new HashMap<>();
            user.put("userRealName",userRealName);
            user.put("userName",userName);
            user.put("userPwd",MD5Util.MD5(DEFAULT_USER_PWD));
            user.put("status",0);
            if(userType.endsWith(".0")) {
                userType=userType.substring(0,userType.lastIndexOf("."));
            }
            user.put("type",userType);
            user.put("mobile",userMobile);
            if(!StringUtil.isEmpty(age)) {
                if(age.endsWith(".0")){
                    age=age.substring(0,age.lastIndexOf("."));
                }
                user.put("age",age);
            }
            if(!StringUtil.isEmpty(gender)){
                if(gender.endsWith(".0")){
                    gender=gender.substring(0,gender.lastIndexOf("."));
                }
                user.put("gender",gender);
            }
            if(!StringUtil.isEmpty(subject)){
                if(subject.endsWith(".0")){
                    subject=subject.substring(0,subject.lastIndexOf("."));
                }
                user.put("subjectId",subject);
            }
            if(!StringUtil.isEmpty(period)){
                if(period.endsWith(".0")){
                    period=period.substring(0,period.lastIndexOf("."));
                }
                user.put("periodId",period);
            }
            user.put("createTime",DateUtil.currentDatetime());
            String errorMsg=checkUserIsValid(user);
            if(!StringUtil.isEmpty(errorMsg)){
                Map<String,Object> errorMsgMap=new HashMap<>();
                errorMsgMap.put("userRealName",userRealName);
                errorMsgMap.put("userName",userName);
                errorMsgMap.put("userType",userType);
                errorMsgMap.put("userMobile",userMobile);
                errorMsgMap.put("userAge",age);
                errorMsgMap.put("userGender",gender);
                errorMsgMap.put("subjectId",subject);
                errorMsgMap.put("periodId",period);
                errorMsgMap.put("errorMsg",errorMsg);
                errorMsgList.add(errorMsgMap);
                resultMap.put("failedList",errorMsgList);
                continue;
            }else{
                userList.add(user);
            }
        }
        if(userList!=null && !userList.isEmpty()){
            resultMap= addBatchUsers(userList,resultMap);
        }
        return resultMap;
    }

    /**
     * 批量保存用户
     * @param userList
     * @param resultMap
     * @return
     */
    private Map<String,Object> addBatchUsers(List<Map<String,Object>> userList, Map<String, Object> resultMap) {
        boolean result = userDao.addBatchUsers(userList);
        resultMap.put("addBatchUsers",result);
        return resultMap;
    }

    private final static String EXCEL_REAL_NAME_TITLE="姓名*";
    private final static String EXCEL_NAME_TITLE="用户名*";
    private final static String EXCEL_TYPE_TITLE="用户类型*";
    private final static String EXCEL_MOBILE_TITLE="手机号";
    private final static String EXCEL_AGE_TITLE="年龄";
    private final static String EXCEL_GENDER_TITLE="性别";
    private final static String EXCEL_SUBJECT_TITLE="授课科目";
    private final static String EXCEL_PERIOD_TITLE="授课学段";
    /**
     * 验证模板是否正确
     * @param row
     * @return
     */
    private boolean checkExcelIsTrue(Row row) {
        String userRealNameTitle = row.getCell(0).toString();
        String userNameTitle = row.getCell(1).toString();
        String userTypeTitle = row.getCell(2).toString();
        String userMobileTitle = row.getCell(3).toString();
        String userAgeTitle = row.getCell(4).toString();
        String userGenderTitle = row.getCell(5).toString();
        String subjectTitle = row.getCell(6).toString();
        String periodTitle = row.getCell(7).toString();
        if(EXCEL_REAL_NAME_TITLE.equals(userRealNameTitle) && EXCEL_NAME_TITLE.equals(userNameTitle)
                && EXCEL_TYPE_TITLE.equals(userTypeTitle) && EXCEL_MOBILE_TITLE.equals(userMobileTitle)
                && EXCEL_AGE_TITLE.equals(userAgeTitle) && EXCEL_GENDER_TITLE.equals(userGenderTitle)
                && EXCEL_SUBJECT_TITLE.equals(subjectTitle) && EXCEL_PERIOD_TITLE.equals(periodTitle)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 验证数据是否合法
     * @param user  用户信息
     * @return
     */
    private String checkUserIsValid(Map<String,Object> user) {
        StringBuffer stringBuffer=new StringBuffer();
        if(userDao.exitUserMap(user)>0){
            stringBuffer.append("用户已存在,");
        }else {
            if (user.get("userRealName").toString().length() > 10) {
                stringBuffer.append("用户姓名超出长度限制,");
            } else {
                if (!matchAreaInfo(user.get("userRealName").toString())) {
                    stringBuffer.append("用户姓名格式错误,包含特殊字符串,");
                }
            }
            if (user.get("userName").toString().length() > 15) {
                stringBuffer.append("用户名超出长度限制,");
            }
            if(!StringUtil.isEmpty(user.get("type").toString()) && !ValidatorUtil.isIntNum(user.get("type").toString())){
                stringBuffer.append("用户类型格式错误,");
            }
            if (!StringUtil.isEmpty(user.get("mobile").toString()) && !ValidatorUtil.isMobile(user.get("mobile").toString())) {
                stringBuffer.append("手机号码格式错误,");
            }
            if(!StringUtil.isEmpty(user.get("age").toString()) && !ValidatorUtil.isIntNum(user.get("age").toString())){
                stringBuffer.append("用户年龄格式错误,");
            }
            if(!StringUtil.isEmpty(user.get("gender").toString()) && !ValidatorUtil.isIntNum(user.get("gender").toString())){
                stringBuffer.append("用户性别格式错误,");
            }
            if(!StringUtil.isEmpty(user.get("subjectId").toString()) && !ValidatorUtil.isIntNum(user.get("subjectId").toString())){
                stringBuffer.append("授课科目格式错误,");
            }
            if(!StringUtil.isEmpty(user.get("periodId").toString()) && !ValidatorUtil.isIntNum(user.get("periodId").toString())){
                stringBuffer.append("授课学段格式错误,");
            }
        }
        String result=stringBuffer.toString();
        if(result.endsWith(",")){
            result=result.substring(0,result.lastIndexOf(","));
        }
        return result;
    }
    private boolean matchAreaInfo(String areaInfo){
        //验证名称是否合法=============================================================
        Pattern regex = Pattern.compile("^[\\u4e00-\\u9fa5_a-zA-Z0-9]{1,15}");
        Matcher matcher;
        matcher = regex.matcher(areaInfo);
        return matcher.matches();
    }


}

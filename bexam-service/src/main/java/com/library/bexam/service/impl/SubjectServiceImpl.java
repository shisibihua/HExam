package com.library.bexam.service.impl;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.common.util.DateUtil;
import com.library.bexam.common.util.TipsMessage;
import com.library.bexam.dao.NodeBookDao;
import com.library.bexam.dao.SubjectDao;
import com.library.bexam.dao.TextBookDao;
import com.library.bexam.dao.VersionDao;
import com.library.bexam.entity.NodeBookEntity;
import com.library.bexam.entity.SubjectEntity;
import com.library.bexam.entity.TextBookEntity;
import com.library.bexam.entity.VersionEntity;
import com.library.bexam.form.PeriodForm;
import com.library.bexam.service.NodeBookService;
import com.library.bexam.service.SubjectService;
import com.library.bexam.util.ConvertResult;
import jodd.typeconverter.Convert;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SubjectServiceImpl implements SubjectService {
    private Logger logger= LoggerFactory.getLogger(SubjectServiceImpl.class);
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private VersionDao versionDao;
    @Autowired
    private TextBookDao bookDao;
    @Autowired
    private NodeBookDao nodeDao;
    @Autowired
    private NodeBookService nodeService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result addSubject(SubjectEntity subjectEntity) {
        if(subjectEntity==null || StringUtil.isEmpty(subjectEntity.getSubjectName()) || subjectEntity.getPeriodId()==0){
            return ConvertResult.getParamErrorResult("科目名称或学段id不能为空");
        }
        subjectEntity.setCreateTime(DateUtil.currentDatetime());
        //保存科目
        boolean result=subjectDao.addSubject(subjectEntity);
        if(result){
            List<SubjectEntity> subjectList=new ArrayList<>();
            subjectList.add(subjectEntity);
            subjectDao.addPeriod2Subject(subjectList);
            return ConvertResult.getSuccessResult(result);
        }else{
            Result errorResult=new Result(TipsMessage.FAILED_CODE);
            return ConvertResult.getErrorResult("保存科目失败",errorResult);
        }
    }

    @Override
    public Result searchSubjectById(int subjectId) {
        if(subjectId==0){
            return ConvertResult.getParamErrorResult("科目id不能为空");
        }
        SubjectEntity subjectEntity=subjectDao.searchSubjectById(subjectId);
        if(subjectEntity!=null) {
            subjectEntity.setPeriodName(subjectEntity.getPeriodEntity()==null?"":subjectEntity.getPeriodEntity().getPeriodName());
            subjectEntity.setPeriodId(subjectEntity.getPeriodEntity()==null?0:subjectEntity.getPeriodEntity().getPeriodId());
        }
        return ConvertResult.getSuccessResult(subjectEntity);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result deleteSubjectByIds(Map<String, String> params) {
        if(params==null || params.isEmpty() || !params.containsKey("subjectId") || StringUtil.isEmpty(params.get("subjectId"))){
            return ConvertResult.getParamErrorResult("科目ids不能为空");
        }
        String subjectId=params.get("subjectId");
        try {
            String[] subjectIdArr=subjectId.split(",");
            boolean result=subjectDao.deleteSubjectById(subjectIdArr);
            if(result){
                //删除学段与科目的关系
                subjectDao.deletePeriod2SubjectBySubjectIds(subjectIdArr);
                return ConvertResult.getSuccessResult(result);
            }else{
                Result errorResult=new Result(TipsMessage.FAILED_CODE);
                return ConvertResult.getErrorResult("删除学段失败",errorResult);
            }
        }catch (Exception e){
            logger.error("根据科目ids删除科目信息异常,subjectIds="+subjectId,e);
            return ConvertResult.getErrorResult("根据科目ids删除科目信息失败");
        }
    }

    @Override
    public Result updateSubjectInfo(SubjectEntity subjectEntity) {
        if(subjectEntity==null || subjectEntity.getSubjectId()==0){
            return ConvertResult.getParamErrorResult("科目信息不能为空");
        }
        return ConvertResult.getSuccessResult(subjectDao.updateSubject(subjectEntity));
    }

    @Override
    public Result searchSubjectList(int periodId) {
        List<SubjectEntity> subjectList=new ArrayList<>();
        try {
            subjectList = subjectDao.searchSubjectListByPeriodId(periodId);
            if (subjectList != null && !subjectList.isEmpty()) {
                for (SubjectEntity subject : subjectList) {
                    PeriodForm period = subject.getPeriodEntity();
                    if(period!=null) {
                        subject.setPeriodId(period.getPeriodId());
                        subject.setPeriodName(period.getPeriodName());
                    }
                }
            }
        }catch (Exception e){
            logger.error("根据学段查询学科异常，periodId="+periodId,e);
        }
        Map<String,Object> result=new HashMap<>();
        result.put("subject",subjectList);
        result.put("totalCount",subjectList.size());
        return ConvertResult.getSuccessResult(result);
    }

    /**
     * 根据科目id查询教材版本、册别、章节信息
     * @param subjectId
     * @author caoqian
     * @return
     */
    @Override
    public Result searchExamList(int subjectId) {
        if(subjectId==0){
            return ConvertResult.getParamErrorResult("科目id不能为空");
        }
        Map<String,Object> result=new LinkedHashMap<>();
        SubjectEntity subject=subjectDao.searchSubjectById(subjectId);
        if(subject!=null) {
            subject.setPeriodId(subject.getPeriodEntity()==null?0:subject.getPeriodEntity().getPeriodId());
            subject.setPeriodName(subject.getPeriodEntity()==null?"":subject.getPeriodEntity().getPeriodName());
            result.put("subject", subject);
            List<VersionEntity> versionList = versionDao.getVersionListBySubId(subjectId);
            if (versionList != null && !versionList.isEmpty()) {
                result.put("version", versionList);
                VersionEntity version = versionList.get(0);
                List<TextBookEntity> bookList = bookDao.searchTextBookListByVersionId(version.getVersionId());
                if (bookList != null && !bookList.isEmpty()) {
                    result.put("textBook", bookList);
                    TextBookEntity book = bookList.get(0);
                    List<NodeBookEntity> nodeList = nodeDao.searchNodeListByBookId(book.getTextBookId());
                    if (nodeList != null && !nodeList.isEmpty()) {
                        List<NodeBookEntity> allNodeList=nodeService.getAllNodeBook(nodeList);
                        result.put("node",allNodeList);
                    }
                }
            }
        }else{
            result.put("subject","");
            result.put("version","");
            result.put("textBook","");
            result.put("node","");
        }
        return ConvertResult.getSuccessResult(result);
    }
}

package com.library.bexam.dataacquisition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.Versioned;
import com.library.bexam.common.util.DateUtil;
import com.library.bexam.dao.*;
import com.library.bexam.entity.*;
import com.library.bexam.form.NodeBookForm;
import com.library.bexam.form.PointForm;
import com.library.bexam.service.QuestionService;
import com.library.bexam.service.QuestionTypeService;
import com.library.bexam.util.DocCenterApi;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 获取学科网数据类
 * @author caoqian
 * @date 20181219
 */
@Component
public class ExamDataAcquistion {
    @Autowired
    private PeriodDao periodDao;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private VersionDao versionDao;
    @Autowired
    private TextBookDao textBookDao;
    @Autowired
    private NodeBookDao nodeBookDao;
    @Autowired
    private PointDao pointDao;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionTypeService questionTypeService;

    /**
     * 拉取学科网科目信息
     */
    public boolean getSubjects(){
        List<PeriodEntity> periodList=periodDao.searchPeriodList();
        boolean re_value=false;
        if(periodList!=null && !periodList.isEmpty()){
            List<SubjectEntity> subjectList=new ArrayList<>();
            for(PeriodEntity period:periodList){
                int periodId=period.getPeriodId();
                Map<String,Object> params=new HashMap<>();
                params.put("stage",periodId);
                JSONObject result=DocCenterApi.getInstance().searchApiDoc("getSubjectList",params);
                if(checkApiResult(result)){
                    JSONArray jsonArray=JSONArray.parseArray(result.get("data").toString());
                    for(Object data:jsonArray){
                        JSONObject json=JSONObject.parseObject(data.toString());
                        SubjectEntity subject=new SubjectEntity();
                        subject.setSubjectId(Integer.parseInt(String.valueOf(json.get("id"))));
                        subject.setSubjectName(json.get("subjectName").toString());
                        subject.setCreateTime(DateUtil.currentDatetime());
                        subject.setPeriodId(periodId);
                        subjectList.add(subject);
                    }
                }
            }
            if(subjectList!=null && !subjectList.isEmpty()) {
                subjectDao.addPeriod2Subject(subjectList);
                re_value=subjectDao.addSubjectList(subjectList);
            }
        }
        return re_value;
    }

    /**
     * 获取教材版本信息
     * @return
     */
    public boolean getVersions(){
        List<SubjectEntity> subjectList = subjectDao.searchSubjectListByPeriodId(0);
        boolean re_value=false;
        if(subjectList!=null && !subjectList.isEmpty()){
            List<VersionEntity> versionEntityList=new ArrayList<>();
            for(SubjectEntity subject:subjectList){
                Map<String,Object> params=new HashMap<>();
                params.put("subjectId",subject.getSubjectId());
                JSONObject resultJson=DocCenterApi.getInstance().searchApiDoc("getVersionList",params);
                if(checkApiResult(resultJson)){
                    JSONArray data=JSONArray.parseArray(resultJson.get("data").toString());
                    for(Object o:data){
                        JSONObject dataJson=JSONObject.parseObject(o.toString());
                        VersionEntity version=new VersionEntity();
                        version.setVersionId(Integer.parseInt(dataJson.get("id").toString()));
                        version.setVersionName(dataJson.get("versionName").toString());
                        version.setCreateTime(DateUtil.currentDatetime());
                        version.setSubjectId(subject.getSubjectId());
                        versionEntityList.add(version);
                    }
                }
            }
            if(versionEntityList!=null && !versionEntityList.isEmpty()){
                versionDao.addSubject2Version(versionEntityList);
                re_value=versionDao.addVersionList(versionEntityList);
            }
        }
        return re_value;
    }

    /**
     * 获取教材册别信息
     * @return
     */
    public boolean getTextBooks(){
        List<VersionEntity> versionList = versionDao.searchVersionList();
        boolean re_value=false;
        if(versionList!=null && !versionList.isEmpty()){
            List<TextBookEntity> textBookEntityList=new ArrayList<>();
            for(VersionEntity version:versionList){
                Map<String,Object> params=new HashMap<>();
                params.put("versionId",version.getVersionId());
                JSONObject resultJson=DocCenterApi.getInstance().searchApiDoc("getTextbookList",params);
                if(checkApiResult(resultJson)){
                    JSONArray data=JSONArray.parseArray(resultJson.get("data").toString());
                    for(Object o:data){
                        JSONObject dataJson=JSONObject.parseObject(o.toString());
                        TextBookEntity textBook=new TextBookEntity();
                        textBook.setTextBookId(Integer.parseInt(String.valueOf(dataJson.get("id"))));
                        textBook.setTextBookName(dataJson.get("bookName").toString());
                        textBook.setVersionId(version.getVersionId());
                        textBook.setCreateTime(DateUtil.currentDatetime());
                        textBookEntityList.add(textBook);
                    }
                }
            }
            if(textBookEntityList!=null && !textBookEntityList.isEmpty()){
                textBookDao.addVersion2TextBook(textBookEntityList);
                re_value=textBookDao.addTextBookList(textBookEntityList);
            }
        }
        return re_value;
    }
    /**
     * 获取教材章节信息
     * @return
     */
    public boolean getNodeBooks(Map<String,String> periodIds){
        String[] periodIdArr=periodIds.get("periodIds").split(",");
        //根据学段查询教材章节
        List<TextBookEntity> textBookList = textBookDao.searchTextBookList(periodIdArr);
        boolean re_value=false;
        if(textBookList!=null && !textBookList.isEmpty()){
            List<NodeBookForm> nodeBookEntityList=new ArrayList<>();
            for(TextBookEntity textBook:textBookList){
                Map<String,Object> params=new HashMap<>();
                int textBookId=textBook.getTextBookId();
                params.put("textbookId",textBookId);
                JSONObject resultJson=DocCenterApi.getInstance().searchApiDoc("getBookNodeList",params);
                if(checkApiResult(resultJson)){
                    JSONArray data=JSONArray.parseArray(resultJson.get("data").toString());
                    getNodeList(textBookId,0, data,nodeBookEntityList);
                }
            }
            if(nodeBookEntityList!=null && !nodeBookEntityList.isEmpty()){
                List<NodeBookForm> childNodeList=new ArrayList<>();
                for(NodeBookForm childNode:nodeBookEntityList){
                    if(childNode.getTextBookId()!=0){
                        childNodeList.add(childNode);
                    }
                }
                boolean addBook2Node=false;
                if(!childNodeList.isEmpty()) {
                    addBook2Node=nodeBookDao.addTextBook2NodeBook(childNodeList);
                }
                boolean addNode=nodeBookDao.addNodeBookList(nodeBookEntityList);
                re_value=addBook2Node && addNode;
            }
        }
        return re_value;
    }
    private List<NodeBookForm> getNodeList(int textBookId,int parentId,JSONArray data,List<NodeBookForm> nodeBookEntityList){
        if(data!=null && !data.isEmpty()){
            for(Object nodes:data){
                JSONObject nodeJson=JSONObject.parseObject(nodes.toString());
                NodeBookForm nodeBook=new NodeBookForm();
                nodeBook.setNodeId(nodeJson.getIntValue("bookNodeId"));
                nodeBook.setNodeName(nodeJson.getString("bookNodeName"));
                nodeBook.setParentId(parentId);
                nodeBook.setTextBookId(textBookId);
                nodeBook.setCreateTime(DateUtil.currentDatetime());
                nodeBookEntityList.add(nodeBook);
                JSONArray childNodeJsonArr=JSONArray.parseArray(nodeJson.getString("childrens"));
                if(childNodeJsonArr!=null && !childNodeJsonArr.isEmpty()){
                    getNodeList(0,nodeBook.getNodeId(),childNodeJsonArr,nodeBookEntityList);
                }
            }
        }
        return nodeBookEntityList;
    }
    /**
     * 获取知识点
     * @return
     */
    public boolean getPoints(int subjectId) {
        //根据学段查询知识点
        boolean re_value=false;
        List<PointForm> pointEntityList=new ArrayList<>();
        Map<String,Object> params=new HashMap<>();
        params.put("subjectId",subjectId);
        JSONObject resultJson=DocCenterApi.getInstance().searchApiDoc("getKnowledgePointList",params);
        if(checkApiResult(resultJson)){
            JSONArray data=JSONArray.parseArray(resultJson.get("data").toString());
            getPointList(subjectId,data,pointEntityList);
        }
        if(pointEntityList!=null && !pointEntityList.isEmpty()){
            List<PointForm> childPointList=new ArrayList<>();
            for(PointForm childPoint:pointEntityList) {
                if(childPoint.getSubjectId()!=0){
                    childPointList.add(childPoint);
                }
            }
            boolean addSub2point=false;
            if(!childPointList.isEmpty()) {
                addSub2point = pointDao.addSubject2Point(childPointList);
            }
            boolean addPoint = pointDao.addPointList(pointEntityList);
            re_value=addSub2point && addPoint;
        }
        return re_value;
    }
    private List<PointForm>  getPointList (int subjectId,JSONArray data,List<PointForm> pointEntityList){
        for(Object o:data){
            JSONObject dataJson=JSONObject.parseObject(o.toString());
            PointForm point=new PointForm();
            point.setPointId(dataJson.getIntValue("id"));
            point.setPointName(dataJson.get("name").toString());
            point.setParentId(dataJson.getIntValue("parentId"));
            point.setSubjectId(subjectId);
            point.setCreateTime(DateUtil.currentDatetime());
            pointEntityList.add(point);
            JSONArray childrenJsonArr=JSONArray.parseArray(dataJson.get("childrens").toString());
            if(childrenJsonArr!=null && !childrenJsonArr.isEmpty()){
                getPointList(0,childrenJsonArr,pointEntityList);
            }
        }
        return pointEntityList;
    }
    /**
     * 根据教材章节获取试题列表(此接口不方便使用)
     */

    public boolean getChaptersList(Map<String, Object> params) {
        Map<String,Object> paramsMap=new HashMap<>();
        //科目id
        paramsMap.put("subjectId",Integer.parseInt(String.valueOf(params.get("subjectId"))));
        List<VersionEntity> versionList=versionDao.getVersionListBySubId((int)params.get("subjectId"));
        if(versionList!=null && !versionList.isEmpty()){
            for(VersionEntity version:versionList){
                //教材版本id
                paramsMap.put("versionId",version.getVersionId());
                List<TextBookEntity> bookList=textBookDao.searchTextBookListByVersionId(version.getVersionId());
                if(bookList!=null && !bookList.isEmpty()){
                    for(TextBookEntity book:bookList){
                        //教材册别id
                        paramsMap.put("textbookId",book.getTextBookId());
                        List<NodeBookEntity> nodeBookList=nodeBookDao.searchNodeListByBookId(book.getTextBookId());
                        if(nodeBookList!=null && !nodeBookList.isEmpty()){
                            for(NodeBookEntity node:nodeBookList){
                                //教材章节一级目录id
                                paramsMap.put("nodeId1",node.getNodeId());
                                List<NodeBookEntity> list=new ArrayList<>();
                                list.add(node);
                                List<NodeBookEntity> childList=nodeBookDao.searchChildNodeBookList(list);
                                if(childList!=null && !childList.isEmpty()){
                                    for(NodeBookEntity childNode:childList){
                                        //教材章节二级目录id
                                        paramsMap.put("nodeId2",childNode.getNodeId());
                                        List<NodeBookEntity> list2=new ArrayList<>();
                                        list2.add(childNode);
                                        List<NodeBookEntity> child2List=nodeBookDao.searchChildNodeBookList(list2);
                                        if(child2List!=null && !child2List.isEmpty()){
                                            for(NodeBookEntity child2Node:child2List){
                                                //教材章节三级目录id
                                                paramsMap.put("nodeId3",child2Node.getNodeId());
                                                //存放公共参数
                                                getCommonParamsMap(params, paramsMap);
                                                updateQuestionVersionAndBookAndNode(version, book, paramsMap);
                                            }
                                        }else{
                                            //存放公共参数
                                            getCommonParamsMap(params, paramsMap);
                                            updateQuestionVersionAndBookAndNode(version, book, paramsMap);
                                        }
                                    }
                                }else {
                                    //存放公共参数
                                    getCommonParamsMap(params, paramsMap);
                                    updateQuestionVersionAndBookAndNode(version, book, paramsMap);
                                }
                            }
                        }else {
                            //存放公共参数
                            getCommonParamsMap(params,paramsMap);
                            updateQuestionVersionAndBookAndNode(version,book,paramsMap);
                        }
                    }
                }
            }
        }
        return true;
    }
    private Map<String,Object> getCommonParamsMap(Map<String,Object> params,Map<String,Object> paramsMap){
        //试题难度
        if (params.containsKey("difficult")) {
            paramsMap.put("difficult", Integer.parseInt(String.valueOf(params.get("difficult"))));
        }
        //试题更新的年份
        if (params.containsKey("year")) {
            paramsMap.put("year", Integer.parseInt(String.valueOf(params.get("year"))));
        }
        paramsMap.put("startPage", Integer.parseInt(String.valueOf(params.get("startPage"))));
        paramsMap.put("countPerPage", Integer.parseInt(String.valueOf(params.get("countPerPage"))));
        return paramsMap;
    }
    private void updateQuestionVersionAndBookAndNode(VersionEntity version,TextBookEntity book,Map<String,Object> params) {
        JSONObject resultJson=DocCenterApi.getInstance().searchApiDoc("getSyncChapterQuestionList",params);
        int subjectId=(int)params.get("subjectId");
        if(checkApiResult(resultJson)){
//            System.out.println(">>>>>>>>>>>>>>>successResult========"+resultJson);
            JSONArray dataJsonArr=JSONArray.parseArray(resultJson.getString("data"));
            if(dataJsonArr!=null && !dataJsonArr.isEmpty()) {
                for(Object o:dataJsonArr) {
                    JSONObject json=JSONObject.parseObject(o.toString());
                    QuestionEntity questionEntity = new QuestionEntity();
                    questionEntity.setSubjectId(subjectId);
                    questionEntity.setQuestionTypeId(json.getIntValue("questionTypeId"));
                    questionEntity.setQuestionDifficult(json.getIntValue("difficult"));
                    questionEntity.setQuestionContent(json.getString("stem"));
                    questionEntity.setQuestionOption(json.getString("questionOptions"));
                    questionEntity.setQuestionSource(json.getString("source"));
                    questionEntity.setQuestionVersionId(version.getVersionId());
                    questionEntity.setQuestionBookId(book.getTextBookId());
                    if(params.containsKey("nodeId1") && !StringUtil.isEmpty(String.valueOf(params.get("nodeId1")))) {
                        questionEntity.setQuestionNodeId((int)params.get("nodeId1"));
                    }
                    if(params.containsKey("nodeId2") && !StringUtil.isEmpty(String.valueOf(params.get("nodeId2")))) {
                        questionEntity.setQuestionNodeId2((int)params.get("nodeId2"));
                    }
                    if(params.containsKey("nodeId3") && !StringUtil.isEmpty(String.valueOf(params.get("nodeId3")))) {
                        questionEntity.setQuestionNodeId3((int)params.get("nodeId3"));
                    }
                    questionEntity.setQuestionId(json.getIntValue("id"));
                    if(questionService.getQuestionById(json.getString("id"))!=null) {
                        questionService.update(questionEntity);
                    }else{
                        questionService.add(questionEntity);
                    }
                }
            }
        }else{
            System.out.println("接口不通，result="+resultJson);
        }
    }

    /**
     * 根据知识点获取试题列表
     * @param params
    * @return
     */
    public boolean saveKnowledgePointQuestionsList(Map<String, Object> params) {
        JSONObject result=new JSONObject();
        if(params.containsKey("subjectId") && (int)params.get("subjectId")!=0){
            result=getPointsQuestions(params);
        }
        boolean addResult=false;
        if(checkApiResult(result)){
            JSONArray dataJsonArr=JSONArray.parseArray(result.getString("data"));
            if(dataJsonArr!=null && !dataJsonArr.isEmpty()) {
                List<QuestionEntity> questionList=new ArrayList<>();
                for (Object data : dataJsonArr) {
                    JSONObject questionJson=JSONObject.parseObject(data.toString());
                    QuestionEntity question=new QuestionEntity();
                    question.setSubjectId((int)params.get("subjectId"));
                    question.setQuestionId(questionJson.getInteger("id"));
                    question.setQuestionTypeId(questionJson.getInteger("questionTypeId"));
                    question.setQuestionDifficult(questionJson.getInteger("difficult"));
                    question.setQuestionContent(questionJson.getString("stem"));
                    question.setQuestionOption(questionJson.getString("questionOptions"));
                    question.setQuestionSource(questionJson.getString("source"));
                    questionList.add(question);
                }
                questionService.batchAdd(questionList);
            }
        }
        return addResult;
    }

    /**
     * 获取单个试题的答案和解析
     * @param subjectId  科目id
     * @return
     */
    public boolean addQuestionAnswerAndAnalyze(int subjectId,int page,int pageSize) {
        List<QuestionEntity> questionEntityList=new ArrayList<>();
        List<QuestionEntity> questionList=questionService.searchQuestionListBySubId(subjectId,page,pageSize);
        if(questionList!=null && !questionList.isEmpty()){
            //TODO:学科网只能每天拉取10道题
            int limit=0;
            for(QuestionEntity question:questionList){
                int questionId=question.getQuestionId();
                Map<String,Object> paramsMap=new HashMap<>();
                paramsMap.put("subjectId",subjectId);
                paramsMap.put("questionId",questionId);
                System.out.println("subjectId="+subjectId+",questionId="+questionId);
                JSONObject answerJson=DocCenterApi.getInstance().searchApiDoc("getAnswerAndAnalyze",paramsMap);
                if(checkApiResult(answerJson)){
                    //TODO:10道题结束后，跳出循环
                    limit++;
                    JSONObject dataJson=JSONObject.parseObject(answerJson.getString("data"));
                    if(dataJson!=null && !dataJson.isEmpty()) {
                        String questionAnalyze=dataJson.getString("questionAnalyze");
                        String answer=dataJson.getString("answer");
                        System.out.println("analyze="+questionAnalyze+",answer="+answer);
                        QuestionEntity q=new QuestionEntity();
                        q.setQuestionId(questionId);
                        q.setQuestionAnalysis(questionAnalyze);
                        q.setQuestionAnswer(answer);
                        questionEntityList.add(q);
                        //TODO:手动限制，没办法，节约时间，后面的调了也没值
                        if(limit>10){
                            break;
                        }
                    }
                }
            }
        }
        boolean result=false;
        System.out.println("试题答案数量====="+questionEntityList.size());
        if(questionEntityList!=null && !questionEntityList.isEmpty()){
            result=questionService.addQuestionAnswerAndAnalyze(questionEntityList);
        }
        return result;
    }


    /**
     * 根据学段id拉取学科试题类型
     * @param periodId 学段id
     * @return
     */
    public boolean addQuestionType(int periodId) {
        List<SubjectEntity> subjectList=subjectDao.searchSubjectListByPeriodId(periodId);
        boolean result=false;
        if(subjectList!=null && !subjectList.isEmpty()){
            List<QuestionTypeEntity> list=new ArrayList<>();
            //题目去重
            Set<String> typeIdSet=new HashSet<>();
            for(SubjectEntity subject:subjectList){
                int subjectId=subject.getSubjectId();
                Map<String,Object> params=new HashMap<>();
                params.put("subjectId",subjectId);
                JSONObject data=DocCenterApi.getInstance().searchApiDoc("getQuestionTypeList",params);
                if(checkApiResult(data)){
                    JSONArray dataJsonArr=JSONArray.parseArray(data.getString("data"));
                    if(dataJsonArr!=null && !dataJsonArr.isEmpty()) {
                        for(Object questionType:dataJsonArr){
                            JSONObject typeJson=JSONObject.parseObject(questionType.toString());
                            QuestionTypeEntity typeEntity=new QuestionTypeEntity();
                            String typeId=typeJson.getString("id");
                            if(!typeIdSet.contains(typeId)) {
                                typeEntity.setTypeId(typeId);
                                typeEntity.setTypeName(typeJson.getString("questionTypeName"));
                                typeEntity.setSubjectId(String.valueOf(subjectId));
                                list.add(typeEntity);
                            }
                            typeIdSet.add(typeId);
                        }
                    }
                }
            }
            if(!list.isEmpty()){
                result=questionTypeService.batchAdd(list,true);
            }
        }
        return result;
    }

    /**
     * 更改试题的知识点
     * @param params
     * @return
     */
    public Object updateKnowledgePointsList(Map<String, Object> params) {
        boolean addResult=false;
        List<QuestionEntity> list=new ArrayList<>();
        if(params.containsKey("subjectId") && (int)params.get("subjectId")!=0){
            List<PointEntity> pointEntityList=pointDao.searchPointListBySubId((int)params.get("subjectId"));
            if(pointEntityList!=null && !pointEntityList.isEmpty()) {
                for(PointEntity point:pointEntityList) {
                    int pointId = point.getPointId();
                    params.put("knowledgePointId1",pointId);
                    List<PointEntity> parentPointList=new ArrayList<>();
                    parentPointList.add(point);
                    List<PointEntity> childPointList=pointDao.searchChildPointList(parentPointList);
                    if(childPointList!=null && !childPointList.isEmpty()){
                        for(PointEntity childPoint:childPointList){
                            params.put("knowledgePointId2",childPoint.getPointId());
                            List<PointEntity> parentPointList1=new ArrayList<>();
                            parentPointList1.add(childPoint);
                            List<PointEntity> childPointList1=pointDao.searchChildPointList(parentPointList1);
                            if(childPointList1!=null && !childPointList.isEmpty()){
                                for(PointEntity childPoint1:childPointList){
                                    params.put("knowledgePointId3",childPoint1.getPointId());
                                    addResult=updateQuestionPoints(params);
                                }
                            }
                            else {
                                addResult=updateQuestionPoints(params);
                            }
                        }
                    }else {
                        addResult=updateQuestionPoints(params);
                    }
                }
            }
        }
        return addResult;
    }
    private boolean updateQuestionPoints(Map<String,Object> params){
        JSONObject result=getPointsQuestions(params);
        System.out.println(">>>>>>>>>>>>>>>根据知识点拉取学科网试题列表数据："+result.toString());
        boolean addResult=false;
        if(checkApiResult(result)){
            JSONArray dataJsonArr=JSONArray.parseArray(result.getString("data"));
            if(dataJsonArr!=null && !dataJsonArr.isEmpty()) {
                for (Object data : dataJsonArr) {
                    JSONObject questionJson=JSONObject.parseObject(data.toString());
                    QuestionEntity question=new QuestionEntity();
                    question.setSubjectId((int)params.get("subjectId"));
                    question.setQuestionId((int)questionJson.get("id"));
                    question.setQuestionTypeId(questionJson.getInteger("questionTypeId"));
                    question.setQuestionDifficult(questionJson.getInteger("difficult"));
                    question.setQuestionContent(questionJson.getString("stem"));
                    question.setQuestionOption(questionJson.getString("questionOptions"));
                    question.setQuestionSource(questionJson.getString("source"));
                    if(params.containsKey("knowledgePointId1")) {
                        question.setQuestionPointId((int)params.get("knowledgePointId1"));
                    }
                    if(params.containsKey("knowledgePointId2")) {
                        question.setQuestionPointId2((int)params.get("knowledgePointId2"));
                    }
                    if(params.containsKey("knowledgePointId3")) {
                        question.setQuestionPointId3((int)params.get("knowledgePointId3"));
                    }
                    QuestionEntity questionEntity=questionService.getQuestionById(String.valueOf(question.getQuestionId()));
                    if(questionEntity!=null && questionEntity.getQuestionId()!=0){
                        addResult=questionService.update(question);
                    }else{
                        addResult=questionService.add(question);
                    }
                }
            }
        }
        return addResult;
    }
    private JSONObject getPointsQuestions(Map<String,Object> params){
        Map<String,Object> paramsMap=new HashMap<>();
        paramsMap.put("subjectId",(int)params.get("subjectId"));
        int startPage=(int)params.get("startPage");
        int countPerPage=(int)params.get("countPerPage");
        paramsMap.put("startPage",startPage);
        paramsMap.put("countPerPage",countPerPage);
        if(params.containsKey("knowledgePointId1")){
            paramsMap.put("knowledgePointId1",(int)params.get("knowledgePointId1"));
        }
        if(params.containsKey("knowledgePointId2")){
            paramsMap.put("knowledgePointId2",(int)params.get("knowledgePointId2"));
        }
        if(params.containsKey("knowledgePointId3")){
            paramsMap.put("knowledgePointId3",(int)params.get("knowledgePointId3"));
        }
        if(params.containsKey("questionTypeId")){
            paramsMap.put("questionTypeId",(int)params.get("questionTypeId"));
        }
        if(params.containsKey("difficult")) {
            paramsMap.put("difficult", (int) params.get("difficult"));
        }
        if(params.containsKey("year")) {
            paramsMap.put("year", (int) params.get("year"));
        }
        return DocCenterApi.getInstance().searchApiDoc("getKnowledgePointQuestionList",paramsMap);
    }
    /**
     * 验证调用学科网结果
     * @param result
     * @return
     */
    private boolean checkApiResult(JSONObject result) {
        if(result!=null && !result.isEmpty() && "ok".equalsIgnoreCase(result.get("status").toString())){
            return true;
        }else{
            return false;
        }
    }

}

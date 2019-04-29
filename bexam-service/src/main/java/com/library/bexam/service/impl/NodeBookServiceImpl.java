package com.library.bexam.service.impl;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.dao.NodeBookDao;
import com.library.bexam.entity.NodeBookEntity;
import com.library.bexam.form.TextBookForm;
import com.library.bexam.service.NodeBookService;
import com.library.bexam.util.ConvertResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NodeBookServiceImpl implements NodeBookService{
    @Autowired
    private NodeBookDao nodeBookDao;

    @Override
    public Result serachNodeBookList(Map<String,Object> params) {
        if(params==null || params.isEmpty() || !params.containsKey("page") || !params.containsKey("pageSize")){
            return ConvertResult.getParamErrorResult("分页参数不能为空");
        }
        int startPage=(int)params.get("page");
        int pageSize=(int)params.get("pageSize");
        int firstIndex=(startPage-1)*pageSize;
        List<NodeBookEntity> nodeBookEntityList=nodeBookDao.searchNodeBookPageList(firstIndex,pageSize);
        for(NodeBookEntity node:nodeBookEntityList){
            TextBookForm text=node.getTextBookEntity();
            if(text!=null) {
                node.setTextBookId(text.getTextBookId());
                node.setTextBookName(text.getTextBookName());
            }
        }
        return ConvertResult.getSuccessResult(nodeBookEntityList);
    }

    @Override
    public Result searchNodeListByBookId(int textBookId) {
        if(textBookId==0){
            return ConvertResult.getParamErrorResult("教材册别id不能为没空");
        }
        List<NodeBookEntity> nodeBookList = nodeBookDao.searchNodeListByBookId(textBookId);
        Map<String,Object> result=new HashMap<>();
        if(nodeBookList!=null && !nodeBookList.isEmpty()){
            List<NodeBookEntity> allNodeBookList=getAllNodeBook(nodeBookList);
            result.put("node",allNodeBookList);
            result.put("totalCount",allNodeBookList.size());
        }else{
            result.put("node",new ArrayList<>());
            result.put("totalCount",0);
        }
        return ConvertResult.getSuccessResult(result);
    }

    /**
     * 获取教材册别树形结构
     * @param nodeBookList
     * @return
     */
    @Override
    public List<NodeBookEntity> getAllNodeBook(List<NodeBookEntity> nodeBookList){
        List<NodeBookEntity> childNodeBookList=nodeBookDao.searchChildNodeBookList(nodeBookList);
        for(NodeBookEntity node:nodeBookList){
            node.setTextBookId(node.getTextBookEntity()==null?0:node.getTextBookEntity().getTextBookId());
            node.setTextBookName(node.getTextBookEntity()==null?"":node.getTextBookEntity().getTextBookName());
            if(childNodeBookList!=null && !childNodeBookList.isEmpty()){
                List<NodeBookEntity> childNodeBookEntityList=new ArrayList<>();
                for(NodeBookEntity childNodeBook:childNodeBookList){
                    if(node.getNodeId()==childNodeBook.getParentId()){
                        childNodeBook.setTextBookId(childNodeBook.getTextBookEntity()==null?0:
                                childNodeBook.getTextBookEntity().getTextBookId());
                        childNodeBook.setTextBookName(childNodeBook.getTextBookEntity()==null?"":
                                childNodeBook.getTextBookEntity().getTextBookName());
                        childNodeBookEntityList.add(childNodeBook);
                    }
                }
                node.setNodeList(childNodeBookEntityList);
            }else {
                node.setNodeList(new ArrayList<>());
            }
        }
        if(childNodeBookList!=null && !childNodeBookList.isEmpty()){
            getAllNodeBook(childNodeBookList);
        }
        return nodeBookList;
    }
}

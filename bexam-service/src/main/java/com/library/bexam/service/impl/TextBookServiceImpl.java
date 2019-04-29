package com.library.bexam.service.impl;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.dao.TextBookDao;
import com.library.bexam.entity.TextBookEntity;
import com.library.bexam.form.TextBookForm;
import com.library.bexam.service.TextBookService;
import com.library.bexam.util.ConvertResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TextBookServiceImpl implements TextBookService {
    @Autowired
    private TextBookDao textBookDao;
    @Override
    public Result searchTextBookList(int versionId) {
        if(versionId==0){
            return ConvertResult.getParamErrorResult("教材版本id不能为空");
        }
        List<TextBookEntity> bookList=textBookDao.searchTextBookListByVersionId(versionId);
        Map<String,Object> result=new HashMap<>();
        if(bookList!=null && !bookList.isEmpty()){
            List<TextBookForm> bookFormList=new ArrayList<>();
            for(TextBookEntity book:bookList){
                TextBookForm bookForm=new TextBookForm();
                bookForm.setTextBookId(book.getTextBookId());
                bookForm.setTextBookName(book.getTextBookName());
                bookForm.setCreateTime(book.getCreateTime());
                bookFormList.add(bookForm);
            }
            result.put("textBook",bookFormList);
            result.put("totalCount",bookFormList.size());
        }else{
            result.put("textBook",new ArrayList<>());
            result.put("totalCount",0);
        }
        return ConvertResult.getSuccessResult(result);
    }
}

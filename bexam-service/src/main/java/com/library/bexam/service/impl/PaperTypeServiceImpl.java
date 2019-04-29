package com.library.bexam.service.impl;

import com.library.bexam.dao.PaperTypeDao;
import com.library.bexam.entity.PaperTypeEntity;
import com.library.bexam.entity.QuestionTypeEntity;
import com.library.bexam.service.PaperTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 试卷类型服务实现类
 *
 * @author JayChen
 */
@Service
public class PaperTypeServiceImpl implements PaperTypeService {

    @Autowired
    private PaperTypeDao paperTypeDao;

    @Override
    public List list() {
        return paperTypeDao.list();
    }

    @Override
    public boolean add(PaperTypeEntity paperTypeEntity) {
        boolean result = false;
        try {
            result = paperTypeDao.add(paperTypeEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (paperTypeEntity.getTypeId() != 0) {
//            // TODO: 添加
//        }
        return result;
    }

    @Override
    public boolean delete(String[] typeIdArray) {
        return paperTypeDao.delete(typeIdArray);
    }

    @Override
    public boolean update(PaperTypeEntity paperTypeEntity) {
        return paperTypeDao.update(paperTypeEntity);
    }

    @Override
    public PaperTypeEntity get(int typeId) {
        return paperTypeDao.get(typeId);
    }
}

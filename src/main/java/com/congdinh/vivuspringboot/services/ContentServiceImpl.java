package com.congdinh.vivuspringboot.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.congdinh.vivuspringboot.entities.Content;
import com.congdinh.vivuspringboot.repository.ContentDAO;

@Service
public class ContentServiceImpl implements ContentService {

    private final ContentDAO contentDAO;
    

   
    public ContentServiceImpl(ContentDAO contentDAO) {
        this.contentDAO = contentDAO;
    }

    @SuppressWarnings("deprecation")
    public Content getById(Integer id) {
        return contentDAO.getById(id);
    }

    @Override
    public Content save(Content content) {
        // TODO Auto-generated method stub
        contentDAO.save(content);
        return content;
    }

    @Override
    public void update(Content content) {
        // TODO Auto-generated method stub
        contentDAO.save(content);
    }

    @Override
    public void delete(Integer id) {
        contentDAO.deleteById(id);
    }

    @Override
    public List<Content> findAll(Sort sort) {
        return contentDAO.findAll(sort);  // Sử dụng phương thức từ JpaRepository
    }

  

}




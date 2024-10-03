package com.congdinh.vivuspringboot.services;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.congdinh.vivuspringboot.entities.Content;

public interface ContentService {
    Content save(Content content);

    void update(Content content);

    void delete(Integer id);

    List<Content> findAll(Sort sort);

    Content getById(Integer id);
}

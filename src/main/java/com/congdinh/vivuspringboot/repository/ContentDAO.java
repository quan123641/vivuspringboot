package com.congdinh.vivuspringboot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.congdinh.vivuspringboot.entities.Content;
public interface ContentDAO extends JpaRepository<Content, Integer>, JpaSpecificationExecutor<Content>{
     

}

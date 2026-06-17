//package com.habits.habits.repository;
//
//
//import com.habits.habits.model.Category;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CategoryRepository extends JpaRepository<Category, Long> { // Entity and primary key data type
//    Category findByNameAndUserId(String categoryName, Long userId);
//    Category findByIdAndUserId(Long categoryId, Long userId);
//}


package com.habits.habits.repository;

import com.habits.habits.model.Category;
import com.habits.habits.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
}


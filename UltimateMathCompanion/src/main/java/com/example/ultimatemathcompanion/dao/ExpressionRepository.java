package com.example.ultimatemathcompanion.dao;

import com.example.ultimatemathcompanion.datamodel.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ExpressionRepository extends JpaRepository<Expression, Integer> {

    List<Expression> findAll();
}

package com.mathverse.legacy.dao;

import com.mathverse.legacy.model.Lecture;

import java.util.List;

public interface LectureDao {
    List<Lecture> findAll();
    Lecture findById(int id);
}
package com.mathverse.legacy.servlet;

import com.mathverse.legacy.dao.JdbcLectureDao;
import com.mathverse.legacy.dao.LectureDao;
import com.mathverse.legacy.model.Lecture;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class LectureListServlet extends HttpServlet {

    private final LectureDao lectureDao = new JdbcLectureDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Lecture> lectures = lectureDao.findAll();
            request.setAttribute("lectures", lectures);
            request.getRequestDispatcher("/WEB-INF/jsp/lectures.jsp").forward(request, response);
        } catch (RuntimeException e) {
            request.setAttribute("errorMessage", "Не удалось загрузить список лекций. Попробуйте позже.");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
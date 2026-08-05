package com.mathverse.legacy.servlet;

import com.mathverse.legacy.dao.JdbcLectureDao;
import com.mathverse.legacy.dao.LectureDao;
import com.mathverse.legacy.model.Lecture;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LectureDetailServlet extends HttpServlet {

    private final LectureDao lectureDao = new JdbcLectureDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный id лекции");
            return;
        }

        try {
            Lecture lecture = lectureDao.findById(id);
            if (lecture == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Лекция не найдена");
                return;
            }
            request.setAttribute("lecture", lecture);
            request.getRequestDispatcher("/WEB-INF/jsp/lecture-detail.jsp").forward(request, response);
        } catch (RuntimeException e) {
            request.setAttribute("errorMessage", "Не удалось загрузить лекцию. Попробуйте позже.");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
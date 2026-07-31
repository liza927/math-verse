package com.mathverse.legacy.servlet;

import com.mathverse.legacy.model.Lecture;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/lectures")
public class LectureListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Lecture> lectures = List.of(
                new Lecture(1, "Введение в матрицы", "Алгебра"),
                new Lecture(2, "Определители и их свойства", "Алгебра"),
                new Lecture(3, "Производная функции", "Анализ")
        );

        request.setAttribute("lectures", lectures);
        request.getRequestDispatcher("/WEB-INF/jsp/lectures.jsp").forward(request, response);
    }
}
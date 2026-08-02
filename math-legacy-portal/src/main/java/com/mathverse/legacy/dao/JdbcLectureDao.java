package com.mathverse.legacy.dao;

import com.mathverse.legacy.config.DatabaseConfig;
import com.mathverse.legacy.model.Lecture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcLectureDao implements LectureDao {

    @Override
    public List<Lecture> findAll() {
        String sql = "SELECT lecture_id, title, topic, content FROM lectures ORDER BY lecture_id";
        List<Lecture> lectures = new ArrayList<>();

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                lectures.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось загрузить список лекций", e);
        }
        return lectures;
    }

    @Override
    public Lecture findById(int id) {
        String sql = "SELECT lecture_id, title, topic, content FROM lectures WHERE lecture_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapRow(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось загрузить лекцию с id=" + id, e);
        }
    }

    private Lecture mapRow(ResultSet resultSet) throws SQLException {
        return new Lecture(
                resultSet.getInt("lecture_id"),
                resultSet.getString("title"),
                resultSet.getString("topic"),
                resultSet.getString("content")
        );
    }
}
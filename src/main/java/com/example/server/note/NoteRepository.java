package com.example.server.note;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
class NoteRepository {

    private final JdbcClient jdbcClient;

    NoteRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    int maxId() {
        var sql = "SELECT max(id) FROM note;";
        Optional<Integer> id = jdbcClient.sql(sql)
                .query(Integer.class)
                .optional();
        return id.isEmpty() ? 0 : id.get();
    }

    void createTable() {
        var sql = """
                CREATE TABLE IF NOT EXISTS note (
                    id integer,
                    processing boolean,
                    content varchar
                );
                """;
        jdbcClient.sql(sql)
                .update();
    }

    List<Note> findAll() {
        var sql = """
                SELECT id, processing, content
                    FROM note;
                """;
        return jdbcClient.sql(sql)
                .query(Note.class)
                .list();
    }

    Optional<Note> findById(int id) {
        var sql = """
                SELECT id, processing, content
                    FROM note
                    WHERE id = :id;
                """;
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(Note.class)
                .optional();
    }

    void save(Note note) {
        var sql = """
                INSERT INTO note (id, processing, content)
                    VALUES (:id, :processing, :content);
                """;
        jdbcClient.sql(sql)
                .param("id", note.id())
                .param("processing", note.processing())
                .param("content", note.content())
                .update();
    }

    void update(Note note) {
        var sql = """
                UPDATE note
                    SET processing = :processing, content = :content
                    WHERE id = :id;
                """;
        int num = jdbcClient.sql(sql)
                .param("id", note.id())
                .param("processing", note.processing())
                .param("content", note.content())
                .update();
        if (num == 0) {
            throw new RuntimeException("Note not found");
        }
    }

    void deleteAll() {
        var sql = """
                DELETE FROM note;
                """;
        jdbcClient.sql(sql)
                .update();
    }

    void deleteById(int id) {
        var sql = """
                DELETE FROM note
                    WHERE id = :id;
                """;
        int num = jdbcClient.sql(sql)
                .param("id", id)
                .update();
        if (num == 0) {
            throw new RuntimeException("Note note found");
        }
    }

}

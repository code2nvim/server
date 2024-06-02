package com.example.server.note;

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
        var id = jdbcClient.sql(sql).query(Integer.class).optional();
        return id.isPresent() ? id.get() : 0;
    }

    void createTable() {
        var sql = """
                CREATE TABLE IF NOT EXISTS note (
                    id integer,
                    done boolean,
                    content varchar
                );
                """;
        jdbcClient.sql(sql)
                .update();
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
                    WHERE id = ?;
                """;
        jdbcClient.sql(sql)
                .params(id)
                .update();
    }

    Iterable<Note> findAll() {
        var sql = """
                SELECT id, done, content
                    FROM note;
                """;
        return jdbcClient.sql(sql)
                .query(Note.class)
                .list();
    }

    Optional<Note> findById(int id) {
        var sql = """
                SELECT id, done, content
                    FROM note
                    WHERE id = ?;
                """;
        return jdbcClient.sql(sql)
                .params(id)
                .query(Note.class)
                .optional();
    }

    void save(Note note) {
        var sql = """
                INSERT INTO note (id, done, content)
                    VALUES (?, ?, ?);
                """;
        jdbcClient.sql(sql)
                .params(note.id(), note.done(), note.content())
                .update();
    }

}

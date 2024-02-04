package com.example.contentcalender.repository;

import com.example.contentcalender.model.Content;
import com.example.contentcalender.model.Status;
import com.example.contentcalender.model.Type;
import com.example.contentcalender.service.InvalidContentOrContentNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ContentJdbcTemplateRepository {
    /*
     This class is dependent on JdbcTemplate, so instead of creating new instance of this class in
     constructor we just autowire this class in constructor, we have the instance of this
     class already in Application context
     */
    private final JdbcTemplate jdbcTemplate;


    // Autowired (injected) JdbcTemplate in Constructor
    public ContentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) { // DI
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1) write a rowMapper
    /*
       using RowMapper to map rows to Content instance
       mapRow() and rowMapper can be used interchangeably
       a resultSet is a tabular data structure containing the rows and columns that satisfy the
       conditions of sql query
       The result set is represented in Java through the ResultSet interface, which is part of the JDBC API.
        The ResultSet interface provides methods to iterate through the rows, retrieve data
        from specific columns, and perform other operations.
     */
    RowMapper<Content> contentRowMapper = (rs, rowNum) -> {
        return new Content(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                Status.valueOf(rs.getString("status")),
                Type.valueOf(rs.getString("content_type")),
                rs.getObject("date_created", LocalDateTime.class),
                rs.getObject("date_updated", LocalDateTime.class),
                rs.getString("url")
        );
    };

    /*
    method to map each row of data in the ResultSet. (Abstract method in RowMapper interface)
    This method should not call next() on the ResultSet; it is only supposed to map values of the current row.
     */
    // 2) implement mapRow
    private static Content mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Content(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                Status.valueOf(rs.getString("status")),
                Type.valueOf(rs.getString("content_type")),
                rs.getObject("date_created", LocalDateTime.class),
                rs.getObject("date_updated", LocalDateTime.class),
                rs.getString("url")
        );
    }

    // 3) implement getAllContent()
    public List<Content> getAllContent() {
        String sql = "SELECT * FROM content";
        //  List<Content> contentList = jdbcTemplate.query(sql,contentRowMapper);
        return jdbcTemplate.query(sql, ContentJdbcTemplateRepository::mapRow);
    }

    // 4) implement createContent()
    public void createContent(String title, String description, Status status, Type contentType, String URL) {
        String sql = "INSERT INTO content (title, description, status, content_type, date_created, URL) VALUES (?, ?, ?, ?, NOW(), ?)";
        jdbcTemplate.update(sql, title, description, status.toString(), contentType.toString(), URL);
    }

    // implement updateContent()
    public void updateContent(int id, String title, String description, Status status, Type content_Type, String URL) {
        String sql = "UPDATE content SET title = ?, description = ?, status = ?, content_type = ?," +
                " date_created = NOW(), url = ? WHERE id = ?";
        int updatedRow = jdbcTemplate.update(sql, title, description, status.toString(), content_Type.toString(), URL, id);
        if (updatedRow == 0) {
            throw new InvalidContentOrContentNotFoundException("Content with id " + id + " not found");
        }
    }


    // implement deleteContent()
    public void deleteContent(int id) {
        String sql = "DELETE FROM content WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // implement getContent()  by id
    public Content getContent(Integer id) {
        String sql = "SELECT * FROM content WHERE id = ?";
        Content content = jdbcTemplate.queryForObject(sql, new Object[]{id}, contentRowMapper);
        return content;
    }
}

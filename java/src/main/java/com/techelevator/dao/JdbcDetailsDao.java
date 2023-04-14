package com.techelevator.dao;

import com.techelevator.model.Details;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcDetailsDao implements DetailsDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcDetailsDao(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate;}


    @Override
    public List<Details> findAllDetails() {
        List<Details> details = new ArrayList<>();
        String sql = "select * from details";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Details detail = mapRowToProvider(results);
            details.add(detail);
        }
        return details;
    }

    @Override
    public Details getDetailsById(int detailsId) {
        String sql = "SELECT * FROM details WHERE details_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, detailsId);
        if (results.next()) {
            return mapRowToProvider(results);
        } else {
            return null;
        }
    }


    @Override
    public Details getDetailsIdByLastName(String lastName) {
        String sql = "SELECT details_id FROM details WHERE last_name = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, lastName);
        if (results.next()) {
            return mapRowToProvider(results);
        } else {
            return null;
        }
    }


    @Override
    public Details getDetailsByUserId(int userId) {
        String sql = "SELECT * from details where user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            return mapRowToProvider(results);
        } else {
            return null;
        }
    }

    @Override
    public Details getProviderDetailsById(int detailsId) {
        String sql = "SELECT * from details where is_provider = true AND details_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, detailsId);
        if (results.next()) {
            return mapRowToProvider(results);
        } else {
            return null;
        }
    }

    @Override
    public List<Details> findAllByUserId(int userId) {
        List<Details> details = new ArrayList<>();
        String sql = "select * from details where user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Details detail = mapRowToProvider(results);
            details.add(detail);
        }

        return details;
    }

    @Override
    public List<Details> findAllByDetailsId(int detailsId) {
        List<Details> detailList = new ArrayList<>();
        String sql = "select * from details where details_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, detailsId);
        while (results.next()) {
            Details detail = mapRowToProvider(results);
            detailList.add(detail);
        }

        return detailList;
    }

    @Override
    public boolean create(int userId, String first_name, String last_name, boolean isProvider, int titleId) {
        String insertCreateSql = "insert into details (user_id, first_name, last_name, is_provider, title_id) values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(insertCreateSql, userId, first_name, last_name, isProvider, titleId) == 1;
    }

    @Override
    public Details getDetailsByOfficeId(int officeId) {
        String sql = "SELECT DISTINCT details_id FROM office_users WHERE office_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, officeId);
        if (results.next()) {
            return mapRowToProvider(results);
        } else {
            return null;
        }
    }

    private Details mapRowToProvider(SqlRowSet rs) {
        Details details = new Details();
        details.setId(rs.getInt("details_id"));
        details.setUserId(rs.getInt("user_id"));
        details.setFirstName(rs.getString("first_name"));
        details.setLastName(rs.getString("last_name"));
        details.setIsProvider(rs.getBoolean("is_provider"));
        details.setTitleId(rs.getInt("title_id"));
        return details;
    }
}
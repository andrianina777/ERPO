package com.opham.prepa.repository;

import com.opham.prepa.model.Commande;
import com.opham.prepa.mapper.CommandeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
@Repository
public class JdbcCommandeRepository implements CommandeRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Commande> findAll() {
        return jdbcTemplate.query("exec x_Atte_Prepa_Partiel3 ",
                new CommandeMapper());
    }

    @Override
    public List<Commande> findByDate(String groupe, Date dateliv) {
        return jdbcTemplate.query("exec x_Atte_Prepa_Partiel2 ?,?",
                new CommandeMapper(), groupe,dateliv);
    }
}

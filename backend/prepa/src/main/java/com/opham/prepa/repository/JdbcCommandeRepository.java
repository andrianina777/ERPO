package com.opham.prepa.repository;

import com.opham.prepa.model.Commande;
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

//    public JdbcCommandeRepository(DataSource dataSource) {
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }
    @Override
    public List<Commande> findAll() {
        return jdbcTemplate.query("exec x_Atte_Prepa_Partiel2 ",
                BeanPropertyRowMapper.newInstance(Commande.class));
    }

    @Override
    public List<Commande> findByDate(String group, Date dateliv) {
        return jdbcTemplate.query("execute x_Atte_Prepa_Partiel2 ?,?",
                BeanPropertyRowMapper.newInstance(Commande.class), group,dateliv);
    }
}

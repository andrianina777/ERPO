package com.opham.prepa.repository;

import com.opham.prepa.model.Commande;
import com.opham.prepa.mapper.CommandeMapper;
import com.opham.prepa.mapper.LigneCommandeMapper;
import com.opham.prepa.model.LigneCommande;
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
        return jdbcTemplate.query("exec x_Atte_Prepa_Partiel2_test ?,?",
                new CommandeMapper(), groupe,dateliv);
    }

    public List<LigneCommande> findLigneCmdebyCode(String code) {
        return jdbcTemplate.query("select CCLARTICLE as CODE_ARTICLE,ARLIB as LIBELLE,CCLQTE as QTE_CMD,CCLRESTE as QTE_RESTE,CCLQTEEXP as QTE_EXPEDIE,(case when isnull(xSeq,0)<>0 then '*T*' else '' end) as COMS\n" +
                        "from FCCL  left join VIEW_FAR on ARCODE=CCLARTICLE left join xFCCL on xSeq=CCLSEQ\n" +
                        "where CCLCODE=? order by CCLARTICLE",
                new LigneCommandeMapper(), code);
    }
}

package com.opham.prepa.repository;

import com.opham.prepa.mapper.DetailPrepaMapper;
import com.opham.prepa.mapper.InfoCmdMapper;
import com.opham.prepa.model.Commande;
import com.opham.prepa.mapper.CommandeMapper;
import com.opham.prepa.mapper.LigneCommandeMapper;
import com.opham.prepa.model.DetailPrep;
import com.opham.prepa.model.InfoCommande;
import com.opham.prepa.model.LigneCommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<DetailPrep> listDetailBP(String code_CC) {
        return jdbcTemplate.query("exec x_DetailPrepa2 ?",
                new DetailPrepaMapper(), code_CC);
    }

    @Override
    public InfoCommande plusInfoCmd(String code_CC) {
        return jdbcTemplate.queryForObject("select CCCODE as CODE_CC,CCNOM as NOM_CLIENT,CCADR1||CCADR2 as adresse,CC2AXE as axe,CC2DATELIV as DATE_LIV,CCCOMMENTAIRES as coms_client,CCCOMMENTMAG as coms_mag,CLTEL||' - '||CLTEL2 as contact    from FCC \n" +
                        "inner join FCC2 on CC2CODE=CCCODE inner join FCL on CLCODE=CCCLIENT where CCCODE=?",
                new Object[] { code_CC } , new InfoCmdMapper());
    }
}

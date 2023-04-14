package com.opham.prepa.repository.Apreparer;

import com.opham.prepa.mapper.Apreparer.DetailPrepaMapper;
import com.opham.prepa.mapper.Apreparer.InfoCmdMapper;
import com.opham.prepa.mapper.genererBP.ArticleCmdMapper;
import com.opham.prepa.mapper.genererBP.DepotCmdMapper;
import com.opham.prepa.mapper.genererBP.ListeCmdMapper;
import com.opham.prepa.mapper.genererBP.RayonCmdMapper;
import com.opham.prepa.model.Apreparer.Commande;
import com.opham.prepa.mapper.Apreparer.CommandeMapper;
import com.opham.prepa.mapper.Apreparer.LigneCommandeMapper;
import com.opham.prepa.model.Apreparer.DetailPrep;
import com.opham.prepa.model.Apreparer.InfoCommande;
import com.opham.prepa.model.Apreparer.LigneCommande;
import com.opham.prepa.model.genererBP.ArticleCmd;
import com.opham.prepa.model.genererBP.DepotCmd;
import com.opham.prepa.model.genererBP.ListeCmd;
import com.opham.prepa.model.genererBP.RayonCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.*;

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
        return jdbcTemplate.queryForObject("select CCCODE as CODE_CC,CCNOM as NOM_CLIENT,CCADR1||CCADR2 as adresse,CC2AXE as axe,CC2DATELIV_PREVU as DATE_LIV,CCCOMMENTAIRES as coms_client,CCCOMMENTMAG as coms_mag,CLTEL||' | '||CLTEL2 as contact,CC2RAISONDATELIVPREV as raisonDateLiv,convert(date,CC2DATELIV_PREVU) as date_l,convert(time,CC2DATELIV_PREVU) as heure_l   from FCC \n" +
                        "inner join FCC2 on CC2CODE=CCCODE inner join FCL on CLCODE=CCCLIENT where CCCODE=?",
                new Object[] { code_CC } , new InfoCmdMapper());
    }

    @Override
    public int updateInfoCmd(InfoCommande inf) {
        String sql = "exec v_bp_updateInfoCmd ?,?,?,?,?";
        return jdbcTemplate.update(sql, inf.getComs_mag(),inf.getAxe(),inf.getDate_liv(),inf.getRaisonDateLiv(),inf.getCode_cc());
    }

    @Override
    public Map<String, List<Object>> genererBP(String code) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("BECCPREP_Liste_Depot")
                .declareParameters(
                        new SqlParameter("code", Types.VARCHAR)

                )
                .returningResultSet("result1", new DepotCmdMapper())
                .returningResultSet("result2", new ArticleCmdMapper())
                .returningResultSet("result3", new ListeCmdMapper())
                .returningResultSet("result4", new RayonCmdMapper());

        Map<String, Object> results = jdbcCall.execute(code);

        Map<String, List<Object>> mappedResults = new HashMap<>();


        mappedResults.put("result1", Collections.singletonList((List<DepotCmd>) results.get("result1")));
        mappedResults.put("result2", Collections.singletonList((List<ArticleCmd>) results.get("result2")));
        mappedResults.put("result3", Collections.singletonList((List<ListeCmd>) results.get("result3")));
        mappedResults.put("result4", Collections.singletonList((List<RayonCmd>) results.get("result4")));
        return mappedResults;
    }


}

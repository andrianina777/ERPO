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
import org.springframework.jdbc.core.*;
import java.sql.*;
import java.sql.Types;
import java.util.*;
import org.springframework.dao.DataAccessException;

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
    public List<Commande> findByDate(String groupe, java.util.Date dateliv) {
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
    public String insert_BP(String code_CC,String depot) {
        String sql = "exec v_x_New_BP ?,?";
        String result =  jdbcTemplate.queryForObject(sql, new Object[] { code_CC,depot }, String.class);
        return result;
    }

    @Override
    public void insertL6(ListeCmd lc,String id) {
        String sql = "INSERT INTO DBSUIVI..L6_PREPA_INSERT\n" +
                "\t(Article, Lettre, Designation, LienCode, LienNum, Qte, UnitFact, PrixHT, ModeLiv, LigneLibre, TypeVente, Reglement, Echeancesp, abs_Qte, Factman, Offert, Artype, Devise, Coursdev, PrixHTdev, TotHTdev, Rem1, Rem2, Rem3, TotPrixHT, Emplacement, Attachement, Lot, Arreffour, cclmarche, ccldate, cclcolis, arqtecolis, cclpaht, seqLib, comment_mag, cclcolisage, cclnbcolis, cclpack, rayon, commande, depot, cclpromo,ID)\n" +
                "VALUES \n" +
                "\t(?,?,?,?,?,?,?,? ,?,?,?,?,? ,?,? ,?,? ,?,?,? ,?,? ,?,? ,?,?,?,?,?,?,?,? ,?,? ,?,?,?,?,? ,?,?,?,?,? );";
        jdbcTemplate.update(sql, lc.getArticle(),lc.getLettre(),lc.getDesignation(),lc.getLienCode(),lc.getLienNum(),
                lc.getQte(),lc.getUnitfact(),lc.getPrix_ht(),lc.getModeliv(),lc.getLignelibre(),lc.getTypeVente(),
                lc.getReglement(),lc.getEcheancesp(),lc.getAbs_qte(),lc.getFactman(),lc.getOffert(),lc.getArtype(),lc.getDevise(),
                lc.getCoursdev(),lc.getPrixht_dev(),lc.getTotht_dev(),lc.getRem1(),lc.getRem2(),lc.getRem3(),lc.getTotPrix_HT(),lc.getEmplacement(),
                lc.getAttachement(),lc.getLot(),lc.getArreffour(),lc.getCclmarche(),lc.getCcldate(),lc.getCclcolis(),lc.getArqtecolis(),lc.getCclpaht(),
                lc.getSeqLib(),lc.getComment_mag(),lc.getCclcolisage(),lc.getCclnbcolis(),
                lc.getCclpack(),lc.getRayon(),lc.getLienCode(),lc.getDepot(),lc.getCclpromo(),id);
    }

    @Override
    public String getId() {
        String sql = "select newID()";
        String result =  jdbcTemplate.queryForObject(sql, new Object[] { }, String.class);
        return result;
    }

        @Override
    public List<List<Object>> genererBP(String code) {
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

        List<List<Object>> resultList = new ArrayList<>();
        resultList.add((List<Object>) results.get("result1"));
        resultList.add((List<Object>) results.get("result2"));
        resultList.add((List<Object>) results.get("result3"));
        resultList.add((List<Object>) results.get("result4"));
      /*  mappedResults.put("result1", Collections.singletonList((List<DepotCmd>) results.get("result1")));
        mappedResults.put("result2", Collections.singletonList((List<ArticleCmd>) results.get("result2")));
        mappedResults.put("result3", Collections.singletonList((List<ListeCmd>) results.get("result3")));
        mappedResults.put("result4", Collections.singletonList((List<RayonCmd>) results.get("result4")));*/
        return resultList;
    }

    @Override
    public List<List<Object>> preparerCmd(String code) {
        List<List<Object>> results = jdbcTemplate.query("{ exec BECCPREP_Liste_Depot ? }", new Object[]{code}, new ResultSetExtractor<List<List<Object>>>() {
            @Override
            public List<List<Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<List<Object>> results = new ArrayList<>();
                RowMapper<?> rowMapper = null;
                while (rs.next()) {
                    List<Object> result = new ArrayList<>();
                    int columnCount = rs.getMetaData().getColumnCount();
                    System.out.println(columnCount);
                    if (columnCount == 2) {
                        rowMapper = new DepotCmdMapper();
                    } else if (columnCount ==3) {
                        rowMapper = new ArticleCmdMapper();
                    } else if (columnCount == 4) {
                        rowMapper = new ListeCmdMapper();
                    } else {
                        rowMapper = new RayonCmdMapper();
                    }
                    do {
                        result.add(rowMapper.mapRow(rs, rs.getRow()));
                    } while (rs.next());
                    results.add(result);
                }
                return results;
            }
        });
        return results;
    }





}

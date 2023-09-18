package com.opham.prepa.repository.Transfert;

import com.opham.prepa.Utils.Convert;
import com.opham.prepa.Utils.DataSourceConfig;
import com.opham.prepa.mapper.Apreparer.CommandeMapper;
import com.opham.prepa.mapper.Transfert.*;
import com.opham.prepa.mapper.Utlis.AlleMapper;
import com.opham.prepa.mapper.Utlis.DroitMapper;
import com.opham.prepa.model.Apreparer.Commande;
import com.opham.prepa.model.Transfert.ProblemeStock;
import com.opham.prepa.model.Transfert.Rangement;
import com.opham.prepa.model.Transfert.Transfert;
import com.opham.prepa.model.Transfert.TransfertConseilReappro;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class JdbcTransfertRepository implements TransfertRepository {


    private final JdbcTemplate jdbcTemplate;
    private final DataSourceConfig dataSourceConfig;

    @Autowired
    public JdbcTransfertRepository(JdbcTemplate jdbcTemplate, DataSourceConfig dataSourceConfig) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSourceConfig = dataSourceConfig;
    }


    @Override
    public List<List<Object>> listRotation(String depot, String alle) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("bp_afficheRotation2")
                .declareParameters(
                        new SqlParameter("depot", Types.VARCHAR),
                        new SqlParameter("alle", Types.VARCHAR)

                )
                .returningResultSet("result1", new RotationMapper())
                .returningResultSet("result2", new TransfertMapper());

        Map<String, Object> results = jdbcCall.execute(depot, alle);

        Map<String, List<Object>> mappedResults = new HashMap<>();

        List<List<Object>> resultList = new ArrayList<>();
        resultList.add((List<Object>) results.get("result1"));
        resultList.add((List<Object>) results.get("result2"));
        return resultList;
    }

    @Override
    public List<List<Object>> listColisIncomplet(String depotOrg, String depotDest, String alle) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("bp_colisIncomplet1")
                .declareParameters(
                        new SqlParameter("depotOrg", Types.VARCHAR),
                        new SqlParameter("depotDest", Types.VARCHAR),
                        new SqlParameter("alle", Types.VARCHAR)

                )
                .returningResultSet("result1", new ColisIncompletMapper())
                .returningResultSet("result2", new TransfertMapper());

        Map<String, Object> results = jdbcCall.execute(depotOrg, depotDest, alle);

        Map<String, List<Object>> mappedResults = new HashMap<>();

        List<List<Object>> resultList = new ArrayList<>();
        resultList.add((List<Object>) results.get("result1"));
        resultList.add((List<Object>) results.get("result2"));
        return resultList;
    }

    @Override
    public List<List<Object>> listPetitColis(String depotOrg, String depotDest, String alle) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("bp_petitColis")
                .declareParameters(
                        new SqlParameter("depotOrg", Types.VARCHAR),
                        new SqlParameter("depotDest", Types.VARCHAR),
                        new SqlParameter("alle", Types.VARCHAR)

                )
                .returningResultSet("result1", new PetitColisMapper())
                .returningResultSet("result2", new TransfertMapper());

        Map<String, Object> results = jdbcCall.execute(depotOrg, depotDest, alle);

        Map<String, List<Object>> mappedResults = new HashMap<>();

        List<List<Object>> resultList = new ArrayList<>();
        resultList.add((List<Object>) results.get("result1"));
        resultList.add((List<Object>) results.get("result2"));
        return resultList;
    }

    @Override
    public List<List<Object>> listRangement(String depot) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("x_RangementAffiche")
                .declareParameters(
                        new SqlParameter("depot", Types.VARCHAR)

                )
                .returningResultSet("result1", new RangementMapper())
                .returningResultSet("result2", new TransfertMapper());

        Map<String, Object> results = jdbcCall.execute(depot);

        Map<String, List<Object>> mappedResults = new HashMap<>();

        List<List<Object>> resultList = new ArrayList<>();
        resultList.add((List<Object>) results.get("result1"));
        resultList.add((List<Object>) results.get("result2"));
        return resultList;
    }

    @Override
    public int insertL6_Transfert(Transfert t) {
        String sql = "\n" +
                "INSERT INTO DBSUIVI..L6_TRANSFERT\n" +
                "\t(Article, libelle, labo, depotOrg, emplOrg, depotDest, emplDest, lot, dateper, lettre, qte, numarm1, numarm2, devise, padev, paht, frais, ug, qtenonEtq, qteEncours, qteEtiq, id)\n" +
                "VALUES \n" +
                "\t(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? ,? ,? ,? ,? ,? ,? , ?)";

        return jdbcTemplate.update(sql, t.getArticle(), t.getLibelle(), t.getLabo(), t.getDepot_org(), t.getEmpl_org(), t.getDepot_dest(), t.getEmpl_dest(), t.getLot(), t.getDatePer(), t.getLettre(),
                t.getQte(), t.getNumarm1(), t.getNumarm1(), t.getDevise(), t.getPadev(), t.getPaht(), t.getFrais(), t.getUg(), t.getQteNonEt(), t.getQteEnCoursEt(), t.getQteEt(), t.getMyId());
    }

    @Override
    public int insertL6_TransfertConseil(TransfertConseilReappro t) {
        String sql = "\n" +
                "INSERT INTO DBSUIVI..L6_TRANSFERT\n" +
                "\t(Article, libelle, labo, depotOrg, emplOrg, depotDest, emplDest, lot, dateper, lettre, qte, numarm1, numarm2, devise, padev, paht, frais, ug, qtenonEtq, qteEncours, qteEtiq, id,commentaire, urgent)\n" +
                "VALUES \n" +
                "\t(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? ,? ,? ,? ,? ,? ,? , ?,?,?)";

        return jdbcTemplate.update(sql, t.getArticle(), t.getLibelle(), t.getLabo(), t.getDepot_org(), t.getEmpl_org(), t.getDepot_dest(), t.getEmpl_dest(), t.getLot(), t.getDatePer(), t.getLettre(),
                t.getQte(), t.getNumarm1(), t.getNumarm1(), t.getDevise(), t.getPadev(), t.getPaht(), t.getFrais(), t.getUg(), t.getQteNonEt(), t.getQteEnCoursEt(), t.getQteEt(), t.getMyId(), t.getCommentaire(), t.getUrg());
    }

    @Override
    public String insert_FSIL(String ids, String commentaire, String depOrg, String depDest) {
        String sql = "exec v_bp_transfert ?, ?,?,?";
        String result = jdbcTemplate.queryForObject(sql, new Object[]{ids, commentaire, depOrg, depDest}, String.class);
        return result;
    }

    @Override
    public ProblemeStock testStock(String article, int qte, String depot, String lettre, String empl) {
        String sql = "exec v_bp_testStockConseilAppro ?,?,?,?,?";
        return jdbcTemplate.queryForObject(sql, new ProblemeStockMapper(), article, qte, depot, lettre, empl);
    }

    @Override
    public int stockPasVide(String depot_Dest, String empl_Dest) {
        return jdbcTemplate.queryForObject("select count(*) from VIEW_STOCK_LOT_EMPL_FRBP where  STEMPDEPOT=? and STEMPEMP=? and isnull(QTE_DISPO,0)>0",
                Integer.class, depot_Dest, empl_Dest);
    }

    @Override
    public byte[] generateReportTransfert(String td, String org, String dest, Integer isDouble, String users) {
        try {

            String hexes = Convert.decimalToHexadecimal(Integer.parseInt(td.substring(2, 10).concat("1")));
            InputStream jrxmlInputStream = getClass().getResourceAsStream("/report/transfertA5.jrxml");
            if (jrxmlInputStream == null) {
                throw new FileNotFoundException("Le fichier JRXML n'a pas été trouvé.");
            }
            JasperReport jasperReport = net.sf.jasperreports.engine.JasperCompileManager.compileReport(jrxmlInputStream);

            // Paramètres du rapport
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("code_TD", td);
            parameters.put("isDouble", isDouble);
            parameters.put("hexa", hexes);
            parameters.put("depot_org", org);
            parameters.put("depot_dest", dest);
            parameters.put("users", users);

            // Générer le rapport
            byte[] reportBytes = JasperRunManager.runReportToPdf(jasperReport, parameters, jdbcTemplate.getDataSource().getConnection());

            return reportBytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<List<Object>> conseilReappro(String article, String depotOrg, String depotDest) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("x_ListeReappro3")
                .declareParameters(
                        new SqlParameter("article", Types.VARCHAR),
                        new SqlParameter("depotOrg", Types.VARCHAR),
                        new SqlParameter("depotDest", Types.VARCHAR)

                )
                .returningResultSet("result1", new ConseilReapproMapper())
                .returningResultSet("result2", new TransfertConseilReapproMapper());

        Map<String, Object> results = jdbcCall.execute(null, null, article, depotOrg, depotDest);

        Map<String, List<Object>> mappedResults = new HashMap<>();

        List<List<Object>> resultList = new ArrayList<>();
        resultList.add((List<Object>) results.get("result1"));
        resultList.add((List<Object>) results.get("result2"));
        return resultList;
    }


}

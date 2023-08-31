package com.opham.prepa.repository.Transfert;

import com.opham.prepa.Utils.DataSourceConfig;
import com.opham.prepa.mapper.Transfert.*;
import com.opham.prepa.model.Transfert.Rangement;
import com.opham.prepa.model.Transfert.Transfert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;


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
}

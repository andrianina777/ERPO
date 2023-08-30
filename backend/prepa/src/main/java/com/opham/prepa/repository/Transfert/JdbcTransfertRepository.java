package com.opham.prepa.repository.Transfert;

import com.opham.prepa.Utils.DataSourceConfig;
import com.opham.prepa.mapper.Transfert.RotationDetailMapper;
import com.opham.prepa.mapper.Transfert.RotationMapper;
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
                .returningResultSet("result2", new RotationDetailMapper());

        Map<String, Object> results = jdbcCall.execute(depot, alle);

        Map<String, List<Object>> mappedResults = new HashMap<>();

        List<List<Object>> resultList = new ArrayList<>();
        resultList.add((List<Object>) results.get("result1"));
        resultList.add((List<Object>) results.get("result2"));
        return resultList;
    }
}

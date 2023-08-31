package com.opham.prepa.repository.Utils;

import com.opham.prepa.Utils.DataSourceConfig;
import com.opham.prepa.mapper.Utlis.*;
import com.opham.prepa.model.Utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcUtilsRepository implements UtilsRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSourceConfig dataSourceConfig;

    @Autowired
    public JdbcUtilsRepository(JdbcTemplate jdbcTemplate, DataSourceConfig dataSourceConfig) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public List<String> findAllAxe() {
        return jdbcTemplate.query("select distinct CLPR from FCL order by CLPR", new AxeMapper());
    }

    @Override
    public List<String> findParCode(String code) {
        return jdbcTemplate.query("select rtrim(KELIBELLE) as KELIBELLE from KEnumerations where rtrim(KECODE)=rtrim(?) order by KEORDRE",
                new EnumerationMapper(), code);
    }


    @Override
    public Alerte getAlerte(String code) {
        return jdbcTemplate.queryForObject("select rtrim(code) as code,val,Alerte from xTiming where rtrim(code) =rtrim(?)",
                new Object[]{code}, new AlerteMapper());
    }

    @Override
    public Etape getEtape() {
        return jdbcTemplate.queryForObject(" exec v_bp_etape_badge",
                new Object[]{}, new EtapeMapper());
    }

    @Override
    public Lock getLock(String xTable, String xCode, int isClose) {
        return jdbcTemplate.queryForObject("exec v_bp_lock ?,?,?"
                , new LockMapper(), xTable, xCode, isClose);
    }

    @Override
    public Droit getDoit(String xUser, String access) {
        return jdbcTemplate.queryForObject("exec v_bp_getDroit ?,?",
                new DroitMapper(), xUser, access);
    }

    @Override
    public List<Droit> listDoit(String xUser) {
        return jdbcTemplate.query("select isnull(rtrim(xGroupeCode),'') as CodeGroupe,isnull(xRead,0) as LIRE,isnull(xWrite,0) as WRITE,isnull(rtrim(xUser),'') as xUser ,isnull(rtrim(xDroit),'') as xDroit from v_xGroupeUsers  \n" +
                        "left join v_xGroupe on rtrim(xGroupeCode)=rtrim(xCode) left join v_xDroit on  rtrim(xGroupe)=rtrim(xGroupeCode) where  rtrim(xUser)=? ",
                new DroitMapper(), xUser);
    }

    @Override
    public List<Depot> listDepot(String critere) {
        String sql = "SELECT DPCODE, DPNOM, isnull(DPCENTRAL,0) as DPCENTRAL, isnull(DPCOLIS,0) as DPCOLIS, isnull(xDPVTE,0) as xDPVTE,isnull(DP_TRANSFERT,0) as DP_TRANSFERT, isnull(DP_PREPA,0) as  DP_PREPA FROM FDP WHERE ISNULL(DP_DESACTIVE, 0) = 0 " +
                (critere != null && !critere.isEmpty() ? "AND " + critere : "");

        return jdbcTemplate.query(sql, new DepotMapper());
    }

    @Override
    public List<Alle> listAlle(String depot, String critere) {
        String sql = "select xDEPOT,xALLE,isnull(xSTATUT,0) as xSTATUT from xEMP_DIGUE where rtrim(xDEPOT)=rtrim(?)" +
                (critere != null && !critere.isEmpty() ? "AND " + critere : "")+ "order by xALLE" ;

        return jdbcTemplate.query(sql, new AlleMapper(),depot);
    }
}
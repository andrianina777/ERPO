package com.opham.prepa.repository.Utils;

import com.opham.prepa.Utils.DataSourceConfig;
import com.opham.prepa.mapper.Apreparer.DetailPrepaMapper;
import com.opham.prepa.mapper.Utlis.*;
import com.opham.prepa.model.Utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcComboRepository implements ComboRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSourceConfig dataSourceConfig;

    @Autowired
    public JdbcComboRepository(JdbcTemplate jdbcTemplate, DataSourceConfig dataSourceConfig) {
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
        return jdbcTemplate.queryForObject("select code,val,Alerte from xTiming where rtrim(code) =rtrim(?)",
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
    public List<Depot> listDepot(String critaire) {
        return jdbcTemplate.query(
                "SELECT DPCODE, DPNOM, DPCENTRAL, DPCOLIS, xDPVTE, DP_TRANSFERT, DP_PREPA FROM FDP WHERE ISNULL(DP_DESACTIVE, 0) = 1 " +
                        (critaire != null && !critaire.isEmpty() ? "AND ?" : ""),
                new DepotMapper(),
                critaire
        );
    }
}

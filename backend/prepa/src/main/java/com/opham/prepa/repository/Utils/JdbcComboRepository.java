package com.opham.prepa.repository.Utils;

import com.opham.prepa.Utils.DataSourceConfig;
import com.opham.prepa.mapper.Apreparer.DetailPrepaMapper;
import com.opham.prepa.mapper.Utlis.*;
import com.opham.prepa.model.Utils.Alerte;
import com.opham.prepa.model.Utils.Droit;
import com.opham.prepa.model.Utils.Etape;
import com.opham.prepa.model.Utils.Lock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcComboRepository implements ComboRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSourceConfig dataSourceConfig;

    @Autowired
    public JdbcComboRepository(JdbcTemplate jdbcTemplate , DataSourceConfig dataSourceConfig) {
        this.jdbcTemplate = jdbcTemplate;
       this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public List<String> findAllAxe() {
        return jdbcTemplate.query("select distinct CLPR from FCL order by CLPR",new AxeMapper());
    }

    @Override
    public List<String> findParCode(String code) {
        return jdbcTemplate.query("select rtrim(KELIBELLE) as KELIBELLE from KEnumerations where rtrim(KECODE)=rtrim(?) order by KEORDRE",
                new EnumerationMapper(),code);
    }



    @Override
    public Alerte getAlerte(String code) {
        return jdbcTemplate.queryForObject("select code,val,Alerte from xTiming where rtrim(code) =rtrim(?)",
                new Object[] { code } , new AlerteMapper());
    }

    @Override
    public Etape getEtape() {
        return jdbcTemplate.queryForObject("select count(case when ETAPE='Attente Prep' then 1 end) as AttePrep,\n" +
                        "count(case when ETAPE='En cours Prep' then 1 end) as EncoursPrep,\n" +
                        "count(case when ETAPE='Attente Ctrl' then 1 end) as AtteCtrl,\n" +
                        "count(case when ETAPE='En cours Ctrl' then 1 end) as EncoursCtrl,\n" +
                        "count(case when ETAPE in ('Emballage','Atte Facture') then 1 end) as Emballage from x_CC_ANNUEL where DATE_COMMANDE>='2023/01/01'",
                new Object[] {  } , new EtapeMapper());
    }

    @Override
    public Lock getLock( String xTable,String xCode,int isClose) {
        return jdbcTemplate.queryForObject("exec v_bp_lock ?,?,?"
                 , new LockMapper(),xTable,xCode,isClose);
    }

    @Override
    public Droit getDoit(String xUser, String access) {
        return jdbcTemplate.queryForObject("exec v_bp_getDroit ?,?",
                 new DroitMapper(),xUser,access);
    }

    @Override
    public List<Droit> listDoit(String xUser) {
        return jdbcTemplate.query("select isnull(rtrim(xGroupeCode),'') as CodeGroupe,isnull(xRead,0) as LIRE,isnull(xWrite,0) as WRITE,isnull(rtrim(xUser),'') as xUser ,isnull(rtrim(xDroit),'') as xDroit from v_xGroupeUsers  \n" +
                        "left join v_xGroupe on rtrim(xGroupeCode)=rtrim(xCode) left join v_xDroit on  rtrim(xGroupe)=rtrim(xGroupeCode) where  rtrim(xUser)=? ",
                new DroitMapper(), xUser);
    }
}

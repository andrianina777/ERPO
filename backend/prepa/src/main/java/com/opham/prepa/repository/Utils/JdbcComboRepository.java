package com.opham.prepa.repository.Utils;

import com.opham.prepa.mapper.Apreparer.InfoCmdMapper;
import com.opham.prepa.mapper.Utlis.AlerteMapper;
import com.opham.prepa.mapper.Utlis.AxeMapper;
import com.opham.prepa.mapper.Utlis.EnumerationMapper;
import com.opham.prepa.mapper.Utlis.EtapeMapper;
import com.opham.prepa.model.Apreparer.InfoCommande;
import com.opham.prepa.model.Utils.Alerte;
import com.opham.prepa.model.Utils.Axe;
import com.opham.prepa.model.Utils.Enumeration;
import com.opham.prepa.model.Utils.Etape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcComboRepository implements ComboRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

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
                        "count(case when ETAPE='Emballage' then 1 end) as Emballage from x_CC_ANNUEL where DATE_COMMANDE>='2023/01/01'",
                new Object[] {  } , new EtapeMapper());
    }
}

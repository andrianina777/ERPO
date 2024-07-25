package com.opham.prepa.repository.AppelEntrant;

import com.opham.prepa.Utils.DataSourceConfig;
import com.opham.prepa.mapper.AppelEntrant.AppelEntrantMapper;
import com.opham.prepa.model.AppelEntrant.AppelEntrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class JdbcAppelEntrantRepository implements AppelEntrantRepository{
    private final JdbcTemplate jdbcTemplate;
    // private final DynamicDataSourceConfig dynamicDataSourceConfig;
    private final DataSourceConfig dataSourceConfig;

    @Autowired
    public JdbcAppelEntrantRepository(JdbcTemplate jdbcTemplate, DataSourceConfig dataSourceConfig) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public List<AppelEntrant> findAppelEntrant(Date date_deb , Date date_fin) {
        //Requête qui affiche les appels entrant
        return jdbcTemplate.query("select SEQ,CALLDATE,SRC,NUMERO,NUMERO_INDICATIF,CLIENT,DCONTEXT,DST,DSTCHANNEL,POSTE_INTERNE,DURATION,BILLSEC,DISPOSITION,UNIQUEID,ETAT,STATUT,DEPUIS from CALLING_OPHAM..VIEW_APPEL_ENTRANT where  convert(date,CALLDATE) between ? and ? ",
                new AppelEntrantMapper(),date_deb,date_fin);
    }


    @Override
    public void updateEtat(Long AppelId) {
        String sql = "update CALLING_OPHAM..FAPPEL set ETAT=1 where SEQ=?";
        jdbcTemplate.update(sql, AppelId);
    }

    @Override
    public void updateEtat1(Long AppelId) {
        String sql = "update CALLING_OPHAM..FAPPEL set ETAT=2 where SEQ=?";
        jdbcTemplate.update(sql, AppelId);
    }
}

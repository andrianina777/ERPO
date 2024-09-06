package com.opham.prepa.repository.AppelEntrant;

import com.opham.prepa.Utils.DataSourceConfig;
import com.opham.prepa.mapper.AppelEntrant.ActionFirstAppelMapper;
import com.opham.prepa.mapper.AppelEntrant.AppelEntrantMapper;
import com.opham.prepa.mapper.AppelEntrant.AppelSortantMapper;
import com.opham.prepa.model.AppelEntrant.AppelEntrant;
import com.opham.prepa.model.AppelEntrant.AppelSortant;
import com.opham.prepa.model.AppelEntrant.ActionFirstAppel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
        return jdbcTemplate.query("select SEQ,CALLDATE,SRC,NUMERO,NUMERO_INDICATIF,CLIENT,DCONTEXT,DST,DSTCHANNEL,POSTE_INTERNE,DURATION,BILLSEC,DISPOSITION,UNIQUEID,ETAT,STATUT,DEPUIS,DATERAPPEL_1,DATERAPPEL_2,POSTE_RAP1,POSTE_RAP2 from CALLING_OPHAM..VIEW_APPEL_ENTRANT where  convert(date,CALLDATE) between ? and ? ",
                new AppelEntrantMapper(),date_deb,date_fin);
    }

    @Override
    public List<AppelSortant> findAppelSortant(Date date_sortant , String numero) {
        return jdbcTemplate.query("select SEQ,CALLDATE,SRC,NUMERO,NUMERO_INDICATIF,CLIENT,DCONTEXT,DST,DSTCHANNEL,POSTE_INTERNE,DURATION,BILLSEC,DISPOSITION,UNIQUEID,ETAT from CALLING_OPHAM..VIEW_APPEL_SORTANT where  NUMERO =? and CALLDATE>? ",
                new AppelSortantMapper(),numero,date_sortant);
    }

    @Override
    public List<ActionFirstAppel> findActionFirstAppel() {
        return jdbcTemplate.query("select SEQ,ACTION from CALLING_OPHAM..ACTIONS order by SEQ",
                new ActionFirstAppelMapper());
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

    @Override
    public int updateEtatSortie(BigDecimal seq, int etat, BigDecimal seqSortant, int rappel, String src_sortant) {
        String sql = "execute CALLING_OPHAM..bp_majEtatAppel ?,?,?,?,?";
       return jdbcTemplate.update(sql, seq,etat,seqSortant,rappel,src_sortant);
    }
}

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
        return jdbcTemplate.query("select CLPR from FCL group by CLPR", new AxeMapper());
    }

    @Override
    public List<Alle> listAlle(String depot, String critere) {
        String sql = "select xDEPOT,xALLE,isnull(xSTATUT,0) as xSTATUT from xEMP_DIGUE where rtrim(xDEPOT)=rtrim(?)" +
                (critere != null && !critere.isEmpty() ? "AND " + critere : "")+ "order by xALLE" ;

        return jdbcTemplate.query(sql, new AlleMapper(),depot);
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
                (critere != null && !critere.isEmpty() ? "AND " + critere : "")+"order by DPCODE ";

        return jdbcTemplate.query(sql, new DepotMapper());
    }



    @Override
    public List<Article> listArticle(String critere) {
        String sql = "\t\tselect ARCODE,ARLIB,ARFO,L.FONOM as NON_LABO,ARDEPART,F.DTLIB as FAMILLE,ARFAM,C.FPLIB as CATEGORIE,xARTP_GAM,G.FONOM as GAMME,xARTP_DISTR,D.FONOM as DISTRIBUTEUR,xARTP_FRNS,P.FONOM as FOURNIS_PRINC\n" +
                "\t\t,xARTP_FAB,B.FONOM as FABRICANT,xARTP_PLATFORME,PL.FONOM as PLATFORME,ARCHEFP,SC.FPLIB as SOUS_CAT,xARTP_DCI,ARLIBXENO,ARFRANCOFO,\n" +
                "\t\tcase when isnull(ARGENERIQUE,0)=0 then 'NORMAL' when isnull(ARGENERIQUE,0)=1 then 'GENERIQUE' when isnull(ARGENERIQUE,0)=2 then 'SPECIALITE' when isnull(ARGENERIQUE,0)=3 then 'GENERIQUE DE MARQUE' end as TYPE,\n" +
                "\t\tcase when isnull(xARTP_FROID,0)=0 then 'NORMAL' when isnull(xARTP_FROID,0)=1 then 'FROID' when isnull(xARTP_FROID,0)=2 then 'FRAIS' end as conversation,\n" +
                "\t\tcase when isnull(xARTP_STATSPEC,0)=0 then 'NORMAL' when isnull(xARTP_STATSPEC,0)=1 then 'STUPEFIANT' when isnull(xARTP_STATSPEC,0)=2 then 'PSYCHOTROPE' when isnull(xARTP_STATSPEC,0)=3 then 'HOSPITALIER' end as STATUT \n" +
                "\t\tfrom VIEW_FAR left join FFO L on L.FOCODE=ARFO left join FDT F on F.DTCODE=ARDEPART \n" +
                "\t\tleft join FFP C on C.FPCODE=ARFAM and isnull(C.FTYPE,0)=1 left join FFO G on G.FOCODE=xARTP_GAM and isnull(G.FOANCIEN,0)=0 and isnull(G.FOGAMME,0)=1\n" +
                "\t\tleft join FFO D on D.FOCODE=xARTP_DISTR and isnull(D.FOANCIEN,0)=0 and isnull(D.FODISTR,0)=1\n" +
                "\t\tleft join FFO P on P.FOCODE=xARTP_FRNS and isnull(P.FOANCIEN,0)=0 and isnull(P.FOFOURNIS,0)=1\n" +
                "\t\tleft join FFO B on B.FOCODE=xARTP_FAB and isnull(B.FOANCIEN,0)=0 and isnull(B.FOFABRIC,0)=1\n" +
                "\t\tleft join FFO PL on PL.FOCODE=xARTP_PLATFORME and isnull(PL.FOANCIEN,0)=0 and isnull(PL.FOPLATEFORME,0)=1\n" +
                "\t\tleft join FFP SC on SC.FPCODE=ARCHEFP and isnull(SC.FTYPE,0)=1\n" +
                "\t\twhere isnull(AROLD,0)=0 " +
                (critere != null && !critere.isEmpty() ? "AND " + critere : "")+ "order by ARCODE,ARLIB" ;

        return jdbcTemplate.query(sql, new ArticleMapper());
    }

    @Override
    public List<Labo> listLabo(String critere) {
        String sql = "select rtrim(FOCODE),rtrim(FONOM),rtrim(FONOM1) from FFO where isnull(FOANCIEN,0)=0 " +
                (critere != null && !critere.isEmpty() ? "AND " + critere : "") + "order by FOCODE,FONOM ";
        return jdbcTemplate.query(sql, new LaboMapper());
    }

    @Override
    public List<Famille> listFamille() {
        String sql = "select rtrim(DTCODE),rtrim(DTLIB) from FDT order by DTCODE,DTLIB";
        return jdbcTemplate.query(sql, new FamilleMapper());
    }
}
package com.opham.prepa.repository.Utils;

import com.opham.prepa.model.Utils.CauseRacine;
import com.opham.prepa.model.Utils.*;

import java.util.Date;
import java.util.List;

public interface UtilsRepository {

    List<String> findAllAxe() ;

    List<String> findallemetteur();

    List<Proc> listEnum();
    List<String> findalldpt();
    List<String> findalldpt2();
    List<String> findalluser();

    List<String> findParCode(String code);

    Alerte getAlerte(String code);
    Etape getEtape();
    Lock getLock(String xTable,String xCode,int isClose);

    Droit getDoit(String xUser,String access);

    List<Droit> listDoit(String xUser);

    List<Depot> listDepot(String critere);

    List<Alle> listAlle(String depot,String critere);

    List<Article> listArticle(String critere);

    List<Labo> listLabo(String critere);

    List<Famille> listFamille();

    List<Client> listClient();

    //code de cause racine
    void insertFNCPS_USER(String ncpsuserusercode,String ncpsuserncpscode);
    List<Userinsert> listUserinsert(String ncpsuserncpscode);
    List<CauseRacine> findcauseraccode(String code_causerac);
    void insertFNCPS_CAUSE(String causeRacCode, String causeRacDescription, Date causeRacDate, int causeRacValid, String causeRacProcImput);
    void updateFCAUSE_RAC(int causeRacSeq, String causeRacCode, Date causeRacDate, String causeRacDescription, String causeRacProcImput, int causeRacValide);
    void deleteFCAUSE_RAC(int causeracseq);

    //fin code de cause racine


    List<Proc> listProc(String enumType);

    List<String> findallactiontype();

    void insertAction(String actionNcpscode, int actionValide, int actionMiseappli, String actionDescription,int actionCausesseq, int actionType);
}

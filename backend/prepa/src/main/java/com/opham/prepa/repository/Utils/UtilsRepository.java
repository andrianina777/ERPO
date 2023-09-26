package com.opham.prepa.repository.Utils;

import com.opham.prepa.model.Utils.*;

import java.util.List;

public interface UtilsRepository {

    List<String> findAllAxe() ;

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
}

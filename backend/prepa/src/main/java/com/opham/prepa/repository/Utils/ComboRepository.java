package com.opham.prepa.repository.Utils;

import com.opham.prepa.model.Utils.*;

import java.util.List;

public interface ComboRepository {

    List<String> findAllAxe() ;

    List<String> findParCode(String code);

    Alerte getAlerte(String code);
    Etape getEtape();
    Lock getLock(String xTable,String xCode,int isClose);

    Droit getDoit(String xUser,String access);

    List<Droit> listDoit(String xUser);
}

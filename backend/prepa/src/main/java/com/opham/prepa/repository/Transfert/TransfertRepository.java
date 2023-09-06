package com.opham.prepa.repository.Transfert;

import com.opham.prepa.model.Transfert.ProblemeStock;
import com.opham.prepa.model.Transfert.Rotation;
import com.opham.prepa.model.Transfert.Transfert;

import java.util.List;

public interface TransfertRepository {
    List<List<Object>> listRotation(String depot,String alle);
    List<List<Object>> listColisIncomplet(String depotOrg,String depotDest,String alle);

    List<List<Object>> listPetitColis(String depotOrg,String depotDest,String alle);

    List<List<Object>> listRangement(String depot);

    int insertL6_Transfert(Transfert t);

    String insert_FSIL(String ids, String commentaire);


    ProblemeStock testStock(String article,int qte,String depot,String lettre,String empl);
    ProblemeStock stockPasVide(String article,String depot_Dest,String empl_Dest);

}

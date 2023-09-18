package com.opham.prepa.repository.Transfert;

import com.opham.prepa.model.Transfert.ProblemeStock;
import com.opham.prepa.model.Transfert.Rotation;
import com.opham.prepa.model.Transfert.Transfert;
import com.opham.prepa.model.Transfert.TransfertConseilReappro;

import java.util.List;

public interface TransfertRepository {
    List<List<Object>> listRotation(String depot,String alle);
    List<List<Object>> listColisIncomplet(String depotOrg,String depotDest,String alle);

    List<List<Object>> listPetitColis(String depotOrg,String depotDest,String alle);

    List<List<Object>> listRangement(String depot);

    int insertL6_Transfert(Transfert t);

    int insertL6_TransfertConseil(TransfertConseilReappro t);

    String insert_FSIL(String ids, String commentaire,String depOrg,String depDest);


    ProblemeStock testStock(String article,int qte,String depot,String lettre,String empl);
    int stockPasVide(String depot_Dest,String empl_Dest);

    byte[] generateReportTransfert(String td,String org,String dest, Integer isDouble,String users);

    List<List<Object>> conseilReappro(String article,String depotOrg,String depotDest);




}

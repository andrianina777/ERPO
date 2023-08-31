package com.opham.prepa.repository.Transfert;

import com.opham.prepa.model.Transfert.Rotation;
<<<<<<< HEAD
=======
import com.opham.prepa.model.Transfert.Transfert;
>>>>>>> backend_prepa

import java.util.List;

public interface TransfertRepository {
    List<List<Object>> listRotation(String depot,String alle);
<<<<<<< HEAD
=======
    List<List<Object>> listColisIncomplet(String depotOrg,String depotDest,String alle);

    List<List<Object>> listPetitColis(String depotOrg,String depotDest,String alle);

    List<List<Object>> listRangement(String depot);

    int insertL6_Transfert(Transfert t);
>>>>>>> backend_prepa

}

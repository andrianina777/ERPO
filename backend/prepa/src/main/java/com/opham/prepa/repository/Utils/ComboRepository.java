package com.opham.prepa.repository.Utils;

import com.opham.prepa.model.Utils.Alerte;
import com.opham.prepa.model.Utils.Axe;
import com.opham.prepa.model.Utils.Enumeration;

import java.util.List;

public interface ComboRepository {

    List<String> findAllAxe() ;

    List<String> findParCode(String code);

    Alerte getAlerte(String code);
}

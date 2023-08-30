package com.opham.prepa.repository.Transfert;

import com.opham.prepa.model.Transfert.Rotation;

import java.util.List;

public interface TransfertRepository {
    List<List<Object>> listRotation(String depot,String alle);

}

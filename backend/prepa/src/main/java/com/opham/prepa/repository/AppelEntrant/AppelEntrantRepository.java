package com.opham.prepa.repository.AppelEntrant;

import com.opham.prepa.model.AppelEntrant.AppelEntrant;

import java.util.Date;
import java.util.List;

public interface AppelEntrantRepository {
    List<AppelEntrant> findAppelEntrant();

    void updateEtat(Long AppelId);

}

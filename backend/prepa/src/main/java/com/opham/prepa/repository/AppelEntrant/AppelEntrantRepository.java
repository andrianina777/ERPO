package com.opham.prepa.repository.AppelEntrant;

import com.opham.prepa.model.AppelEntrant.AppelEntrant;

import java.util.Date;
import java.util.List;

public interface AppelEntrantRepository {
    List<AppelEntrant> findAppelEntrant(Date date_deb, Date date_fin);

    void updateEtat(Long AppelId);
    void updateEtat1(Long AppelId);

}

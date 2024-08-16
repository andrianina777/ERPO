package com.opham.prepa.repository.AppelEntrant;

import com.opham.prepa.model.AppelEntrant.AppelEntrant;
import com.opham.prepa.model.AppelEntrant.AppelSortant;
import com.opham.prepa.model.AppelEntrant.ActionFirstAppel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface AppelEntrantRepository {
    List<AppelEntrant> findAppelEntrant(Date date_deb, Date date_fin);
    List<AppelSortant> findAppelSortant(Date date_sortant, String numero);
    List<ActionFirstAppel> findActionFirstAppel();

    void updateEtat(Long AppelId);
    void updateEtat1(Long AppelId);

    int updateEtatSortie(BigDecimal seq, int etat, BigDecimal seqSortie);

}

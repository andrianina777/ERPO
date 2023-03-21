package com.opham.prepa.repository;

import com.opham.prepa.model.Commande;
import com.opham.prepa.model.DetailPrep;
import com.opham.prepa.model.LigneCommande;

import java.util.Date;
import java.util.List;

public interface CommandeRepository {
  /*  int save(Commande book);

    int update(Commande book);

    Commande findById(Long id);

    int deleteById(Long id);*/

    List<Commande> findAll();

    List<Commande> findByDate(String groupe, Date dateliv);

    List<LigneCommande> findLigneCmdebyCode(String code);

    List<DetailPrep> listDetailBP(String code_CC);



}

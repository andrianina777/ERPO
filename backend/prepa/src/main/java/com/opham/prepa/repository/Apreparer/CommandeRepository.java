package com.opham.prepa.repository.Apreparer;

import com.opham.prepa.model.Apreparer.Commande;
import com.opham.prepa.model.Apreparer.DetailPrep;
import com.opham.prepa.model.Apreparer.InfoCommande;
import com.opham.prepa.model.Apreparer.LigneCommande;

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

    InfoCommande plusInfoCmd(String code_cc);

    int updateInfoCmd(InfoCommande in);


}

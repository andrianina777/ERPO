package com.opham.prepa.repository.Apreparer;

import com.opham.prepa.model.Apreparer.Commande;
import com.opham.prepa.model.Apreparer.DetailPrep;
import com.opham.prepa.model.Apreparer.InfoCommande;
import com.opham.prepa.model.Apreparer.LigneCommande;
import com.opham.prepa.model.EnPreparation.DetailBP;
import com.opham.prepa.model.EnPreparation.EnCoursCMD;
import com.opham.prepa.model.EnPreparation.EnCoursPrepBP;
import com.opham.prepa.model.EnPreparation.ListLigneBP;
import com.opham.prepa.model.Utils.Credentials;
import com.opham.prepa.model.genererBP.ListeCmd;

import java.util.Date;
import java.util.List;

public interface CommandeRepository {
    List<Commande> findAll();

    List<Commande> findByDate(String groupe, Date dateliv);

    List<LigneCommande> findLigneCmdebyCode(String code);

    List<DetailPrep> listDetailBP(String code_CC);

    InfoCommande plusInfoCmd(String code_cc);

    int updateInfoCmd(InfoCommande in);

    List<List<Object>>   genererBP(String code);

    List<List<Object>> preparerCmd(String code);

    String insert_BP(String ids,String code_CC,String depot);

    String getId();

    int insertL6(ListeCmd l);

    byte[] generateReport(String codeBP, Integer isDouble);

    int atteTransfert(String code_CC, int xSeq);

    List<EnCoursCMD> listEnCoursPrepa(int prepspecif,int isVisible,Date dateliv);
    Credentials checkCredentials(Credentials credentials);
    void updateCCResume(String codeCC);
    void updateFrais(String codeCC);

    List<EnCoursPrepBP> listBPEncours(int etat);

    List<ListLigneBP> listLigneBP(String BP);
    List<DetailBP> detailBP(String BP);

    List<DetailBP> detailBPCTRL(String BP);

    EnCoursPrepBP infoEncoursBP(String BP);





}

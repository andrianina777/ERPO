package com.opham.prepa.repository.Capa;

import com.opham.prepa.model.Capa.AtteVal;
import com.opham.prepa.model.Capa.Capa;
import com.opham.prepa.model.Capa.CauseRac;
import com.opham.prepa.model.Capa.ktable;
import com.opham.prepa.model.Utils.Action;

import java.util.Date;
import java.util.List;

public interface CapaRepository {
    List<Capa> listCapa(String code, String description, String emetteur, String libelleDep, String type, Date date, Integer causevalid, Integer causeatte, Integer attevalid, Integer attemiseappli, Integer appli);
    void updateFNCPS(int ncpsSeq,String ncpsCode,Date ncpsDate, String ncpsDescription, String ncpsDepartement, String ncpsType, String ncpsModeDetect, String ncpsProcessusDetect);
    void insertFNCPS(String ncpsEmetteur, String ncpsDescription , String ncpsDepartement, Date ncpsDate, int ncpsEtat, int ncpsValide);
    List<AtteVal> listatteval();
    List<CauseRac> findByCodeAction(int actionCauseseq);
    void deleteFACTION(int actionSeq);
    void updateFACTION(int actionSeq,String actionResponsable,Date actionDateprev);
    void updateFACTION2(int actionSeq,Date actionDateanaleffic,Date actionDaterelleeffic);
    void updateFACTION3(int actionSeq,Date actionDaterelleeffic,String actionCommentaire);
    List<ktable> findktable();
    void updateKTABLE(int seqk);
    void insertKTABLE();
}

package com.opham.prepa.mapper.EnPreparation;

import com.opham.prepa.model.EnPreparation.EnCoursCMD;
import com.opham.prepa.model.EnPreparation.EnCoursPrepBP;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class EnCoursPrepBPMapper  implements RowMapper<EnCoursPrepBP> {
    @Override
    public EnCoursPrepBP mapRow(ResultSet rs, int rowNum) throws SQLException {
        EnCoursPrepBP cmd = new EnCoursPrepBP();
        cmd.setCodeBP(rs.getString("BPCODE"));
        cmd.setCodeCC(rs.getString("BPCC"));
        cmd.setDate(rs.getDate("BPDATE"));
        cmd.setDepot(rs.getString("BPDEPOT"));
        cmd.setCodeClient(rs.getString("BPCL"));
        cmd.setNomClient(rs.getString("BPNOM"));
        cmd.setEtat_BP(rs.getString("ETAT_BP"));
        cmd.setQteTotal(rs.getInt("QTE_TOTAL"));
        cmd.setNbLigne(rs.getInt("NB_LIGNE"));
        cmd.setDepuis(rs.getInt("DEPUIS"));
        cmd.setDetail(rs.getString("DETAIL"));
        cmd.setEmpl(rs.getString("EMP_ATTE_CTRL"));
        cmd.setSortieMag(rs.getInt("SORTIE_MAG"));
        cmd.setSituation_Mag(rs.getString("SITUATION_MAG"));
        cmd.setCodeBP_traite(rs.getString("CODEBP_TRAITE"));
        cmd.setCodeCC_traite(rs.getString("CODECC_TRAITE"));
        cmd.setURGENT(rs.getInt("URGENT"));
        cmd.setA_PRENDRE(rs.getInt("A_PRENDRE"));
        cmd.setPRIO(rs.getInt("PRIO"));
        cmd.setGARDE(rs.getInt("GARDE"));
        cmd.setAO(rs.getInt("AO"));
        cmd.setValeur(rs.getString("CLVALEUR"));

        return cmd;
    }
}
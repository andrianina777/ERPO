package com.opham.prepa.mapper.Transfert;

import com.opham.prepa.model.Transfert.Rotation;
import com.opham.prepa.model.Transfert.RotationDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RotationDetailMapper implements RowMapper<RotationDetail> {

    @Override
    public RotationDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        RotationDetail cmd = new RotationDetail();
        cmd.setArticle(rs.getString("STEMPAR"));
        cmd.setLibelle(rs.getString("ARLIB"));
        cmd.setLabo(rs.getString("STFO"));
        cmd.setDepot_org(rs.getString("xDEPOT"));
        cmd.setEmpl_org(rs.getString("xEMP_ORG"));
        cmd.setEmpl_dest(rs.getString("xDEPOT"));
        cmd.setEmpl_dest(rs.getString("xEMP_DEST"));
        cmd.setLot(rs.getString("STLOT"));
        cmd.setDatePer(rs.getDate("NLOTDATEPER"));
        cmd.setLettre(rs.getString("STLETTRE"));
        cmd.setQte(rs.getInt("STEMPQTE"));
        cmd.setNumarm1(rs.getString("STNUMARM1"));
        cmd.setNumarm2(rs.getString("STNUMARM2"));
        cmd.setDevise(rs.getString("STDEVISE"));
        cmd.setPadev(rs.getDouble("STPADEV"));
        cmd.setPaht(rs.getDouble("STPAHT"));
        cmd.setFrais(rs.getDouble("STFRAIS"));
        cmd.setUg(rs.getInt("UG"));
        cmd.setQteNonEt(rs.getInt(19));
        cmd.setQteEnCoursEt(rs.getInt(20));
        cmd.setQteEt(rs.getInt(21));
        return cmd;
    }
}

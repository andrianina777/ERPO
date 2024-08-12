package com.opham.prepa.mapper.AppelEntrant;

import com.opham.prepa.model.AppelEntrant.AppelSortant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppelSortantMapper implements RowMapper<AppelSortant> {
    public AppelSortant mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppelSortant cmd = new AppelSortant();
        cmd.setSeq(rs.getLong(1));
        cmd.setCalldate_sortant(rs.getTimestamp(2));
        cmd.setSrc_sortant(rs.getString(3));
        cmd.setNumero_sortant(rs.getString(4));
        cmd.setNumero_indicatif_sortant(rs.getString(5));
        cmd.setClient_sortant(rs.getString(6));
        cmd.setDcontext_sortant(rs.getString(7));
        cmd.setDst_sortant(rs.getString(8));
        cmd.setDstchannel_sortant(rs.getString(9));
        cmd.setPoste_interne_sortant(rs.getString(10));
        cmd.setDuration_sortant(rs.getInt(11));
        cmd.setBillsec_sortant(rs.getInt(12));
        cmd.setDisposition_sortant(rs.getString(13));
        cmd.setUniqueid_sortant(rs.getString(14));
        cmd.setEtat_sortant(rs.getInt(15));
        //cmd.setStatut(rs.getString(16));
        //cmd.setDepuis(rs.getLong(17));
        return cmd;

    }
}

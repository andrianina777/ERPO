package com.opham.prepa.mapper.AppelEntrant;

import com.opham.prepa.model.AppelEntrant.AppelEntrant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class
AppelEntrantMapper implements RowMapper<AppelEntrant> {
    public AppelEntrant mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppelEntrant cmd = new AppelEntrant();
        cmd.setSeq(rs.getLong(1));
        cmd.setCalldate(rs.getTimestamp(2));
        cmd.setSrc(rs.getString(3));
        cmd.setNumero(rs.getString(4));
        cmd.setNumero_indicatif(rs.getString(5));
        cmd.setClient(rs.getString(6));
        cmd.setDcontext(rs.getString(7));
        cmd.setDst(rs.getString(8));
        cmd.setDstchannel(rs.getString(9));
        cmd.setPoste_interne(rs.getString(10));
        cmd.setDuration(rs.getInt(11));
        cmd.setBillsec(rs.getInt(12));
        cmd.setDisposition(rs.getString(13));
        cmd.setUniqueid(rs.getString(14));
        cmd.setEtat(rs.getInt(15));
        cmd.setStatut(rs.getString(16));
        cmd.setDepuis(rs.getLong(17));
        return cmd;

    }
}

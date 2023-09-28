package com.opham.prepa.mapper.Transfert;

import com.opham.prepa.model.Transfert.SuiviTD;
import com.opham.prepa.model.Transfert.TransfertConseilReappro;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SuiviTDMapper  implements RowMapper<SuiviTD> {
    @Override
    public SuiviTD mapRow(ResultSet rs, int rowNum) throws SQLException {
        SuiviTD td = new SuiviTD();
        td.setCode(rs.getString(1));
        td.setDate(rs.getDate(2));
        td.setEmpl_org(rs.getString(3));
        td.setLibelle(rs.getString(4));
        td.setQte(rs.getInt(5));
        td.setEmpl_dest(rs.getString(6));
        td.setCommentaire(rs.getString(7));
        td.setMotif(rs.getString(8));
        return td;
    }
}

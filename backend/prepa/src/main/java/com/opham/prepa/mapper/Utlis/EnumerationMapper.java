package com.opham.prepa.mapper.Utlis;

import com.opham.prepa.model.Utils.Axe;
import com.opham.prepa.model.Utils.Enumeration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;



public class EnumerationMapper implements RowMapper<Enumeration> {
    @Override
    public Enumeration mapRow(ResultSet rs, int rowNum) throws SQLException {
        Enumeration enuma = new Enumeration();
     //   enuma.setCode(rs.getString("KECODE"));
        enuma.setLibelle(rs.getString("KELIBELLE"));
        return enuma;
    }
}

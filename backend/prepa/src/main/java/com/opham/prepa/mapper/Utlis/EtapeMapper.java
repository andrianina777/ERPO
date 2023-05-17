package com.opham.prepa.mapper.Utlis;


import com.opham.prepa.model.Utils.Alerte;
import com.opham.prepa.model.Utils.Etape;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EtapeMapper implements RowMapper<Etape> {
    @Override
    public Etape mapRow(ResultSet rs, int rowNum) throws SQLException {
        Etape etape = new Etape();
        etape.setAttePrep(rs.getInt("AttePrep"));
        etape.setEncoursPrep(rs.getInt("EncoursPrep"));
        etape.setAtteCtrl(rs.getInt("AtteCtrl"));
        etape.setEncoursCtrl(rs.getInt("EncoursCtrl"));
        etape.setEmballage(rs.getInt("Emballage"));
        return etape;
    }
}

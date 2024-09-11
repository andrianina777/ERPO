package com.opham.prepa.mapper.Utlis;

import com.opham.prepa.model.Utils.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client d = new Client();
        d.setClcode(rs.getString(1));
        d.setClnom1(rs.getString(2));
        d.setCladr1(rs.getString(3));
        d.setCladr2(rs.getString(4));
        d.setClstatus(rs.getInt(5));
        d.setClsa(rs.getString(6));
        d.setClvaleur(rs.getString(7));

        return d;
    }
}

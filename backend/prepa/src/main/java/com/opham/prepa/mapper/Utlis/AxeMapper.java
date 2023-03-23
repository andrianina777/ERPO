package com.opham.prepa.mapper.Utlis;

import com.opham.prepa.model.Utils.Axe;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AxeMapper implements RowMapper<Axe> {
    @Override
    public Axe mapRow(ResultSet rs, int rowNum) throws SQLException {
        Axe axe = new Axe();
        axe.setAxe(rs.getString("CLPR"));
        return axe;
    }
}

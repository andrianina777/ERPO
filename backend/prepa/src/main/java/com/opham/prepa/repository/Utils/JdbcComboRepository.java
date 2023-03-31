package com.opham.prepa.repository.Utils;

import com.opham.prepa.mapper.Utlis.AxeMapper;
import com.opham.prepa.mapper.Utlis.EnumerationMapper;
import com.opham.prepa.model.Utils.Axe;
import com.opham.prepa.model.Utils.Enumeration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcComboRepository implements ComboRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<String> findAllAxe() {
        return jdbcTemplate.query("select distinct CLPR from FCL order by CLPR",new AxeMapper());
    }

    @Override
    public List<String> findParCode(String code) {
        return jdbcTemplate.query("select rtrim(KELIBELLE) as KELIBELLE from KEnumerations where rtrim(KECODE)=rtrim(?) order by KEORDRE",
                new EnumerationMapper(),code);
    }
}

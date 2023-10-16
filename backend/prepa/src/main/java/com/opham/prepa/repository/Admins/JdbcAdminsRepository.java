package com.opham.prepa.repository.Admins;

import com.opham.prepa.Utils.DataSourceConfig;
import com.opham.prepa.mapper.Admins.AccesMapper;
import com.opham.prepa.mapper.Admins.GroupeMapper;
import com.opham.prepa.mapper.Admins.UsersMapper;
import com.opham.prepa.mapper.Transfert.SuiviTDMapper;
import com.opham.prepa.model.Admins.Acces;
import com.opham.prepa.model.Admins.Groupe;
import com.opham.prepa.model.Admins.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JdbcAdminsRepository implements AdminsRepository{

    private final JdbcTemplate jdbcTemplate;
    private final DataSourceConfig dataSourceConfig;

    @Autowired
    public JdbcAdminsRepository(JdbcTemplate jdbcTemplate, DataSourceConfig dataSourceConfig) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSourceConfig = dataSourceConfig;
    }
    @Override
    public List<Groupe> listGroupe() {
        String sql = "select rtrim(xCode),rtrim(xLibelle) from v_xGroupe order by xCode,xLibelle ";
        return jdbcTemplate.query(sql, new GroupeMapper());
    }

    @Override
    public List<Users> listUsers(String groupe) {
        String sql = "select rtrim(name),isnull(rtrim(fullname),'') from master..syslogins left join Equagestion..v_xGroupeUsers on  name=xUser and rtrim(xGroupeCode)=? where isnull(status,0)<>2  and xUser is null order by name,fullname";
        return jdbcTemplate.query(sql, new UsersMapper(),groupe);
    }

    @Override
    public List<Users> listMembres(String groupe) {
        String sql = "select xUser,isnull(rtrim(fullname),'') from Equagestion..v_xGroupeUsers inner join master..syslogins on name=xUser where isnull(status,0)<>2  and xGroupeCode=? order by name,fullname";
        return jdbcTemplate.query(sql, new UsersMapper(),groupe);
    }

    @Override
    public List<Acces> listAcces(String groupe) {
        String sql = "exec v_bp_list_acces ?";
        return jdbcTemplate.query(sql, new AccesMapper(),groupe);
    }

    @Override
    public List<Acces> listDroit(String groupe) {
        String sql = "exec v_bp_getSesDroit ? ";
        return jdbcTemplate.query(sql, new AccesMapper(),groupe);
    }

    @Override
    public int insert_Membres(String groupe, String users) {
        String sql="INSERT INTO Equagestion.dbo.v_xGroupeUsers (xGroupeCode, xUser) VALUES (?,? )";
        return jdbcTemplate.update(sql,groupe,users);
    }

    @Override
    public int insert_droit(Acces A) {
        String sql="INSERT INTO Equagestion..v_xDroit(xGroupe, xDroit, xRead, xWrite) VALUES (?,?,?,?)";
        return jdbcTemplate.update(sql,A.getGroupe(),A.getCode(),A.isRead(),A.isWrite());
    }

    @Override
    public int delete_Membres(String groupe, String users) {
        String sql="delete from v_xGroupeUsers where xGroupeCode=? and xUser=?";
        return jdbcTemplate.update(sql,groupe,users);
    }

    @Override
    public int delete_droit(String groupe, String droit) {
        String sql="delete from v_xDroit where xGroupe=? and xDroit=?";
        return jdbcTemplate.update(sql,groupe,droit);
    }

    @Override
    public int update_droit(String groupe, String droit, boolean w, boolean r) {
        String sql="update v_xDroit set xRead=?,xWrite=? where xGroupe=? and xDroit=?";
        return jdbcTemplate.update(sql,r,w,groupe,droit);
    }

    @Override
    public int insert_groupe(Groupe G) {
        String sql="INSERT INTO Equagestion..v_xGroupe ( xCode, xLibelle) VALUES (?,?)";
        return jdbcTemplate.update(sql,G.getCode(),G.getLibelle());
    }


}

package com.opham.prepa.repository.Admins;

import com.opham.prepa.model.Admins.Acces;
import com.opham.prepa.model.Admins.Groupe;
import com.opham.prepa.model.Admins.Users;

import java.util.List;

public interface AdminsRepository {
    List<Groupe> listGroupe();
    List<Users> listUsers(String groupe);

    List<Users> listMembres(String groupe);

    List<Acces> listAcces(String groupe);

    List<Acces> listDroit(String groupe);

    int insert_Membres(String groupe,String users);

    int insert_droit(Acces A);

    int delete_Membres(String groupe,String users);

    int delete_droit(String groupe,String droit);

    int update_droit(String groupe,String droit,boolean w,boolean r);


}

package com.opham.prepa.repository.Etatcommande;

import com.opham.prepa.Utils.DataSourceConfig;
import com.opham.prepa.mapper.Apreparer.CommandeMapper;
import com.opham.prepa.mapper.EtatCommande.EtatCommandeMapper;
import com.opham.prepa.model.Apreparer.Commande;
import com.opham.prepa.model.EtatCommande.EtatCommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class JdbcEtatcommandeRepository implements EtatcommandeRepository{
    private final JdbcTemplate jdbcTemplate;
    // private final DynamicDataSourceConfig dynamicDataSourceConfig;
    private final DataSourceConfig dataSourceConfig;

    @Autowired
    public JdbcEtatcommandeRepository(JdbcTemplate jdbcTemplate, DataSourceConfig dataSourceConfig) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public List<EtatCommande> findEtatcommande(String CodeCommande, String CodeClient, Date DateDeb, Date DateFin , String Axe) {
        return jdbcTemplate.query("exec x_Etat_commande ?,?,?,?,?",
                new EtatCommandeMapper(),CodeCommande,CodeClient,DateDeb,DateFin,Axe);
    }

}

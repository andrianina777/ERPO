package com.opham.prepa.controller;

import com.opham.prepa.model.Admins.Acces;
import com.opham.prepa.model.Admins.Groupe;
import com.opham.prepa.model.Admins.Users;
import com.opham.prepa.model.Transfert.Transfert;
import com.opham.prepa.model.Utils.Droit;
import com.opham.prepa.repository.Admins.AdminsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminsControlleur {
    @Autowired
    AdminsRepository adminsRepository;

    @GetMapping("/listGroupe")
    public ResponseEntity<List<Groupe>> listGroupe() {
        try {
            List<Groupe> cmd = adminsRepository.listGroupe();
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listUsers")
    public ResponseEntity<List<Users>> listUsers(@RequestParam(required = true) String groupe) {
        try {
            List<Users> cmd = adminsRepository.listUsers(groupe);
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listMembres")
    public ResponseEntity<List<Users>> listMembres(@RequestParam(required = true) String groupe) {
        try {
            List<Users> cmd = adminsRepository.listMembres(groupe);
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listAcces")
    public ResponseEntity<List<Acces>> listAcces(@RequestParam(required = true) String groupe) {
        try {
            List<Acces> cmd = adminsRepository.listAcces(groupe);
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
                e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sesDroits")
    public ResponseEntity<List<Acces>> listDroit(@RequestParam(required = true) String groupe) {
        try {
            List<Acces> cmd = adminsRepository.listDroit(groupe);
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/insert_Membres")
    public ResponseEntity<String> insert_Membres(@RequestParam(required = true) String groupe,@RequestParam(required = true) String users) {
        int updatedRows = adminsRepository.insert_Membres(groupe,users);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete_Membres")
    public ResponseEntity<String> delete_Membres(@RequestParam(required = true) String groupe,@RequestParam(required = true) String users) {
        int updatedRows = adminsRepository.delete_Membres(groupe,users);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete_Droit")
    public ResponseEntity<String> delete_droit(@RequestParam(required = true) String groupe,@RequestParam(required = true) String droit) {
        int updatedRows = adminsRepository.delete_droit(groupe,droit);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/insert_droit")
    public ResponseEntity<Acces> insert_droit(@RequestBody Acces t) {
        int updatedRows = adminsRepository.insert_droit(t);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping ("/update_Droit")
    public ResponseEntity<String> update_Droit(@RequestParam(required = true) boolean read,@RequestParam(required = true) boolean write,@RequestParam(required = true) String groupe,@RequestParam(required = true) String droit) {
        int updatedRows = adminsRepository.update_droit(groupe,droit,read,write);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/insert_groupe")
    public ResponseEntity<Groupe> insert_groupe(@RequestBody Groupe G) {
        int updatedRows = adminsRepository.insert_groupe(G);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

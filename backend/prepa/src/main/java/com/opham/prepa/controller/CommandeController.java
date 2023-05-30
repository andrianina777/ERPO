package com.opham.prepa.controller;

import com.opham.prepa.model.Apreparer.Commande;
import com.opham.prepa.model.Apreparer.DetailPrep;
import com.opham.prepa.model.Apreparer.InfoCommande;
import com.opham.prepa.model.Apreparer.LigneCommande;
import com.opham.prepa.model.EnPreparation.EnCoursCMD;
import com.opham.prepa.model.Utils.Credentials;
import com.opham.prepa.model.genererBP.ListeCmd;
import com.opham.prepa.repository.Apreparer.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

//
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.130.64:3000"},methods = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH})
@RequestMapping("/api")

public class CommandeController  {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

@Autowired
CommandeRepository commandeRepository;

    @GetMapping("/prep")
    public ResponseEntity<List<Commande>> findByDate(@RequestParam(required = false,defaultValue="") String groupe,@RequestParam(required = false,defaultValue="") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateliv ) {
        try {
            List<Commande> cmd = commandeRepository.findByDate(groupe,dateliv);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lignecmd")
    public ResponseEntity<List<LigneCommande>> findLigneCmdebyCode(@RequestParam(required = true) String code ) {
        try {
            List<LigneCommande> cmd = commandeRepository.findLigneCmdebyCode(code);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listDetailPrepa")
    public ResponseEntity<List<DetailPrep>> listDetailPrepa(@RequestParam(required = true) String code ) {
        try {
            List<DetailPrep> cmd = commandeRepository.listDetailBP(code);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/infoCmd")
    public ResponseEntity<InfoCommande> getInfoCommande(@RequestParam(required = true) String code ) {
        try {
            InfoCommande cmd = commandeRepository.plusInfoCmd(code);

            if (cmd!=null) {
                return new ResponseEntity<>(cmd, HttpStatus.OK);

            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/updateInfoCmd")
    public ResponseEntity<InfoCommande> updateInfoCmd(@RequestBody InfoCommande inf ) {
        int updatedRows = commandeRepository.updateInfoCmd(inf);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/genererBP")
    public ResponseEntity<List<List<Object>>> genererBP(@RequestParam(required = true) String code ) {
        try {
            List<List<Object>>  cmd = commandeRepository.genererBP(code);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insertBP")
    public ResponseEntity<String> insertBP(@RequestParam(required = true) String ids,@RequestParam(required = true) String code_CC, @RequestParam(required = true) String depot) {
        try {
            String cmd = commandeRepository.insert_BP(ids,code_CC, depot);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insertL6")
    public ResponseEntity<ListeCmd> updateInfoCmd(@RequestBody ListeCmd lc ) {
        int updatedRows = commandeRepository.insertL6(lc);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/new-id")
    public ResponseEntity<String> getNewId() {
        try {
            String id = commandeRepository.getId();
            if (id.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/printPrepa")
    public ResponseEntity<byte[]> generateReport(@RequestParam String codeBP, @RequestParam Integer isDouble) {
        byte[] reportBytes = commandeRepository.generateReport(codeBP, isDouble);

        if (reportBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "prepaA5.pdf");

            return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/atteTransfert")
    public ResponseEntity<String> atteTransfert(@RequestParam String code_CC, @RequestParam Integer xSeq) {
        try {
            commandeRepository.atteTransfert(code_CC, xSeq);
            return ResponseEntity.ok("Transfer successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Transfer failed: " + e.getMessage());
        }
    }

    @GetMapping("/encoursprep")
    public ResponseEntity<List<EnCoursCMD>> listEnCoursPrepa(@RequestParam(required = true) Integer prepspecif,@RequestParam(required = true) Integer isVisible, @RequestParam(required = false,defaultValue="") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateliv ) {
        try {
            List<EnCoursCMD> cmd = commandeRepository.listEnCoursPrepa(prepspecif,isVisible,dateliv);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password) {
//        boolean isValidCredentials = commandeRepository.checkCredentials(username, password);
//
//        if (isValidCredentials) {
//            return "Connexion réussie !";
//        } else {
//            return "Échec de la connexion.";
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Credentials credentials) {
        boolean isValidCredentials = commandeRepository.checkCredentials(credentials);

        if (isValidCredentials) {
            return ResponseEntity.ok("Connexion réussie !");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de la connexion.");
        }
    }
}

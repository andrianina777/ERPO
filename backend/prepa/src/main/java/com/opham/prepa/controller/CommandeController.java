package com.opham.prepa.controller;

import com.opham.prepa.model.Apreparer.Commande;
import com.opham.prepa.model.Apreparer.DetailPrep;
import com.opham.prepa.model.Apreparer.InfoCommande;
import com.opham.prepa.model.Apreparer.LigneCommande;
import com.opham.prepa.model.EnPreparation.DetailBP;
import com.opham.prepa.model.EnPreparation.EnCoursCMD;
import com.opham.prepa.model.EnPreparation.EnCoursPrepBP;
import com.opham.prepa.model.EnPreparation.ListLigneBP;
import com.opham.prepa.model.Utils.Credentials;
import com.opham.prepa.model.Utils.Droit;
import com.opham.prepa.model.genererBP.ListeCmd;
import com.opham.prepa.repository.Apreparer.CommandeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.130.64:3000", "http://192.168.201.13:3000","http://192.168.130.69:8081"}, methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
@RequestMapping("/api")

public class CommandeController {
    private final Map<String, Credentials> activeSessions = new ConcurrentHashMap<>();
    @Autowired
    CommandeRepository commandeRepository;

    @GetMapping("/prep")
    public ResponseEntity<List<Commande>> findByDate(@RequestParam(required = false, defaultValue = "DETAIL") String groupe, @RequestParam(required = false, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateliv) {
        try {
            List<Commande> cmd = commandeRepository.findByDate(groupe, dateliv);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lignecmd")
    public ResponseEntity<List<LigneCommande>> findLigneCmdebyCode(@RequestParam(required = true) String code) {
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
    public ResponseEntity<List<DetailPrep>> listDetailPrepa(@RequestParam(required = true) String code) {
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
    public ResponseEntity<InfoCommande> getInfoCommande(@RequestParam(required = true) String code) {
        try {
            InfoCommande cmd = commandeRepository.plusInfoCmd(code);

            if (cmd != null) {
                return new ResponseEntity<>(cmd, HttpStatus.OK);

            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/updateInfoCmd")
    public ResponseEntity<InfoCommande> updateInfoCmd(@RequestBody InfoCommande inf) {
        int updatedRows = commandeRepository.updateInfoCmd(inf);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/genererBP")
    public ResponseEntity<List<List<Object>>> genererBP(@RequestParam(required = true) String code) {
        try {
            List<List<Object>> cmd = commandeRepository.genererBP(code);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insertBP")
    public ResponseEntity<String> insertBP(@RequestParam(required = true) String ids, @RequestParam(required = true) String code_CC, @RequestParam(required = true) String depot) {
        try {
            String cmd = commandeRepository.insert_BP(ids, code_CC, depot);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insertL6")
    public ResponseEntity<ListeCmd> updateInfoCmd(@RequestBody ListeCmd lc) {
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
        int updatedRows = commandeRepository.atteTransfert(code_CC, xSeq);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/resumeT")
    public ResponseEntity<String> updateCCResume(@RequestParam String codeCC) {
        commandeRepository.updateCCResume(codeCC);
        return ResponseEntity.status(HttpStatus.OK).body("CCRESUME updated successfully.");
    }

    @PutMapping("/updateFrais")
    public ResponseEntity<String> updateFrais(@RequestParam String codeCC) {
        commandeRepository.updateFrais(codeCC);
        return ResponseEntity.status(HttpStatus.OK).body("cmd frais updated successfully.");
    }

    @GetMapping("/encoursprep")
    public ResponseEntity<List<EnCoursCMD>> listEnCoursPrepa(@RequestParam(required = true) Integer prepspecif, @RequestParam(required = true) Integer isVisible, @RequestParam(required = false, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateliv) {
        try {
            List<EnCoursCMD> cmd = commandeRepository.listEnCoursPrepa(prepspecif, isVisible, dateliv);

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

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody Credentials credentials) {
//        boolean isValidCredentials = commandeRepository.checkCredentials(credentials);
//
//        if (isValidCredentials) {
//            return ResponseEntity.ok("Connexion réussie !");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de la connexion.");
//        }
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpSession session) {
//        session.invalidate();
//        return ResponseEntity.ok("Logout successful");
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Credentials credentials) {
        Credentials authenticatedUser = commandeRepository.checkCredentials(credentials);
        if (authenticatedUser != null) {
            String sessionId = UUID.randomUUID().toString();
            activeSessions.put(sessionId, authenticatedUser);
            return ResponseEntity.ok(sessionId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de la connexion.");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String sessionId,HttpSession session) {
        if (activeSessions.remove(sessionId) != null) {
            session.invalidate();
            return ResponseEntity.ok("Déconnexion réussie !");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de la déconnexion.");
        }
    }

    @GetMapping("/listEnCoursBP")
    public ResponseEntity<List<EnCoursPrepBP>> listBPEncours(@RequestParam(required = true) int etat) {
        try {
            List<EnCoursPrepBP> cmd = commandeRepository.listBPEncours(etat);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listLigneBP")
    public ResponseEntity<List<ListLigneBP>> listLigneBP(@RequestParam(required = true) String BP) {
        try {
            List<ListLigneBP> cmd = commandeRepository.listLigneBP(BP);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detailBP")
    public ResponseEntity<List<DetailBP>> detailBP(@RequestParam(required = true) String BP) {
        try {
            List<DetailBP> cmd = commandeRepository.detailBP(BP);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detailBPCTRL")
    public ResponseEntity<List<DetailBP>> detailBPCTRL(@RequestParam(required = true) String BP) {
        try {
            List<DetailBP> cmd = commandeRepository.detailBPCTRL(BP);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/infoEncoursBP")
    public ResponseEntity<EnCoursPrepBP> infoEncoursBP(@RequestParam(required = true) String BP ) {
        try {
            EnCoursPrepBP cmd = commandeRepository.infoEncoursBP(BP);

            if (cmd!=null) {
                return new ResponseEntity<>(cmd, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

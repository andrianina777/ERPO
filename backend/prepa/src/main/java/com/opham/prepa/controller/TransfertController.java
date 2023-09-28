package com.opham.prepa.controller;

import com.opham.prepa.model.Apreparer.Commande;
import com.opham.prepa.model.EnPreparation.EnCoursCMD;
import com.opham.prepa.model.Transfert.ProblemeStock;
import com.opham.prepa.model.Transfert.SuiviTD;
import com.opham.prepa.model.Transfert.Transfert;
import com.opham.prepa.model.Transfert.TransfertConseilReappro;
import com.opham.prepa.model.Utils.Droit;
import com.opham.prepa.model.genererBP.ListeCmd;
import com.opham.prepa.repository.Transfert.TransfertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransfertController {
    @Autowired
    TransfertRepository transfertRepository;

    @GetMapping("/rotation")
    public ResponseEntity<List<List<Object>>> listRotation(@RequestParam(required = true) String depot, @RequestParam(required = false) String alle) {
        try {
            List<List<Object>> cmd = transfertRepository.listRotation(depot, alle);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/colisIncomplet")
    public ResponseEntity<List<List<Object>>> listColisIncomplet(@RequestParam(required = false, defaultValue = "GROS") String depotOrg, @RequestParam(required = false, defaultValue = "DET") String depotDest, @RequestParam(required = false) String alle) {
        try {
            List<List<Object>> cmd = transfertRepository.listColisIncomplet(depotOrg, depotDest, alle);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/petitColis")
    public ResponseEntity<List<List<Object>>> listPetitColis(@RequestParam(required = false, defaultValue = "GROS") String depotOrg, @RequestParam(required = false, defaultValue = "DET") String depotDest, @RequestParam(required = false) String alle) {
        try {
            List<List<Object>> cmd = transfertRepository.listPetitColis(depotOrg, depotDest, alle);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rangement")
    public ResponseEntity<List<List<Object>>> listRangement(@RequestParam(required = true) String depot) {
        try {
            List<List<Object>> cmd = transfertRepository.listRangement(depot);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insertL6_transfert")
    public ResponseEntity<Transfert> insertL6_Transfert(@RequestBody Transfert t) {
        int updatedRows = transfertRepository.insertL6_Transfert(t);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/insertL6_transfertConseil")
    public ResponseEntity<TransfertConseilReappro> insertL6_TransfertConseil(@RequestBody TransfertConseilReappro t) {
        int updatedRows = transfertRepository.insertL6_TransfertConseil(t);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/insertFSIL")
    public ResponseEntity<String> insert_FSIL(@RequestParam(required = true) String ids, @RequestParam(required = true) String commentaire, @RequestParam(required = true) String depOrg, @RequestParam(required = true) String depDest) {
        try {
            String cmd = transfertRepository.insert_FSIL(ids, commentaire, depOrg, depDest);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/testStock")
    public ResponseEntity<ProblemeStock> testStock(@RequestParam(required = true) String article, @RequestParam(required = true) int qte, @RequestParam(required = true) String depot, @RequestParam(required = true) String lettre, @RequestParam(required = true) String empl) {
        try {
            ProblemeStock cmd = transfertRepository.testStock(article, qte, depot, lettre, empl);
            if (cmd != null) {
                return new ResponseEntity<>(cmd, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stockPasVide")
    public ResponseEntity<Integer> stockPasVide(@RequestParam(required = true) String depot, @RequestParam(required = true) String empl) {
        try {
            int cmd = transfertRepository.stockPasVide(depot, empl);

/*            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }*/
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/printTransfert")
    public ResponseEntity<byte[]> generateReport(@RequestParam String td, @RequestParam String org, @RequestParam String dest, @RequestParam Integer isDouble, @RequestParam String users) {
        byte[] reportBytes = transfertRepository.generateReportTransfert(td, org, dest, isDouble, users);

        if (reportBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "transfertA5.pdf");

            return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/conseilReappro")
    public ResponseEntity<List<List<Object>>> conseilReappro(@RequestParam(required = false) String article, @RequestParam(required = false, defaultValue = "GROS") String depotOrg, @RequestParam(required = false, defaultValue = "DET") String depotDest) {
        try {
            List<List<Object>> cmd = transfertRepository.conseilReappro(article, depotOrg, depotDest);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/suiviTD")
    public ResponseEntity<List<SuiviTD>> listSuiviTD(@RequestParam(required = false) String code, @RequestParam(required = true) String depOrg, @RequestParam(required = true) String depDest,
                                                     @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date datedeb, @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date datefin, @RequestParam(required = false) String article) {
        try {
            List<SuiviTD> cmd = transfertRepository.listSuiviTD(code, depOrg, depOrg, datedeb, datefin, article);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

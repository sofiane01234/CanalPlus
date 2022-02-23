package canalplus.testtechnique.sofiane.services.servicesimpl;

import canalplus.testtechnique.sofiane.domain.Reunion;
import canalplus.testtechnique.sofiane.domain.Salle;
import canalplus.testtechnique.sofiane.domain.enums.Equipement;
import canalplus.testtechnique.sofiane.domain.enums.ReunionType;
import canalplus.testtechnique.sofiane.repository.ReunionRepository;
import canalplus.testtechnique.sofiane.repository.SalleRepository;
import canalplus.testtechnique.sofiane.services.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalleServiceImpl implements SalleService {

    @Autowired
    SalleRepository salleRepository;

    @Autowired
    ReunionRepository reunionRepository;


    private final int NombrePieuvre = 4;
    private final int NombreTableau = 2;
    private final int NombreWebCam = 4;
    private final int NombreEcran = 5;

    @Override
    public Iterable<Salle> getAllSalles() {
        return salleRepository.findAll();
    }

    @Override
    public Set<Equipement> getEquipementsManquants(ReunionType reunionType, Salle salle) {
        List<Equipement> EquipementNecessaires = ReunionType.getEquipementsNecessaires(reunionType);
        List<Equipement> EquipementDispo;
        if (salle.getMaterial().isEmpty()) {
            EquipementDispo = new ArrayList<>();
        } else {
            EquipementDispo = List.of(salle.getMaterial().split(",")).stream().map(Equipement::valueOf).collect(Collectors.toList());

        }
        return EquipementNecessaires.stream()
                .filter(Objects::nonNull)
                .filter(tools -> !EquipementDispo.contains(tools))
                .collect(Collectors.toUnmodifiableSet());
    }


    @Override
    public Map<Equipement, Integer> getNombreEquipementsAmovibles(Reunion reunion) {
        synchronized (this) {
            Map<Equipement, Integer> cptEquipAmo = new HashMap<>();
            List<Reunion> listReunionsDansInterv = reunionRepository.findBetween(reunion.getStart(), reunion.getEnd());
            if (!listReunionsDansInterv.isEmpty()) {
                for (Reunion reu : listReunionsDansInterv) {
                    if (reu.getMaterial() != null) {
                        gererStockEquipement(cptEquipAmo, reu);
                    } else {
                        initStock(cptEquipAmo);
                    }
                }
            } else {
                initStock(cptEquipAmo);
            }
            return cptEquipAmo;
        }
    }

    private void initStock(Map<Equipement, Integer> counterOfRemovableTools) {
        counterOfRemovableTools.put(Equipement.Tableau, NombreTableau);
        counterOfRemovableTools.put(Equipement.Pieuvre, NombrePieuvre);
        counterOfRemovableTools.put(Equipement.Ecran, NombreEcran);
        counterOfRemovableTools.put(Equipement.WebCam, NombreWebCam);
    }

    private void gererStockEquipement(Map<Equipement, Integer> counterOfRemovableTools, Reunion reunion) {
        for (Equipement tools : List.of(reunion.getMaterial().split(",")).stream().map(Equipement::valueOf).collect(Collectors.toList())) {
            if (counterOfRemovableTools.containsKey(tools)) {
                counterOfRemovableTools.put(tools, counterOfRemovableTools.get(tools) - 1);
            } else {
                if (tools == Equipement.Tableau) {
                    counterOfRemovableTools.put(tools, NombreTableau - 1);
                }
                if (tools == Equipement.Ecran) {
                    counterOfRemovableTools.put(tools, NombreEcran - 1);
                }
                if (tools == Equipement.Pieuvre) {
                    counterOfRemovableTools.put(tools, NombrePieuvre - 1);
                }
                if (tools == Equipement.WebCam) {
                    counterOfRemovableTools.put(tools, NombreWebCam - 1);
                }
            }
        }
    }
}

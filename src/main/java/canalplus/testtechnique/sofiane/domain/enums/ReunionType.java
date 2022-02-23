package canalplus.testtechnique.sofiane.domain.enums;

import java.util.ArrayList;
import java.util.List;

public enum ReunionType {

    SPEC,
    RS,
    VC,
    RC;

    public static List<Equipement> getEquipementsNecessaires(ReunionType reunionType) {
        List<Equipement> equipements = new ArrayList<>();
        switch (reunionType) {
            case SPEC:
                equipements.add(Equipement.Tableau);
                break;
            case VC:
                equipements.add(Equipement.Ecran);
                equipements.add(Equipement.Pieuvre);
                equipements.add(Equipement.WebCam);
                break;
            case RC:
                equipements.add(Equipement.Tableau);
                equipements.add(Equipement.Ecran);
                equipements.add(Equipement.Pieuvre);
                break;
            default:
        }
        return equipements;

    }


}

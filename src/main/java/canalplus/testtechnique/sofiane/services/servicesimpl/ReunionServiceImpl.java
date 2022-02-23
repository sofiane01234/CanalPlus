package canalplus.testtechnique.sofiane.services.servicesimpl;

import canalplus.testtechnique.sofiane.domain.Reunion;
import canalplus.testtechnique.sofiane.repository.ReunionRepository;
import canalplus.testtechnique.sofiane.services.ReunionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReunionServiceImpl implements ReunionService {

    @Autowired
    ReunionRepository reunionRepository;

    @Override
    public Iterable<Reunion> getAllReunions() {
        return reunionRepository.findAll();
    }
}

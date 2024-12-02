package springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.modelesringboot.InscriptionCours;
import springboot.repository.InscriptionCoursRepository;

import java.util.List;

@Service
public class InscriptionCoursService {

    @Autowired
    private InscriptionCoursRepository inscriptionCoursRepository;

    public List<InscriptionCours> getAllInscriptions() {
        return inscriptionCoursRepository.findAll();
    }

    public InscriptionCours saveInscription(InscriptionCours inscriptionCours) {
        return inscriptionCoursRepository.save(inscriptionCours);
    }
}

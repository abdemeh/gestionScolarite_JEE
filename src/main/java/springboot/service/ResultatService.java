package springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.modelesringboot.Resultat;
import springboot.repository.ResultatRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ResultatService {

    @Autowired
    private ResultatRepository resultatRepository;

    public List<Resultat> getAllResultats() {
        return resultatRepository.findAll();
    }

    public Resultat saveResultat(Resultat resultat) {
        return resultatRepository.save(resultat);
    }

    public Resultat getResultatById(Integer id) {
        Optional<Resultat> resultatOptional = resultatRepository.findById(id);
        return resultatOptional.orElse(null); // Retourne null si aucun résultat trouvé
    }
}

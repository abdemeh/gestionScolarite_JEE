package springboot.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.modelesringboot.Cours;
import springboot.repository.CoursRepository;

import java.util.List;

@Service
public class CoursService {

    @Autowired
    private CoursRepository coursRepository;

    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    public Cours saveCours(Cours cours) {
        return coursRepository.save(cours);
    }
    public Cours findCoursById(int idCours) {
        return coursRepository.findByIdCours(idCours);
    }
    public List<Cours> findCoursByNom(String nom) {
        return coursRepository.findByNom(nom);
    }
}


package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import peaksoft.model.Hospital;
import peaksoft.repository.HospitalRepository;

import java.util.List;
@Repository
@Transactional
public class HospitalRepositoryImpl implements HospitalRepository {

    @PersistenceContext
    private  final EntityManager manager;

    @Autowired
    public HospitalRepositoryImpl(EntityManager manager) {
        this.manager = manager;
    }


    @Override
    public List<Hospital> getAllHospitals() {
        return manager.createQuery("select h from Hospital h",Hospital.class).getResultList();
    }

    @Override
    public void saveHospital(Hospital hospital) {
        manager.persist(hospital);
    }

    @Override
    public Hospital getHospitalById(Long id) {
        return manager.find(Hospital.class,id);
    }

    @Override
    public void deleteHospitalById(Long id) {
        manager.remove(manager.find(Hospital.class,id));
    }

    @Override
    public void updateHospital(Hospital hospital) {
        Hospital hospital1 = getHospitalById(hospital.getId());
        hospital1.setName(hospital.getName());
        hospital1.setAddress(hospital.getAddress());
        manager.merge(hospital1);
    }
}

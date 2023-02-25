package peaksoft.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.model.Hospital;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.HospitalService;

import java.util.List;
@Service
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository repository;

    @Autowired
    public HospitalServiceImpl(HospitalRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Hospital> getAllHospitals() {
        return repository.getAllHospitals();
    }

    @Override
    public void saveHospital(Hospital hospital) {
        repository.saveHospital(hospital);
    }

    @Override
    public Hospital getHospitalById(Long id) {
        return repository.getHospitalById(id);
    }

    @Override
    public void deleteHospitalById(Long id) {
        repository.deleteHospitalById(id);
    }

    @Override
    public void updateHospital(Hospital hospital) {
        Hospital hospital1 = getHospitalById(hospital.getId());
        hospital1.setName(hospital.getName());
        hospital1.setAddress(hospital.getAddress());
        repository.updateHospital(hospital1);
    }
}

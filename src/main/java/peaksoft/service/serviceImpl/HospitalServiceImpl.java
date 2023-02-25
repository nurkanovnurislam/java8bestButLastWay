package peaksoft.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.model.Hospital;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.HospitalService;

import java.util.List;
@Service
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;

    @Autowired
    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }


    @Override
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.getAllHospitals();
    }

    @Override
    public void saveHospital(Hospital hospital) {
        hospitalRepository.saveHospital(hospital);
    }

    @Override
    public Hospital getHospitalById(Long id) {
        return hospitalRepository.getHospitalById(id);
    }

    @Override
    public void deleteHospitalById(Long id) {
        hospitalRepository.deleteHospitalById(id);
    }

    @Override
    public void updateHospital(Hospital hospital) {
        Hospital hospital1 = getHospitalById(hospital.getId());
        hospital1.setName(hospital.getName());
        hospital1.setAddress(hospital.getAddress());
        hospitalRepository.updateHospital(hospital1);
    }
}

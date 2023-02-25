package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.model.Patient;
import peaksoft.repository.PatientRepository;
import peaksoft.service.PatientService;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    @Override
    public List<Patient> getAllPatient(Long patientId) {
        return patientRepository.getAllPatient(patientId);
    }

    @Override
    public void savePatient(Patient patient, Long hospitalId) {
        Patient patient1 = new Patient();
        patient1.setFirstName(patient.getFirstName());
        patient1.setLastName(patient.getLastName());
        validation(patient.getPhoneNumber().replace(" ", ""));
        patient1.setPhoneNumber(patient.getPhoneNumber());
        patient1.setEmail(patient.getEmail());
        patient1.setGender(patient.getGender());
        patientRepository.savePatient(patient1, hospitalId);
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.getPatientById(id);
    }

    @Override
    public void deletePatientById(Long id) {
        patientRepository.deletePatientById(id);
    }

    @Override
    public void updatePatient(Long patientId, Patient patient) {
        Patient patient1 = patientRepository.getPatientById(patientId);
        patient1.setFirstName(patient.getFirstName());
        patient1.setLastName(patient.getLastName());
        patient1.setEmail(patient.getEmail());
        validation(patient.getPhoneNumber().replace(" ", ""));
        patient1.setPhoneNumber(patient.getPhoneNumber());
        patient1.setGender(patient.getGender());
        patientRepository.updatePatient(patientId, patient);
    }

    @Override
    public void assignPatient(Long appointmentId, Long patientId) throws IOException {
        patientRepository.assignPatient(appointmentId, patientId);
    }

    public void validation(String phoneNumber) {
        if (phoneNumber.length() == 13
                && phoneNumber.charAt(0) == '+'
                && phoneNumber.charAt(1) == '9'
                && phoneNumber.charAt(2) == '9'
                && phoneNumber.charAt(3) == '6'){
            int count = 0;
            for (Character i : phoneNumber.toCharArray()) {
                if (count != 0){
                    if (!Character.isDigit(i)){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"номер!!!");
                    }
                }
                count++;
            }
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"номер!!!");
        }
    }
}

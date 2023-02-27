package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Hospital;
import peaksoft.model.Patient;
import peaksoft.repository.HospitalRepository;
import peaksoft.repository.PatientRepository;

import java.io.IOException;
import java.util.List;

@Repository
@Transactional
public class PatientRepositoryImpl implements PatientRepository {
    @PersistenceContext
    private final EntityManager manager;

    private final HospitalRepository hospitalRepository;

    @Autowired
    public PatientRepositoryImpl(EntityManager manager, HospitalRepository hospitalRepository) {
        this.manager = manager;
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public List<Patient> getAllPatient(Long patientId) {
        return manager.createQuery("select p from Patient p where p.hospital.id=:patientId",
                Patient.class).setParameter("patientId",patientId).getResultList();
    }

    @Override
    public void savePatient(Patient patient, Long hospitalId) {
        Patient patient1 = new Patient();
        if (hospitalRepository.getHospitalById(hospitalId) == null){
            throw new NotFoundException(String.format("Hospital with id %d not found",hospitalId));
        }
        Hospital hospital = hospitalRepository.getHospitalById(hospitalId);
        patient1.setFirstName(patient.getFirstName());
        patient1.setLastName(patient.getLastName());
        patient1.setEmail(patient.getEmail());
        patient1.setGender(patient.getGender());
        patient1.setPhoneNumber(patient.getPhoneNumber());
        patient1.setHospital(hospital);
        hospital.plusPatient();
        manager.persist(patient1);
    }

    @Override
    public Patient getPatientById(Long id) {
        return manager.find(Patient.class,id);
    }

    @Override
    public void deletePatientById(Long id) {
        Patient patient = manager.find(Patient.class,id);
        patient.getHospital().minusPatient();
        manager.remove(manager.find(Appointment.class,id));
        manager.remove(manager.find(Patient.class,id));
    }

    @Override
    public void updatePatient(Long patientId, Patient patient) {
        Patient patient1 = manager.find(Patient.class, patientId);
        patient1.setFirstName(patient.getFirstName());
        patient1.setLastName(patient.getLastName());
        patient1.setEmail(patient.getEmail());
        patient1.setPhoneNumber(patient.getPhoneNumber());
        patient1.setGender(patient.getGender());
        manager.merge(patient1);
    }

    @Override
    public void assignPatient(Long appointmentId, Long patientId) throws IOException {
        Patient patient = manager.find(Patient.class, patientId);
        Appointment appointment = manager.find(Appointment.class, appointmentId);
        if (appointment.getPatient()!=null){
            for (Patient p: appointment.getHospital().getPatients()) {
                if (p.getId() == patientId){
                    throw new IOException("this is patient added");
                }
            }
        }
        patient.addAppointment(appointment);
        appointment.setPatient(patient);
        manager.merge(patient);
        manager.merge(appointment);
    }
}

package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.DoctorRepository;

import java.io.IOException;
import java.util.List;
@Repository
@Transactional
public class DoctorRepositoryImpl implements DoctorRepository {

    @PersistenceContext
    private final EntityManager manager;


    @Autowired
    public DoctorRepositoryImpl(EntityManager entityManager) {
        this.manager = entityManager;
    }

    @Override
    public List<Doctor> getAllDoctors(Long doctorId) {
        return manager.createQuery("select d from Doctor d where d.hospital.id=:doctorId",
                Doctor.class).setParameter("doctorId",doctorId).getResultList();
    }

    @Override
    public void saveDoctor(Doctor doctor,Long hospitalId) {
        Doctor doctor1 = new Doctor();
        if (manager.find(Hospital.class, hospitalId) == null){
            throw new NotFoundException(String.format("Hospital with id %d not found",hospitalId));
        }
        Hospital hospital = manager.find(Hospital.class, hospitalId);
        doctor1.setFirstName(doctor.getFirstName());
        doctor1.setLastName(doctor.getLastName());
        doctor1.setEmail(doctor.getEmail());
        doctor1.setPosition(doctor.getPosition());
        doctor1.setHospital(hospital);
        hospital.plusDoctor();
        manager.persist(doctor1);
    }

    @Override
    public void saveDoctorInfo(Doctor doctor) {
        manager.persist(doctor);
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return manager.find(Doctor.class,id);
    }

    @Override
    public void deleteDoctorById(Long id) {
        Doctor doctor = manager.find(Doctor.class, id);
        doctor.getHospital().minusDoctor();
        manager.remove(doctor);
    }

    @Override
    public void updateDoctor(Long doctorId, Doctor doctor) {
        Doctor doctor1 = manager.find(Doctor.class, doctorId);
        doctor1.setFirstName(doctor.getFirstName());
        doctor1.setLastName(doctor.getLastName());
        doctor1.setEmail(doctor.getEmail());
        doctor1.setPosition(doctor.getPosition());
        manager.merge(doctor1);
    }

    @Override
    public void assignDoctor(Long appointmentId, Long doctorId) throws IOException {
        Doctor doctor = manager.find(Doctor.class, doctorId);
        Appointment appointment = manager.find(Appointment.class, appointmentId);
        if(appointment.getDoctor()!=null){
            for (Doctor d: appointment.getHospital().getDoctors()) {
                if(d.getId() == doctorId){
                    throw new IOException("this is have signed");
                }
            }
        }
        doctor.addAppointments(appointment);
        appointment.setDoctor(doctor);
        manager.merge(doctor);
        manager.merge(appointment);
    }
}

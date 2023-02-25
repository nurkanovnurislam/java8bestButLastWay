package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepository;

import java.util.List;

@Repository
@Transactional
public class AppointmentRepositoryImpl implements AppointmentRepository {

    @PersistenceContext
    private final EntityManager manager;

    @Autowired
    public AppointmentRepositoryImpl(EntityManager entityManager) {
        this.manager = entityManager;
    }

    @Override
    public List<Appointment> getAllAppointment(Long appointmentId) {
         return manager.createQuery("select a from Appointment a where a.hospital.id=:appointmentId",
                Appointment.class).setParameter("appointmentId",appointmentId).getResultList();
    }

    @Override
    public void saveAppointment(Appointment appointment, Long hospitalId) {
            Appointment appointment1 = new Appointment();
            if (manager.find(Hospital.class, hospitalId) == null){
                throw new NotFoundException(String.format("Hospital with id %d not found", hospitalId));
            }
            Hospital hospital = manager.find(Hospital.class, hospitalId);
            appointment1.setHospital(hospital);
            appointment1.setDate(appointment.getDate());
        manager.persist(appointment1);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return manager.find(Appointment.class,id);
    }

    @Override
    public void deleteAppointmentById(Long id) {
        manager.remove(manager.find(Appointment.class,id));
    }

    @Override
    public void updateAppointment(Long appointmentId, Appointment appointment) {
        Appointment appointment1 = manager.find(Appointment.class, appointmentId);
        appointment1.setDate(appointment.getDate());
        manager.merge(appointment1);
    }
}

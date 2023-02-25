package peaksoft.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.model.Appointment;
import peaksoft.repository.AppointmentRepository;
import peaksoft.service.AppointmentService;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository repository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Appointment> getAllAppointment(Long appointmentId){
        return repository.getAllAppointment(appointmentId);
    }

    @Override
    public void saveAppointment(Appointment appointment,Long hospitalId) {
        Appointment appointment1 = new Appointment();
        appointment1.setDate(appointment.getDate());
        repository.saveAppointment(appointment1,hospitalId);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return repository.getAppointmentById(id);
    }

    @Override
    public void deleteAppointmentById(Long id) {
        repository.deleteAppointmentById(id);
    }

    @Override
    public void updateAppointment(Long appointmentId, Appointment appointment) {
        repository.updateAppointment(appointmentId,appointment);
    }
}

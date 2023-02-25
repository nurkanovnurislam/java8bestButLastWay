package peaksoft.repository;

import peaksoft.model.Appointment;

import java.util.List;

public interface AppointmentRepository {
    List<Appointment> getAllAppointment(Long appointmentId);

    void saveAppointment(Appointment appointment,Long hospital);

    Appointment getAppointmentById(Long id);

    void deleteAppointmentById(Long id);

    void updateAppointment(Long appointmentId,Appointment appointment);
}

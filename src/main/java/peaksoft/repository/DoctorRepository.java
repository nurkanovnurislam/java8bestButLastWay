package peaksoft.repository;

import peaksoft.model.Doctor;

import java.io.IOException;
import java.util.List;

public interface DoctorRepository {
    List<Doctor> getAllDoctors(Long doctorId);

    void saveDoctor(Doctor doctor,Long hospital);

    void saveDoctorInfo(Doctor doctor);

    Doctor getDoctorById(Long id);

    void deleteDoctorById(Long id);

    void updateDoctor(Long doctorId,Doctor doctor);

    void assignDoctor(Long appointmentId,Long doctorId) throws IOException;
}

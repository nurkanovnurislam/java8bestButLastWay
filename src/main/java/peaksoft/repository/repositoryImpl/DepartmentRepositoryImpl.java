package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.DoctorRepository;

import java.io.IOException;
import java.util.List;
@Repository
@Transactional
public class DepartmentRepositoryImpl implements DepartmentRepository {
    @PersistenceContext
    private final EntityManager entityManager;


    @Autowired
    public DepartmentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Department> getAllDepartment(Long departmentId) {
        return entityManager.createQuery("select d from Department d where d.hospital.id=:departmentId",
                Department.class).setParameter("departmentId",departmentId).getResultList();
    }

    @Override
    public void saveDepartment(Department department, Long hospitalId) {
        Department department1 = new Department();
        if (entityManager.find(Hospital.class, hospitalId) == null){
            throw new NotFoundException(String.format("Hospital with id %d not found",hospitalId));
        }
        Hospital hospital = entityManager.find(Hospital.class, hospitalId);
        department1.setName(department.getName());
        department1.setHospital(hospital);
        entityManager.persist(department1);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return entityManager.find(Department.class,id);
    }

    @Override
    public void deleteDepartmentById(Long id) {
        Department department = entityManager.find(Department.class, id);
        for (int i = 0; i < department.getDoctors().size(); i++) {
            department.getDoctors().get(i).getDepartments().remove(department);
        }
        entityManager.remove(department);
    }

    @Override
    public void updateDepartment(Long departmentId, Department department) {
        Department department1 = entityManager.find(Department.class, departmentId);
        department1.setName(department.getName());
        entityManager.merge(department1);
    }

    @Override
    public void AssignDepartment(Long doctorId, Long departmentId) throws IOException {
        Department department = entityManager.find(Department.class, departmentId);
        Doctor doctor = entityManager.find(Doctor.class, doctorId);
        if (doctor.getDepartments() != null){
            for (Department d: doctor.getDepartments()) {
                if(d.getId()==departmentId){
                    throw new IOException("Bul Department uje koshulgan");
                }
            }
        }
        department.addDoctors(doctor);
        doctor.addDepartments(department);
        entityManager.merge(department);
        entityManager.merge(doctor);
    }

    @Override
    public void AssignDepartmentToAppointment(Long appointmentId, Long departmentId) throws IOException {
        Department department = entityManager.find(Department.class, departmentId);
        Appointment appointment = entityManager.find(Appointment.class, appointmentId);
        if (appointment.getDepartment() != null){
            for (Department d: appointment.getHospital().getDepartments()) {
                if (d.getId() ==departmentId){
                    throw  new IOException("but uje assign bolgon");
                }
            }
        }
        department.addAppointment(appointment);
        appointment.setDepartment(department);
        entityManager.merge(department);
        entityManager.merge(appointment);
    }
}

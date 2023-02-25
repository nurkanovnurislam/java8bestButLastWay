package peaksoft.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.model.Department;
import peaksoft.repository.DepartmentRepository;
import peaksoft.service.DepartmentService;

import java.io.IOException;
import java.util.List;
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository repository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Department> getAllDepartment(Long departmentId) {
        return repository.getAllDepartment(departmentId);
    }

    @Override
    public void saveDepartment(Department department, Long hospitalId) {
        Department department1 = new Department();
        department1.setName(department.getName());
        repository.saveDepartment(department1,hospitalId);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return repository.getDepartmentById(id);
    }

    @Override
    public void deleteDepartmentById(Long id) {
        repository.deleteDepartmentById(id);
    }

    @Override
    public void updateDepartment(Long departmentId, Department department) {
        repository.updateDepartment(departmentId,department);
    }

    @Override
    public void AssignDepartment(Long doctorId, Long departmentId) throws IOException {
        repository.AssignDepartment(doctorId,departmentId);
    }

    @Override
    public void AssignDepartmentToAppointment(Long appointmentId, Long departmentId) throws IOException {
        repository.AssignDepartmentToAppointment(appointmentId,departmentId);
    }
}

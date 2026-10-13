package com.example.ala.STAFF.Service;



import com.example.ala.Enum.RoleEnum;
import com.example.ala.STAFF.DTO.StaffDTO;
import com.example.ala.STAFF.Repo.StaffRepository;
import com.example.ala.STAFF.Entity.Staff;
import com.example.ala.Student.Entity.Admin;
import com.example.ala.Student.Repo.AdminRepo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final AdminRepo adminRepo;

    public StaffService(
            StaffRepository staffRepository,
            AdminRepo adminRepo) {
        this.staffRepository = staffRepository;
        this.adminRepo = adminRepo;
    }

    @Transactional
    public Staff createStaff(StaffDTO dto) {

        String email = dto.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        Admin account = adminRepo.findByEmail(email);

        if (account == null || account.getRole() != RoleEnum.STAFF) {
            throw new IllegalArgumentException(
                    "Create the STAFF login account first using /admin/create"
            );
        }

        if (staffRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Staff profile already exists for this email"
            );
        }

        Staff staff = new Staff();
        staff.setName(dto.getName().trim());
        staff.setEmail(email);
        staff.setDepartment(dto.getDepartment());
        staff.setPhone(dto.getPhone());
        staff.setStatus("ACTIVE");

        return staffRepository.save(staff);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Staff not found with ID: " + id
                        )
                );
    }

    @Transactional
    public Staff updateStaff(Long id, StaffDTO dto) {

        Staff staff = getStaffById(id);

        staff.setName(dto.getName().trim());
        staff.setDepartment(dto.getDepartment());
        staff.setPhone(dto.getPhone());

        return staffRepository.save(staff);
    }

    @Transactional
    public void deleteStaff(Long id) {
        Staff staff = getStaffById(id);
        staffRepository.delete(staff);
    }
}


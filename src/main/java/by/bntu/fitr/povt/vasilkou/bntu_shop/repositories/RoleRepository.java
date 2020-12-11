package by.bntu.fitr.povt.vasilkou.bntu_shop.repositories;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}

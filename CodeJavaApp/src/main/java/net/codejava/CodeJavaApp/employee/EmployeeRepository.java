package net.codejava.CodeJavaApp.employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.codejava.CodeJavaApp.user.*;

import java.util.*;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //to get the expired fet test employeee
    @Query(value = "select * from employee where (date(fet_date) - date(CURRENT_TIMESTAMP))<= 0 and user_id = ?1", nativeQuery = true)
    List<Employee> listExpiredTestEmployees(Long userid); 

    Optional<Employee> findByName(String employeeName);
    List<Employee> findByUserId(Long userId);
    Optional<Employee> findByIdAndUserId(Long id, Long userId);

}

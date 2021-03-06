package net.codejava.CodeJavaApp.user;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.codejava.CodeJavaApp.Business.*;
import net.codejava.CodeJavaApp.employee.Employee;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

/* Implementations of UserDetails to provide user information to Spring Security, 
e.g., what authorities (roles) are granted to the user and whether the account is enabled or not
*/
public class User implements UserDetails{
    private static final long serialVersionUID = 1L;

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    
    @NotNull(message = "Username should not be null") @Email(message = "Please enter a proper email address")
    @Size( min = 10,  message = "Username should be well-formed")
    private String username;
    
    // @NotNull(message = "Password should not be null")
    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;

    @NotNull(message = "First name shouldnt be null")
    private String firstName;

    @NotNull(message = "Last name shouldnt be null")
    private String lastName;

    // @JsonIgnore
    // @NotNull(message = "Authorities should not be null")
    // We define two roles/authorities: ROLE_USER or ROLE_ADMIN
    private String authorities;  

    private int fetConfig;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Business> businesses;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Employee> employees;

    

    

    public User(Long id,String username,String password,String firstName,String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.businesses = new ArrayList<Business>();
        this.employees = new ArrayList<Employee>();

    }

    public User(String username,String password,String firstName,String lastName,String authorities) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
    }

    public User(String password,String firstName,String lastName) {
        this.username = "dummyuser@gmail.com";
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = "authoritydummy";
    }

    public User(String username){
        this.username = username;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    /* Return a collection of authorities granted to the user.
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(authorities));
    }

    /*
    The various is___Expired() methods return a boolean to indicate whether
    or not the user???s account is enabled or expired.
    */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
        public boolean isAccountNonLocked() {
    return true;
    }
    @Override
        public boolean isCredentialsNonExpired() {
    return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}

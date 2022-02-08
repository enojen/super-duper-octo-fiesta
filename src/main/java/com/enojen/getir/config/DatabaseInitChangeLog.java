package com.enojen.getir.config;

import com.enojen.getir.model.Book;
import com.enojen.getir.model.ERole;
import com.enojen.getir.model.Role;
import com.enojen.getir.model.User;
import com.enojen.getir.repository.BookRepository;
import com.enojen.getir.repository.RoleRepository;
import com.enojen.getir.repository.UserRepository;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@ChangeLog(order = "001")
public class DatabaseInitChangeLog {
    @ChangeSet(order = "001", id = "init_roles", author = "enojen")
    public void initRoles(RoleRepository roleRepository) {
        roleRepository.save(Role.builder().name(ERole.ROLE_CUSTOMER).build());
        roleRepository.save(Role.builder().name(ERole.ROLE_ADMIN).build());
    }

    @ChangeSet(order = "002", id = "init_users", author = "enojen")
    public void initUsers(UserRepository userRepository, PasswordEncoder encoder,RoleRepository roleRepository) {
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add((roleRepository.findByName(ERole.ROLE_ADMIN)).get());
        User admin = User.builder()
                .username("admin")
                .email("admin@example.com")
                .password(encoder.encode("123456"))
                .roles(adminRoles)
                .build();

        userRepository.save(admin);

        Set<Role> customerRoles = new HashSet<>();
        customerRoles.add((roleRepository.findByName(ERole.ROLE_CUSTOMER)).get());
        User customer = User.builder()
                .username("customer")
                .email("customer@example.com")
                .password(encoder.encode("123456"))
                .roles(customerRoles)
                .build();

        userRepository.save(customer);
    }

    @ChangeSet(order = "003", id = "init_books", author = "enojen")
    public void initBooks(BookRepository bookRepository) {

        Book sefiller =  Book.builder()
                .title("Sefiller")
                .description("Sefiller dünya klasikleri listesinde yer alan...")
                .author("Victor Hugo")
                .price(BigDecimal.TEN)
                .quantity(30L)
                .build();

        bookRepository.save(sefiller);

        Book karamazov =  Book.builder()
                .title("Karamazov")
                .description("Fyodor Dostoyevski'nin birikiminin ve edebi gücünün...")
                .author("Fyodor Dostoyevski")
                .price(BigDecimal.valueOf(15L))
                .quantity(21L)
                .build();
        bookRepository.save(karamazov);

        Book notre =  Book.builder()
                .title("Notre Dame")
                .description("Victor Hugo, olayları ince ince ördüğü Notre-Dame'ın...")
                .author("Victor Hugo")
                .price(BigDecimal.valueOf(23L))
                .quantity(39L)
                .build();
        bookRepository.save(notre);
        //another test
    }
}

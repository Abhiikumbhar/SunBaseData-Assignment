package com.customer.CustomerCRUD;

import com.customer.CustomerCRUD.user.User;
import com.customer.CustomerCRUD.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Test
    public void testAddNew(){
        User user = new User();
        user.setEmail("Random@gmail.com");
        user.setFirst_name("random");
        user.setLast_name("person");
        user.setStreet("1stStreet");
        user.setCity("1stCity");
        user.setState("1stState");
        user.setPhone("1234567890");

        User savedUser = repo.save(user);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for(User user : users){
            System.out.println(user);
        }
    }


    @Test
    public void testUpdate(){
        Integer userId = 1;
        Optional<User> optionalUser =  repo.findById(userId);
        User user = optionalUser.get();
        user.setLast_name("Customer");
        repo.save(user);

        User updatedUser = repo.findById(userId).get();
        Assertions.assertThat(updatedUser.getLast_name()).isEqualTo("Customer");
    }


    @Test
    public void testGet()
    {
        Integer userId = 1;
        Optional<User> optionalUser =  repo.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete(){
        Integer userId = 1;
        repo.deleteById(userId);

        Optional<User> optionalUser =  repo.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }

}

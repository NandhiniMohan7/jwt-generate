package jwtTokenDemo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jwtTokenDemo.Entity.Users;

public interface UsersRepository extends JpaRepository<Users,Integer>
{
       Optional<Users> findByUserName(String name);
}

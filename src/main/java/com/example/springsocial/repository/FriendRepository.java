package com.example.springsocial.repository;

import com.example.springsocial.model.Friend;
import com.example.springsocial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Integer> {
    boolean existsByFirstUserAndSecondUser(User first, User second);

    List<Friend> findByFirstUser(User user);



    @Transactional
    @Modifying
    @Query("DELETE from Friend f where f.id= ?1" )
    void deleteFollower(Integer id);

    Friend findByFirstUserAndSecondUser(User first, User second);

    List<Friend> findBySecondUser(User user);
}

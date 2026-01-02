package org.reversetech.repository;


import org.reversetech.entity.Auth;

public interface AuthRepository extends MyGenericRepo<Auth, Long> {
    Boolean existsByUserNameAndPassword(String userName, String password);
}

package com.damao.repository;

import com.damao.entity.TUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author huyongxing
 * @site www.skson.com
 * @date 2018/8/2 10:00
 */
public interface TUserRepository extends JpaRepository<TUser, Integer> {

    public TUser findByUsername(String username);
}

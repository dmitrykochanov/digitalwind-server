package com.dmko.iconf.users

import com.dmko.iconf.users.entities.UserEntity
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository


@Mapper
@Repository
interface UsersDao {

    @Insert("""INSERT INTO users(email, first_name, last_name, password)
                      VALUES(#{email}, #{firstName}, #{lastName}, #{password})""")
    fun insertUser(userEntity: UserEntity)

    @Select("SELECT * FROM users WHERE email = #{email} LIMIT 1")
    fun findUserByEmail(email: String): UserEntity?
}
package com.dmko.iconf.users

import com.dmko.iconf.users.entities.RoleEntity
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

    @Select("""SELECT roles.id, roles.name
        FROM users_roles JOIN roles ON users_roles.role_id = roles.id
        WHERE users_roles.user_id = #{userId}""")
    fun getUserRoles(userId: Long): List<RoleEntity>

    @Insert("""INSERT INTO users_roles(user_id, role_id)
        VALUES(#{userId}, #{roleId})""")
    fun addRoleToUser(userId: Long, roleId: Long)
}
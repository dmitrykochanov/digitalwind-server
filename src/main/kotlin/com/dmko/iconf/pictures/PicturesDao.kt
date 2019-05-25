package com.dmko.iconf.pictures

import com.dmko.iconf.pictures.entities.CommentEntity
import com.dmko.iconf.pictures.entities.PictureEntity
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface PicturesDao {

    @Select("SELECT * FROM pictures")
    fun getPictures(): List<PictureEntity>

    @Insert("""INSERT INTO pictures(title, image_url, number, city, rate, description)
        VALUES(#{title}, #{imageUrl}, #{number}, #{city}, #{rate}, #{description})""")
    fun insertPicture(picture: PictureEntity)

    @Delete("DELETE FROM pictures WHERE id = #{id}")
    fun deletePicture(id: Long)

    @Update("UPDATE pictures SET rate=, #{rate} WHERE id = #{id}")
    fun ratePicture(id: Long, rate: Int)

    @Select("SELECT * FROM comments")
    fun getComments(pictureId: Long): List<CommentEntity>

    @Insert("INSERT INTO comments(date, author_id, body) VALUES(#{date}, #{authorId}, #{body})")
    fun insertComment(comment: CommentEntity)
}

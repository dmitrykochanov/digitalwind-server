package com.dmko.iconf.pictures

import com.dmko.iconf.pictures.entities.CommentEntity
import com.dmko.iconf.pictures.entities.PictureEntity
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface PicturesDao {

    @Select("SELECT * FROM pictures")
    @Results(
            Result(property = "imageUrl", column = "image_url")
    )
    fun getPictures(): List<PictureEntity>

    @Insert("""INSERT INTO pictures(title, image_url, number, city, rate, description)
        VALUES(#{title}, #{imageUrl}, #{number}, #{city}, #{rate}, #{description})""")
    fun insertPicture(picture: PictureEntity)

    @Delete("DELETE FROM pictures WHERE id = #{id}")
    fun deletePicture(id: Long)

    @Update("UPDATE pictures SET rate= #{rate} WHERE id = #{id}")
    fun ratePicture(id: Long, rate: Int)

    @Select("SELECT * FROM comments WHERE picture_id = #{pictureId}")
    @Results(
            Result(property = "pictureId", column = "picture_id"),
            Result(property = "authorId", column = "author_id")
    )
    fun getComments(pictureId: Long): List<CommentEntity>

    @Insert("""INSERT INTO comments(date, picture_id, author_id, body)
        VALUES(#{date}, #{pictureId}, #{authorId}, #{body})""")
    fun insertComment(comment: CommentEntity)
}

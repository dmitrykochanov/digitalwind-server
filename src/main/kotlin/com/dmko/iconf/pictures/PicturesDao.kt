package com.dmko.iconf.pictures

import com.dmko.iconf.pictures.entities.CommentEntity
import com.dmko.iconf.pictures.entities.PictureEntity
import com.dmko.iconf.pictures.entities.RatingEntity
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

    @Insert("""INSERT INTO pictures(title, image_url, number, city, description)
        VALUES(#{title}, #{imageUrl}, #{number}, #{city}, #{description})""")
    fun insertPicture(picture: PictureEntity)

    @Delete("DELETE FROM pictures WHERE id = #{id}")
    fun deletePicture(id: Long)

    @Select("SELECT * FROM ratings WHERE picture_id = #{pictureId}")
    @Results(
            Result(property = "userId", column = "user_id"),
            Result(property = "pictureId", column = "picture_id")
    )
    fun getRatings(pictureId: Long): List<RatingEntity>

    @Insert("""INSERT INTO ratings(user_id, picture_id, rate)
        VALUES(#{userId}, #{pictureId}, #{rate})""")
    fun insertRating(rating: RatingEntity)

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

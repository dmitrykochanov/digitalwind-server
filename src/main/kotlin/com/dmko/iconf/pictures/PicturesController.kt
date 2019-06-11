package com.dmko.iconf.pictures

import com.dmko.iconf.pictures.entities.*
import com.dmko.iconf.users.UsersDao
import com.dmko.iconf.users.entities.CommentRequest
import com.dmko.iconf.users.entities.UserEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class PicturesController(
        private val usersDao: UsersDao,
        private val picturesDao: PicturesDao
) {

    @CrossOrigin
    @GetMapping("/pictures")
    fun getPictures(): List<Picture> {
        val picturesEntities = picturesDao.getPictures()
        val pictures = mutableListOf<Picture>()

        for (entity in picturesEntities) {
            val commentsEntities = picturesDao.getComments(entity.id)
            val comments = mutableListOf<Comment>()

            val ratings = picturesDao.getRatings(entity.id)
            val rate = if (ratings.isEmpty()) {
                0.0
            } else {
                var totalRating = 0
                for (rating in ratings) {
                    totalRating += rating.rate
                }
                totalRating / ratings.size.toDouble()
            }

            for (commentEntity in commentsEntities) {
                val authorName = usersDao.findUserById(commentEntity.authorId).login

                comments += Comment(
                        id = commentEntity.id,
                        date = commentEntity.date,
                        authorName = authorName,
                        body = commentEntity.body
                )
            }

            pictures += Picture(
                    id = entity.id,
                    title = entity.title,
                    imageUrl = entity.imageUrl,
                    number = entity.number,
                    city = entity.city,
                    rate = rate,
                    description = entity.description,
                    comments = comments
            )
        }

        return pictures
    }

    @CrossOrigin
    @PostMapping("/pictures")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun addPicture(@RequestBody picture: PictureEntity) {
        picturesDao.insertPicture(picture)
    }

    @CrossOrigin
    @DeleteMapping("/pictures/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun deletePicture(@PathVariable("id") id: Long) {
        picturesDao.deletePicture(id)
    }

    @CrossOrigin
    @PostMapping("/picture/{id}/comment")
    @PreAuthorize("hasAuthority('USER')")
    fun addComment(
            @PathVariable("id") id: Long,
            @RequestBody comment: CommentRequest,
            @AuthenticationPrincipal user: UserEntity
    ) {
        val commentEntity = CommentEntity(
                pictureId = id,
                date = System.currentTimeMillis(),
                authorId = user.id,
                body = comment.body
        )
        picturesDao.insertComment(commentEntity)
    }

    @CrossOrigin
    @PostMapping("/picture/{id}/rate/{rating}")
    @PreAuthorize("hasAuthority('USER')")
    fun ratePicture(
            @PathVariable("id") pictureId: Long,
            @PathVariable("rating") rating: Int,
            @AuthenticationPrincipal user: UserEntity
    ): ResponseEntity<Unit> {
        if (rating !in 0..10) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        val entity = RatingEntity(
                userId = user.id,
                pictureId = pictureId,
                rate = rating
        )
        picturesDao.insertRating(entity)
        return ResponseEntity(HttpStatus.OK)
    }
}

package com.dmko.iconf.pictures

import com.dmko.iconf.pictures.entities.Comment
import com.dmko.iconf.pictures.entities.Picture
import com.dmko.iconf.pictures.entities.PictureEntity
import com.dmko.iconf.users.UsersDao
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class PicturesController(
        private val usersDao: UsersDao,
        private val picturesDao: PicturesDao
) {

    @CrossOrigin
    @GetMapping("/pictures")
    @PreAuthorize("hasAuthority('USER')")
    fun getPictures(): List<Picture> {
        val picturesEntities = picturesDao.getPictures()
        val pictures = mutableListOf<Picture>()

        for (entity in picturesEntities) {
            val commentsEntities = picturesDao.getComments(entity.id)
            val comments = mutableListOf<Comment>()

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
                    rate = entity.rate,
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
}

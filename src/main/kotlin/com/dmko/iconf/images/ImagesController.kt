package com.dmko.iconf.images

import com.dmko.iconf.images.entities.ImageEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder
import java.util.*


@RestController
class ImagesController(
        private val imagesDao: ImagesDao
) {

    @CrossOrigin
    @PostMapping("/images")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun uploadImage(
            @RequestParam("image") image: MultipartFile,
            uriBuilder: UriComponentsBuilder
    ): ResponseEntity<String> {
        val imageSize = image.size
        val maxSize = 20971520 //20Mb
        if (imageSize == 0L || imageSize > maxSize) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        val id = UUID.randomUUID().toString()

        val entity = ImageEntity(
                id = id,
                data = image.bytes
        )
        imagesDao.saveImage(entity)

        val imageUrl = uriBuilder.path("/images/$id").build().toUri().toString()
        return ResponseEntity(imageUrl, HttpStatus.OK)
    }

    @CrossOrigin
    @GetMapping("/images/{id}")
    fun getImage(@PathVariable("id") id: String): ResponseEntity<ByteArray> {
        val image = imagesDao.getImage(id)

        val headers = HttpHeaders()
        headers.contentType = MediaType.IMAGE_JPEG
        headers.contentLength = image.data.size.toLong()

        return ResponseEntity(image.data, headers, HttpStatus.OK)
    }
}

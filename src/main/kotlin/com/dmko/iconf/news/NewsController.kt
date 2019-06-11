package com.dmko.iconf.news

import com.dmko.iconf.news.entities.NewsEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/news")
class NewsController(
        private val newsDao: NewsDao
) {

    @CrossOrigin
    @GetMapping
    fun getNews() = newsDao.getNews()

    @CrossOrigin
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    fun addNews(@RequestBody news: NewsEntity) {
        newsDao.insertNews(news)
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun deleteNews(@PathVariable("id") id: Long) {
        newsDao.deleteNews(id)
    }
}

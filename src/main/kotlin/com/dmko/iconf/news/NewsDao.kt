package com.dmko.iconf.news

import com.dmko.iconf.news.entities.NewsEntity
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface NewsDao {

    @Select("SELECT * FROM news")
    fun getNews(): List<NewsEntity>

    @Insert("INSERT INTO news(title, body, image_url) VALUES(#{title}, #{body}, #{imageUrl}) ")
    fun insertNews(news: NewsEntity)

    @Delete("DELETE FROM news WHERE id = #{id}")
    fun deleteNews(id: Long)
}

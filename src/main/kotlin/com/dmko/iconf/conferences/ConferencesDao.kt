package com.dmko.iconf.conferences

import com.dmko.iconf.conferences.entities.ConferenceEntity
import com.dmko.iconf.conferences.entities.ParticipantEntity
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface ConferencesDao {

    @Select("SELECT * FROM conferences")
    fun getConferences(): List<ConferenceEntity>

    @Select("SELECT * FROM conferences WHERE id = #{conferenceId} LIMIT 1")
    fun findConferenceById(conferenceId: Long): ConferenceEntity

    @Insert("INSERT INTO conferences(name,description,image_url) VALUES(#{name}, #{description}, #{imageUrl})")
    fun insertConference(conferenceEntity: ConferenceEntity)

    @Delete("DELETE FROM conferences WHERE id = #{conferenceId}")
    fun deleteConference(conferenceId: Long)

    @Select("""
            SELECT users.id, users.email FROM conferences_participants AS cp
            JOIN users ON cp.user_id = users.id
            WHERE cp.conference_id = #{conferenceId}
            """)
    fun getConferenceParticipants(conferenceId: Long): List<ParticipantEntity>

    @Insert("INSERT INTO conferences_participants(conference_id, user_id) VALUES(#{conferenceId}, #{userId})")
    fun addParticipantToConference(conferenceId: Long, userId: Long)

    @Delete("DELETE FROM conferences_participants WHERE conference_id = #{conferenceId} AND user_id = #{userId}")
    fun deleteParticipantFromConference(conferenceId: Long, userId: Long)
}
package com.dmko.iconf.conferences

import com.dmko.iconf.base.BaseResponse
import com.dmko.iconf.conferences.entities.ConferenceEntity
import com.dmko.iconf.conferences.entities.ConferenceResponse
import com.dmko.iconf.conferences.entities.ParticipantEntity
import org.springframework.web.bind.annotation.*

@RestController
class ConferencesController(private val conferencesDao: ConferencesDao) {

    @CrossOrigin
    @GetMapping("/conferences")
    fun getConferences(): BaseResponse<List<ConferenceResponse>> {
        val conferencesWithParticipants = mutableListOf<ConferenceResponse>()
        val conferences = conferencesDao.getConferences()
        for (conferenceEntity: ConferenceEntity in conferences) {
            val participants = conferencesDao.getConferenceParticipants(conferenceEntity.id)
            conferencesWithParticipants.add(ConferenceResponse(
                    conferenceEntity.id,
                    conferenceEntity.name,
                    conferenceEntity.description,
                    conferenceEntity.imageUrl,
                    participants
            ))
        }
        return BaseResponse(conferencesWithParticipants, true)
    }

    @CrossOrigin
    @GetMapping("/conference/{conferenceId}")
    fun getConference(@PathVariable("conferenceId") conferenceId: Long): BaseResponse<ConferenceResponse> {
        val conference = conferencesDao.findConferenceById(conferenceId)
        val conferenceParticipants = conferencesDao.getConferenceParticipants(conferenceId)
        return BaseResponse(ConferenceResponse(
                conference.id,
                conference.name,
                conference.description,
                conference.imageUrl,
                conferenceParticipants
        ), true)
    }

    @CrossOrigin
    @PostMapping("/conferences")
    fun addConference(@RequestBody conferenceEntity: ConferenceEntity): BaseResponse<Nothing> {
        return try {
            conferencesDao.insertConference(conferenceEntity)
            BaseResponse(null, true)
        } catch (t: Throwable) {
            BaseResponse(null, false)
        }
    }

    @CrossOrigin
    @DeleteMapping("/conferences/{conferenceId}")
    fun deleteConference(@PathVariable("conferenceId") conferenceId: Long): BaseResponse<Nothing> {
        return try {
            conferencesDao.deleteConference(conferenceId)
            BaseResponse(null, true)
        } catch (t: Throwable) {
            BaseResponse(null, false)
        }
    }

    @CrossOrigin
    @PostMapping("/conferences/{conferenceId}/participants")
    fun addParticipantToConference(@PathVariable("conferenceId") conferenceId: Long, @RequestBody participantEntity: ParticipantEntity): BaseResponse<Nothing> {
        return try {
            conferencesDao.addParticipantToConference(conferenceId, participantEntity.id)
            BaseResponse(null, true)
        } catch (t: Throwable) {
            BaseResponse(null, false)
        }
    }

    @CrossOrigin
    @DeleteMapping("/conferences/{conferenceId}/participants")
    fun deleteParticipantFromConference(@PathVariable("conferenceId") conferenceId: Long, @RequestBody participantId: Long): BaseResponse<Nothing> {
        return try {
            conferencesDao.deleteParticipantFromConference(conferenceId, participantId)
            BaseResponse(null, true)
        } catch (t: Throwable) {
            BaseResponse(null, false)
        }
    }
}
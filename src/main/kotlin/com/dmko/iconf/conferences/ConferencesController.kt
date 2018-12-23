package com.dmko.iconf.conferences

import com.dmko.iconf.base.BaseResponse
import com.dmko.iconf.conferences.entities.ConferenceEntity
import com.dmko.iconf.conferences.entities.ConferenceResponse
import com.dmko.iconf.conferences.entities.ParticipantEntity
import com.dmko.iconf.conferences.entities.ParticipateRequest
import com.dmko.iconf.users.entities.UserEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ConferencesController(private val conferencesDao: ConferencesDao) {

    @CrossOrigin
    @PreAuthorize("hasAuthority('USER')")
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
    @PreAuthorize("hasAuthority('USER')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/conferences/{conferenceId}/participants")
    fun deleteParticipantFromConference(@PathVariable("conferenceId") conferenceId: Long, @RequestBody participantId: Long): BaseResponse<Nothing> {
        return try {
            conferencesDao.deleteParticipantFromConference(conferenceId, participantId)
            BaseResponse(null, true)
        } catch (t: Throwable) {
            BaseResponse(null, false)
        }
    }

    @CrossOrigin
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/conferences/{conferenceId}/participate")
    fun participateInConference(@AuthenticationPrincipal user: UserEntity,
                                @PathVariable("conferenceId") conferenceId: Long,
                                @RequestBody participateRequest: ParticipateRequest): ResponseEntity<BaseResponse<Nothing>> {
        if (participateRequest.participate) {
            conferencesDao.addParticipantToConference(conferenceId, user.id)
        } else {
            conferencesDao.deleteParticipantFromConference(conferenceId, user.id)
        }
        return ResponseEntity(BaseResponse(null, true), HttpStatus.OK)
    }
}
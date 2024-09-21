package com.example.afaloan.services

import com.example.afaloan.exceptions.ErrorCode
import com.example.afaloan.exceptions.InternalException
import com.example.afaloan.models.Profile
import com.example.afaloan.repositories.ProfileRepository
import com.example.afaloan.utils.PROFILE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import java.util.*

class ProfileServiceTest {

    private val profileRepository = mock<ProfileRepository>()

    private val profileService = ProfileService(profileRepository)

    @Test
    fun `find(id UUID) should execute successfully`() {
        val profile = PROFILE
        whenever(profileRepository.findById(any())).thenReturn(Optional.of(profile))

        val result = profileService.find(profile.id!!)

        assertThat(result.id).isEqualTo(profile.id)
        assertThat(result.name).isEqualTo(profile.name)
        assertThat(result.phoneNumber).isEqualTo(profile.phoneNumber)
    }

    @Test
    fun `find(id UUID) should throw NOT_FOUND`() {
        whenever(profileRepository.findByIdAndUserId(any(), any())).thenReturn(null)

        val ex = assertThrows<InternalException> { profileService.find(UUID.randomUUID()) }

        assertThat(ex.httpStatus).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(ex.errorCode).isEqualTo(ErrorCode.PROFILE_NOT_FOUND)
    }

    @Test
    fun `find(id UUID, userId UUID) should execute successfully`() {
        val profile = PROFILE
        whenever(profileRepository.findByIdAndUserId(any(), any())).thenReturn(profile)

        val result = profileService.find(profile.id!!, profile.user!!.id!!)

        assertThat(result.id).isEqualTo(profile.id)
        assertThat(result.name).isEqualTo(profile.name)
        assertThat(result.phoneNumber).isEqualTo(profile.phoneNumber)
    }

    @Test
    fun `find(id UUID, userId UUID) should throw NOT_FOUND`() {
        whenever(profileRepository.findByIdAndUserId(any(), any())).thenReturn(null)

        val ex = assertThrows<InternalException> { profileService.find(UUID.randomUUID(), UUID.randomUUID()) }

        assertThat(ex.httpStatus).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(ex.errorCode).isEqualTo(ErrorCode.PROFILE_NOT_FOUND)
    }

    @Test
    fun `findByUserID should execute successfully`() {
        val profile = PROFILE
        whenever(profileRepository.findByUserId(any())).thenReturn(profile)

        val result = profileService.findByUserID(profile.user!!.id!!)

        assertThat(result.id).isEqualTo(profile.id)
        assertThat(result.name).isEqualTo(profile.name)
        assertThat(result.phoneNumber).isEqualTo(profile.phoneNumber)
    }

    @Test
    fun `findByUserID should throw NOT_FOUND`() {
        whenever(profileRepository.findByUserId(any())).thenReturn(null)

        val ex = assertThrows<InternalException> { profileService.findByUserID(UUID.randomUUID()) }

        assertThat(ex.httpStatus).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(ex.errorCode).isEqualTo(ErrorCode.PROFILE_NOT_FOUND)
    }

    @Test
    fun `create should execute successfully`() {
        val profile = PROFILE
        whenever(profileRepository.save(any<Profile>())).thenReturn(profile)

        val result = profileService.create(profile)

        assertThat(result).isEqualTo(profile.id)
    }

    @Test
    fun `update should execute successfully`() {
        val oldProfile = PROFILE
        val newProfile = oldProfile.copy(name = "ch name", surname = "ch surname")
        whenever(profileRepository.findByIdAndUserId(any(), any())).thenReturn(oldProfile)
        whenever(profileRepository.save(any<Profile>())).thenReturn(newProfile)

        val result = profileService.update(oldProfile.id!!, oldProfile)

        assertThat(result.id).isEqualTo(newProfile.id)
        assertThat(result.name).isEqualTo(newProfile.name)
        assertThat(result.phoneNumber).isEqualTo(newProfile.phoneNumber)
    }
}
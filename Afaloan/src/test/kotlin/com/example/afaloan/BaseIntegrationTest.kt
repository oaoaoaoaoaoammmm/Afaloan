package com.example.afaloan

import com.example.afaloan.configurations.PostgresAutoConfiguration
import com.example.afaloan.repositories.ProfileRepository
import com.example.afaloan.repositories.RoleRepository
import com.example.afaloan.repositories.UserRepository
import com.example.afaloan.utils.PROFILE
import com.example.afaloan.utils.USER
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [PostgresAutoConfiguration::class]
)
@ActiveProfiles("test")
abstract class BaseIntegrationTest {

    protected lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userRoleRepository: RoleRepository

    @Autowired
    private lateinit var profileRepository: ProfileRepository

    @BeforeEach
    fun setUp(context: WebApplicationContext) {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build()
        createUserIfNotExist()
    }

    private fun createUserIfNotExist() {
        val user = userRepository.findById(USER.id!!)
        if (user.isEmpty) {
            val roles = userRoleRepository.findAll().toSet()
            USER = userRepository.save(USER.copy(roles = roles))
            PROFILE = profileRepository.save(PROFILE.copy(user = USER))
        }
    }

    companion object {
        const val API_PREFIX = "/api/v1"
    }
}

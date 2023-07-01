package study.datajpa.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import study.datajpa.entity.Member

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest(
    @Autowired private val memberJpaRepository: MemberJpaRepository,
) {

    @Test
    fun testMember() {
        val member = Member(username = "memberA")
        val saveMember = memberJpaRepository.save(member)

        val findMember = memberJpaRepository.find(saveMember.id)

        findMember.id?.let { assertThat(it == member.id) }
        assertThat(findMember.username.equals(member.username))
    }

}
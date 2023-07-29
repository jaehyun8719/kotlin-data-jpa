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

    @Test
    fun basicCRUD() {
        val member1 = Member(username = "member1")
        val member2 = Member(username = "member2")
        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        // 단건 조회 검증
        val findMember1 = member1.id?.let { memberJpaRepository.findById(it).get() }
        val findMember2 = member2.id?.let { memberJpaRepository.findById(it).get() }

        assertThat(findMember1).isEqualTo(member1)
        assertThat(findMember2).isEqualTo(member2)

        // 리스트 조회 검증
        val all = memberJpaRepository.findAll()
        assertThat(all.size).isEqualTo(2)

        // 카운트 검증
        val count = memberJpaRepository.count()
        assertThat(count).isEqualTo(2)

        // 삭제 검증
        memberJpaRepository.delete(member1)
        memberJpaRepository.delete(member2)

        val deletedCount = memberJpaRepository.count()
        assertThat(deletedCount).isEqualTo(0)
    }

    @Test
    fun findByUsernameAndAgeGreaterThan() {
        val member1 = Member(username = "AAA", age = 10)
        val member2 = Member(username = "AAA", age = 20)
        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        val result = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15)
        assertThat(result[0].username).isEqualTo("AAA")
        assertThat(result[0].age).isEqualTo(20)
        assertThat(result.size).isEqualTo(1)
    }

    @Test
    fun testNamedQuery() {
        val member1 = Member(username = "AAA", age = 10)
        val member2 = Member(username = "BBB", age = 20)
        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        val result = memberJpaRepository.findByUsername("AAA")
        val findMember = result[0]
        assertThat(findMember).isEqualTo(member1)
    }

}
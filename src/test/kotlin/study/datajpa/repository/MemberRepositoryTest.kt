package study.datajpa.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import study.datajpa.entity.Member

@SpringBootTest
@Transactional
class MemberRepositoryTest(
    @Autowired private val memberRepository: MemberRepository,
) {

    @Test
    fun testMember() {
        val member = Member(username = "memberA")
        val saveMember = memberRepository.save(member)

        val findMember = memberRepository.findById(saveMember.id).get()

        findMember.id?.let { assertThat(it == member.id) }
        assertThat(findMember.username.equals(member.username))
    }

    @Test
    fun basicCRUD() {
        val member1 = Member(username = "member1")
        val member2 = Member(username = "member2")
        memberRepository.save(member1)
        memberRepository.save(member2)

        // 단건 조회 검증
        val findMember1 = member1.id?.let { memberRepository.findById(it).get() }
        val findMember2 = member2.id?.let { memberRepository.findById(it).get() }

        assertThat(findMember1).isEqualTo(member1)
        assertThat(findMember2).isEqualTo(member2)

        // 리스트 조회 검증
        val all = memberRepository.findAll()
        assertThat(all.size).isEqualTo(2)

        // 카운트 검증
        val count = memberRepository.count()
        assertThat(count).isEqualTo(2)

        // 삭제 검증
        memberRepository.delete(member1)
        memberRepository.delete(member2)

        val deletedCount = memberRepository.count()
        assertThat(deletedCount).isEqualTo(0)
    }

}
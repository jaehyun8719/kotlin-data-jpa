package study.datajpa.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import study.datajpa.entity.Member
import study.datajpa.entity.Team
import java.util.*

@SpringBootTest
@Transactional
class MemberRepositoryTest(
    @Autowired private val memberRepository: MemberRepository,
) {
    @Autowired
    private lateinit var teamRepository: TeamRepository

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

    @Test
    fun findByUsernameAndAgeGreaterThan() {
        val member1 = Member(username = "AAA", age = 10)
        val member2 = Member(username = "AAA", age = 20)
        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15)
        assertThat(result[0].username).isEqualTo("AAA")
        assertThat(result[0].age).isEqualTo(20)
        assertThat(result.size).isEqualTo(1)
    }

    @Test
    fun testNamedQuery() {
        val member1 = Member(username = "AAA", age = 10)
        val member2 = Member(username = "BBB", age = 20)
        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findByUsername("AAA")
        val findMember = result[0]
        assertThat(findMember).isEqualTo(member1)
    }

    @Test
    fun testQuery() {
        val member1 = Member(username = "AAA", age = 10)
        val member2 = Member(username = "BBB", age = 20)
        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findUser("AAA", 10)
        assertThat(result[0]).isEqualTo(member1)
    }

    @Test
    fun testFindUsernameList() {
        val member1 = Member(username = "AAA", age = 10)
        val member2 = Member(username = "BBB", age = 20)
        memberRepository.save(member1)
        memberRepository.save(member2)

        val usernameList = memberRepository.findUsernameList()
        for (s in usernameList) {
            println("s = $s")
        }
    }

    @Test
    fun testFindMemberDto() {
        val team1 = Team(1,"teamA")
        teamRepository.save(team1)

        val member1 = Member(username = "AAA", age = 10)
        memberRepository.save(member1)

        val memberDto = memberRepository.findMemberDto()
        for (dto in memberDto) {
            println("dto = $dto")
        }
    }

    @Test
    fun testFindByNames() {
        val member1 = Member(username = "AAA", age = 10)
        val member2 = Member(username = "BBB", age = 20)
        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findByNames(listOf("AAA", "BBB"))
        for (member in result) {
            println("member= $member")
        }
    }

    @Test
    fun testReturnType() {
        val member1 = Member(username = "AAA", age = 10)
        val member2 = Member(username = "BBB", age = 20)
        memberRepository.save(member1)
        memberRepository.save(member2)

        val findMemberByUsername = memberRepository.findMemberByUsername("asdasd")
        println("result = $findMemberByUsername")
    }

}
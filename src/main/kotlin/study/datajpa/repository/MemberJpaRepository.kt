package study.datajpa.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import study.datajpa.entity.Member
import javax.persistence.EntityManager

@Repository
class MemberJpaRepository(
    @Autowired private val em: EntityManager
) {

    fun save(member: Member): Member {
        em.persist(member)
        return member
    }

    fun find(id: Long?): Member {
        return em.find(Member::class.java, id)
    }

}
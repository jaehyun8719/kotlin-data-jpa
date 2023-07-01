package study.datajpa.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Member(

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    var id: Long? = null,

    var username: String? = null,

    var age: Int? = null,

) {

    @ManyToOne
    @JoinColumn(name = "team_id")
    var team: Team? = null

    fun changeTeam(team: Team) {
        this.team = team;
        team.members.add(this)
    }
}
package gr.makris.smartConnect.data.user

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userid", nullable = true)
    var userId: String,
    var firstname: String,
    var lastname: String,
    var email: String,
    var password: String,
    var enabled: Boolean
)

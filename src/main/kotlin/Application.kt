import com.example.monstertutorial.createmonsterusecase.GetMonsterRequest
import com.example.monstertutorial.createmonsterusecase.GetMonsterUsecase
import com.example.monstertutorial.entities.Monster
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database
import com.example.monstertutorial.createmonsterusecase.repositories.CreateMonsterRepositoryExposed

fun main(args: Array<String>) {
    // This should come from configuration
    val db by lazy { Database.connect("jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1;", "org.h2.Driver", "root", "")}
    val createMonsterRepository =
        CreateMonsterRepositoryExposed(db)

    // We shouldn't do this
    createMonsterRepository.insertMonster(Monster("abc", "Fred", 12))

    val getMonsterUsecase = GetMonsterUsecase(createMonsterRepository)

    embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            jackson {
                configure(SerializationFeature.INDENT_OUTPUT, true)
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

                // Exposed uses Joda for backwards compatibility with Java 1.6 otherwise might use Java 8 DateTime
                registerModule(JodaModule())
            }
        }

        install(CallLogging)

        routing {
            get("/monster/{id}") {
                val id: String = this.call.parameters["id"]!!
                call.respond(getMonsterUsecase.handle(GetMonsterRequest(id)))
            }
        }
    }.start(wait = true)
}
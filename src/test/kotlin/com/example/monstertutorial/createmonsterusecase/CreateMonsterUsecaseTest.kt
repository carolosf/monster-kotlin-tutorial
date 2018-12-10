package com.example.monstertutorial.createmonsterusecase

import com.example.monstertutorial.createmonsterusecase.repositories.CreateMonsterRepository
import com.example.monstertutorial.createmonsterusecase.repositories.CreateMonsterRepositoryExposed
import com.example.monstertutorial.entities.Monster
import org.jetbrains.exposed.sql.Database
import org.junit.Test
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.test.assertEquals

class CreateMonsterUsecaseTest {
    companion object { // kind of like static in java
        private val log = LoggerFactory.getLogger(CreateMonsterUsecaseTest::class.qualifiedName)
        private val db by lazy { Database.connect("jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1;", "org.h2.Driver", "root", "")}
    }

    @Test
    fun `ensure monster is created`() {
        val createMonsterRepository =
            CreateMonsterRepository()
        val createMonsterUsecase = CreateMonsterUsecase(createMonsterRepository)
        val createMonsterRequest = CreateMonsterRequest("abc", "createmonsterusecase.Monster", 21)
        val createMonsterResponse = createMonsterUsecase.handle(createMonsterRequest)

        assertEquals(createMonsterRequest.name, createMonsterResponse.name)
        assertEquals(createMonsterRequest.age, createMonsterResponse.age)
        assertEquals(createMonsterRequest.id, createMonsterResponse.id)
    }

    @Test
    fun `ensure monster is created in db`() {
        val createMonsterRepository = CreateMonsterRepository()

        val createMonsterUsecase = CreateMonsterUsecase(createMonsterRepository)
        val createMonsterRequest = CreateMonsterRequest("abc", "createmonsterusecase.Monster", 21)

        createMonsterUsecase.handle(createMonsterRequest)

        assertEquals(createMonsterRequest.name, createMonsterRepository.getMonster(createMonsterRequest.id).name)
    }

    @Test
    fun `ensure monster is created in db using exposed repository`() {
        val createMonsterRepository = CreateMonsterRepositoryExposed(db)

        val createMonsterUsecase = CreateMonsterUsecase(createMonsterRepository)
        val createMonsterRequest = CreateMonsterRequest("abc", "createmonsterusecase.Monster", 21)

        createMonsterUsecase.handle(createMonsterRequest)

        assertEquals(createMonsterRequest.name, createMonsterRepository.getMonster(createMonsterRequest.id).name)
    }

    @Test
    fun `ensure get monster use case works`() {
        val createMonsterRepository = CreateMonsterRepository()
        val monster = Monster("abc", "Fred", 12)
        createMonsterRepository.insertMonster(monster)
        val getMonsterUsecase = GetMonsterUsecase(createMonsterRepository)

        getMonsterUsecase.handle(GetMonsterRequest(monster.id))
    }

    @Test
    fun `logging and reading files`() {
        // Files, logging and resources in kotlin
        log.info("Cool")

        File("myfile.txt").writeText("hello from working directory where java was run")
        val readText = File("myfile.txt").readText()
        log.info(readText)

        val readText1 = CreateMonsterUsecaseTest::class.java.getResource("myresource.txt").readText()
        log.info(readText1)
    }

    @Test
    fun `ensure vals can change if they are objects`() {
        val myList: MutableList<String> = mutableListOf("1", "wooo")
        myList.add("Hello") // this works because the pointer to the variable doesn't change even though we use val

         // myList = mutableListOf() // this doesn't work because I can't change the pointer of the "variable"

        println(myList)
        assertEquals(myList.size, 3)
    }

    @Test
    fun `ifs are expressions`() {
        val x = if (true) {
            3 // this value is returned
        } else {
            4
        }

        assertEquals(3, x)
    }
}
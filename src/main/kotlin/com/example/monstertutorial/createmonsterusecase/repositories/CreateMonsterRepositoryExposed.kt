package com.example.monstertutorial.createmonsterusecase.repositories

import com.example.monstertutorial.entities.Monster
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object MonsterTable : Table() {
    val id = varchar("id", 254).primaryKey()
    val name = varchar("name", 254)
    val age = integer("age")
}

class CreateMonsterRepositoryExposed(private val db: Database) :
    ICreateMonsterRepository {
    init {
        transaction(db) {
            SchemaUtils.create(MonsterTable)
        }
    }

    override fun insertMonster(monster: Monster) {
        transaction(db) {
            MonsterTable.insert {
                it[id] = monster.id
                it[name] = monster.name
                it[age] = monster.age
            }
        }
    }

    override fun getMonster(id: String): Monster {
        return transaction(db) {
            MonsterTable.select { MonsterTable.id eq id }.map {
                Monster(
                    it[MonsterTable.id],
                    it[MonsterTable.name],
                    it[MonsterTable.age]
                )
            }.first()
        }
    }
}

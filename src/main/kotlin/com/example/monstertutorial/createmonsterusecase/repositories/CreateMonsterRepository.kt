package com.example.monstertutorial.createmonsterusecase.repositories

import com.example.monstertutorial.entities.Monster

class CreateMonsterRepository:
    ICreateMonsterRepository {
    private val monsterMap = mutableMapOf<String, Monster>()

    override fun insertMonster(monster: Monster) {
        monsterMap[monster.id] = monster
    }

    override fun getMonster(id: String): Monster {
        return monsterMap[id] ?: throw NoSuchElementException("createmonsterusecase.Monster not found")
    }
}

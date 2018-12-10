package com.example.monstertutorial.createmonsterusecase.repositories

import com.example.monstertutorial.entities.Monster

interface ICreateMonsterRepository {
    fun insertMonster(monster: Monster)
    fun getMonster(id: String): Monster
}
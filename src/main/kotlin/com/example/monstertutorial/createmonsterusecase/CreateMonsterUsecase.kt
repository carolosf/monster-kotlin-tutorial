package com.example.monstertutorial.createmonsterusecase

import com.example.monstertutorial.createmonsterusecase.repositories.ICreateMonsterRepository
import com.example.monstertutorial.entities.Monster

class CreateMonsterUsecase(private val createMonsterRepository: ICreateMonsterRepository) {
    fun handle(createMonsterRequest: CreateMonsterRequest): CreateMonsterResponse {

        createMonsterRepository.insertMonster(
            Monster(
                createMonsterRequest.id,
                createMonsterRequest.name,
                createMonsterRequest.age
            )
        )

        return CreateMonsterResponse(
            createMonsterRequest.id,
            createMonsterRequest.name,
            createMonsterRequest.age
        )
    }
}

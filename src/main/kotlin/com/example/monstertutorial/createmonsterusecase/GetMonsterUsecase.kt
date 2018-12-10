package com.example.monstertutorial.createmonsterusecase

import com.example.monstertutorial.createmonsterusecase.repositories.ICreateMonsterRepository

class GetMonsterUsecase(private val createMonsterRepository: ICreateMonsterRepository) {
    fun handle(getMonsterRequest: GetMonsterRequest): GetMonsterResponse {
        val monster = createMonsterRepository.getMonster(getMonsterRequest.id)

        // Should use presenter pattern instead of returning a response - kept simple for teaching
        return GetMonsterResponse(monster.id, monster.name, monster.age)
    }
}

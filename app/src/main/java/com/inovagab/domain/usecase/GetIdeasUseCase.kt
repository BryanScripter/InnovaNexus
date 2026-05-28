package com.inovagab.domain.usecase

import com.inovagab.data.repository.IdeaRepository
import com.inovagab.domain.model.Idea
import kotlinx.coroutines.flow.Flow

class GetIdeasUseCase(
    private val ideaRepository: IdeaRepository
) {
    operator fun invoke(): Flow<List<Idea>> = ideaRepository.observeIdeas()
}

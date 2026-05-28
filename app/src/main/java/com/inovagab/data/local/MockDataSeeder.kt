package com.inovagab.data.local

import com.inovagab.data.local.dao.IdeaDao
import com.inovagab.data.local.dao.ProjectDao
import com.inovagab.data.local.dao.StrategyDao
import com.inovagab.data.local.dao.UserDao
import com.inovagab.data.local.entities.IdeaEntity
import com.inovagab.data.local.entities.ProjectEntity
import com.inovagab.data.local.entities.StrategyEntity
import com.inovagab.data.local.entities.UserEntity

object MockDataSeeder {

    suspend fun seedIfNeeded(
        userDao: UserDao,
        ideaDao: IdeaDao,
        projectDao: ProjectDao,
        strategyDao: StrategyDao
    ) {
        userDao.insertAll(mockUsers)
        ideaDao.insertAll(mockIdeas)
        projectDao.insertAll(mockProjects)
        strategyDao.insertAll(mockStrategies)
    }

    private val mockUsers = listOf(
        UserEntity("1", "Carlos Silva", "carlos.silva@empresa.com", "operador", "Analista de Operações", 850, 12, 8),
        UserEntity("2", "Maria Santos", "maria.santos@empresa.com", "gestor", "Gerente de Projetos", 1200, 25, 18),
        UserEntity("3", "Roberto Lima", "roberto.lima@empresa.com", "lideranca", "Diretor de Inovação", 2500, 50, 35)
    )

    private val mockStrategies = listOf(
        StrategyEntity("1", "Redução de Custos", "Identificar oportunidades para otimizar gastos e reduzir despesas operacionais", "coins"),
        StrategyEntity("2", "Produtividade", "Aumentar a eficiência dos processos e otimizar o tempo de execução", "trending"),
        StrategyEntity("3", "Segurança Operacional", "Garantir a segurança dos colaboradores e dos processos operacionais", "shield"),
        StrategyEntity("4", "Transformação Digital", "Implementar soluções tecnológicas para modernizar processos", "settings")
    )

    private val mockIdeas = listOf(
        IdeaEntity("1", "Automação de Relatórios", "Criar sistema automatizado para geração de relatórios mensais, eliminando trabalho manual", "Operação", "Alto", "aprovada", "Carlos Silva", "1", "2024-01-15", 85),
        IdeaEntity("2", "App de Check-in de Segurança", "Aplicativo mobile para registro de inspeções de segurança em tempo real", "Segurança", "Alto", "em_projeto", "Ana Costa", "4", "2024-01-10", 92),
        IdeaEntity("3", "Otimização de Rotas", "Sistema inteligente para otimização de rotas de entrega", "Logística", "Médio", "em_analise", "Pedro Alves", "5", "2024-01-20", 78),
        IdeaEntity("4", "Chatbot de Atendimento", "Implementar chatbot com IA para atendimento inicial ao cliente", "Atendimento", "Alto", "em_analise", "Lucia Mendes", "6", "2024-01-18", 88),
        IdeaEntity("5", "Redução de Impressões", "Digitalização de documentos para reduzir custos com papel e impressão", "Economia", "Baixo", "rejeitada", "João Santos", "7", "2024-01-05", 45)
    )

    private val mockProjects = listOf(
        ProjectEntity("1", "Automação de Relatórios", "Sistema automatizado para geração de relatórios mensais", "execucao", 65, 50000.0, 150000.0, "2024-06-30", "1"),
        ProjectEntity("2", "App de Segurança", "Aplicativo mobile para inspeções de segurança", "planejamento", 20, 80000.0, 200000.0, "2024-09-30", "2"),
        ProjectEntity("3", "Portal do Colaborador", "Novo portal interno para gestão de RH", "concluido", 100, 120000.0, 300000.0, "2024-03-15", null),
        ProjectEntity("4", "Sistema de Feedback", "Plataforma para coleta de feedback de clientes", "pausado", 40, 35000.0, 80000.0, "2024-08-30", null)
    )
}

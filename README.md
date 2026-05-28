# InnovaNexus

Aplicativo mobile nativo desenvolvido em Kotlin para gestão de inovação corporativa, permitindo o acompanhamento de ideias, projetos estratégicos e indicadores executivos.

O projeto foi desenvolvido como solução para o Challenge FIAP – Grupo Águia Branca.

---

# Sobre o Projeto

O InnovaNexus foi criado para centralizar processos de inovação dentro da empresa, conectando operadores, gestores e liderança em uma única plataforma.

A aplicação permite:

* Cadastro e acompanhamento de ideias
* Gestão estratégica corporativa
* Controle de projetos e iniciativas
* Dashboard executivo com indicadores
* Acompanhamento de ROI e produtividade
* Integração com API REST
* Persistência de dados local e em nuvem

---

# Tecnologias Utilizadas

## Mobile

* Kotlin
* XML Layouts
* Android Native
* Material Design 3
* MVVM Architecture
* Navigation Component
* ViewBinding
* Coroutines

## Banco de Dados

* Room Database
* Firebase

## Comunicação

* Retrofit
* Gson
* REST API

## Componentes

* RecyclerView
* MPAndroidChart
* Material Components

---

# Arquitetura do Projeto

O projeto segue a arquitetura MVVM (Model-View-ViewModel), organizada em camadas:

```bash
com.innovanexus
│
├── data
│   ├── local
│   ├── remote
│   └── repository
│
├── domain
│   ├── model
│   └── usecase
│
├── presentation
│   ├── login
│   ├── operator
│   ├── manager
│   ├── leadership
│   └── components
│
└── utils
```

---

# Funcionalidades

## Operador

* Visualização de estratégias da empresa
* Cadastro de ideias
* Acompanhamento de status
* Ranking de participação

## Gestor

* Gestão e priorização de ideias
* Aprovação de propostas
* Cadastro de projetos
* Atualização de progresso

## Liderança

* Gestão estratégica
* Dashboard executivo
* Indicadores financeiros
* Acompanhamento de ROI

---

# Integrações

## API REST

A aplicação consome endpoints REST para:

* Autenticação
* Gestão de estratégias
* Cadastro de ideias
* Gestão de projetos
* Dashboard executivo

## Firebase

Utilizado para:

* Persistência em nuvem
* Sincronização de dados
* Estrutura escalável

---

# Estrutura de Navegação

```text
Splash Screen
   ↓
Login
   ↓
Home por perfil
   ├── Operador
   ├── Gestor
   └── Liderança
```

---

# Design e Interface

A interface foi desenvolvida com foco em:

* Experiência mobile nativa
* Fidelidade visual
* Responsividade
* Componentização
* Material Design 3
* Navegação intuitiva

---

# Como Executar o Projeto

## Requisitos

* Android Studio
* JDK 17
* Gradle
* Emulador Android ou dispositivo físico

## Passos

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/innovanexus.git

# Abra no Android Studio

# Sincronize o Gradle

# Execute o projeto
```

---

# Estrutura de Dados

## Entidades Principais

* UserEntity
* IdeaEntity
* ProjectEntity
* StrategyEntity

---

# Diferenciais do Projeto

* Arquitetura escalável
* Separação de responsabilidades
* Integração com Firebase
* Consumo de API REST
* Interface moderna
* Dashboard executivo
* Navegação segmentada por perfil
* Persistência local com Room

---

# Objetivo Acadêmico

Projeto desenvolvido para o Challenge FIAP em parceria com o Grupo Águia Branca.

O objetivo da solução é incentivar inovação corporativa através de uma plataforma centralizada de gestão de ideias e projetos.

---

# Equipe

Desenvolvido pelos integrantes da equipe do projeto InnovaNexus.

---

# Licença

Projeto acadêmico desenvolvido exclusivamente para fins educacionais.

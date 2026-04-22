# Requirements Document

## Introduction

O PlaySync atualmente controla toda a navegação via estado React (`selectedGame`, `hasSearched`) dentro do `App.jsx`. Isso impede que o usuário compartilhe URLs, use o botão Voltar do navegador ou acesse diretamente uma página de jogo ou resultado de busca. Esta feature substitui essa lógica condicional por React Router com rotas reais, mantendo toda a lógica de dados existente intacta.

Rotas alvo:
- `/` — Home (featured + trending)
- `/search?q=zelda` — Resultados de busca
- `/game/:id` — Detalhe do jogo

## Glossary

- **Router**: O componente `BrowserRouter` do `react-router-dom` que provê contexto de navegação para a aplicação.
- **App**: O componente raiz `App.jsx` que orquestra estado global e renderiza as rotas.
- **Routes**: O componente `<Routes>` do React Router que seleciona qual rota renderizar com base na URL atual.
- **Route**: Um par `<Route path="..." element={...} />` que mapeia um padrão de URL a um componente de página.
- **HomePage**: Página em `/` que exibe o hero, featured game e trending games.
- **SearchPage**: Página em `/search` que exibe a barra de busca e os resultados filtrados pelo query param `q`.
- **GamePage**: Página em `/game/:id` que exibe o detalhe completo de um jogo identificado pelo parâmetro `:id`.
- **SearchBar**: Componente de input de busca presente em todas as páginas.
- **Navbar**: Componente de cabeçalho com link para a home.
- **URL_State**: Estado derivado da URL (pathname + query params) em vez de variáveis React.

---

## Requirements

### Requirement 1: Estrutura de Rotas no App

**User Story:** Como desenvolvedor, quero que o `App.jsx` use `<Routes>` para decidir qual página renderizar, para que a lógica de navegação seja baseada em URL e não em estado.

#### Acceptance Criteria

1. THE App SHALL renderizar um componente `<Routes>` contendo exatamente três `<Route>` filhos: `/`, `/search` e `/game/:id`.
2. WHEN a URL atual não corresponder a nenhuma rota definida, THE App SHALL redirecionar o usuário para `/`.
3. THE App SHALL manter o `<Navbar>` e o `<Footer>` fora do `<Routes>`, renderizando-os em todas as páginas.
4. THE App SHALL remover as variáveis de estado `hasSearched` e `selectedGame` que controlavam a navegação condicional anterior.

---

### Requirement 2: Rota Home (`/`)

**User Story:** Como usuário, quero acessar a home pelo endereço `/`, para que eu possa ver os jogos em destaque e em alta diretamente pela URL.

#### Acceptance Criteria

1. WHEN o usuário navegar para `/`, THE Router SHALL renderizar o componente `HomePage`.
2. THE HomePage SHALL exibir o hero heading completo (título "PlaySync" em tamanho grande), a `SearchBar`, o `FeaturedGame` e o `TrendingSection`.
3. WHEN o usuário submeter a `SearchBar` na `HomePage`, THE Router SHALL navegar para `/search?q={termo}` sem recarregar a página.
4. WHEN o usuário clicar em um jogo na `HomePage`, THE Router SHALL navegar para `/game/{id}` sem recarregar a página.

---

### Requirement 3: Rota de Busca (`/search?q=`)

**User Story:** Como usuário, quero que a busca seja acessível via URL `/search?q=zelda`, para que eu possa compartilhar ou favoritar resultados de busca.

#### Acceptance Criteria

1. WHEN o usuário navegar para `/search?q={termo}`, THE Router SHALL renderizar o componente `SearchPage`.
2. WHEN a `SearchPage` for montada com um query param `q` não vazio, THE SearchPage SHALL disparar automaticamente a busca pelo termo contido em `q`.
3. WHEN o query param `q` estiver ausente ou vazio em `/search`, THE SearchPage SHALL exibir a `SearchBar` sem resultados e sem disparar busca.
4. WHEN o usuário submeter a `SearchBar` na `SearchPage`, THE Router SHALL atualizar o query param `q` na URL para o novo termo e disparar nova busca.
5. WHEN o usuário clicar em um jogo nos resultados da `SearchPage`, THE Router SHALL navegar para `/game/{id}`.
6. IF a busca retornar erro, THEN THE SearchPage SHALL exibir uma mensagem de erro sem navegar para outra rota.

---

### Requirement 4: Rota de Detalhe do Jogo (`/game/:id`)

**User Story:** Como usuário, quero acessar o detalhe de um jogo pela URL `/game/570`, para que eu possa compartilhar ou abrir diretamente a página de um jogo.

#### Acceptance Criteria

1. WHEN o usuário navegar para `/game/{id}`, THE Router SHALL renderizar o componente `GamePage`.
2. WHEN a `GamePage` for montada, THE GamePage SHALL usar o parâmetro `:id` da URL para buscar e exibir os dados do jogo correspondente.
3. IF o jogo com o `:id` fornecido não for encontrado ou a busca falhar, THEN THE GamePage SHALL exibir uma mensagem "Jogo não encontrado" com um botão de retorno.
4. WHEN o usuário clicar no botão de voltar na `GamePage`, THE Router SHALL navegar para a página anterior no histórico do navegador.
5. WHEN o usuário submeter a `SearchBar` na `GamePage`, THE Router SHALL navegar para `/search?q={termo}`.

---

### Requirement 5: Navegação pela Navbar

**User Story:** Como usuário, quero clicar no logo "PlaySync" na Navbar para voltar à home, para que eu possa reiniciar minha navegação a qualquer momento.

#### Acceptance Criteria

1. WHEN o usuário clicar no logo ou nome "PlaySync" na `Navbar`, THE Router SHALL navegar para `/` sem recarregar a página.
2. THE Navbar SHALL usar um componente `<Link>` ou `<NavLink>` do React Router em vez de um elemento `<a>` com `href` absoluto.

---

### Requirement 6: Compatibilidade com Navegação do Browser

**User Story:** Como usuário, quero usar os botões Voltar e Avançar do navegador, para que a navegação no PlaySync se comporte como qualquer outro site.

#### Acceptance Criteria

1. WHEN o usuário pressionar o botão Voltar do navegador, THE Router SHALL navegar para a URL anterior no histórico sem recarregar a página.
2. WHEN o usuário pressionar o botão Avançar do navegador, THE Router SHALL navegar para a URL seguinte no histórico sem recarregar a página.
3. THE Router SHALL preservar o histórico de navegação ao transitar entre `/`, `/search` e `/game/:id`.

---

### Requirement 7: Configuração do Servidor de Desenvolvimento (SPA Fallback)

**User Story:** Como desenvolvedor, quero que o Vite sirva o `index.html` para qualquer rota, para que o React Router funcione corretamente ao acessar URLs diretamente no browser.

#### Acceptance Criteria

1. THE Vite_Config SHALL estar configurado para servir `index.html` como fallback para todas as rotas não encontradas em desenvolvimento (historyApiFallback).
2. WHERE a aplicação for implantada em produção, THE vercel_json SHALL conter uma regra de rewrite que redireciona todas as requisições para `/index.html`.

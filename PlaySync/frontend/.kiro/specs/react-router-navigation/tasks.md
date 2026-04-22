# Tasks — React Router Navigation

## Task List

- [x] 1. Instalar react-router-dom e configurar dependências
  - [x] 1.1 Adicionar `react-router-dom` ao `package.json` e instalar via npm
  - [x] 1.2 Adicionar `vitest`, `@testing-library/react`, `@testing-library/user-event`, `jsdom` e `fast-check` como devDependencies
  - [x] 1.3 Configurar `vitest` no `vite.config.js` com `environment: 'jsdom'` e `globals: true`

- [x] 2. Configurar SPA fallback no Vite e verificar Vercel
  - [x] 2.1 Adicionar `server: { historyApiFallback: true }` ao `vite.config.js`
  - [x] 2.2 Verificar que `vercel.json` já contém a regra `"/(.*)" → "/"` (já existe — apenas confirmar)

- [x] 3. Refatorar Navbar para usar `<Link>` do React Router
  - [x] 3.1 Substituir o `<button onClick={onReset}>` por `<Link to="/">` em `Navbar.jsx`
  - [x] 3.2 Remover a prop `onReset` da interface do componente

- [x] 4. Refatorar App.jsx para usar `<Routes>` e `<Route>`
  - [x] 4.1 Importar `Routes`, `Route`, `Navigate`, `useNavigate` de `react-router-dom`
  - [x] 4.2 Remover as variáveis de estado `hasSearched` e `selectedGame` do App
  - [x] 4.3 Adicionar estado `selectedGame` gerenciado por rota (via `useRef` ou estado local em `GamePage`)
  - [x] 4.4 Criar a estrutura `<Routes>` com as três rotas: `/`, `/search`, `/game/:id`
  - [x] 4.5 Adicionar `<Route path="*" element={<Navigate to="/" replace />} />` para rotas desconhecidas
  - [x] 4.6 Mover `<Navbar>` e `<Footer>` para fora do `<Routes>`, mantendo-os em todas as páginas
  - [x] 4.7 Remover a chamada `onReset` da prop do `<Navbar>`

- [x] 5. Refatorar HomePage para navegação via Router
  - [x] 5.1 Usar `useNavigate()` para navegar para `/game/{id}` ao clicar em um jogo
  - [x] 5.2 Usar `useNavigate()` para navegar para `/search?q={termo}` ao submeter a SearchBar
  - [x] 5.3 Manter estado local de `searchTerm` dentro da própria `HomePage`

- [x] 6. Refatorar SearchPage para ler estado da URL
  - [x] 6.1 Usar `useSearchParams()` para ler o query param `q`
  - [x] 6.2 Adicionar `useEffect([q])` que dispara `onSearch(q)` quando `q` é não vazio
  - [x] 6.3 Usar `setSearchParams({ q: novoTermo })` ao submeter nova busca na SearchBar
  - [x] 6.4 Garantir que quando `q` está ausente ou vazio, nenhuma busca é disparada
  - [x] 6.5 Usar `useNavigate()` para navegar para `/game/{id}` ao clicar em resultado

- [x] 7. Refatorar GamePage para ler :id da URL e carregar jogo
  - [x] 7.1 Usar `useParams()` para ler o parâmetro `:id`
  - [x] 7.2 Adicionar `useEffect([id])` que chama `onLoadGame(id)` ao montar
  - [x] 7.3 Exibir "Jogo não encontrado" com botão de voltar quando o jogo não é encontrado após carregamento
  - [x] 7.4 Usar `navigate(-1)` no botão de voltar
  - [x] 7.5 Usar `useNavigate()` para navegar para `/search?q={termo}` ao submeter a SearchBar

- [x] 8. Implementar `onLoadGame` no App.jsx
  - [x] 8.1 Criar a função `handleLoadGame(id)` no App que busca o jogo pelo id (Steam/RAWG) e atualiza o estado
  - [x] 8.2 Passar `handleLoadGame` como prop `onLoadGame` para `GamePage`
  - [x] 8.3 Garantir que o estado do jogo carregado é limpo ao navegar para outra rota

- [x] 9. Escrever testes unitários (exemplos e casos de borda)
  - [x] 9.1 Teste: App renderiza exatamente 3 Routes (req 1.1)
  - [x] 9.2 Teste: navegar para `/` renderiza HomePage (req 2.1)
  - [x] 9.3 Teste: HomePage exibe hero, SearchBar, FeaturedGame, TrendingSection (req 2.2)
  - [x] 9.4 Teste: navegar para `/search?q=test` renderiza SearchPage (req 3.1)
  - [x] 9.5 Teste: `/search` sem `q` não dispara busca (req 3.3)
  - [x] 9.6 Teste: busca com erro mantém URL em `/search` e exibe mensagem (req 3.6)
  - [x] 9.7 Teste: navegar para `/game/570` renderiza GamePage (req 4.1)
  - [x] 9.8 Teste: jogo não encontrado exibe mensagem e botão de voltar (req 4.3)
  - [x] 9.9 Teste: botão de voltar na GamePage chama navigate(-1) (req 4.4)
  - [x] 9.10 Teste: clicar no logo da Navbar navega para `/` (req 5.1)
  - [x] 9.11 Teste: Navbar usa `<Link>` do React Router (req 5.2)
  - [x] 9.12 Teste: vite.config.js contém historyApiFallback (req 7.1)
  - [x] 9.13 Teste: vercel.json contém rewrite `/(.*) → /` (req 7.2)

- [x] 10. Escrever testes de propriedade com fast-check
  - [x] 10.1 Property 1: rotas desconhecidas redirecionam para `/` (req 1.2)
  - [x] 10.2 Property 2: Navbar e Footer presentes em todas as rotas válidas (req 1.3)
  - [x] 10.3 Property 3: busca em qualquer página navega para `/search?q=` (req 2.3, 4.5)
  - [x] 10.4 Property 4: clique em jogo navega para `/game/{id}` (req 2.4, 3.5)
  - [x] 10.5 Property 5: SearchPage dispara busca automaticamente para qualquer q não vazio (req 3.2)
  - [x] 10.6 Property 6: submissão na SearchPage atualiza o query param q (req 3.4)
  - [x] 10.7 Property 7: GamePage usa o :id da URL para carregar o jogo (req 4.2)
  - [x] 10.8 Property 8: navegação entre rotas preserva o histórico (req 6.3)

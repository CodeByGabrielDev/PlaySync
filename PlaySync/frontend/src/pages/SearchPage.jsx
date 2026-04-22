import { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import SearchBar from '../components/Search/SearchBar';
import SearchResults from '../components/Search/SearchResults';

function SearchPage({ games, isLoading, error, onSearch, onGameClick }) {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();
  const q = searchParams.get('q') || '';
  const [searchTerm, setSearchTerm] = useState(q);

  useEffect(() => {
    if (q) onSearch(q);
  }, [q]);

  const handleSearch = (e) => {
    e.preventDefault();
    if (!searchTerm.trim()) return;
    setSearchParams({ q: searchTerm.trim() });
  };

  const handleGameClick = (game) => {
    onGameClick(game);
    navigate(`/game/${game.id}`);
  };

  return (
    <>
      <div className="text-center mb-6 animate-fade-in">
        <h1 className="text-3xl md:text-5xl font-black font-display tracking-tight leading-none">
          <span
            style={{
              background: 'linear-gradient(135deg, #60a5fa 0%, #3b82f6 55%, #06b6d4 100%)',
              WebkitBackgroundClip: 'text',
              WebkitTextFillColor: 'transparent',
              backgroundClip: 'text',
            }}
          >
            Play
          </span>
          <span className="text-zinc-50">Sync</span>
        </h1>
      </div>

      <div className="flex justify-center mb-8 mt-2">
        <SearchBar
          value={searchTerm}
          onChange={setSearchTerm}
          onSubmit={handleSearch}
          isLoading={isLoading}
        />
      </div>

      {error && (
        <div className="max-w-2xl mx-auto mb-6 p-4 bg-red-950/30 border border-red-500/30 rounded-xl text-red-400 text-sm text-center animate-fade-in">
          {error}
        </div>
      )}

      <div className="mb-10">
        <SearchResults
          results={games}
          searchTerm={q}
          onGameClick={handleGameClick}
        />
      </div>
    </>
  );
}

export default SearchPage;

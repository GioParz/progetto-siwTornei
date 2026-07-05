-- 1. INSERIMENTO DEI TORNEI (ID, Anno, Nome)
INSERT INTO torneo (id, anno, nome) VALUES (1, 2026, 'Champions League');
INSERT INTO torneo (id, anno, nome) VALUES (2, 2026, 'Mondiale Universitario');

-- 2. INSERIMENTO DELLE SQUADRE (ID, Nome)
INSERT INTO squadra (id, nome) VALUES (1, 'Raptors FC');
INSERT INTO squadra (id, nome) VALUES (2, 'Titans Squad');
INSERT INTO squadra (id, nome) VALUES (3, 'Spartans FC');

-- 3. INSERIMENTO DEGLI ARBITRI (ID, Codice AIA, Cognome, Nome)
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (1, 'AIA-111', 'Collina', 'Pierluigi');
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (2, 'AIA-222', 'Rizzoli', 'Nicola');

-- 4. INSERIMENTO DEI GIOCATORI (ID, Cognome, Nome, Ruolo, Squadra ID)
-- L'ultima colonna rappresenta la Foreign Key che lega il giocatore alla sua squadra
INSERT INTO giocatore (id, cognome, nome, ruolo, squadra_id) VALUES (1, 'Rossi', 'Mario', 'PORTIERE', 1);
INSERT INTO giocatore (id, cognome, nome, ruolo, squadra_id) VALUES (2, 'Verdi', 'Luigi', 'ATTACCANTE', 1);
INSERT INTO giocatore (id, cognome, nome, ruolo, squadra_id) VALUES (3, 'Bianchi', 'Alessandro', 'DIFENSORE', 2);

-- 5. INSERIMENTO DELLE PARTITE (ID, Gol Casa, Gol Ospite, Arbitro ID, Squadra Casa ID, Squadra Ospite ID, Torneo ID)
-- Partita 1: Raptors vs Titans (Finità 2-1) nel torneo Champions League (ID 1), arbitrata da Collina (ID 1)
INSERT INTO partita (id, goals_home, goals_away, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id) VALUES (1, 2, 1, 1, 1, 2, 1);
-- Partita 2: Titans vs Spartans (Finità 0-0) nel torneo Champions League (ID 1), arbitrata da Rizzoli (ID 2)
INSERT INTO partita (id, goals_home, goals_away, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id) VALUES (2, 0, 0, 2, 2, 3, 1);
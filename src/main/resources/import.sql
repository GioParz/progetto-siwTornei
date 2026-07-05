-- 1. PULIZIA DI SICUREZZA
TRUNCATE TABLE partita CASCADE;
TRUNCATE TABLE giocatore CASCADE;
TRUNCATE TABLE squadra CASCADE;
TRUNCATE TABLE arbitro CASCADE;
TRUNCATE TABLE torneo CASCADE;

-- 2. INSERIMENTO TORNEI
INSERT INTO torneo (id, anno, nome) VALUES (nextval('torneo_seq'), 2026, 'Champions League');
INSERT INTO torneo (id, anno, nome) VALUES (nextval('torneo_seq'), 2026, 'Mondiale Universitario');

-- 3. INSERIMENTO SQUADRE
INSERT INTO squadra (id, nome) VALUES (nextval('squadra_seq'), 'Raptors FC');
INSERT INTO squadra (id, nome) VALUES (nextval('squadra_seq'), 'Titans Squad');
INSERT INTO squadra (id, nome) VALUES (nextval('squadra_seq'), 'Spartans FC');

-- 4. INSERIMENTO ARBITRI
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (nextval('arbitro_seq'), 'AIA-111', 'Collina', 'Pierluigi');
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (nextval('arbitro_seq'), 'AIA-222', 'Rizzoli', 'Nicola');

-- 5. INSERIMENTO GIOCATORI (Chiave esterna recuperata dinamicamente tramite il nome del club)
INSERT INTO giocatore (id, cognome, nome, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Rossi', 'Mario', 'PORTIERE', (SELECT id FROM squadra WHERE nome = 'Raptors FC'));

INSERT INTO giocatore (id, cognome, nome, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Verdi', 'Luigi', 'ATTACCANTE', (SELECT id FROM squadra WHERE nome = 'Raptors FC'));

INSERT INTO giocatore (id, cognome, nome, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Bianchi', 'Alessandro', 'DIFENSORE', (SELECT id FROM squadra WHERE nome = 'Titans Squad'));

-- 6. INSERIMENTO PARTITE (Tutte le relazioni recuperate dinamicamente basandosi sui nomi di squadre, arbitri e tornei)
INSERT INTO partita (id, goals_home, goals_away, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id) VALUES (nextval('partita_seq'), 2, 1, (SELECT id FROM arbitro WHERE cognome = 'Collina'), (SELECT id FROM squadra WHERE nome = 'Raptors FC'), (SELECT id FROM squadra WHERE nome = 'Titans Squad'), (SELECT id FROM torneo WHERE nome = 'Champions League'));

INSERT INTO partita (id, goals_home, goals_away, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id) VALUES (nextval('partita_seq'), 0, 0, (SELECT id FROM arbitro WHERE cognome = 'Rizzoli'), (SELECT id FROM squadra WHERE nome = 'Titans Squad'), (SELECT id FROM squadra WHERE nome = 'Spartans FC'), (SELECT id FROM torneo WHERE nome = 'Champions League'));
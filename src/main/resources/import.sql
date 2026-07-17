-- ====================================================================
-- 1. PULIZIA DI SICUREZZA CON CASCADE
-- ====================================================================
TRUNCATE TABLE partita CASCADE;
TRUNCATE TABLE giocatore CASCADE;
TRUNCATE TABLE squadra CASCADE;
TRUNCATE TABLE arbitro CASCADE;
TRUNCATE TABLE torneo CASCADE;
TRUNCATE TABLE credentials CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE torneo_squadre CASCADE;


-- ====================================================================
-- 2. UTENTI E CREDENZIALI
-- ====================================================================

-- Utente Admin (username: admin | password: admin123)
INSERT INTO users (id, nome, cognome, email) VALUES (nextval('users_seq'), 'Direttore', 'Gara', 'direttore.gara@tornei.it');
INSERT INTO credentials (id, username, password, ruolo, utente_id) VALUES (nextval('credentials_seq'), 'admin', '$2a$10$1pWD29vOPWS1oSjaSR41wO9kYEdt0.jSczqUQoFRRdnDMKHT0iQtm', 'ADMIN', (SELECT id FROM users WHERE cognome = 'Gara'));

-- Utente Standard (username: user | password: user123)
INSERT INTO users (id, nome, cognome, email) VALUES (nextval('users_seq'), 'Mario', 'Rossi', 'mario.rossi@gmail.com');
INSERT INTO credentials (id, username, password, ruolo, utente_id) VALUES (nextval('credentials_seq'), 'user', '$2a$10$xq1wMvOsPJekW7FF..Xc4.cynxv59yB9gPtrwPeY/.RC5meVVxqRC', 'USER', (SELECT id FROM users WHERE cognome = 'Rossi'));


-- ====================================================================
-- 3. INSERIMENTO TORNEI
-- ====================================================================
INSERT INTO torneo (id, anno, nome, descrizione) VALUES (nextval('torneo_seq'), 2026, 'Champions League', 'Il massimo torneo continentale per club.');
INSERT INTO torneo (id, anno, nome, descrizione) VALUES (nextval('torneo_seq'), 2026, 'Mondiale Universitario', 'La competizione dedicata agli atleti degli atenei.');


-- ====================================================================
-- 4. INSERIMENTO SQUADRE
-- ====================================================================
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Real Madrid', 1902, 'Madrid');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Milan AC', 1899, 'Milano');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Manchester City', 1880, 'Manchester');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Bayern Monaco', 1900, 'Monaco di Baviera');


-- ====================================================================
-- 5. ASSOCIAZIONE SQUADRE-TORNEI (Tutte iscritte alla Champions League 2026)
-- ====================================================================
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Real Madrid'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Milan AC'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Manchester City'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Bayern Monaco'));


-- ====================================================================
-- 6. INSERIMENTO ARBITRI
-- ====================================================================
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (nextval('arbitro_seq'), 'AIA-111', 'Collina', 'Pierluigi');
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (nextval('arbitro_seq'), 'AIA-222', 'Orsato', 'Daniele');
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (nextval('arbitro_seq'), 'AIA-333', 'Marciniak', 'Szymon');


-- ====================================================================
-- 7. INSERIMENTO GIOCATORI
-- ====================================================================
-- Real Madrid
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Courtois', 'Thibaut', '1992-05-11', 'PORTIERE', 200, (SELECT id FROM squadra WHERE nome = 'Real Madrid'));
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Mbappe', 'Kylian', '1998-12-20', 'ATTACCANTE', 178, (SELECT id FROM squadra WHERE nome = 'Real Madrid'));

-- Milan AC
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Maignan', 'Mike', '1995-07-03', 'PORTIERE', 191, (SELECT id FROM squadra WHERE nome = 'Milan AC'));
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Leao', 'Rafael', '1999-06-10', 'ATTACCANTE', 188, (SELECT id FROM squadra WHERE nome = 'Milan AC'));

-- Manchester City
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Ederson', 'Santana', '1993-08-17', 'PORTIERE', 188, (SELECT id FROM squadra WHERE nome = 'Manchester City'));
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Haaland', 'Erling', '2000-07-21', 'ATTACCANTE', 194, (SELECT id FROM squadra WHERE nome = 'Manchester City'));

-- Bayern Monaco
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Neuer', 'Manuel', '1986-03-27', 'PORTIERE', 193, (SELECT id FROM squadra WHERE nome = 'Bayern Monaco'));
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Kane', 'Harry', '1993-07-28', 'ATTACCANTE', 188, (SELECT id FROM squadra WHERE nome = 'Bayern Monaco'));


-- ====================================================================
-- 8. INSERIMENTO PARTITE
-- ====================================================================

-- Partita 1: TERMINATA (Real Madrid vs Milan AC) - Giocata il 10 Luglio 2026 alle 21:00
-- Arbitro: Collina
INSERT INTO partita (id, stato, goals_home, goals_away, luogo, dataeora, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id, squadra_casa_nome_storico, squadra_ospite_nome_storico) VALUES (nextval('partita_seq'), 'TERMINATA', 3, 1, 'Santiago Bernabeu', '2026-07-10 21:00:00', (SELECT id FROM arbitro WHERE cognome = 'Collina'), (SELECT id FROM squadra WHERE nome = 'Real Madrid'), (SELECT id FROM squadra WHERE nome = 'Milan AC'), (SELECT id FROM torneo WHERE nome = 'Champions League'), 'Real Madrid', 'Milan AC');

-- Partita 2: TERMINATA (Manchester City vs Bayern Monaco) - Giocata lo stesso giorno (10 Luglio 2026 alle 21:00) 
-- Arbitro: Orsato (Nessun conflitto perché l'arbitro della partita 1 è Collina e le squadre sono diverse!)
INSERT INTO partita (id, stato, goals_home, goals_away, luogo, dataeora, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id, squadra_casa_nome_storico, squadra_ospite_nome_storico) VALUES (nextval('partita_seq'), 'TERMINATA', 2, 2, 'Etihad Stadium', '2026-07-10 21:00:00', (SELECT id FROM arbitro WHERE cognome = 'Orsato'), (SELECT id FROM squadra WHERE nome = 'Manchester City'), (SELECT id FROM squadra WHERE nome = 'Bayern Monaco'), (SELECT id FROM torneo WHERE nome = 'Champions League'), 'Manchester City', 'Bayern Monaco');

-- Partita 3: PROGRAMMATA (Milan AC vs Manchester City) - Prevista per il 18 Luglio 2026 alle 21:00
-- Arbitro: Marciniak (0-0 rigoroso, coerente con lo stato PROGRAMMATA)
INSERT INTO partita (id, stato, goals_home, goals_away, luogo, dataeora, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id, squadra_casa_nome_storico, squadra_ospite_nome_storico) VALUES (nextval('partita_seq'), 'PROGRAMMATA', 0, 0, 'San Siro', '2026-07-18 21:00:00', (SELECT id FROM arbitro WHERE cognome = 'Marciniak'), (SELECT id FROM squadra WHERE nome = 'Milan AC'), (SELECT id FROM squadra WHERE nome = 'Manchester City'), (SELECT id FROM torneo WHERE nome = 'Champions League'), 'Milan AC', 'Manchester City');

-- Partita 4: PROGRAMMATA (Bayern Monaco vs Real Madrid) - Prevista per il 25 Luglio 2026 alle 21:00
-- Arbitro: Collina (0-0 rigoroso, nessun conflitto temporale)
INSERT INTO partita (id, stato, goals_home, goals_away, luogo, dataeora, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id, squadra_casa_nome_storico, squadra_ospite_nome_storico) VALUES (nextval('partita_seq'), 'PROGRAMMATA', 0, 0, 'Allianz Arena', '2026-07-25 21:00:00', (SELECT id FROM arbitro WHERE cognome = 'Collina'), (SELECT id FROM squadra WHERE nome = 'Bayern Monaco'), (SELECT id FROM squadra WHERE nome = 'Real Madrid'), (SELECT id FROM torneo WHERE nome = 'Champions League'), 'Bayern Monaco', 'Real Madrid');
-- ====================================================================
-- 1. PULIZIA DI SICUREZZA CON CASCADE (Mantiene il tuo ordine)
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
-- 2. UTENTI E CREDENZIALI (Per testare Spring Security e i link protetti)
-- ====================================================================

-- Utente Admin (username: admin | password: admin123)
INSERT INTO users (id, nome, cognome) VALUES (nextval('users_seq'), 'Direttore', 'Gara');
INSERT INTO credentials (id, username, password, ruolo, utente_id) VALUES (nextval('credentials_seq'), 'admin', '$2a$10$1pWD29vOPWS1oSjaSR41wO9kYEdt0.jSczqUQoFRRdnDMKHT0iQtm', 'ADMIN', (SELECT id FROM users WHERE cognome = 'Gara'));

-- Utente Standard (username: user | password: user123)
INSERT INTO users (id, nome, cognome) VALUES (nextval('users_seq'), 'Mario', 'Rossi');
INSERT INTO credentials (id, username, password, ruolo, utente_id) VALUES (nextval('credentials_seq'), 'user', '$2a$10$xq1wMvOsPJekW7FF..Xc4.cynxv59yB9gPtrwPeY/.RC5meVVxqRC', 'USER', (SELECT id FROM users WHERE cognome = 'Rossi'));


-- ====================================================================
-- 3. INSERIMENTO TORNEI
-- ====================================================================
INSERT INTO torneo (id, anno, nome, descrizione) VALUES (nextval('torneo_seq'), 2026, 'Champions League', 'Il massimo torneo continentale per club.');
INSERT INTO torneo (id, anno, nome, descrizione) VALUES (nextval('torneo_seq'), 2026, 'Mondiale Universitario', 'La competizione dedicata agli atleti degli atenei.');


-- ====================================================================
-- 4. INSERIMENTO SQUADRE (Aggiunti i dettagli mancanti)
-- ====================================================================
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Raptors FC', 2018, 'Roma');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Titans Squad', 2020, 'Milano');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Spartans FC', 2015, 'Napoli');


-- ====================================================================
-- 5. ASSOCIAZIONE SQUADRE-TORNEI (Tabella ponte ManyToMany)
-- ====================================================================
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Raptors FC'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Titans Squad'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Spartans FC'));


-- ====================================================================
-- 6. INSERIMENTO ARBITRI
-- ====================================================================
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (nextval('arbitro_seq'), 'AIA-111', 'Collina', 'Pierluigi');
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (nextval('arbitro_seq'), 'AIA-222', 'Rizzoli', 'Nicola');


-- ====================================================================
-- 7. INSERIMENTO GIOCATORI
-- ====================================================================
INSERT INTO giocatore (id, cognome, nome, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Rossi', 'Mario', 'PORTIERE', (SELECT id FROM squadra WHERE nome = 'Raptors FC'));
INSERT INTO giocatore (id, cognome, nome, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Verdi', 'Luigi', 'ATTACCANTE', (SELECT id FROM squadra WHERE nome = 'Raptors FC'));
INSERT INTO giocatore (id, cognome, nome, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Bianchi', 'Alessandro', 'DIFENSORE', (SELECT id FROM squadra WHERE nome = 'Titans Squad'));


-- ====================================================================
-- 8. INSERIMENTO PARTITE 
-- ====================================================================
-- Gara 1: TERMINATA (Aggiorna la classifica visibile nella show del torneo)
INSERT INTO partita (id, stato, goals_home, goals_away, luogo, dataeora, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id, squadra_casa_nome_storico, squadra_ospite_nome_storico) VALUES (nextval('partita_seq'), 'TERMINATA', 2, 1, 'Stadium Ovale', '2026-07-10 21:00:00', (SELECT id FROM arbitro WHERE cognome = 'Collina'), (SELECT id FROM squadra WHERE nome = 'Raptors FC'), (SELECT id FROM squadra WHERE nome = 'Titans Squad'), (SELECT id FROM torneo WHERE nome = 'Champions League'), 'Raptors FC', 'Titans Squad');

-- Gara 2: PROGRAMMATA (Per testare il badge "Da Giocare" e l'azione "Inserisci Score" visibile solo all'Admin)
INSERT INTO partita (id, stato, goals_home, goals_away, luogo, dataeora, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id, squadra_casa_nome_storico, squadra_ospite_nome_storico) VALUES (nextval('partita_seq'), 'PROGRAMMATA', 0, 0, 'Arena Centrale', '2026-07-15 21:00:00', (SELECT id FROM arbitro WHERE cognome = 'Rizzoli'), (SELECT id FROM squadra WHERE nome = 'Titans Squad'), (SELECT id FROM squadra WHERE nome = 'Spartans FC'), (SELECT id FROM torneo WHERE nome = 'Champions League'), 'Titans Squad', 'Spartans FC');
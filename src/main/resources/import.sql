-- ====================================================================
-- 1. PULIZIA DI SICUREZZA CON CASCADE (rende lo script ri-eseguibile)
-- ====================================================================
TRUNCATE TABLE commento CASCADE;
TRUNCATE TABLE partita CASCADE;
TRUNCATE TABLE giocatore CASCADE;
TRUNCATE TABLE squadra CASCADE;
TRUNCATE TABLE arbitro CASCADE;
TRUNCATE TABLE torneo CASCADE;
TRUNCATE TABLE credentials CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE torneo_squadre CASCADE;

-- Riporto le sequence a 1 per avere id puliti e prevedibili ad ogni riavvio
ALTER SEQUENCE users_seq RESTART WITH 1;
ALTER SEQUENCE credentials_seq RESTART WITH 1;
ALTER SEQUENCE torneo_seq RESTART WITH 1;
ALTER SEQUENCE squadra_seq RESTART WITH 1;
ALTER SEQUENCE arbitro_seq RESTART WITH 1;
ALTER SEQUENCE giocatore_seq RESTART WITH 1;
ALTER SEQUENCE partita_seq RESTART WITH 1;
ALTER SEQUENCE commento_seq RESTART WITH 1;


-- ====================================================================
-- 2. UTENTI E CREDENZIALI
-- ====================================================================

-- Utente Admin (username: admin | password: admin123)
INSERT INTO users (id, nome, cognome, email) VALUES (nextval('users_seq'), 'Direttore', 'Gara', 'direttore.gara@tornei.it');
INSERT INTO credentials (id, username, password, ruolo, utente_id) VALUES (nextval('credentials_seq'), 'admin', '$2a$10$1pWD29vOPWS1oSjaSR41wO9kYEdt0.jSczqUQoFRRdnDMKHT0iQtm', 'ADMIN', (SELECT id FROM users WHERE cognome = 'Gara'));

-- Utente Standard (username: user | password: user123)
INSERT INTO users (id, nome, cognome, email) VALUES (nextval('users_seq'), 'Mario', 'Rossi', 'mario.rossi@gmail.com');
INSERT INTO credentials (id, username, password, ruolo, utente_id) VALUES (nextval('credentials_seq'), 'user', '$2a$10$xq1wMvOsPJekW7FF..Xc4.cynxv59yB9gPtrwPeY/.RC5meVVxqRC', 'USER', (SELECT id FROM users WHERE cognome = 'Rossi'));

-- (username: giulia | password: user123)
INSERT INTO users (id, nome, cognome, email) VALUES (nextval('users_seq'), 'Giulia', 'Neri', 'giulia.neri@gmail.com');
INSERT INTO credentials (id, username, password, ruolo, utente_id) VALUES (nextval('credentials_seq'), 'giulia', '$2a$10$xq1wMvOsPJekW7FF..Xc4.cynxv59yB9gPtrwPeY/.RC5meVVxqRC', 'USER', (SELECT id FROM users WHERE cognome = 'Neri'));

-- (username: luca | password: user123)
INSERT INTO users (id, nome, cognome, email) VALUES (nextval('users_seq'), 'Luca', 'Ferri', 'luca.ferri@gmail.com');
INSERT INTO credentials (id, username, password, ruolo, utente_id) VALUES (nextval('credentials_seq'), 'luca', '$2a$10$xq1wMvOsPJekW7FF..Xc4.cynxv59yB9gPtrwPeY/.RC5meVVxqRC', 'USER', (SELECT id FROM users WHERE cognome = 'Ferri'));


-- ====================================================================
-- 3. INSERIMENTO TORNEI
-- ====================================================================
INSERT INTO torneo (id, anno, nome, descrizione) VALUES (nextval('torneo_seq'), 2026, 'Champions League', 'Il massimo torneo continentale per club.');
INSERT INTO torneo (id, anno, nome, descrizione) VALUES (nextval('torneo_seq'), 2026, 'Mondiale Universitario', 'La competizione dedicata agli atleti degli atenei.');
INSERT INTO torneo (id, anno, nome, descrizione) VALUES (nextval('torneo_seq'), 2026, 'Europa League', 'Il secondo torneo continentale per club, per chi non è arrivato in Champions.');


-- ====================================================================
-- 4. INSERIMENTO SQUADRE
-- ====================================================================
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Real Madrid', 1902, 'Madrid');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Milan AC', 1899, 'Milano');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Manchester City', 1880, 'Manchester');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Bayern Monaco', 1900, 'Monaco di Baviera');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Liverpool FC', 1892, 'Liverpool');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Paris Saint-Germain', 1970, 'Parigi');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Juventus', 1897, 'Torino');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (nextval('squadra_seq'), 'Barcellona', 1899, 'Barcellona');


-- ====================================================================
-- 5. ASSOCIAZIONE SQUADRE-TORNEI
-- ====================================================================

-- Champions League: le 4 squadre originarie + Liverpool e Juventus
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Real Madrid'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Milan AC'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Manchester City'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Bayern Monaco'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Liverpool FC'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Champions League'), (SELECT id FROM squadra WHERE nome = 'Juventus'));

-- Europa League: le due squadre rimaste fuori dalla Champions + due "ripescate" per completare il quadro
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Europa League'), (SELECT id FROM squadra WHERE nome = 'Paris Saint-Germain'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Europa League'), (SELECT id FROM squadra WHERE nome = 'Barcellona'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Europa League'), (SELECT id FROM squadra WHERE nome = 'Manchester City'));
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES ((SELECT id FROM torneo WHERE nome = 'Europa League'), (SELECT id FROM squadra WHERE nome = 'Bayern Monaco'));

-- Nota: il Mondiale Universitario resta volutamente senza squadre iscritte,
-- così puoi testare in fase di demo anche il caso "torneo senza squadre/partite"


-- ====================================================================
-- 6. INSERIMENTO ARBITRI
-- ====================================================================
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (nextval('arbitro_seq'), 'AIA-111', 'Collina', 'Pierluigi');
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (nextval('arbitro_seq'), 'AIA-222', 'Orsato', 'Daniele');
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (nextval('arbitro_seq'), 'AIA-333', 'Marciniak', 'Szymon');
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (nextval('arbitro_seq'), 'AIA-444', 'Taylor', 'Anthony');
INSERT INTO arbitro (id, codiceaia, cognome, nome) VALUES (nextval('arbitro_seq'), 'AIA-555', 'Turpin', 'Clement');


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

-- Liverpool FC
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Alisson', 'Becker', '1992-10-02', 'PORTIERE', 191, (SELECT id FROM squadra WHERE nome = 'Liverpool FC'));
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Salah', 'Mohamed', '1992-06-15', 'ATTACCANTE', 175, (SELECT id FROM squadra WHERE nome = 'Liverpool FC'));

-- Paris Saint-Germain
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Donnarumma', 'Gianluigi', '1999-02-25', 'PORTIERE', 196, (SELECT id FROM squadra WHERE nome = 'Paris Saint-Germain'));
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Dembele', 'Ousmane', '1997-05-15', 'ATTACCANTE', 178, (SELECT id FROM squadra WHERE nome = 'Paris Saint-Germain'));

-- Juventus
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Di Gregorio', 'Michele', '1997-08-27', 'PORTIERE', 190, (SELECT id FROM squadra WHERE nome = 'Juventus'));
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Vlahovic', 'Dusan', '1999-01-28', 'ATTACCANTE', 190, (SELECT id FROM squadra WHERE nome = 'Juventus'));

-- Barcellona
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Ter Stegen', 'Marc-Andre', '1992-04-30', 'PORTIERE', 187, (SELECT id FROM squadra WHERE nome = 'Barcellona'));
INSERT INTO giocatore (id, cognome, nome, data_nascita, ruolo, altezza, squadra_id) VALUES (nextval('giocatore_seq'), 'Yamal', 'Lamine', '2007-07-13', 'ATTACCANTE', 180, (SELECT id FROM squadra WHERE nome = 'Barcellona'));


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
-- Arbitro: Collina (0-0 rigoroso, nessun conflitto temporale: la partita 1 arbitrata da Collina era il 10 Luglio, qui siamo al 25)
INSERT INTO partita (id, stato, goals_home, goals_away, luogo, dataeora, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id, squadra_casa_nome_storico, squadra_ospite_nome_storico) VALUES (nextval('partita_seq'), 'PROGRAMMATA', 0, 0, 'Allianz Arena', '2026-07-25 21:00:00', (SELECT id FROM arbitro WHERE cognome = 'Collina'), (SELECT id FROM squadra WHERE nome = 'Bayern Monaco'), (SELECT id FROM squadra WHERE nome = 'Real Madrid'), (SELECT id FROM torneo WHERE nome = 'Champions League'), 'Bayern Monaco', 'Real Madrid');

-- Partita 5: TERMINATA (Liverpool FC vs Juventus) - Giocata il 12 Luglio 2026 alle 20:45
-- Arbitro: Taylor
INSERT INTO partita (id, stato, goals_home, goals_away, luogo, dataeora, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id, squadra_casa_nome_storico, squadra_ospite_nome_storico) VALUES (nextval('partita_seq'), 'TERMINATA', 1, 1, 'Anfield', '2026-07-12 20:45:00', (SELECT id FROM arbitro WHERE cognome = 'Taylor'), (SELECT id FROM squadra WHERE nome = 'Liverpool FC'), (SELECT id FROM squadra WHERE nome = 'Juventus'), (SELECT id FROM torneo WHERE nome = 'Champions League'), 'Liverpool FC', 'Juventus');

-- Partita 6: PROGRAMMATA (Juventus vs Liverpool FC, ritorno) - Prevista per il 26 Luglio 2026 alle 20:45
-- Arbitro: Marciniak (ha già arbitrato la partita 3 il 18 Luglio, ma qui siamo all'orario diverso dell'8 giorni dopo: nessun conflitto)
INSERT INTO partita (id, stato, goals_home, goals_away, luogo, dataeora, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id, squadra_casa_nome_storico, squadra_ospite_nome_storico) VALUES (nextval('partita_seq'), 'PROGRAMMATA', 0, 0, 'Allianz Stadium', '2026-07-26 20:45:00', (SELECT id FROM arbitro WHERE cognome = 'Marciniak'), (SELECT id FROM squadra WHERE nome = 'Juventus'), (SELECT id FROM squadra WHERE nome = 'Liverpool FC'), (SELECT id FROM torneo WHERE nome = 'Champions League'), 'Juventus', 'Liverpool FC');

-- Partita 7: TERMINATA (Paris Saint-Germain vs Barcellona) - Europa League, giocata l'11 Luglio 2026 alle 21:00
-- Arbitro: Turpin
INSERT INTO partita (id, stato, goals_home, goals_away, luogo, dataeora, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id, squadra_casa_nome_storico, squadra_ospite_nome_storico) VALUES (nextval('partita_seq'), 'TERMINATA', 4, 2, 'Parc des Princes', '2026-07-11 21:00:00', (SELECT id FROM arbitro WHERE cognome = 'Turpin'), (SELECT id FROM squadra WHERE nome = 'Paris Saint-Germain'), (SELECT id FROM squadra WHERE nome = 'Barcellona'), (SELECT id FROM torneo WHERE nome = 'Europa League'), 'Paris Saint-Germain', 'Barcellona');

-- Partita 8: PROGRAMMATA (Manchester City vs Bayern Monaco, Europa League) - Prevista per il 30 Luglio 2026 alle 21:00
-- Arbitro: Orsato (ha arbitrato la partita 2 il 10 Luglio: qui siamo 20 giorni dopo, nessun conflitto)
-- Nota: le stesse due squadre si erano già affrontate anche in Champions League (partita 2): capita nella fase a gruppi di tornei diversi nella stessa stagione
INSERT INTO partita (id, stato, goals_home, goals_away, luogo, dataeora, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id, squadra_casa_nome_storico, squadra_ospite_nome_storico) VALUES (nextval('partita_seq'), 'PROGRAMMATA', 0, 0, 'Etihad Stadium', '2026-07-30 21:00:00', (SELECT id FROM arbitro WHERE cognome = 'Orsato'), (SELECT id FROM squadra WHERE nome = 'Manchester City'), (SELECT id FROM squadra WHERE nome = 'Bayern Monaco'), (SELECT id FROM torneo WHERE nome = 'Europa League'), 'Manchester City', 'Bayern Monaco');

-- Partita 9: TERMINATA (Real Madrid vs squadra storica non più iscritta) - dimostra il caso "squadra_ospite_id" nullo con nome storico salvato
-- Arbitro: Collina non assegnato (dimostra anche il caso arbitro nullo, con nome storico salvato)
INSERT INTO partita (id, stato, goals_home, goals_away, luogo, dataeora, arbitro_id, squadra_casa_id, squadra_ospite_id, torneo_id, squadra_casa_nome_storico, squadra_ospite_nome_storico, arbitro_nome_cognome_storico) VALUES (nextval('partita_seq'), 'TERMINATA', 5, 0, 'Santiago Bernabeu', '2026-06-20 18:00:00', NULL, (SELECT id FROM squadra WHERE nome = 'Real Madrid'), NULL, (SELECT id FROM torneo WHERE nome = 'Champions League'), 'Real Madrid', 'Real Sporting Amatori', 'Gianluca Rocchi');


-- ====================================================================
-- 9. INSERIMENTO COMMENTI (CU utente registrato: inserimento commento a una partita)
-- ====================================================================

-- Commento di Mario sulla partita Real Madrid - Milan AC (partita 1, TERMINATA 3-1)
INSERT INTO commento (id, testo, data_creazione, partita_id, utente_id) VALUES (nextval('commento_seq'), 'Che partita! Il Real ha dominato nel primo tempo, il 3-1 finale è giusto.', '2026-07-10 23:15:00', (SELECT id FROM partita WHERE luogo = 'Santiago Bernabeu' AND dataeora = '2026-07-10 21:00:00'), (SELECT id FROM users WHERE cognome = 'Rossi'));

-- Commento di Giulia sulla stessa partita
INSERT INTO commento (id, testo, data_creazione, partita_id, utente_id) VALUES (nextval('commento_seq'), 'Peccato per il Milan, nella ripresa ha creato tanto ma non è bastato.', '2026-07-11 09:02:00', (SELECT id FROM partita WHERE luogo = 'Santiago Bernabeu' AND dataeora = '2026-07-10 21:00:00'), (SELECT id FROM users WHERE cognome = 'Neri'));

-- Commento di Luca sul pareggio Manchester City - Bayern Monaco (partita 2, TERMINATA 2-2)
INSERT INTO commento (id, testo, data_creazione, partita_id, utente_id) VALUES (nextval('commento_seq'), 'Difese totalmente assenti oggi, ma spettacolo puro dall''inizio alla fine.', '2026-07-10 23:40:00', (SELECT id FROM partita WHERE luogo = 'Etihad Stadium' AND dataeora = '2026-07-10 21:00:00'), (SELECT id FROM users WHERE cognome = 'Ferri'));

-- Commento di Mario sul pareggio Liverpool - Juventus (partita 5, TERMINATA 1-1)
INSERT INTO commento (id, testo, data_creazione, partita_id, utente_id) VALUES (nextval('commento_seq'), 'Vlahovic ancora decisivo, la Juve porta a casa un pareggio prezioso ad Anfield.', '2026-07-13 08:20:00', (SELECT id FROM partita WHERE luogo = 'Anfield'), (SELECT id FROM users WHERE cognome = 'Rossi'));

-- Commento di Giulia sulla goleada PSG - Barcellona (partita 7, TERMINATA 4-2)
INSERT INTO commento (id, testo, data_creazione, partita_id, utente_id) VALUES (nextval('commento_seq'), 'Il PSG ha surclassato il Barcellona, primo tempo semplicemente perfetto.', '2026-07-12 07:55:00', (SELECT id FROM partita WHERE luogo = 'Parc des Princes'), (SELECT id FROM users WHERE cognome = 'Neri'));

-- Commento di Luca in attesa della partita 3 (PROGRAMMATA, dimostra un commento su una partita non ancora giocata)
INSERT INTO commento (id, testo, data_creazione, partita_id, utente_id) VALUES (nextval('commento_seq'), 'Non vedo l''ora di questa sfida al San Siro, sarà decisiva per il girone!', '2026-07-15 12:00:00', (SELECT id FROM partita WHERE luogo = 'San Siro'), (SELECT id FROM users WHERE cognome = 'Ferri'));

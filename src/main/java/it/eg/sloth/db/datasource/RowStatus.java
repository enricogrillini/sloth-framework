package it.eg.sloth.db.datasource;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
public enum RowStatus {
  CLEAN, // Consistente con la sorgente dati (db, ...)
  INSERTED, // Sulla sorgente dati non esiste
  DELETED, // Va cancellato dalla sorgente dati
  UPDATED, // Va aggiornato sulla sorgente dati
  INCONSISTENT, // Le informazioni sono inconsistenti con la sorente dati
  POST_INSERT, // L'inserimento è stato effettuato ma non committato
  POST_DELETE, // La cancellazione è stata effettuata ma non committata
  POST_UPDATE // l'aggiornamento è stato effettuato ma non committato
}

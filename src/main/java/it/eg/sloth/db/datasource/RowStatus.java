package it.eg.sloth.db.datasource;

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

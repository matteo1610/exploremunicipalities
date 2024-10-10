# Explore Municipalities

Progetto di Ingegneria del Software per la valorizzazione e digitalizzazione dei territori comunali.

Sviluppato da:

- Matteo Cardellini (matricola 119137)
- Leonardo Pigliacampo (matricola 119405)

Frequentati il corso di Informatica e Informatica per la Comunicazione Digitale (L-31) presso l’università di Camerino.

## Descrizione del progetto 

Il progetto si propone di creare una piattaforma innovativa dedicata alla valorizzazione e digitalizzazione dei territori
comunali, dove ogni comune può raccontare e promuovere le proprie bellezze attraverso la segnalazione dei punti di interesse.

La piattaforma è pensata come uno spazio collaborativo, in cui i cittadini, una volta registrati e approvati dal gestore comunale,
possono contribuire attivamente inserendo nuovi punti di interesse, valorizzando così il territorio  in cui vivono.

## Frontend 

![image](https://github.com/user-attachments/assets/7d91401a-e5b4-47f1-9339-6c87f3c5a02a)
![image](https://github.com/user-attachments/assets/5348101b-fe08-4bc8-9b3f-35d43e633c93)

Lato frontend l'applicazione è stata realizzata utilizzando il framework Angular, implementa un pattern Single Page Application 
ed è stata sviluppata per essere responsive, e quindi fruibile sia in modalità web che mobile. Fa affidamento, tra le varie, 
sulle seguenti librerie npm:

- auth0/angular-jwt: modulo Angular che semplifica l'integrazione di JSON Web Token (JWT)
- ng-bootstrap: libreria di componenti Angular basata su Bootstrap
- leaflet: libreria JavaScript leggera e flessibile per la creazione di mappe interattive, utilizzata in questo contesto per
  l'aggiunta di mappe tramite le API OSM (Open Street Map).

## BackEnd 

Il backend dell'applicativo è stato realizzato sfruttando il framework open-source SpringBoot, in particolar il modo il suo modulo 
web per lo sviluppo di API REST. Per quanto riguarda la persistenza dei dati, il servizio web fornito fa affidamento sulle 
JPA (Java Persistence API) per comunicare con il database SQL Server sottostante.

Il backend fa affidamento, tra le varie, sulle seguenti librerie:

- spring-boot-starter-web: dipendenze per lo sviluppo di applicazioni web con Spring Boot

- spring-boot-starter-security: dipendenze per l'integrazione di Spring Security in un'applicazione Spring Boot.

- spring-boot-starter-data-jpa: abilita l'integrazione di Spring Data JPA, che semplifica l'accesso e la gestione dei
   dati in un'applicazione attraverso Java Persistence API (JPA).

- jjwt: libreria per la gestione di JSON Web Token (JWT).

## Sicurezza 🔐

- Trasferimento dati: Il trasferimento dei dati tra le classi Java nel backend e le tabelle del database relazionale può comportare
  rischi di sicurezza, specialmente se si restituiscono all'utente informazioni sensibili come la password durante l'autenticazione.

- SQL injection: l'utilizzo delle JPA (Java Persistence API) lato backend permette una mappatura ORM (Object-Relational Mapping)
  uno a uno tra le classi Java e le tabelle relazionali della base di dati, e una generazione automatica delle query da parte del
  framework, rimuovendo la necessità di scrivere ed eseguire manualmente query SQL con conseguenti vulnerabilità di SQL Injection.

- Richieste cross-origin: per evitare problemi di CORS durante lo sviluppo, il client Angular è stato configurato in modo da inoltrare
  le richieste che vengono effettuate al backend tramite proxy (vedi proxy.conf.json); questa configurazione ha lo scopo di facilitare
 lo sviluppo dati i fini didattici del progetto, mentre in un reale ambiente di produzione è necessario configurare correttamente il
  server per gestire le autorizzazioni CORS.

- Autenticazione e autorizzazioni: vengono gestite grazie al framework Spring Security e fanno affidamento sullo standard JWT (JSON Web Token),
   che ci consente di generare token crittograficamente sicuri inclusi in ogni richiesta del client.

Per Testare le API fornite, è stata aggiunta la dipendenza Swagger  (http://localhost:8080/swagger-ui/index.html).

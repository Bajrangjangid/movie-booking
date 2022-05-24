### Movie Booking Service

- A movie ticket booking system provides its customers the ability to purchase theatre seats online.
- E-ticketing systems allow the customers to browse through movies currently being played and to book seats, anywhere and anytime.

#### Step-1: Requirements and Goals of the System
Our ticket booking service should meet the following requirements:
##### Functional Requirements:
  - Our ticket booking service should be able to list down different cities where its affiliate cinemas are located.
  - Once the user selects the city, the service should display the movies released in that particular city.
  - Once the user selects the movie, the service should display the cinemas running that movie and its available shows.
  - The user should be able to select the show at a particular cinema and book their tickets.
  - The service should be able to show the user the seating arrangement of the cinema hall and the user should be able to select multiple seats according to their preference.
  - The user should be able to distinguish available seats from the booked ones.
  - Users should be able to put a hold on the seats for five minutes before they make a payment to finalize the booking.
  - The user should be able to wait if there is a chance that seats might become available – e.g. when holds by other users expire.
  - Waiting customers should be serviced fairly in a first come first serve manner.
##### Non-Functional Requirements:
  - The system would need to be highly concurrent. There will be multiple booking requests for the same seat at any particular point in time. The service should handle this gracefully and fairly.
  - The core thing of the service is ticket booking which means financial transactions. This means that the system should be secure and the database ACID compliant.

#### Step-2: Some Design Considerations
- For simplicity, let’sassume our service doesn’t require user authentication.
- The system will not handle partial ticket orders. Either user gets all the tickets they want, or they get nothing.
- Fairness is mandatory for the system.
- To stop system abuse, we can restrict users not to book more than ten seats.
- We can assume that traffic would spike on popular/much-awaited movie releases, and the seats fill up pretty fast.
- The system should be scalable, highly available to cope up with the surge in traffic.

#### Step-3: Capacity Estimation
##### Traffic estimates:
- Let’s assume that our service has 3 billion page views per month and sells 10 million tickets a month.
##### Storage estimates:
- Let’s assume that we have 500 cities and on average each city has ten cinemas.
- If there are 2000 seats in each cinema and on average, there are two shows every day.
- Let’s assume each seat booking needs 50 bytes (IDs, NumberOfSeats, ShowID, MovieID, SeatNumbers, SeatStatus, Timestamp, etc.) to store in the database.
- We would also need to store information about movies and cinemas, let’s assume it’ll take 50 bytes.
- Total storage in a day for all shows of all cinemas of all cities: 500 cities*10 cinemas*2000 seats*2 shows*(50+50) bytes = 2GB / day.
- To store 5 years of this data, we would need around 3.6PB. (Petabyte) 

#### Step-4: Database Design
Here are a few observations about the data we are going to store:

- Each City can have multiple Cinemas.
- Each Cinema will have multiple halls.
- Each Movie will have many Shows, and each Show will have multiple Bookings.
- A user can have multiple bookings.

![image](https://user-images.githubusercontent.com/41802889/170082826-a8a95960-9cd2-4cc0-ae2b-ddac0ef3f8bb.png)

#### Step-5: High Level Design
- At a high level, our web servers will manage Users’ sessions.
- Application servers will handle:
  - all the ticket management
  - storing data in the databases and
  - work with cache servers to process reservations.
  
![image](https://user-images.githubusercontent.com/41802889/170101630-56bdd423-110b-40ff-96b5-607025b12781.png)

#### Step-6: Detailed Component Design
First, let’s try to build our service assuming if it is being served from a single server.
##### Ticket Booking Workflow:
- User searches for a movie.
- User selects a movie.
- User is shown the available shows of the movie.
- User selects a show.
- User selects the number of seats to be reserved.
- If the required no. of seats are available, the user is shown a map of the theater to select seats, if not, user is taken to ‘step-8’ below.
- Once the user selects the seat, the system will try to reserve those selected seats.
- If seats can’t be reserved, we have following options:
    - Show is full; the user is shown the error message.
    - The seats user wants to reserve are no longer available, but there are other seats available, so the user is taken back to the theater map to choose different seats.
    - There are no seats available to reserve, but all the seats are not booked yet as there are some seats that other users are holding in reservation pool and have not booked yet. The user will be taken to a waiting page where they can wait until required seats get freed from the reservation pool. This waiting could result in following options:
        - If the required number of seats become available, the user is taken to the theater map page where they can choose the seats.
        - While waiting if all seats get booked, or there are fewer seats in the reservation pool than the user intend to book, the user is shown the error message.
        - User cancels the waiting and is taken back to the movie search page.
        - At max, a user can wait one hour, after that user’s session gets expired and the user is taken back to the movie search page.
- If seats are reserved successfully, the user has five minutes to pay for the reservation.
- After payment, booking is marked complete. If the user is not able to pay within five minutes, all their reserved seats are freed to become available to other users.

![image](https://user-images.githubusercontent.com/41802889/170096061-664aae95-8ad9-472b-96c0-e6ac776d46b7.png)

##### How would the server keep track of all the active reservation that haven’t been booked yet? And how would the server keep track of all the waiting customers?
 - We need two daemon services, one to keep track of all active reservations and to remove any expired reservation from the system, let’s call it ActiveReservationService.
- The other service would be keeping track of all the waiting user requests, and as soon as the required number of seats become available, it’ll notify the (the longest waiting) user to choose the seats, let’s call it WaitingUserService.

##### ActiveReservationService
ActiveReservationsService keeping track of all active reservations
- We can keep all the reservations of a ‘show’ in memory in a Linked HashMap in addition to keeping all the data in the database.
- We would need a LinkedHashMap so that we can jump to any reservation to remove it when the booking is complete.
- Also, since we will have expiry time associated with each reservation, the head of the linked HashMap will always point to the oldest reservation record, so that the reservation can be expired when the timeout is reached.
- To store every reservation for every show, we can have a HashTable where the ‘key’ would be ‘ShowID’ and the ‘value’ would be the LinkedHashMap containing ‘BookingID’ and creation ‘Timestamp’.
- In the database, we’ll store the reservation in the ‘Booking’ table, and the expiry time will be in the Timestamp column.
- The ‘Status’ field will have a value of ‘(1) Reserved’ and as soon as a booking is complete, system will update the Status to ‘(2) Booked’ and remove the reservation record from the Linked HashMap of the relevant show. When the reservation is expired, we can either remove it from the Booking table or mark it ‘(3) Expired’ in addition to removing it from memory.
- ActiveReservationsService will also work with the external financial service to process user payments.
- Whenever a booking is completed, or a reservation gets expired, WaitingUsersService will get a signal, so that any waiting customer can be served.

##### WaitingUsersService
- Just like ActiveReservationsService, we can keep all the waiting users of a show in memory in a Linked HashMap.
- We need a Linked HashMap so that we can jump to any user to remove them from the HashMap when the user cancels their request.
- Also, since we are serving in a first-come- first-serve manner, the head of the Linked HashMap would always be pointing to the longest waiting user, so that whenever seats become available, we can serve users in a fair manner.
- We will have a HashTable to store all the waiting users for every Show.
The ‘key’ would be ‘ShowID’, and the ‘value’ would be a LinkedHashMap containing ‘UserIDs’ and their wait-start-time.
- Clients can use Long Polling for keeping themselve updated for their reservation status.
- Whenever seats become available, the server can use this request to notify the user.

##### Reservation Expiration
- On the server, ActiveReservationsService keeps track of expiry (based on reservation time) of active reservations.
- As the client will be shown a timer (for the expiration time) which could be a little out of sync with the server, we can add a buffer of five seconds on the server to safeguard from a broken experience - such that – the client never times out after the server, preventing a successful purchase.

#### Step-7: Concurrency
- How to handle concurrency; such that no two users are able to book same seat ?
- We can use transactions in SQL databases to avoid any clashes.
- For example:- if we are using SQL server we can utilize Transaction Isolation Levels to lock the rows before we can update them.

#### Third Party Payment

To make the system simpler, we can use a third party payment solution. First, the orchestrator requests the notification service to communicate with the theatre’s server to lock a seat for the user, Next, it receives a response back from the theatre that the seat has been locked for the user. At this stage, the orchestrator forwards the user’s request to the third party payment solution. Different kinds of payments may be supported, including credit card, debit card and internet banking. Once the payment is successfully completed, the orchestrator receives a response. The orchestrator will forward it to the movie booking service to notify the theatre’s API that the ticket has been booked.

#### Notification Service

Once the payment is complete and the ticket has been booked for the user, the orchestrator will send the message to the notification service. The purpose of the notification service is to send SMS, Email, and push notifications to the users, as the name implies.

![image](https://user-images.githubusercontent.com/41802889/170104366-c0940e66-9fa5-490b-b5f6-cb2aec6b706c.png)

Since notifications involve calls to third-party APIs, such as Email or SMS servers, latencies can be involved. This is why we need to execute the tasks of the notification service asynchronously. This is the reason why we need a messaging queue to hold messages in a queue and pass them on to any of the relevant asynchronous workers connected to the queue as soon as they are available; i.e. an Email notification is passed to an available worker for Email, and so on.

The messaging queue picks up the messages from the orchestrator. The notifications are held in the queue and forwarded on a first-come-first serve basis, or any other principle, to the available workers. Any messaging queue may be used for the purpose, but we have used Kafka. RabbitMQ is also a good choice. Python celery can be used for the workers.

#### Step-8: Housting Solution (Cloud)

##### CICD Portal

![image](https://user-images.githubusercontent.com/41802889/170103126-0552fb1b-3d47-491e-8b5c-07f770cb8153.png)

##### CICD Pipeline

![image](https://user-images.githubusercontent.com/41802889/170103360-f3316a93-3bc8-4966-9207-db56f918770b.png)



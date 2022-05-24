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
  
![image](https://user-images.githubusercontent.com/41802889/169042676-c160e99c-4069-4a7c-bc21-eb2adc99b762.png)

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






#### High Level Design
The high level design of a basic movie booking system looks like this:

![image](https://user-images.githubusercontent.com/41802889/169036742-c09e4d8d-25d8-4bac-bdec-f1070ab40042.png)

The user connects to the movie booking system and the movie booking system is connected to the systems of all the affiliated cities with theatre. The movie booking system allows users to make bookings at any of the affiliated theatre in selected city.

#### How Movie Booking System Connects To The Theatre System
Now optimizing the design further, let’s add a load balancer to connect the user to the movie booking system. Let’s also zoom into the ‘Theatre’ block to see how the movie booking system connects to the theatre’s system. There are two ways for your movie booking system to connect to the theatre’s system:

#### Option 1: Connect To Theatre’s Database

![image](https://user-images.githubusercontent.com/41802889/169042676-c160e99c-4069-4a7c-bc21-eb2adc99b762.png)

The user connects to a load balancer to communicate with the application server. Since we have a scalable and highly available movie ticket booking application, we will serve the users’ requests using distributed app servers. Scaling the app servers horizontally allows the system to handle multiple parallel requests to make sure that the availability is not compromised even when the load is high.

Upon the user’s prompt, the app server communicates with the cached data to check for shows etc. The cached data is regularly synched with the theatre’s database for up-to-date information. If the information isn’t available in the cache, the app server fetches it directly from the theatre’s database. The theatre’s database is linked with the theatre’s server, so the theatre can keep track of the bookings and payments.

You can see in the diagram that alongside our Movie Booking System, other booking applications also connect to the same database. Furthermore, our Movie Booking System will not only connect to a single theatre’s database. It will connect to multiple databases for all the affiliate theatre.

#### Option 2: Connect To Theatre’s API
Instead of directly connecting to the theatre’s database, an alternative approach is for the movie booking system to connect to the theatre’s API. The theatre’s API connects with the theatre’s backend server to send the available seats information to the movie booking system’s server and handle reservation requests.

So an alternative design for a movie booking application is the one shown in the diagram below:

![image](https://user-images.githubusercontent.com/41802889/169047765-7e762fa3-81b6-4686-b04a-4dce9516d301.png)

#### Multiple Requests For A Seat
There may be cases where multiple movie booking applications attempt to book a seat for a show. The theatre’s server will use a locking mechanism to reserve and lock the seat for a few minutes for the user connecting from a movie booking application on a first come first serve basis. If a booking is not made by the user for, let’s say, 5 minutes, the theatre’s server unlocks the seat for all movie booking apps.

#### Step-By-Step Movie Booking Workflow
Here’s how the workflow of the movie booking system looks like:

- The user visits the movie booking application.
- The user chooses the location. Alternatively, GPS can be used to identify the user’s location if they’re connected to the system from a mobile phone. If they’re using a laptop, the ISP that they’re connected to can identify their location.
- Once the location is selected/identified, the movies available in theatres in that location will be displayed as a list.
- When the user picks a movie, all the available shows for the movie, with different timings and different theatres are displayed. This information is fetched from the cached database of the movie booking application.
- The user picks a show, depending on their preferred showtime and preferred theatre.
- Next, the system fetches the seat information from the theatre’s API and displays it to the user, often in the form of a map. The map will display a collection of available and occupied seats, where the user can only select from the available seats.
- Once the user picks a seat (or seats), our movie booking application will lock the seat temporarily for a predefined interval, let’s say, 5 minutes. This makes sure no other user, from the same application or a different one cannot pick the same seat during this time. If the user does not book the ticket for the locked seats within the timeframe, the system releases the seat to other users. A booking is marked complete by the system once the payment is made.
- Once the user selects a seat, they will be moved to the payment page, which informs them of the price, payment options and other details. We use a third party payment solution to handle the payment part for us.
- Once the payment is carried out successfully, the app server notifies the theatre. The theatre creates a unique ticket ID and sends it to the application server. This completes a booking.
- The movie booking application creates a ticket with the ticket ID and all the details of the ticket, including the theatre, movie, seat number, hall number, timings etc., and sends a copy to the user via Email, SMS or both.

![image](https://user-images.githubusercontent.com/41802889/169103812-c9155982-b8f3-4c1f-baac-883a5747526d.png)

#### Third Party Payment

To make the system simpler, we can use a third party payment solution. First, the orchestrator requests the notification service to communicate with the theatre’s server to lock a seat for the user, Next, it receives a response back from the theatre that the seat has been locked for the user. At this stage, the orchestrator forwards the user’s request to the third party payment solution. Different kinds of payments may be supported, including credit card, debit card and internet banking. Once the payment is successfully completed, the orchestrator receives a response. The orchestrator will forward it to the movie booking service to notify the theatre’s API that the ticket has been booked.

#### Notification Service

Once the payment is complete and the ticket has been booked for the user, the orchestrator will send the message to the notification service. The purpose of the notification service is to send SMS, Email, and push notifications to the users, as the name implies.

![image](https://user-images.githubusercontent.com/41802889/169105537-084694b4-e5fb-46d4-80d5-d6c30d61c131.png)

Since notifications involve calls to third-party APIs, such as Email or SMS servers, latencies can be involved. This is why we need to execute the tasks of the notification service asynchronously. This is the reason why we need a messaging queue to hold messages in a queue and pass them on to any of the relevant asynchronous workers connected to the queue as soon as they are available; i.e. an Email notification is passed to an available worker for Email, and so on.

The messaging queue picks up the messages from the orchestrator. The notifications are held in the queue and forwarded on a first-come-first serve basis, or any other principle, to the available workers. Any messaging queue may be used for the purpose, but we have used Kafka. RabbitMQ is also a good choice. Python celery can be used for the workers.


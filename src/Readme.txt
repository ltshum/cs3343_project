CS3343 Group 27

LI Qiaolan, Project Manager
MAN Hon Pan, Assistant Project Manager
HUI Hiu Wang, Development and Test Led
SHUM Lok Ting, Advanced Programmer
David Alexander Leynes CORNEJO, Programmer
LONG Yancheng, Programmer



-- Restaurant Booking System --



-- Description --

Restaurant Booking System is a booking platform that serves both customers and restaurants. It aims to shorten the process of customers finding and getting a seat in restaurants of interest, while providing an efficient way for restaurants to manage bookings and observe their customers' feedback.

In this program,

Customers may:
- search for restaurants
- make bookings
- give rating and comments to restaurants

Restaurants may:
- manage table information
- take booking attendance
- view weekly report of booking statistics and ratings



-- Prerequisites --

1. Operating System: Windows or macOS
2. Java Runtime Environment version: 8 or above
  - Install JRE here: http://www.oracle.com/technetwork/java/javase/downloads/index.html



-- Program Execution --

1. Go to "Release" folder
2. Run TODONAMEPENDING.jar
  - For Windows, execute "Run.bat"
  - For macOS, open Terminal and input "bash {folder_path}/Run.sh"



-- User Guideline --

Starting the program, user is first greeted with 3 options:
1. Sign-in
2. Sign-up
3. Exit

User may input the number corresponding to the desired action, eg. input "2" to sign up.
User may sign up as either Customer or Restaurant.
To exit the program, input "3".

When signed in as Customer, there are 4 options:
1. Profile
  - This displays the user's profile information. 
  - In edit mode, user may input the number corresponding to the field they wish to edit.
  - User's name and phone number will be used in their comments and their booking respectively.

2. View Booking
  - This displays the booking record of the current date. User may input a date to view another date's booking record, eg. input "2024-12-21".
    Display example:
    [2024-12-21]
    1. AC1: 13:00 - 14:00 3ppl

  2.1 Make comment
    - If user has made a booking and that booking is marked as "Attended" by the restaurant, they may input the number of the booking to give rating and comment to that restaurant.
      eg. input "1" to choose the booking of AC1, then input "3" as the rating and "Good" as the comment.
    - Larger rate number means the user has more positive experience in that restaurant.
    - The submitted comment can be viewed in the restaurant's profile, and will affect that restaurant's rating and ranking.

3. Search Restaurant
  - User may search for restaurants with the following criteria:
    a. Name, eg. "Subway"
    b. District, eg. "Tsim Sha Tsui"
    c. Type, eg. "Japanese"
    d. Rate range, eg. "2-4" or "5"
    e. Seat number, eg. "3"
    f. Start time, eg. "13:00"
    g. Duration, eg. "30"
  - If user has no preference for a criterion, they may input "null" instead.
  - Search result is a list of restaurants with brief information, sorted by how closely it matches the search criteria.

  3.1 Make booking
    - User may input the number corresponding to the restaurant, which displays its detailed information, and gives the option to make booking of that restaurant.
    - User may book for the current day, or book for another day. To book for another day, user must first enter the target date for booking.
    - User must input the desired timeslot, eg. "13:00 - 14:00", then the seat number, in order to successfully create a booking.

4. Logout
  - This takes the user back to the initial page with 3 options.


When signed in as Restaurant, there are 5 options:

1. Profile
  - This displays the user's profile information.
  - In edit mode, user may input the number corresponding to the field they wish to edit.
  - User's name, district, type, etc. will affect their rank in customers' search results.
  - Editing the open time, close time, session duration will automatically generate a new set of timeslots.
  - Editing the table amount will reset all table information.

2. View Booking
  - This displays the booking record of the current date. User may input a date, to view another date's booking record, eg. input "2024-12-21".
    Display example:
    [2024-12-21]
    13:00 - 14:00
                | Table ID: 1
                | Booker: Jack
                | Ppl No: 3
                | Contact: 12345678
                | Arrived?: false

  2.1 Take attendance
    - User may input "T" to enter the attendance taking session.
    - User must first input the timeslot, then the table ID. After this, the booking will be marked as "Attended".

3. Table Management
  - This displays detailed information of all tables, including table ID and seat number.
  - User may edit the seat number of each table.

4. Weekly Report
  - This generates a report based on the current week's and the previous week's statistics, which displays the following information:
    a. Ranking of all restaurants in the system
      - Rank is based on the restaurant's rate. Higher rank means the restaurant has a larger rate.
    b. User's rank, rate, customer number, comments of the previous week
    c. User's rank, rate, customer number, comments of the current week
    d. Changes in rank, rate, customer number between the two weeks

5. Logout
  - This takes the user back to the initial page with 3 options.



-- End --
# My Personal Project

## Hotel Guest Management System

As someone who enjoys travelling, I have spent a considerable amount of time staying at hotels. Before being exposed to
coding and database management, I often wondered how hotels maintain such copious amounts of guest details and data. 
Further, I have also wondered how they check for the earliest availability among hundreds of currently booked rooms
as well. It must have been a tedious process to maintain all this data and manipulate them based on the situation 
before the advent of software systems. Hence, I have taken up this project to gain a better understanding on the 
intricacies of software development. This project would also enable me to explore how Java can be used to simplify an
industry problem.

*Feel free to download demo.mp4 and take a look at the application in action!*

*The application is designed with the vision of simplifying the job of hotel staff.*

**A guest would have the following attributes:**

- Name
- Phone Number
- Check In Date
- Check Out Date
- Outstanding Bill
- Overstay status (Yes/No)
- Minibar usage (Yes/No)

**The hotel would have the following attributes:**

- Number of suites
- Number of double bedrooms
- Number of single bedrooms
- Price for each room
- Price for minibar usage
- Price for each room


### User Stories
- As a user, I want to be able to enter details of a guest who is checking in.
- As a user, I want to be able to add the new guest's details to the existing hotel directory.
- As a user, I want to be able to delete details of a guest who is checking out.
- As a user, I want to be able to modify guest details.
- As a user, I want to be able to check how many rooms of a specific type are available.
- As a user, I want to be able to add to the bill of a guest.
- As a user, I want to be able to generate the bill for a guest's stay. 
- As a user, I want to be able to check which guests have overstayed.
- As a user, I want to be able to save my guestList to file (if I so choose)
- As a user, I want to be able to be able to load my guestList from file (if I so choose)

# User Instructions

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking
on Guest Check In. This adds Guest X to guestList Y.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking 
on Overstayed Guests. This adds Overstayed Guest X to overstayList Y.
- You can locate my visual component by running the splash screen. It is an image on the loading page.
- You can save the state of my application by clicking on Save Hotel. This saves the current state of the hotel.
- You can reload the state of my application by clicking on Load Hotel. This loads the hotel from hotel.json.

# Phase 4: Task 2
All logged events:
1. Thu Apr 11 09:28:31 PDT 2024
   Hotel state loaded from file
2. Thu Apr 11 09:28:31 PDT 2024
   Guest Michael Grey set as overstayer
3. Thu Apr 11 09:28:31 PDT 2024
   Guest Ada Shelby set as overstayer
4. Thu Apr 11 09:28:31 PDT 2024
   Guest Shaam set as overstayer
5. Thu Apr 11 09:28:35 PDT 2024
   Hotel state saved to file

This task can also be viewed in the GUI. Events logged are displayed upon clicking the Exit button.

# Phase 4: Task 3 (Future Iteration Ideas)         

Given more time, I would have worked on introducing an abstract class named Accomodation which can streamline 
the design of the hotel management system by consolidating shared functionality and attributes. By encapsulating common
properties like name, phoneNumber, roomType, checkInDate, and checkOutDate, it reduces redundancy across subclasses 
and ensures consistency in data representation and manipulation. Furthermore, methods like completeCheckIn(),
completeCheckOut(), and getBill() can be abstract methods in this class, allowing subclasses to implement methods 
according to their accommodation type. 

This approach also facilitates future modifications as changes made in the abstract class automatically
propagate to all subclasses. For instance, if we later introduce new accommodation types or extend existing ones,
we can easily incorporate them by creating subclasses that extend Accommodation.

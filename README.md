SCIS Notice Board – Phase 1 Documentation
1. Problem Statement
The SCIS Notice Board is a simple Java application designed for managing and
displaying official announcements at the School of Computer and Information Sciences
(SCIS), University of Hyderabad. The system enables authorized users (Admins and
Faculty) to create, edit, schedule, and archive notices. Students can view active
notices.
This application simulates the core features of a notice board without using a database,
focusing on Java Object-Oriented Programming (OOP) concepts.
2. Use Cases Implemented
- Notice Creation: Admins and faculty can create new notices.
- Notice Viewing: Students can view all currently active notices.
- Notice Archiving: Admins can archive old notices, which are hidden from
students.
3. Class Descriptions
User (Abstract Class)
Represents a general user in the system. Implements abstraction.
- Fields: name, role
- Method: abstract void viewNotices()
- Admin (Inherits User)
   - Can post, view, and archive notices.
   - Methods: postNotice(), archiveNotice(), viewNotices()
- Faculty (Inherits User)
   - Can post and view notices.
   - Methods: postNotice(), viewNotices()
- Student (Inherits User)
   - Can view active notices only.
   - Method: viewNotices()
- Notice
   - Represents a single notice.
   - Fields: title, content, author, dateTime, isArchived
   - Methods: Constructors, Getters, Setters4. OOP Concepts Used
4. OOP Concepts Used
- Abstraction: The User class is abstract and generalized.
- Encapsulation: All fields are private, accessed via getters/setters.
- Inheritance: Admin, Faculty, and Student inherit from User.
- Polymorphism: viewNotices() method is overridden in each subclass.
5. File Storage
- Uses .io package for data storage.

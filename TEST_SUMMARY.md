EventFlow DAO Testing Suite – Summary
What I Made
I’ve put together a full testing suite for the DAO classes in my EventFlow project. It’s designed to test everything—from basic CRUD operations to more complex logic like payments, refunds, and database transactions.

Test Files I Created
UserDAOTest.java – 14 tests covering user registration, login, balance updates, and more

EventDaoTest.java – 12 tests for creating, updating, and deleting events, as well as ticket availability

TicketDaoTest.java – 10 tests that handle ticket creation, retrieval, and purchase scenarios

AllDaoTests.java – A single runner that lets me run everything together

run_tests.bat – A simple batch file to make running the tests easy on Windows

test/README.md – Documentation explaining how the tests are set up and how to run them

Test Coverage Overview
Altogether, I’ve written 36 test methods:

UserDAO (14 tests)
Creating users and validating input

Logging in and checking credentials

Finding users by ID or email

Updating balance and changing passwords

Processing payments (including failure cases)

Deleting accounts and handling admin users

Catching edge cases like duplicate emails or low balance

EventDao (12 tests)
Creating and managing events

Fetching events (all, or by uploader)

Editing and deleting events

Automatically refunding users when events are deleted

Getting ticket availability and refund info

Testing helper classes like EventWithUser

TicketDao (10 tests)
Buying tickets

Listing tickets for users or events

Ordering tickets by date

Testing classes like TicketWithEventDetails

Key Features
Real database connection – No mocking; these tests use the actual DB

Each test is isolated – Sets up and clears its own data

Tests real-world logic – Like refunding tickets or handling failed payments

Covers edge cases – Invalid inputs, missing records, and more

Includes helper class tests – So everything used behind the scenes is also verified

Before Running the Tests
To get these tests working, here’s what needs to be ready:

MySQL should be running

The EventFlow database (with all the required tables) should be created

DbConnection.java needs to be pointing to the right database

Your main app classes should be compiled inside build/classes

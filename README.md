# InsecureSoftwareEngineering

## Contributors
 - Julia Filipczak (18310726)
 - Roland Garza (18200061)
 - Daiana Morjolic (18303531)
 - Lucy Kerrigan (18322913)


Password must be 8-30 characters long and Must contain at least one of each: Uppercase, Lowercase, Number, and Symbol,
when registering a new user. All pre-created users and admin use the password "password" for convenience of testing.

###Admin login:
- username: admin
- password: password

###Users login:
- username: john, jane, mark, mary
- password: password

## Requirements
- [x] Users can register to the Vaccination System. - Roland
- [x] Registered users  can login and logout from the system. - Julia
- [x] Registered users can book a vaccination appointment. - Lucy
- [x] Registered users should be able to retrieve a record of their last activity. - Daiana
- [x] Any user should be able to visualize aggregated statistics about the vaccination campaign. - Roland
- [x] Any user should be able to ask questions. - Roland
- [x] HSE staff who are in charge of administering the vaccination can update vaccination information about a user. - Julia, Lucy, Roland 
- [x] HSE staff can answer the questions that the users ask in the forum. - Roland

## Security Requirements
- [x] During user registration, force users to choose strong passwords. If possible, force the use of 2-factor authentication. -Roland
- [] Ensure that different users cannot register using the same email and/or PPS number of an already registered user
- [] Limit the number of consecutive failed authentication attempts to 3.
- [] If an IP address performs 3 consecutive failed authentication attempts, block it for a given amount of time (e.g., 20 mins).
- [x] Validate the input data provided by the users during registration and in the questions forum. In particular, avoid creating users associated with the same PPS number or email address. -Roland
- [] Handle the errors that are shown to the users in order not to expose implementation information related to your applications. For example, you can return custom error pages when an exception occurs.
- [] Enforce the use of https.
- [] Implement appropriate access control to only allow the user associated with a specific account to access his/her vaccination information. Also apply access control policies to regulate access to the urls that should only be accessible to healthcare staff.
- [] Perform appropriate logging using the log4j Java framework to record sensitive operations, such as logins, access to/modification of sensitive information (reservations, credit card information).
- [] Support session management using JSON Web Tokens
- [] Encrypt sensitive information (such as PPS number, phone number, date of birth) when storing this information in the database.


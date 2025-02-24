Step 1: Register a User
Request:
Method: POST
URL: http://localhost:8080/users/register
Headers:
pgsql
Copy
Edit
Content-Type: application/json
Body (JSON - RAW):
json
Copy
Edit
{
  "email": "testuser@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
Expected Response:
json
Copy
Edit
{
  "message": "User registered successfully with email: testuser@example.com"
}
✅ If this works, check in the database if the password is hashed (BCrypt encoded).

Step 2: Login the User
Request:
Method: POST
URL: http://localhost:8080/users/login
Headers:
pgsql
Copy
Edit
Content-Type: application/json
Body (JSON - RAW):
json
Copy
Edit
{
  "email": "testuser@example.com",
  "password": "password123"
}
Expected Response:
json
Copy
Edit
{
  "message": "Login successful. Use Basic Auth (Email:Password) for protected endpoints."
}
✅ If login works, proceed to private endpoints.

Step 3: Test Authentication with Basic Auth
Now, we'll test a protected endpoint (GET /books) to verify Basic Auth.

Request:
Method: GET

URL: http://localhost:8080/books

Headers:

sql
Copy
Edit
Authorization: Basic testuser@example.com:password123
In Postman, go to Authorization Tab → Select Basic Auth → Enter email/password.
Expected Response (If No Books Exist Yet):

json
Copy
Edit
[]
✅ If this works, it means Basic Auth is working correctly.

Step 4: Test Role-Based Authorization
Now, let’s check if a normal USER can add a book (which should fail).

Request:
Method: POST
URL: http://localhost:8080/books
Headers:
pgsql
Copy
Edit
Authorization: Basic testuser@example.com:password123
Content-Type: application/json
Body:
json
Copy
Edit
{
  "title": "Spring Boot in Action",
  "author": "Craig Walls",
  "isbn": "9781617292545"
}
Expected Response:
json
Copy
Edit
{
  "error": "Forbidden"
}
✅ If this request is forbidden (403 status code), role-based authorization is working.

Step 5: Register an Admin User
Request:
Method: POST
URL: http://localhost:8080/users/register
Headers:
pgsql
Copy
Edit
Content-Type: application/json
Body:
json
Copy
Edit
{
  "email": "admin@example.com",
  "password": "admin123",
  "firstName": "Admin",
  "lastName": "User",
  "role": "ADMIN"
}
✅ Ensure that the role is correctly set as ADMIN in the database.

Step 6: Admin Adds a Book (Should Succeed)
Request:
Method: POST
URL: http://localhost:8080/books
Headers:
pgsql
Copy
Edit
Authorization: Basic admin@example.com:admin123
Content-Type: application/json
Body:
json
Copy
Edit
{
  "title": "Spring Boot in Action",
  "author": "Craig Walls",
  "isbn": "9781617292545"
}
Expected Response:
json
Copy
Edit
{
  "message": "Book added successfully"
}
✅ If the admin can add a book but the normal user cannot, role-based security works fine.

Step 7: Test Unauthorized Access
Try accessing any protected endpoint without Basic Auth.

Method: GET
URL: http://localhost:8080/books
Headers: (No Authorization)
Expected Response: 401 Unauthorized
✅ If unauthorized users cannot access endpoints, authentication is working correctly.

Final Verification
Check if passwords are stored in hashed form (BCrypt) in the database.
Ensure role-based access control works:
USER can view books but cannot add/delete them.
ADMIN can perform all actions.

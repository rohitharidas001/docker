# Overview
Hi Rohit

Thank you for your interest in the H-E-B Digital Fulfillment team. The first part of your Engineering interview includes producing some sample code for you to review with an interviewer during the Technical Problem Review interview. 

Before starting your project, a hiring manager or engineer will go over the details of the technical problem, ensure you have proper access to the repository, and answer any questions you might have. After this, you have 5 days to complete the assessment. We will schedule a 1-hour session to review your submission with an interviewer.

Please let us know if you have any questions in the meantime. We look forward to seeing your work!

# Technical Problem Review 
- Expect to spend no more than 4 hours of your own time on this exercise.
- Submit your solution to this repository as a pull request. 
- Be sure to ask questions during the Q&A and the code review. The interviewer is here to help you succeed!
- Make sure to review your code and be able to walk someone through your decisions.
- Be prepared to discuss the 3rd party libraries you use, including their strengths and weakness, implementation, and complexity. 
- Be prepared to demo your solution via screen share. 
- No frontend is required, but you may implement one to demo the API.

# Order Service
## Overview
Build an HTTP REST API in Java or Scala for a service that stores and returns online grocery orders. A user of the full API should be able to:
- Create new orders in the service.
- Retrieve orders by order id.
- Retrieve orders by the UPCs they contain.
- Retrieve orders by the time they are expected to be picked up.
- Delete existing orders.

## Database
- A persistent datastore is required. Any SQL or NoSQL variation is valid.
- Use the data found in valid.json to seed your database.

## API Specification
- All endpoints should implement error handling and return HTTP codes reflecting the level of failure (client vs. system).
- Unit and/or integration tests for your code are preferred.

### POST /orders
- Send a JSON request body including all data for the order.
- Returns an HTTP 200 OK with a JSON response body.
### GET /orders/:id
- Retrieve an order by id and return its data in JSON format. 
### GET /orders?upc=:upc
- Retrieve all oreders that contain and item with the requested UPC (item identifier) and return their data in JSON format. 
### GET /orders?until=:hours
- Retrieve all orders that are expected to be picked up until the specified number of hours from the time of the request.
-- ex: I want to retrieve all orders that are expected to be picked up before 3PM.
-- Assume that all times are in Central Time.


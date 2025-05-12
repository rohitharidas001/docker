1)Implemented CRUD Restful APIs to create orders(parallel processing for batch request).
2)get an order by order id
3)getting orders by expectedTime
4)deleting an order
5) Postgres DB to store and retrieve data.
6) unit tests leveraging Junit/Mockito

The application is dockerized and load balancer is implemented to split read/write requests to particular servers.

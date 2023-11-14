**Login**
----
로그인

* **URL**

  `/login`

* **Method:**

  `POST`

* **Data Params**

  **Required:** <br/>
  ```json
  {
    "email": "test@test.com",
    "password": "1234test"
  }
  ```

* **Success Response:**

    * **Code:** 200  <br />
      **Header:**`token:{auth token}`
**Sign up**
----
회원가입

* **URL**

  `/members`

* **Method:**

  `POST`

* **Data Params**

  **Required:** <br/>
  ```json
  {
    "email": "test@test.com",
    "name": "testuser",
    "password": "1234test"
  }
  ```

* **Success Response:**

    * **Code:** 201  <br />
      **Content:** <br/>
      ```json
        {            
          "memberId": "3e0011d4-829b-11ee-aa11-839e2981eabf",
          "email": "test@test.com",
          "name": "testuser"
        }
      ```
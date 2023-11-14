**Get Member**
----
단일 회원 조회

* **URL**

  `/members/:memberId`

* **Method:**

  `GET`

* **Headers**
  **Required:**`Authorization: Bearer {bearerToekn}`

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
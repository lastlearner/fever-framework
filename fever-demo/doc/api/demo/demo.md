*User*
----
**Show User**
----
  Returns json data about a single user.
* **URL**
  /users/:id
* **Method**
  GET
* **URL Params**
  **Required:**
   `id=[integer]`
  **Optional:**
   None
* **Data Params**
  None
* **Success Response:**
  * **Code:** 200 OK <br />
    **Content:** `{id:1, name:"fanfever"}`
  OR
  * **Code:** 204 NO_CONTENT <br />
    **Content:** None
* **Error Response:**
  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{error : "You are unauthorized to make this request."}`  
* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/users/1",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
* **Notes:**
  None
  
**Show All Users**
----
  try to use verbs that match both request type (fetching vs modifying) and plurality (one vs multiple.)
* **URL**
  /users
* **Method**
  GET
* **URL Params**
  **Required:**
   `id=[integer]`
  **Optional:**
   None
* **Data Params**
  None
* **Success Response:**
  * **Code:** 200 OK <br />
    **Content:** `{id:1, name:"fanfever"}`
  OR
  * **Code:** 204 NO_CONTENT <br />
    **Content:** None
* **Error Response:**
  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{error : "You are unauthorized to make this request."}`
* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/users",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
* **Notes:**
  None
  
**Add User**
----
* **URL**
  /users
* **Method**
  POST
* **URL Params**
  **Required:**
   None
  **Optional:**
   None
* **Data Params**
  ```
	{
    user : {
    	email : [string],
    	name : [string],
    	current_password : [alphanumeric]
    	password : [alphanumeric],
    	password_confirmation : [alphanumeric]
    }
	}
  ```
* **Success Response:**
  * **Code:** 201 CREATED <br />
    **Content:** None
* **Error Response:**
  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{error : "You are unauthorized to make this request."}`
  OR
  * **Code:** 409 CONFLICT <br />
    **Content:** None
* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/users/1",
      dataType: "json",
      type : "POST",
      success : function(r) {
        console.log(r);
      }
    });
  ```
* **Notes:**
  None
**Update User**
----
* **URL**
  /users/:id
* **Method**
  PUT
* **URL Params**
  **Required:**
   `id=[integer]`
  **Optional:**
   None
* **Data Params**
  ```
	{
    user : {
    	email : [string],
    	name : [string],
    	current_password : [alphanumeric]
    	password : [alphanumeric],
    	password_confirmation : [alphanumeric]
    }
	}
  ```
* **Success Response:**
  * **Code:** 200 OK <br />
    **Content:** `{id:1, name:"fanfever"}`
* **Error Response:**
  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{error : "You are unauthorized to make this request."}`
  OR
  * **Code:** 404 NOT FOUND <br />
    **Content:** `{error:"User doesn't exist"}`
* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/users/1",
      dataType: "json",
      type : "PUT",
      success : function(r) {
        console.log(r);
      }
    });
  ```
* **Notes:**
  None
**Delete User**
----
* **URL**
  /users/:id
* **Method**
  DELETE
* **URL Params**
  **Required:**
   `id=[integer]`
  **Optional:**
   None
* **Data Params**
  None
* **Success Response:**
  * **Code:** 204 NO_CONTENT <br />
    **Content:** None
* **Error Response:**
  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{error : "You are unauthorized to make this request."}`
  OR
  * **Code:** 404 NOT FOUND <br />
    **Content:** `{error:"User doesn't exist"}`
* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/users/1",
      dataType: "json",
      type : "DELETE",
      success : function(r) {
        console.log(r);
      }
    });
  ```
* **Notes:**
  None
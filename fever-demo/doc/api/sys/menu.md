*Menu*
----
**Show Menu**
----
  Returns json data about a single menu.
* **URL**
  /menus/:id
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
    **Content:** `{id:1, parentId:1, name:"系统设置", orders:1, href:"/sys/menu/", target:"", permission:"menu:view", remark:""}`
  OR
  * **Code:** 204 NO_CONTENT <br />
    **Content:** None
* **Error Response:**
  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{error : "You are unauthorized to make this request."}`  
* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/menus/1",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
* **Notes:**
  None
  
**Show All menus**
----
  try to use verbs that match both request type (fetching vs modifying) and plurality (one vs multiple.)
* **URL**
  /menus
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
    **Content:** `{id:1, parentId:1, name:"系统设置", orders:1, href:"/sys/menu/", target:"", permission:"menu:view", remark:""}`
  OR
  * **Code:** 204 NO_CONTENT <br />
    **Content:** None
* **Error Response:**
  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{error : "You are unauthorized to make this request."}`
* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/menus",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
* **Notes:**
  None
  
**Add menu**
----
* **URL**
  /menus
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
    menu : {
    	parentId : [integer],
    	name : [string],
    	orders : [integer]
    	href : [string],
    	target : [string],
    	permission : [string],
    	remark : [string]
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
      url: "/menus/1",
      dataType: "json",
      type : "POST",
      success : function(r) {
        console.log(r);
      }
    });
  ```
* **Notes:**
  None
**Update menu**
----
* **URL**
  /menus/:id
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
    menu : {
    	parentId : [integer],
    	name : [string],
    	orders : [integer]
    	href : [string],
    	target : [string],
    	permission : [string],
    	remark : [string]
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
    **Content:** `{error:"menu doesn't exist"}`
* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/menus/1",
      dataType: "json",
      type : "PUT",
      success : function(r) {
        console.log(r);
      }
    });
  ```
* **Notes:**
  None
**Delete menu**
----
* **URL**
  /menus/:id
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
    **Content:** `{error:"menu doesn't exist"}`
* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/menus/1",
      dataType: "json",
      type : "DELETE",
      success : function(r) {
        console.log(r);
      }
    });
  ```
* **Notes:**
  None
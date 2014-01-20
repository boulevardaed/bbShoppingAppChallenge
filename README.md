BB Shopping App Coding Challenge
======================

Working on the cart part..

Implemented functionalities
----

- Target: Android 4.3 (API ver.18), minimal: Android 4.0 (API ver.14)
- Listview with custom adapter as main frame
- HttpResponseCache for reducing redundant requests
- Get and parse JSON from provided API, including usage of AsyncTask
- Appropriate layout format, including asset studio usage, resources and styles

Program Structure
----

![Structure](https://raw.github.com/boulevardaed/bbShoppingAppChallenge/master/BBChallenge.png)

Component documentation
----

- Cart_Activity - activity for displaying items in cart.java
- Category_Activity.java - activity for category list
- Category_Adapter.java - custom adapter for list view in category activity
- Helper.java - shared functions
	- handleJSONValue: return a list of JSON objects based on input key value
	- isConnected: check whether there's a mobile data connection or not
	- getModifiedDate: convert Unix stamp to date format
	- httpGet: get JSON data from URL
- ImageDownloader.java - use AsyncTask to download Bitmap image
- Product_Dialog.java - a custom dialog to display product details
- Product_List_Activity.java - activity for product list
- Product_List_Adapter.java - custom adapter for list view in product list activity

Challenges
----

 - At first I needed to find a way to update listview after AsyncTask was finished. I tried several ways including setting global variables. Finally I used BroadcastReceiver to receive intent from onPostExecute
 - This is my first time I have implemented images downloading in custom listview. There're several options initially: <br>1. Download them first into a list, then pass it to list view adapter. <br>2. When scrolling the list view, download the image if the item appears on the screen (AsyncTask in adapter). <br>3. Use 3rd party library. Finally I chose the second option. 
 - Not very skillful to parse JSON data, especially when the level is deep.
 - Also this is my first time I have used HttpResponseCache. I come up with several ideas to eliminate redundant data request, like using SQLite and passing data through intent. Finally I found this class in Android documentation.

Current progress
----
![Screenshots](https://raw.github.com/boulevardaed/bbShoppingAppChallenge/master/screenshots/Screenshot_2014-01-19-15-05-39.png)
![Screenshots](https://raw.github.com/boulevardaed/bbShoppingAppChallenge/master/screenshots/Screenshot_2014-01-19-15-06-01.png)
![Screenshots](https://raw.github.com/boulevardaed/bbShoppingAppChallenge/master/screenshots/Screenshot_2014-01-19-15-06-12.png)

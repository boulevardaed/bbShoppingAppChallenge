BB Shopping App Coding Challenge
======================

Working on cart part..

Implemented functionalities
----

- Target: Android 4.3 (API ver.18), minimal: Android 4.0 (API ver.14)
- Listview with custom adapter as main frame
- HttpResponseCache for reducing redundant requests
- Get and parse JSON from provided API, including usage of AsyncTask
- Appropriate layout format, including asset studio usage, resources and styles

Component documentation
----

- Cart_Activity - activity for display items in cart.java
- Category_Activity.java - activity for category list
- Category_Adapter.java - custom adapter for list view in category activity
- Helper.java - shared functions
<br>handleJSONValue: return a list of JSON objects based on an input key value
<br>isConnected: check whether there's a mobile data connection or not
<br>getModifiedDate: convert Unix stamp to date format
<br>httpGet: get JSON data from URL
- ImageDownloader.java - use AsyncTask to download Bitmap image
- Product_Dialog.java - a custom dialog to display product details
- Product_List_Activity.java - activity for product list
- Product_List_Adapter.java - custom adapter for list view in product list activity

Challenges
----

  - At first need to find a way to update listview after AsyncTask is finished. Have tried several ways including setting global variables. Finally use BroadcastReceiver to receive intent from onPostExecute
  - This is my first time to implement images downloading in custom listview. There're several options: <br>1. Download them first into a list, then pass to list view adapter. <br>2. When scrolling the list view, download the image if the item appears on the screen (AsyncTask in adapter). <br>3. Use 3rd party library. Finally choose the second option. 
  - Not very skillful to parse JSON data, especially when the level is deep.
  - Also my first time to use HttpResponseCache. Come up with several ways to eliminate redundant data request, like using SQLite and passing data through intent. Finally find this class in Android documentation.

Current progress
----
![Screenshots](https://raw.github.com/boulevardaed/bbShoppingAppChallenge/master/screenshots/Screenshot_2014-01-19-15-05-39.png)
![Screenshots](https://raw.github.com/boulevardaed/bbShoppingAppChallenge/master/screenshots/Screenshot_2014-01-19-15-06-01.png)
![Screenshots](https://raw.github.com/boulevardaed/bbShoppingAppChallenge/master/screenshots/Screenshot_2014-01-19-15-06-12.png)

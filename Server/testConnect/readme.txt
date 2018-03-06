Test folder has all test php scripts. put these in the server directory. In my case im using wamp, so i put it in the www folder

basic idea:
get is used to read from the database
post is used to write to the database

ip of the server is hardcoded in the link variable
have your server allow access to your device. if you are running an emulator you wont have to worry about this


in this test app, pressing the post button will write whatever is in the text field to the database. Pressing the get button will read from the database using the info in the textfield

model/          Represents the app's data structure

    -User.java
        Represents the users of the app. This class mainly works with DatabaseService.java to handle tasks realted to users and stores personal information.
    -JournalEntry.java
        Represnts posts made by users. This class also works in unison with "DatabaseService.java". 
    -City.java
        Represents Turkey's cities.





utils/          Contains common utilities

        -Validator.java
    Validates emails, usernames, passwords, required fields, etc.

        -FirebasePaths.java
    Stores all Firebase DB path strings in one place to avoid hardcoding.




services/

        -DatabaseService.java

    Reads/writes data to Realtime Database.
    Used for journal entries, user profiles, maps, etc.

        -StorageService.java

    Uploads and downloads images (profile pics, journal photos).
    Uses Firebase Storage.

        -EmailService.java
    Handles tasks related to sending emails (password recovery, welcome message).

        -MapService
    Handles the map on teh main page 


controller/     Handles the logic behind how the GUI should respond, acts as a bridge between  view and modal classes.
view/           Contains GUI elements, does not contain logic.
resources/      Locally stores images or files taht are used within the code 
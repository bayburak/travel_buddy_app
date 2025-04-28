
model/          Represents the app's data structure
controller/     Handles GUI logic for each screen, like specialized panles or such.
view/           Contains General GUI elements, does not contain logic
resources/      Stores images or maps possibly



utils/          Contains common utilities like firebase...

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

        -UserService.java

    Updates user-specific data (e.g., profile updates, follow system).
    Calls DatabaseService inside.
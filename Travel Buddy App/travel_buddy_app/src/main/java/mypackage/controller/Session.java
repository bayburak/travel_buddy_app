package mypackage.controller;

import mypackage.model.User;

public final class Session
{
    private static User currentUser;

    private Session()
    {
    }

    public static User getCurrentUser()
    {
        return currentUser;
    }

    public static String getCurrentUserID()
    {
        return currentUser.getUserID();
    }

    public static void setCurrentUser(User u)
    {
        currentUser = u;
    }
}

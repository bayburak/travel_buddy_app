package mypackage.controller;

import javax.swing.*;

import mypackage.model.User;
import mypackage.view.profile;

import java.awt.*;
import java.io.IOException;

public class ProfileController {

    public void open(JFrame host) throws IOException 
    {
        User currentUser = Session.getCurrentUser();
        profile profile = new profile(currentUser);
    }
}

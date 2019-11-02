package com.example.uoftlife;


import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class UserManager {
    private static Map<String, User> users;
    private static final String FILENAME = "users.dat";
    private static User currentUser;
    private UserManager() {

    }



    public static void loadUsers(Context context){
        try {
            InputStream inputStream = context.openFileInput(FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                users = (HashMap<String, User>) input.readObject();
                inputStream.close();
            }
        } catch (Exception e) {
            users = new HashMap<>();
        }
    }

    static void saveToFile(Context context){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(FILENAME, Context.MODE_PRIVATE));

            outputStream.writeObject(users);
            outputStream.close();
            for (Map.Entry<String,User> entry : users.entrySet())
                Log.e("user", "save " + entry.getKey());

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    static boolean checkUserExist(String username) {
        return users.containsKey(username);
    }

    //login
    static User authenticate(String username, String password){
        if (!checkUserExist(username)) {
            return null;
        }
        if(!users.get(username).checkPassword(password)) {
            return null;
        }
        currentUser = users.get(username);
        return currentUser;
    }

    // sign in
    static User signUp(String username, String password) {
        if (checkUserExist(username)) {
            return null;
        }
        currentUser= new User(username, password, username.hashCode());
        users.put(currentUser.getUsername(), currentUser);
        return currentUser;
    }

    static User getCurrentUser(){
        return currentUser;
    }


}

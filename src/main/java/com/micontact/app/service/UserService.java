package com.micontact.app.service;

import com.github.cliftonlabs.json_simple.Jsoner;
import com.micontact.app.controller.LoginPayload;
import com.micontact.app.model.Contact;
import com.micontact.app.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserService  {
    private JSONArray usersData = new JSONArray();
    private JSONArray contactsData = new JSONArray();

    public List<User> listAllUser() {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < usersData.length(); i++) {
            JSONObject item = usersData.getJSONObject(i);
            User aUser = new User(item.getString("name"), item.getString("username"), item.getString("phone"), item.getString("id"));
            aUser.setPassword(item.getString("password"));
            userList.add(aUser);
        }
        return userList;    
    }

    public List<Contact> listAllUserContacts(String userId) {
        List<Contact> contactList = new ArrayList<Contact>();
        for (int i = 0; i < contactsData.length(); i++) {
            JSONObject item = contactsData.getJSONObject(i);
            if (item.getString("userId").equals(userId)) {
                Contact aContact = new Contact(item.getString("name"), item.getString("phone"), item.getString("userId"));
                contactList.add(aContact);
            }
        }
        return contactList;    
    }

    public void saveUser(User user) throws IOException {
        JSONObject newUserObj = new JSONObject();
        int newUserId = usersData.length() + 1;
        newUserObj.put("id", newUserId + "");
        newUserObj.put("name", user.getName());
        newUserObj.put("password", user.getPassword());
        newUserObj.put("phone", user.getPhone());
        newUserObj.put("username", user.getUsername());
        usersData.put(newUserObj);
        this.persistUsersData();
    }

    public void saveUserContact(Contact contact) throws IOException {
        JSONObject newContactObj = new JSONObject();
        newContactObj.put("name", contact.getName());
        newContactObj.put("phone", contact.getPhone());
        newContactObj.put("userId", contact.getUserId());
        contactsData.put(newContactObj);
        this.persistContactsData();
    }

    public void saveUserContacts(Contact[] contacts, String userId) throws IOException {
        for (int i = 0; i < contacts.length; i++) {
            JSONObject newContactObj = new JSONObject();
            newContactObj.put("name", contacts[i].getName());
            newContactObj.put("phone", contacts[i].getPhone());
            newContactObj.put("userId", contacts[i].getUserId());
            contactsData.put(newContactObj);
        }
        this.persistContactsData();
    }

    private void persistUsersData() throws IOException{
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("users.json"));
        System.out.println(usersData.toString());
        Jsoner.serialize(usersData.toString(), writer);
    }

    private void persistContactsData() throws IOException{
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("contacts.json"));
        System.out.println(contactsData.toString());
        Jsoner.serialize(contactsData.toString(), writer);
    }

    public User getUserByUsername(String username) {
        System.out.println(usersData);
        for (int i = 0; i < usersData.length(); i++) {
            JSONObject item = usersData.getJSONObject(i);
            System.out.println(item);
            System.out.println(item.getString("username"));
            System.out.println(username);
            if (item.getString("username").equals(username)) {
                System.out.println("SOOOOO TRUE");
                // String name, String username, String phone, String password
                User user = new User(
                    item.getString("name"), 
                    item.getString("username"), 
                    item.getString("phone"), 
                    item.getString("id")
                );
                user.setPassword(item.getString("password"));
                return user;
            } else {
                System.out.println("NO NO NO NO");
            }
        }
        return null;
    }

    public String login(LoginPayload loginPayload) {
        User user = this.getUserByUsername(loginPayload.username);
        if (user.getPassword().equals(loginPayload.password)){
            return new JWTService().createJWT(loginPayload);
        }
        return "User detailas are wrong";
    }

    public User getUser(Integer id) {
        for (int i = 0; i < usersData.length(); i++) {
            JSONObject item = usersData.getJSONObject(i);
            if (item.getString("id") == id.toString()) {
                // String name, String username, String phone, String password
                return new User(
                    item.getString("name"), 
                    item.getString("username"), 
                    item.getString("phone"), 
                    item.getString("id")
                );
            }
        }
        return null;
    }

    public void deleteUser(Integer id) {
        // userRepository.deleteById(id);
    } 
}

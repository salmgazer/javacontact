package com.micontact.app.controller;

import com.micontact.app.model.Contact;
import com.micontact.app.model.User;
import com.micontact.app.service.JWTService;
import com.micontact.app.service.UserService;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/app/v1")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/contacts")
    public List<User> list(@RequestHeader (name="Authorization") String token) {
        return userService.listAllUser();
        // Boolean confirmUser = new JWTService().confirm(token);
        // if (confirmUser == true) {
        //     return userService.listAllUser();
        // }
        // return null;
    }

    @GetMapping("/contacts/{id}")
    public List<Contact> userContacts(@PathVariable String id) {
        return userService.listAllUserContacts(id);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) throws IOException {
        userService.saveUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginPayload loginPayload) {
        return userService.login(loginPayload);
    }

    @PostMapping("/contacts/add/{id}")
    public void addContact(@RequestBody Contact contact, @PathVariable String id) throws IOException {
        contact.setUserId(id);
        userService.saveUserContact(contact);
    }

    @RequestMapping(path = "/contacts/add/batch/{id}", method = RequestMethod.POST, consumes = { org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE })
    public void addContacts(@ModelAttribute Contact[] contacts, @PathVariable String id) throws IOException {
        System.out.println(contacts);
        userService.saveUserContacts(contacts, id);
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable String id) throws IOException {
        try {
            user.setId(id);            
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
package com.example.restful.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userCnt = 3;

    static{
        users.add(new User(1, "Jong", new Date(),"init123!!", "701010-1111111" ));
        users.add(new User(2, "magicPark", new Date(),"pass2!!", "701010-1111111" ));
        users.add(new User(3, "mememenu", new Date(),"pass3!!", "701010-1111111" ));
    }

    public List<User> findAll(){
        return users;
    }

    public User findUser(int id){
        for (User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public User inputUser(User user){
        if(user.getId() == null){
            user.setId(++userCnt);
        }
        users.add(user);
        return user;
    }

    public User deleteById(int id){
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}

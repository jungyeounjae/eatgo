package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    public User registerUser(String email, String name, String password) {


        return null;
    }
}

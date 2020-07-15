package engine.Service;

import engine.Exceptions.UserAlreadyExistsException;
import engine.Model.UserEntity;
import engine.QuizDao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService
{
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void insertNewUser(UserEntity userEntity)
    {
        if(userDao.findByEmail(userEntity.getEmail()).isEmpty())
        {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userDao.save(userEntity);
        } else {
            throw new UserAlreadyExistsException();
        }

    }

    public UserEntity getByEmail(String email)
    {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public List<UserEntity> findAllUsers()
    {
        return userDao.findAll();
    }

    public void deleteAllByUser(Authentication auth)
    {
        userDao.deleteById(auth.getName());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return User.withUsername(username)
                .password(getByEmail(username).getPassword())
                .roles("USER")
                .build();

    }
}

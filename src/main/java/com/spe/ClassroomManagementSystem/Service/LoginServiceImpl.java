package com.spe.ClassroomManagementSystem.Service;

import com.spe.ClassroomManagementSystem.Models.*;
import com.spe.ClassroomManagementSystem.Repository.LoginRepository;
import com.spe.ClassroomManagementSystem.Repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import javax.servlet.http.HttpSession;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public Login save(Login login){
        return loginRepository.save(login);
    }

    @Override
    public  Login findByUsernameAndPassword(String username,String password){
        return loginRepository.findByUserNameAndPassword(username,password);
    }

    @Override
    public boolean checkCredentials(String username, String password, String userType, HttpSession session) {
        if (userType.equals("admin")){
            if (username.equals("admin") && password.equals("admin")){
                return true;
            }
        }
        Login user = loginRepository.findByUserNameAndUserType(username, userType);
        System.out.println(user);

        if (user == null) {
            return false;
        } else {
            if (user.getPassword().equals(password)) {
                switch (userType) {
                    case "professor":
                        Professor professor = user.getProfessor();
                        session.setAttribute("professor", professor);
                        break;
                    case "ta":
                        TA ta = user.getTa();
                        session.setAttribute("ta", ta);
                        break;
                    case "committee":
                        Committee committee = user.getCommittee();
                        session.setAttribute("committee", committee);
                        break;
                    case "sac":
                        Sac sac = user.getSac();
                        session.setAttribute("sac", sac);
                        break;

                }
                return true;
            } else {
                return false;
            }
        }
    }
}
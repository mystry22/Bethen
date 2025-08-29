package com.bethen.bethen.services;

import com.bethen.bethen.dto.*;
import com.bethen.bethen.interfaces.AdminInter;
import com.bethen.bethen.models.AdminModel;
import com.bethen.bethen.models.MemberModel;
import com.bethen.bethen.models.TransactionsModel;
import com.bethen.bethen.repos.AdminRepo;
import com.bethen.bethen.repos.MembersRepo;
import com.bethen.bethen.repos.TransactionRepo;
import com.bethen.bethen.util.Helper;
import com.bethen.bethen.util.JwtObjectForGen;
import com.bethen.bethen.util.JwtUtil;
import com.bethen.bethen.util.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService implements AdminInter {
    @Autowired
    private AdminRepo adminRepo;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MembersRepo membersRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private MailingService mailingService;

    //Create admin
    @Override
    public String createAdmin(MemberRequestDto memberRequestDto) {
        //Check if admin exists before now
        MemberModel isAdmin = membersRepo.findByEmail(memberRequestDto.getEmail()).orElse(null);

        if(isAdmin == null) {
            //map dto to model
            MemberModel memberModel = modelMapper.map(memberRequestDto, MemberModel.class);
            //convert names text to lower case
            memberModel.setFirstName(memberRequestDto.getFirstName().toLowerCase());
            memberModel.setLastName(memberRequestDto.getLastName().toLowerCase());
            //Hash password
            memberModel.setPrivateKey(encoder.encode(memberRequestDto.getUniqueKey()));
            //Add role
            memberModel.setRole(String.valueOf(Role.ADMIN));
            //save data
            membersRepo.insert(memberModel);
            //return a response
            return "new admin created";
        }
        return "admin exists";
    }

    //Get all admin
    @Override
    public List<MemberResponseDto> getAllAdmin() {
        //Find and return all admins
        Query query = new Query();
        query.addCriteria(Criteria.where("role").is("ADMIN"));

        List<MemberModel> members = mongoTemplate.find(query, MemberModel.class); // Corrected to use find()

        return members.stream()
                .map(member -> modelMapper.map(member, MemberResponseDto.class)) // Changed to AdminResponseDto
                .collect(Collectors.toList());


    }



    @Override
    public Object loginAdmin(LoginDto loginDto) {
        MemberModel memberModel = membersRepo.findByEmail(loginDto.getEmail()).orElse(null);

        if(memberModel != null) {
            if (encoder.matches(loginDto.getUniqueKey(), memberModel.getPrivateKey())) {
                JwtObjectForGen jwtObjectForGen = new JwtObjectForGen(
                        memberModel.getEmail(),
                        memberModel.getRole().toString(),
                        memberModel.getFirstName(),
                        memberModel.getLastName(),
                        memberModel.getUserId()

                );

                return jwtUtil.generateToken(jwtObjectForGen);
            }
        }
            return "admin not validated";



    }

    @Override
    public String deleteAdmin(String id) {
        //Check if user exists
        MemberModel memberModel = membersRepo.findById(id).orElse(null);

        if(memberModel != null && memberModel.getRole().equals("ADMIN") ){
            membersRepo.deleteById(id);
            return "user deleted";

        }else {

            return  "user does not exist";
        }



    }

    @Override
    public String addFundsToUser(String amount, String email) {
        //get user details
        MemberModel memberModel = membersRepo.findByEmail(email).orElse(null);

        if(memberModel != null){
            double newBal = memberModel.getBalance() + Double.parseDouble(amount);

            memberModel.setBalance(newBal);
            membersRepo.save(memberModel);

            //Add to transactions table
            TransactionsModel transactionsModel = new TransactionsModel();
            transactionsModel.setAmount(amount);
            transactionsModel.setUserId(memberModel.getUserId());
            transactionsModel.setReference(Helper.generateReference());
            transactionsModel.setStatus("success");
            transactionsModel.setType("ROI");
            transactionsModel.setCreatedAt(Helper.generateTodayDateAndTime(),Helper.dateTimeFormatter());

            transactionRepo.insert(transactionsModel);

            //notify user
            mailingService.notificationOfROIPayment(memberModel.getFirstName(),amount,Helper.generateTodayDateAndTime().toString(),email);

            return "balance updated";
        }else {
            return "no user found";
        }

    }


}

package com.bethen.bethen.services;

import com.bethen.bethen.dto.ChangePasswordRequestDto;
import com.bethen.bethen.dto.LoginDto;
import com.bethen.bethen.dto.MemberRequestDto;
import com.bethen.bethen.dto.MemberResponseDto;
import com.bethen.bethen.interfaces.MemberInter;
import com.bethen.bethen.models.MemberModel;
import com.bethen.bethen.models.UserDetailsModel;
import com.bethen.bethen.repos.MembersRepo;
import com.bethen.bethen.util.Helper;
import com.bethen.bethen.util.JwtObjectForGen;
import com.bethen.bethen.util.JwtUtil;
import com.bethen.bethen.util.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.*;

@Service
public class MemberService implements MemberInter {

    @Autowired
    private MembersRepo membersRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MongoTemplate mongoTemplate;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    //Get all members
    @Override
    public List<MemberResponseDto> getAllMembers() {
        Query query = new Query();
        query.addCriteria(Criteria.where("role").is("BASIC"));

        List<MemberModel> members = mongoTemplate.find(query,MemberModel.class);

        return members.stream()
                .map(member -> modelMapper.map(member, MemberResponseDto.class))
                .collect(Collectors.toList());

//        return membersRepo.findAll()
//                .stream().map(member -> modelMapper.map(member, MemberResponseDto.class))
//                .collect(Collectors.toList());
    }

    //Get member by ID
    public MemberResponseDto getMemberById(String id){
        Optional<MemberModel> memberModel = membersRepo.findById(id);
        if(memberModel.isEmpty()){
            return null;
        }

        return modelMapper.map(memberModel, MemberResponseDto.class);
    }

    //Create new member
    @Override
    public MemberResponseDto createNewmember(MemberRequestDto memberRequestDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //Check if user already exists.
        MemberModel isExist = membersRepo.findByEmail(memberRequestDto.getEmail()).orElse(null);
        //hash password
        if(isExist == null) {
            //encode password
            memberRequestDto.setUniqueKey(encoder.encode(memberRequestDto.getUniqueKey()));
            //map response data into member model
            MemberModel memberModel = modelMapper.map(memberRequestDto, MemberModel.class);
            //Assign role
            memberModel.setRole(String.valueOf(Role.BASIC));

            //Add creation date
            memberModel.setRegDate(Helper.generateTodayDateAndTime(), formatter);
            //save data
            MemberModel savedData = membersRepo.insert(memberModel);

            //map data save into response model
            return modelMapper.map(savedData, MemberResponseDto.class);
        }

        return null;

    }

    //Delete member by ID
    @Override
    public String deleteMember(String id) {
        // Check if user exists
        Optional<MemberModel> memberModel = membersRepo.findById(id);
        if(memberModel.isPresent()){
            membersRepo.deleteById(id);
            return "deleted";
        }
       return "not exist";
    }

    @Override
    public String changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        //check if user exists
        MemberModel isUser = membersRepo.findById(changePasswordRequestDto.getUserId()).orElse(null);

        if(isUser != null) {
            //update user
            isUser.setPrivateKey(encoder.encode(changePasswordRequestDto.getUniqueKey()));
            membersRepo.save(isUser);
            return "user password updated";
        }
        return "no user found";
    }



    @Override
    public Object loginUser(LoginDto loginDto) {
        //Check if user exists
        MemberModel memberModel = membersRepo.findByEmail(loginDto.getEmail()).orElse(null);
        if(memberModel != null){
            //Map to response DTO
            MemberResponseDto memberResponseDto = modelMapper.map(memberModel, MemberResponseDto.class);

            //Validate password
            if(encoder.matches(loginDto.getUniqueKey(), memberModel.getPrivateKey())){
                //Map response to JWTObjectGen Claim class

                JwtObjectForGen jwtObjectForGen = new JwtObjectForGen(memberResponseDto.getEmail(),memberResponseDto.getRole(),
                        memberResponseDto.getFirstName(), memberResponseDto.getLastName(), memberResponseDto.getUserId());
                //Generate token
                return jwtUtil.generateToken(jwtObjectForGen);

                //return modelMapper.map(memberModel, MemberResponseDto.class);
            }
        }

        return "wrong credential";
    }

    @Override
    public MemberModel getUserByEmail(String email) {
        MemberModel memberModel = membersRepo.findByEmail(email).orElse(null);

        if(memberModel == null){
            return null;
        }else {
          return  memberModel;

        }

    }

    @Override
    public Optional<UserDetailsModel> getUserDetailsViaToken(String token) {
        Object claims = jwtUtil.getTotalClaims(token);
        UserDetailsModel AllClaims =modelMapper.map(claims, UserDetailsModel.class );
        return Optional.ofNullable(AllClaims);

    }


}

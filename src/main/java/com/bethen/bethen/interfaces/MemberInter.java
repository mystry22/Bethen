package com.bethen.bethen.interfaces;

import com.bethen.bethen.dto.ChangePasswordRequestDto;
import com.bethen.bethen.dto.LoginDto;
import com.bethen.bethen.dto.MemberRequestDto;
import com.bethen.bethen.dto.MemberResponseDto;
import com.bethen.bethen.models.MemberModel;
import com.bethen.bethen.models.UserDetailsModel;

import java.util.List;
import java.util.Optional;

public interface MemberInter {

    public List<MemberResponseDto> getAllMembers();
    public  MemberResponseDto createNewmember(MemberRequestDto memberRequestDto);
    public  String deleteMember(String id);
    public String changePassword(ChangePasswordRequestDto changePasswordRequestDto);
    public Object loginUser(LoginDto loginDto);
    public MemberModel getUserByEmail(String email);
    public Optional<UserDetailsModel> getUserDetailsViaToken(String token);
}

package com.bethen.bethen.interfaces;

import com.bethen.bethen.dto.*;

import java.util.List;

public interface AdminInter {
    public String createAdmin(MemberRequestDto memberRequestDto);
    public List<MemberResponseDto> getAllAdmin();
    public Object loginAdmin(LoginDto loginDto);
    public String deleteAdmin(String id);
    public String addFundsToUser(String amount, String email);
}

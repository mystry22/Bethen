package com.bethen.bethen.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Helper {

    final static String rand = "q0w1er3ty5u6iop4lk9jhgfd8sazx7cvb2nm";

    public static String generateReference(){
        String def = "BETH";

        return  def +"_"+ generateReferenceBody();
    }

    public static String generateInvestmentReference(){
        String def = "INV";
        return def+"_"+generateReferenceBody();
    }

    private static String generateReferenceBody(){
        StringBuilder generatedRef = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i<=10; i++){
            int randomInt = random.nextInt(rand.length());
            char randomChar = rand.charAt(randomInt);
            generatedRef.append(randomChar);
        }

        return generatedRef.toString();
    }

    public static LocalDateTime generateTodayDateAndTime(){
        return LocalDateTime.now();
    }

    public static DateTimeFormatter dateTimeFormatter(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter;
    }
}

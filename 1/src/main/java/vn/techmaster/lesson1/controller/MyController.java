package vn.techmaster.lesson1.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.techmaster.lesson1.model.Student;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MyController {

    @GetMapping(value = "/ramdom")
    @ResponseBody
public String getRamdom(){
    char[] c = new char[62];
        for (int i = 0; i < c.length; i++) {
            if (i<10){ //thêm 0-9
                c[i] = (char) ((char) 48+i);
            }else if (i>=10 & i<36){ // thêm A-Z
                c[i] = (char) ((char) 55+i);
            }else{ // thêm a-z
                c[i] = (char) ((char) 61+i);
            }
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <8 ; i++) {
            int random = (int)(Math.random()*((c.length-0)+1))+0;// tạo số random từ 0 - length
            sb.append(c[random]);
        }
    return "Ký tự random : " + sb.toString();
}

    @GetMapping(value = "/quote")
    @ResponseBody
public String getQuote(){
        String[] s = {"Kiến tha lâu đầy tổ","Có công mài sắt, có ngày nên kim","Không thầy đố mày làm nên","Học thầy không tày học bạn"};
        String result = "";
    for (int i = 0; i < s.length; i++) {
        int random = (int)(Math.random()*((s.length-0)+1))+0;// tạo số random từ 0 - length
        result = s[random];
    }
        return "Tục ngữ : " + result;
}

    @PostMapping(value = "/bmi")
    @ResponseBody
    public double getBmi(@RequestParam double weight, @RequestParam double height){
    double bmi = (double) weight/(double) (Math.pow(height,2));
    return Math.ceil(bmi*100.0)/100.0;
    }



    @GetMapping(value = "/student")
    @ResponseBody
    public List<Student> getStudent(){
    List<Student> myList = new ArrayList<>();
    myList.add(new Student(1,"Vũ",1993));
    myList.add(new Student(2,"Thành",2003));
    myList.add(new Student(3,"Sơn",2000));
    myList.add(new Student(4,"Kiên",1995));
        return myList;
    }

    @PostMapping(value = "/student")
    @ResponseBody
    public List<Student> postStudent(@RequestParam int id, @RequestParam String name, @RequestParam int year){
        List<Student> myList = new ArrayList<>();
        myList.add(new Student(1,"Vũ",1993));
        myList.add(new Student(2,"Thành",2003));
        myList.add(new Student(3,"Sơn",2000));
        myList.add(new Student(4,"Kiên",1995));
        myList.add(new Student(id,name,year));
        return myList;
    }
}

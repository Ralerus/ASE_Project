package layer.presentation;

import layer.Application;
import layer.data.Player;

public class LoginUI {
    public static void displayLogin(){
        LoginUI.displayLoginFor(null);
    }

    public static void displayLoginFor(Player p){
        String username;
        if(p!=null){
            //set username field to readonly with value p.getUsername();
            username = p.getUsername();
        }else{
            username = "";
        }

        //display login and receive username and password
        String password="";
        System.out.println("Please login "+username);
        //hash password

        if(Application.getSession().login(username,password)){
            //success
            System.out.println("Succesfully logged in");
        }else{
            //show error message
        }
    }
}

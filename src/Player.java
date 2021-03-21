public class Player {
    private String username;
    private String password;
    private boolean isLoggedIn;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.isLoggedIn = false;
    }
    public Player(String username, char[] password) {
        this.username = username;
        this.password = new String(password);
        this.isLoggedIn = false;
    }
    public Player(String username){
        this.username = username;
        this.isLoggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean login(){
        if(!this.isLoggedIn){
            this.isLoggedIn = true;
        }
        return this.isLoggedIn;
    }

    public boolean loginForGame(){
        if(!this.isLoggedIn){
            App.getGameUI().displayLoginForGame(this);
            //while(!App.getLoggedInPlayer().getUsername().equals(this.username)){
                //wait for user to login
            //}
            this.isLoggedIn = true;
        }
        return this.isLoggedIn;
    }

    public void logoff(){
        this.isLoggedIn = false;
    }
}

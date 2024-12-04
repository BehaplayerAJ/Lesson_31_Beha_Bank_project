public interface BankInterface {
    public void addAdmin();
    public void signup();
    public boolean signin();
    public void removeUser();
    public void showUsers();
    public String getRole(String nickname, String password);
}

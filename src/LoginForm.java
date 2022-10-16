import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog {
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel LoginPanel;
    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        setMaximumSize(new Dimension(230, 450));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());
                user = getauthenticatedUser(email, password);
                if (user != null) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid",
                            "try Again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
    public static User user;
private User getauthenticatedUser(String email,String password){
        User user=null;
   final String url="jdbc:mysql://localhost:3306/login";
   final String Username="root";
   final String Password="";
   try{

       Connection conn= DriverManager.getConnection(url,Username,Password);
       Statement statement=conn.createStatement();
       String sql="SELECT * FROM users WHERE email=? AND password=?";
       PreparedStatement preparedStatement=conn.prepareStatement(sql);
       preparedStatement.setString(1,email);
       preparedStatement.setString(2,password);
       ResultSet resultSet=preparedStatement.executeQuery();
       if(resultSet.next()){
           user=new User();
           user.email=resultSet.getString("email");
           user.password=resultSet.getString("password");
       }
       statement.close();
       conn.close();
   }catch(Exception e){
       System.out.print(e);
   }

        return user;
}
    public static void main(String[] args) {
        LoginForm myForm =new LoginForm(null);
        User user=LoginForm.user;
        if(user!=null){
            System.out.println("successful authentication of"+" "+user.email);
            System.out.print(user.password);
        }else{
            System.out.println("Create an account");
        }
    }
}

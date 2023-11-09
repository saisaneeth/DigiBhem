
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
* the main class
* @author
*
*/
class MainATM1 {

public static void main(String[] args) {
// create the ATM frame
ATM atm = new ATM();
// set size and location
atm.setSize(700,400);
atm.setLocation(100,100);
// make it visible
atm.setVisible(true);
}

}


/**
* class for implementing the ATM
* @author
*
*/
class ATM extends JFrame{
// the english language id
public static int LANG_ENGLISH = 0;
// the english language id
public static int LANG_SPANISH = 1;
// the english language id
public static int LANG_FRENCH = 2;


// keeps the language
private int language = LANG_ENGLISH;
// messages class
private Messages messages;
// the panel for the language screen
private JPanel languagePanel;
// the panel for the pin screen
private JPanel passwordPanel;
// the text field where the user enters the pin
private JPasswordField password;
// the panel for the main screen
private JPanel mainPanel;
// the panel for choosing the account, savings or checkings
private JPanel savingsOrCheckingPanel;
// keeps the current operation: deposit, withdraw, transfer ...
private int currentOperation;
// keeps the current account: 0=checking 1=savings
private int currentAccount;
// the panel for the deposit screen
private JPanel depositPanel;
// the text field where the user will enter the amount to be deposited
private JTextField amountField;
// the bank account
private BankAccount bankAccount;
// the panel for the screen where user is asked if he wants other transaction
private JPanel makeNextPanel;
// the panel for the screen where the balance is displayed
private JPanel balancePanel;
// the panel for the final screen
private JPanel finalPanel;

/**
* public constructor: initialize the objects and call the show first screen method
*
*/
public ATM(){
super("ATM");
messages = new Messages();
bankAccount = new BankAccount();
showMenu(null);
this.addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e) {
System.exit(0);
}
});
}

/**
* method for the screen where the language is selected
* @param panelToRemove the panel to be removed from the main panel
*/
private void showMenu(JPanel panelToRemove) {
// remove the previous panel if exists
if(panelToRemove!=null)
this.remove(panelToRemove);
// set default language to english
language = LANG_ENGLISH;
// create the label
Icon bug = new ImageIcon("bug1.gif");

JLabel label3 = new JLabel();
label3.setText( "INTERNATIONAL BANK OF JAVA" );
label3.setIcon(bug);
label3.setForeground(Color.GREEN);





// create the label
JLabel label = new JLabel(messages.getMessage("SELECT_LANG",language));
// create the buttons for english and spanish and french
JButton eng = new JButton("English");
// create the action listener which will respond to the actions of pressing the buttons
ActionListener list = new ActionListener(){
public void actionPerformed(ActionEvent e) {
if(e.getActionCommand().equals("English")){
ATM.this.language = ATM.LANG_ENGLISH;
}else if(e.getActionCommand().equals("Espanol")){
ATM.this.language = ATM.LANG_SPANISH;
}else if(e.getActionCommand().equals("Francais")){
ATM.this.language = ATM.LANG_FRENCH;
}else if(e.getActionCommand().equals("Cancel")){
showFinalPanel(languagePanel);
return;
}
showPinMenu(languagePanel,"");
}
};
eng.addActionListener(list);

JButton span = new JButton("Espanol");
span.addActionListener(list);
JButton fran = new JButton("francais");
fran.addActionListener(list);



// create the panel
languagePanel = new JPanel();
languagePanel.setBackground(Color.black);
label.setForeground(Color.WHITE);
languagePanel.setSize(new Dimension(150,150));
languagePanel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
// add the label and the buttons to the panel
c.gridx = 0;
c.gridy = 0;
c.insets = new Insets(10,10,10,10);
languagePanel.add(label3,c);
c.gridy = 1;
c.gridx = 0;
languagePanel.add(label,c);
c.gridy = 2;
languagePanel.add(eng,c);
c.gridy = 3;
languagePanel.add(span,c);
c.gridy = 4;
languagePanel.add(fran,c);
JButton cancel = new JButton("Cancel");
cancel.addActionListener(list);
c.gridy = 5;
languagePanel.add(cancel,c);
// add the panel to the main frame
this.getContentPane().setLayout(new GridBagLayout());
c.gridx = 1;
c.gridy = 1;
c.fill = GridBagConstraints.BOTH;
c.weightx = 150;
c.weighty = 150;
this.getContentPane().add(languagePanel,c);
this.setVisible(true);
}


/**
* method for creating and displaying the screen where the user will enter the PIN
* @param panelToRemove
* @param errorMessage
*/
protected void showPinMenu(JPanel panelToRemove, String errorMessage) {
// remove the previous panel
if(panelToRemove!=null)
this.remove(panelToRemove);
// create the label, the text field and the buttons
JLabel label = new JLabel(messages.getMessage("ENTER_PIN",language));
password = new JPasswordField(5);
password.setFocusable(true);
password.requestFocusInWindow();
JButton ok = new JButton(messages.getMessage("OK",language));
// create the action listener which will respond to the actions of pressing the buttons
ActionListener list = new ActionListener(){
public void actionPerformed(ActionEvent e) {
String pin = new String(ATM.this.password.getPassword());
if(pin.equals("1234")){
showMainMenu(passwordPanel);
}else{
showPinMenu(passwordPanel,"error");
}
}
};
ok.addActionListener(list);
// create the cancel button
JButton cancel = new JButton(messages.getMessage("CANCEL",language));
cancel.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e) {
showMenu(passwordPanel);
}
});
// create the panel
passwordPanel = new JPanel();
passwordPanel.setBackground(Color.black);
label.setForeground(Color.WHITE);
passwordPanel.setSize(new Dimension(150,150));
passwordPanel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
c.gridx = 1;
c.gridy = 0;
// add to panel the labels, the text field and the buttons
c.insets = new Insets(10,10,10,10);
if(!errorMessage.equals("")){
JLabel error = new JLabel(messages.getMessage("WRONG_PIN",language));
error.setForeground(Color.red);
passwordPanel.add(error,c);
}
c.gridy = 1;
passwordPanel.add(label,c);
c.gridy = 2;
passwordPanel.add(password,c);
c.gridx = 2;
c.gridy = 3;
passwordPanel.add(ok,c);
c.gridx = 0;
passwordPanel.add(cancel,c);
// add the panel to the main frame
this.getContentPane().setLayout(new GridBagLayout());
c.gridx = 1;
c.gridy = 1;
c.fill = GridBagConstraints.BOTH;
c.weightx = 150;
c.weighty = 150;
this.getContentPane().add(passwordPanel,c);
password.setFocusable(true);
password.requestFocusInWindow();
getRootPane().setDefaultButton(ok);
this.setVisible(true);
}

/**
* the method for creating and showing the main screen
* @param panelToRemove
*/
protected void showMainMenu(JPanel panelToRemove) {
// remove the previous panel
if(panelToRemove!=null)
this.remove(panelToRemove);
// create the action listener which will respond to the actions of pressing the buttons
ActionListener list = new ActionListener(){
public void actionPerformed(ActionEvent e) {
String command = e.getActionCommand();
if(command.equals(messages.getMessage("MAKE_DEPOSIT",language))){
showAskSavingsOrChecking(mainPanel,0);
}else if(command.equals(messages.getMessage("WITHDRAW",language))){
showAskSavingsOrChecking(mainPanel,1);
}else if(command.equals(messages.getMessage("GET_BALANCE",language))){
showAskSavingsOrChecking(mainPanel,2);
}else if(command.equals(messages.getMessage("TRANSFER",language))){
showAskSavingsOrChecking(mainPanel,3);
}else if(command.equals(messages.getMessage("CANCEL",language))){
showFinalPanel(mainPanel);
}
}
};
// create the label
JLabel label = new JLabel(messages.getMessage("SELECT_TRANSACT",language));
// create the buttons
JButton deposit = new JButton(messages.getMessage("MAKE_DEPOSIT",language));
deposit.addActionListener(list);
JButton withdraw = new JButton(messages.getMessage("WITHDRAW",language));
withdraw.addActionListener(list);
JButton balance = new JButton(messages.getMessage("GET_BALANCE",language));
balance.addActionListener(list);
JButton transfer = new JButton(messages.getMessage("TRANSFER",language));
transfer.addActionListener(list);
JButton cancel = new JButton(messages.getMessage("CANCEL",language));
cancel.addActionListener(list);
// create the panel
mainPanel = new JPanel();
mainPanel.setBackground(Color.black);
label.setForeground(Color.WHITE);
mainPanel.setSize(new Dimension(150,150));
mainPanel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
// add the label and buttons to the panel
c.gridx = 1;
c.gridy = 0;
c.insets = new Insets(10,10,10,10);
mainPanel.add(label,c);
c.gridy = 2;
mainPanel.add(deposit,c);
c.gridy = 3;
mainPanel.add(withdraw,c);
c.gridy = 4;
mainPanel.add(balance,c);
c.gridy = 5;
mainPanel.add(transfer,c);
c.gridy = 6;
mainPanel.add(cancel,c);
// add the panel to the main frame
this.getContentPane().setLayout(new GridBagLayout());
c.gridx = 1;
c.gridy = 1;
c.fill = GridBagConstraints.BOTH;
c.weightx = 150;
c.weighty = 150;
this.getContentPane().add(mainPanel,c);
this.setVisible(true);
}

/**
* create and display the screen where user selects the account, savings or checking
* @param panelToRemove
* @param operation
*/
public void showAskSavingsOrChecking(JPanel panelToRemove,int operation){
// remove the previous panel
if(panelToRemove!=null)
this.remove(panelToRemove);
// set the current operation
currentOperation = operation;
// create the action listener which will respond to the actions of pressing the buttons
ActionListener list = new ActionListener(){
public void actionPerformed(ActionEvent e) {
String command = e.getActionCommand();
int account = 0;
if(command.equals(messages.getMessage("CHECKING",language))){
account = 0;
}else if(command.equals(messages.getMessage("SAVINGS",language))){
account = 1;
}else if(command.equals(messages.getMessage("FROM_CHECKING",language))){
account = 0;
}else if(command.equals(messages.getMessage("FROM_SAVINGS",language))){
account = 1;
}else if(command.equals(messages.getMessage("CANCEL",language))){
showMainMenu(savingsOrCheckingPanel);
return;
}
if(ATM.this.currentOperation == 0)
showDeposit(savingsOrCheckingPanel,account);
else if(ATM.this.currentOperation == 1)
showWithdraw(savingsOrCheckingPanel,account);
else if(ATM.this.currentOperation == 2)
showBalance(savingsOrCheckingPanel,account);
else if(ATM.this.currentOperation == 3)
showTransfer(savingsOrCheckingPanel,account);
}
};
// create the label
JLabel label = new JLabel(messages.getMessage("SELECT_ACCOUNT",language));
// create the buttons
JButton checking = new JButton(messages.getMessage("CHECKING",language));
if(operation == 3){
checking.setText(messages.getMessage("FROM_CHECKING",language));
}
checking.addActionListener(list);
JButton savings = new JButton(messages.getMessage("SAVINGS",language));
if(operation == 3){
savings.setText(messages.getMessage("FROM_SAVINGS",language));
}
savings.addActionListener(list);
JButton cancel = new JButton(messages.getMessage("CANCEL",language));
cancel.addActionListener(list);
// create the panel
savingsOrCheckingPanel = new JPanel();
savingsOrCheckingPanel.setBackground(Color.black);
label.setForeground(Color.WHITE);
savingsOrCheckingPanel.setSize(new Dimension(150,150));
savingsOrCheckingPanel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
// add the label and buttons to the panel
c.gridx = 1;
c.gridy = 0;
c.insets = new Insets(10,10,10,10);
savingsOrCheckingPanel.add(label,c);
c.gridy = 2;
savingsOrCheckingPanel.add(checking,c);
c.gridy = 3;
savingsOrCheckingPanel.add(savings,c);
c.gridy = 4;
savingsOrCheckingPanel.add(cancel,c);
// add the panel to the frame
this.getContentPane().setLayout(new GridBagLayout());
c.gridx = 1;
c.gridy = 1;
c.fill = GridBagConstraints.BOTH;
c.weightx = 150;
c.weighty = 150;
this.getContentPane().add(savingsOrCheckingPanel,c);
this.setVisible(true);
}

/**
* create and display the screen for the transfer operation
* @param panelToRemove
* @param account
*/
protected void showTransfer(JPanel panelToRemove, int account) {
// remove the previous panel
if(panelToRemove!=null)
this.remove(panelToRemove);
currentAccount = account;
// create the action listener which will respond to the actions of pressing the buttons
ActionListener list = new ActionListener(){
public void actionPerformed(ActionEvent e) {
String command = e.getActionCommand();
int amount = 0;
if(command.equals("$100")){
amount = 100;
}else if(command.equals("$200")){
amount = 200;
}else if(command.equals("$300")){
amount = 300;
}else if(command.equals("$20")){
amount = 20;
}else if(command.equals("$40")){
amount = 40;
}else if(command.equals("$80")){
amount = 80;
}else if(command.equals(messages.getMessage("OK",language))){
try{
amount = Integer.parseInt(ATM.this.amountField.getText());
}catch(NumberFormatException nf){
return;
}
}else if(command.equals(messages.getMessage("CANCEL",language))){
showMainMenu(depositPanel);
return;
}
if(currentAccount==0){
bankAccount.setCheckingBalance(bankAccount.getCheckingBalance()-amount);
bankAccount.setSavingsBalance(bankAccount.getSavingsBalance()+amount);
}else if(currentAccount==1){
bankAccount.setCheckingBalance(bankAccount.getCheckingBalance()+amount);
bankAccount.setSavingsBalance(bankAccount.getSavingsBalance()-amount);
}
showMakeOtherTransaction(depositPanel);
}
};
// create the labels
JLabel label = new JLabel(messages.getMessage("TRANSFER",language));
JLabel label1 = new JLabel(messages.getMessage("SELECT_AMOUNT",language));
// create the buttons
JButton amount100 = new JButton("$100");
amount100.addActionListener(list);
JButton amount200 = new JButton("$200");
amount200.addActionListener(list);
JButton amount300 = new JButton("$300");
amount300.addActionListener(list);
JButton amount20 = new JButton("$20");
amount20.addActionListener(list);
JButton amount40 = new JButton("$40");
amount40.addActionListener(list);
JButton amount80 = new JButton("$80");
amount80.addActionListener(list);
JButton ok = new JButton(messages.getMessage("OK",language));
ok.addActionListener(list);
JButton cancel = new JButton(messages.getMessage("CANCEL",language));
cancel.addActionListener(list);
// create the text field where user enters the amount
amountField = new JTextField(10);
// create the panel
depositPanel = new JPanel();
depositPanel.setBackground(Color.black);
label.setForeground(Color.WHITE);
depositPanel.setSize(new Dimension(150,150));
depositPanel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
// add the labels, buttons and text field to panel
c.gridx = 1;
c.gridy = 0;
c.insets = new Insets(10,10,10,10);
depositPanel.add(label,c);
c.gridx = 1;
c.gridy = 1;
depositPanel.add(label,c);
c.gridx = 0;
c.gridy = 2;
depositPanel.add(amount100,c);
c.gridy = 3;
depositPanel.add(amount200,c);
c.gridy = 4;
depositPanel.add(amount300,c);
c.gridy = 5;
depositPanel.add(cancel,c);
c.gridx = 1;
c.gridy = 2;
depositPanel.add(amountField,c);
c.gridx = 2;
c.gridy = 2;
depositPanel.add(amount20,c);
c.gridy = 3;
depositPanel.add(amount40,c);
c.gridy = 4;
depositPanel.add(amount80,c);
c.gridy = 5;
depositPanel.add(ok,c);
// add the panel to the main frame
this.getContentPane().setLayout(new GridBagLayout());
c.gridx = 1;
c.gridy = 1;
c.fill = GridBagConstraints.BOTH;
c.weightx = 150;
c.weighty = 150;
this.getContentPane().add(depositPanel,c);
amountField.setFocusable(true);
amountField.requestFocusInWindow();
this.setVisible(true);

}

/**
* create and display the screen for displaying the balance of the account
* @param panelToRemove
* @param account
*/
protected void showBalance(JPanel panelToRemove, int account) {
// remove the previous panel
if(panelToRemove!=null)
this.remove(panelToRemove);
currentAccount = account;
// create the label
JLabel label = new JLabel(messages.getMessage("ACCOUNT_BALANCE",language)
+((currentAccount==0)?messages.getMessage("CHECK",language):
messages.getMessage("SAVIN",language)));
// create the button
JButton ok = new JButton(messages.getMessage("OK",language));
ok.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e) {
showMakeOtherTransaction(balancePanel);
}
});
// create the text field and display in it the account balance
JTextField amountFieldText = new JTextField(10);
if(currentAccount == 0){
amountFieldText.setText("$ "+bankAccount.getCheckingBalance()+" ");
}else if(currentAccount == 1){
amountFieldText.setText("$ "+bankAccount.getSavingsBalance()+" ");
}
amountFieldText.setEditable(false);
// create the panel
balancePanel = new JPanel();
balancePanel.setBackground(Color.black);
label.setForeground(Color.WHITE);
balancePanel.setSize(new Dimension(150,150));
balancePanel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
// add the label, button and text field to the panel
c.gridx = 1;
c.gridy = 1;
c.insets = new Insets(10,10,10,10);
balancePanel.add(label,c);
c.gridx = 1;
c.gridy = 2;
balancePanel.add(amountFieldText,c);
c.gridx = 2;
c.gridy = 3;
balancePanel.add(ok,c);
// add the panel to the main frame
this.getContentPane().setLayout(new GridBagLayout());
c.gridx = 1;
c.gridy = 1;
c.fill = GridBagConstraints.BOTH;
c.weightx = 150;
c.weighty = 150;
this.getContentPane().add(balancePanel,c);
this.setVisible(true);
}

/**
* create and display the screen for the withdrawal operation
* @param panelToRemove
* @param account
*/
protected void showWithdraw(JPanel panelToRemove, int account) {
// remove the previous panel
if(panelToRemove!=null)
this.remove(panelToRemove);
currentAccount = account;
// create the action listener which will respond to the actions of pressing the buttons
ActionListener list = new ActionListener(){
public void actionPerformed(ActionEvent e) {
String command = e.getActionCommand();
int amount = 0;
if(command.equals("$100")){
amount = 100;
}else if(command.equals("$200")){
amount = 200;
}else if(command.equals("$300")){
amount = 300;
}else if(command.equals("$20")){
amount = 20;
}else if(command.equals("$40")){
amount = 40;
}else if(command.equals("$80")){
amount = 80;
}else if(command.equals(messages.getMessage("OK",language))){
try{
amount = Integer.parseInt(ATM.this.amountField.getText());
}catch(NumberFormatException nf){
return;
}
}else if(command.equals(messages.getMessage("CANCEL",language))){
showMainMenu(depositPanel);
return;
}
if(currentAccount==0){
bankAccount.setCheckingBalance(bankAccount.getCheckingBalance()-amount);
}else if(currentAccount==1){
bankAccount.setSavingsBalance(bankAccount.getSavingsBalance()-amount);
}
showMakeOtherTransaction(depositPanel);
}
};
// create the labels
JLabel label = new JLabel(messages.getMessage("WITHDRAW",language));
JLabel label1 = new JLabel(messages.getMessage("SELECT_AMOUNT",language));
// create the buttons
JButton amount100 = new JButton("$100");
amount100.addActionListener(list);
JButton amount200 = new JButton("$200");
amount200.addActionListener(list);
JButton amount300 = new JButton("$300");
amount300.addActionListener(list);
JButton amount20 = new JButton("$20");
amount20.addActionListener(list);
JButton amount40 = new JButton("$40");
amount40.addActionListener(list);
JButton amount80 = new JButton("$80");
amount80.addActionListener(list);
JButton ok = new JButton(messages.getMessage("OK",language));
ok.addActionListener(list);
JButton cancel = new JButton(messages.getMessage("CANCEL",language));
cancel.addActionListener(list);
// create the text field where the user enters the amount
amountField = new JTextField(10);
// create the panel
depositPanel = new JPanel();
depositPanel.setBackground(Color.black);
label.setForeground(Color.WHITE);
label1.setForeground(Color.WHITE);
depositPanel.setSize(new Dimension(150,150));
depositPanel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
// add the labels, buttons and text field to the panel
c.gridx = 1;
c.gridy = 0;
c.insets = new Insets(10,10,10,10);
depositPanel.add(label,c);
c.gridx = 1;
c.gridy = 1;
depositPanel.add(label1,c);
c.gridx = 0;
c.gridy = 2;
depositPanel.add(amount100,c);
c.gridy = 3;
depositPanel.add(amount200,c);
c.gridy = 4;
depositPanel.add(amount300,c);
c.gridy = 5;
depositPanel.add(cancel,c);
c.gridx = 1;
c.gridy = 2;
depositPanel.add(amountField,c);
c.gridx = 2;
c.gridy = 2;
depositPanel.add(amount20,c);
c.gridy = 3;
depositPanel.add(amount40,c);
c.gridy = 4;
depositPanel.add(amount80,c);
c.gridy = 5;
depositPanel.add(ok,c);
// add the panel to the main frame
this.getContentPane().setLayout(new GridBagLayout());
c.gridx = 1;
c.gridy = 1;
c.fill = GridBagConstraints.BOTH;
c.weightx = 150;
c.weighty = 150;
this.getContentPane().add(depositPanel,c);
amountField.requestFocusInWindow();
this.setVisible(true);
}


/**
* create and display the screen for the deposit operation
* @param panelToRemove
* @param account
*/
protected void showDeposit(JPanel panelToRemove, int account) {
// remove the previous panel
if(panelToRemove!=null)
this.remove(panelToRemove);
currentAccount = account;
// create the action listener which will respond to the actions of pressing the buttons
ActionListener list = new ActionListener(){
public void actionPerformed(ActionEvent e) {
String command = e.getActionCommand();
int amount = 0;
if(command.equals("$100")){
amount = 100;
}else if(command.equals("$200")){
amount = 200;
}else if(command.equals("$300")){
amount = 300;
}else if(command.equals("$20")){
amount = 20;
}else if(command.equals("$40")){
amount = 40;
}else if(command.equals("$80")){
amount = 80;
}else if(command.equals(messages.getMessage("OK",language))){
try{
amount = Integer.parseInt(ATM.this.amountField.getText());
}catch(NumberFormatException nf){
return;
}
}else if(command.equals(messages.getMessage("CANCEL",language))){
showMainMenu(depositPanel);
return;
}
if(currentAccount==0){
bankAccount.setCheckingBalance(bankAccount.getCheckingBalance()+amount);
}else if(currentAccount==1){
bankAccount.setSavingsBalance(bankAccount.getSavingsBalance()+amount);
}
showMakeOtherTransaction(depositPanel);
}
};
// create the labels
JLabel label = new JLabel(messages.getMessage("MAKE_DEPOSIT",language));
JLabel label1 = new JLabel(messages.getMessage("SELECT_AMOUNT",language));
// create the buttons
JButton amount100 = new JButton("$100");
amount100.addActionListener(list);
JButton amount200 = new JButton("$200");
amount200.addActionListener(list);
JButton amount300 = new JButton("$300");
amount300.addActionListener(list);
JButton amount20 = new JButton("$20");
amount20.addActionListener(list);
JButton amount40 = new JButton("$40");
amount40.addActionListener(list);
JButton amount80 = new JButton("$80");
amount80.addActionListener(list);
JButton ok = new JButton(messages.getMessage("OK",language));
ok.addActionListener(list);
JButton cancel = new JButton(messages.getMessage("CANCEL",language));
cancel.addActionListener(list);
// create the text field where the user should enter the amount
amountField = new JTextField(10);
// create the panel
depositPanel = new JPanel();
depositPanel.setBackground(Color.black);
label.setForeground(Color.WHITE);
label1.setForeground(Color.WHITE);
depositPanel.setSize(new Dimension(150,150));
depositPanel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
//add the buttons, labels and text field to the panel
c.gridx = 1;
c.gridy = 0;
c.insets = new Insets(10,10,10,10);
depositPanel.add(label,c);
c.gridx = 1;
c.gridy = 1;
depositPanel.add(label1,c);
c.gridx = 0;
c.gridy = 2;
depositPanel.add(amount100,c);
c.gridy = 3;
depositPanel.add(amount200,c);
c.gridy = 4;
depositPanel.add(amount300,c);
c.gridy = 5;
depositPanel.add(cancel,c);
c.gridx = 1;
c.gridy = 2;
depositPanel.add(amountField,c);
c.gridx = 2;
c.gridy = 2;
depositPanel.add(amount20,c);
c.gridy = 3;
depositPanel.add(amount40,c);
c.gridy = 4;
depositPanel.add(amount80,c);
c.gridy = 5;
depositPanel.add(ok,c);
// add the panel to the main frame
this.getContentPane().setLayout(new GridBagLayout());
c.gridx = 1;
c.gridy = 1;
c.fill = GridBagConstraints.BOTH;
c.weightx = 150;
c.weighty = 150;
this.getContentPane().add(depositPanel,c);
amountField.requestFocusInWindow();
this.setVisible(true);

}

/**
* create and display the screen for asking the user if he wants another transaction
* @param panelToRemove
*/
protected void showMakeOtherTransaction(JPanel panelToRemove) {
// remove the previous panel
if(panelToRemove!=null)
this.remove(panelToRemove);
// create the action listener which will respond to the actions of pressing the buttons
ActionListener list = new ActionListener(){
public void actionPerformed(ActionEvent e) {
String command = e.getActionCommand();
if(command.equals(messages.getMessage("OK",language))){
showPinMenu(makeNextPanel,"");
}else if(command.equals(messages.getMessage("CANCEL",language))){
showFinalPanel(makeNextPanel);
}
}
};
// create the labels
JLabel label = new JLabel(messages.getMessage("MAKE_NEXT",language));
JLabel label1 = new JLabel(messages.getMessage("MAKE_NEXT1",language));
JButton ok = new JButton(messages.getMessage("OK",language));
// create the buttons
ok.addActionListener(list);
JButton cancel = new JButton(messages.getMessage("CANCEL",language));
cancel.addActionListener(list);
// create the panel
makeNextPanel = new JPanel();
makeNextPanel.setBackground(Color.black);
label.setForeground(Color.WHITE);
label1.setForeground(Color.WHITE);
makeNextPanel.setSize(new Dimension(150,150));
makeNextPanel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
// add the labels and buttons to the panel
c.gridx = 1;
c.gridy = 0;
c.insets = new Insets(10,10,10,10);
makeNextPanel.add(label,c);
c.gridx = 1;
c.gridy = 1;
makeNextPanel.add(label1,c);
c.gridx = 0;
c.gridy = 2;
makeNextPanel.add(cancel,c);
c.gridx = 2;
c.gridy = 2;
makeNextPanel.add(ok,c);
// add the panel to the main frame
this.getContentPane().setLayout(new GridBagLayout());
c.gridx = 1;
c.gridy = 1;
c.fill = GridBagConstraints.BOTH;
c.weightx = 150;
c.weighty = 150;
this.getContentPane().add(makeNextPanel,c);
this.setVisible(true);
}

/**
* create and display the final panel
* @param panelToRemove
*/
public void showFinalPanel(JPanel panelToRemove){
// remove the previous panel
if(panelToRemove!=null)
this.remove(panelToRemove);
// create the label
JLabel label = new JLabel(messages.getMessage("THANKS",language));
// create the panel
finalPanel = new JPanel();
finalPanel.setBackground(Color.black);
label.setForeground(Color.WHITE);
finalPanel.setSize(new Dimension(150,150));
finalPanel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
// add the label to the panel
c.gridx = 1;
c.gridy = 0;
c.insets = new Insets(10,10,10,10);
finalPanel.add(label,c);
// add the panel to the main frame
this.getContentPane().setLayout(new GridBagLayout());
c.gridx = 1;
c.gridy = 1;
c.fill = GridBagConstraints.BOTH;
c.weightx = 150;
c.weighty = 150;
this.getContentPane().add(finalPanel,c);
this.setVisible(true);
}

}

/**
* class for keeping the details about the bank account: checking balance
* and savings balance
* @author
*
*/
class BankAccount {
// keeps the checking balance
private int checkingBalance;
// keeps the savings balance
private int savingsBalance;

/**
* public constructor, initiliaze the balances to 1000
*
*/
public BankAccount(){
checkingBalance = 1000;
savingsBalance = 1000;
}
/**
* @return Returns the checkingBalance.
*/
public int getCheckingBalance() {
return checkingBalance;
}
/**
* @param checkingBalance The checkingBalance to set.
*/
public void setCheckingBalance(int checkingBalance) {
this.checkingBalance = checkingBalance;
}
/**
* @return Returns the savingsBalance.
*/
public int getSavingsBalance() {
return savingsBalance;
}
/**
* @param savingsBalance The savingsBalance to set.
*/
public void setSavingsBalance(int savingsBalance) {
this.savingsBalance = savingsBalance;
}

}


/**
* class for implementing the messages for the ATM
* @author
*
*/
class Messages {

// keep the ids of the messages
private String[] ids = {"SELECT_LANG","ENTER_PIN","OK","CANCEL","WRONG_PIN",
"SELECT_TRANSACT","MAKE_DEPOSIT","WITHDRAW","GET_BALANCE",
"TRANSFER","CHECKING","SAVINGS","SELECT_ACCOUNT",
"SELECT_AMOUNT","MAKE_NEXT","MAKE_NEXT1",
"ACCOUNT_BALANCE",
"CHECK","SAVIN",
"FROM_CHECKING","FROM_SAVINGS",
"THANKS"};
// keeps the messages in english
private String[] englishMessages = {"Select language","Enter PIN and press Ok","Ok","Cancel","Wrong Pin!",
"Select transaction","Make deposit","Withdraw","Get balance",
"Transfer funds","From Checking","From Savings","Select an account",
"Select an amount","Would you like to","do another Transaction?",
"Your account balance \n for account ",
"Checking","Savings",
"From checking to savings","From savings to checking",
"Thank you, Please remove your card"};
// keeps the messages in Spanish
private String[] spanishMessages = {"Seleccione Idioma","Porfavor entre PIN \n y presione OK","Ok",
"Cancelar","Numero Equivocado!",
"Seleccione el tipo de transaccion","Deposito","Retiro","Balance",
"Transferir fondos","Cheques","Ahorros","Seleccione una cuenta",
"Seleccione una cantidad","Le gustaria ","hacer otra Transaccion?",
"Su balance de cuenta \n por cada cuenta ",
"Cheques","Ahorros",
"De Checkes a Ahorros","De Ahorros a Checkes",
"Gracias!, Porfavor, retire su tarjeta"};

// keeps the messages in French
private String[] frenchMessages = {"La langue choisie ","�crivent le code et serrent OK ","Ok",
"annulation ","code faux !",
"La transaction choisie ","font le d�p�t ","Se retirent","obtiennent le bilan",
"Transf�rez l'argent ","compte-ch�ques","compte d'�pargne","choisissent un compte",
"Choisissez-vous une quantit�","aimez-vous � ","faites-vous une autre transaction ? ",
"Votre bilan de compte \n pour le compte ",
"compte-ch�ques","compte d'�pargne",
"De la compte-ch�ques � l'�pargne","de l'�pargne � la compte-ch�ques",
"Merci, enl�vent s'il vous pla�t votre carte"};

// keeps a list of Message objects
private ArrayList messages;

/**
* the public constructor
*
*/
public Messages(){
messages = new ArrayList();
// add all messages in the arrays
addMessages();
}

/**
* for every message in the array create an Message object and add it in the messages list
*
*/
private void addMessages() {
for(int i=0;i<ids.length;i++){
messages.add(new Message(ids[i],englishMessages[i],ATM.LANG_ENGLISH));
messages.add(new Message(ids[i],spanishMessages[i],ATM.LANG_SPANISH));
messages.add(new Message(ids[i],frenchMessages[i],ATM.LANG_FRENCH));
}
}

/**
* return a message for an id and language
* @param id the id of the message
* @param language the language id
* @return the message
*/
public String getMessage(String id, int language){
for(int i=0;i<messages.size();i++){
Message msg = (Message)messages.get(i);
if(msg.getId().equals(id) && msg.getLanguage() == language)
return msg.getMessage();
else if(msg.getId().equals(id) && msg.getLanguage() == language)
return msg.getMessage();

}
return "";
}
}

/**
* class for keeping a message
* @author
*
*/
class Message {

// the id of the message
private String id;
// the message
private String message;
// the language
private int language;
/**
... * @param id id of the message
... * @param message the message
... * @param language the languages
... */
... public Message(String id, String message, int language) {
... this.id = id;
... this.message = message;
... this.language = language;
... }
... /**
... * @return Returns the id.
... */
... public String getId() {
... return id;
... }
... /**
... * @param id The id to set.
... */
... public void setId(String id) {
... this.id = id;
... }
... /**
... * @return Returns the language.
... */
... public int getLanguage() {
... return language;
... }
... /**
... * @param language The language to set.
... */
... public void setLanguage(int language) {
... this.language = language;
... }
... /**
... * @return Returns the message.
... */
... public String getMessage() {
... return message;
... }
... /**
... * @param message The message to set.
... */
... public void setMessage(String message) {
... this.message = message;
... }
... }

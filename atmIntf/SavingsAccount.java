public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(int accountNumber, String accountHolder, double interestRate) {
        super(accountNumber, accountHolder);
        this.interestRate = interestRate;
    }

    public void addInterest() {
        double interest = balance * (interestRate / 100);
        balance += interest;
    }

    @Override
    public String getAccountSummary() {
        return "Account Type   : Savings Account\n" +
               super.getAccountSummary() +
               "\nInterest Rate : " + interestRate + "%";
    }
}

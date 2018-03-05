package model;

import java.time.LocalDate;

public class Transaction {
    private LocalDate localDate;
    private int studentId;
    private int itemId;
    private int itemPrice;

    Transaction(int studentId, int itemId, int itemPrice) {
        this.studentId = studentId;
        this.itemId = itemId;
        this.itemPrice = itemPrice;
        this.localDate = LocalDate.now();
    }

    public String toString() {
        return String.format("\tDate: %tF, Student: %d, Item: %d, Price: %d", localDate, studentId, itemId, itemPrice);;
    }
}

package com.okellosoftwarez.mpesastk.Okello;

import com.google.gson.annotations.SerializedName;

public class STKPUSH {

    @SerializedName("BusinessShortCode")
    private String businessShortCode;

    @SerializedName("Password")
    private String password;

    @SerializedName("Timestamp")
    private String timeStamp;

    @SerializedName("TransactionType")
    private String transactionType;

    @SerializedName("Amount")
    private String amount;

    @SerializedName("PartyA")
    private String partyA;

    @SerializedName("PartyB")
    private String partyB;

    @SerializedName("PhoneNumber")
    private String phoneNumber;

    @SerializedName("CallBackURL")
    private String callBackUrl;

    @SerializedName("AccountReference")
    private String accountReference;

    @SerializedName("TransactionDesc")
    private String transactionDesc;

    public STKPUSH(String businessShortCode, String password, String timeStamp,
                   String transactionType, String amount, String partyA, String partyB,
                   String phoneNumber, String callBackUrl, String accountReference, String transactionDesc) {
        this.businessShortCode = businessShortCode;
        this.password = password;
        this.timeStamp = timeStamp;
        this.transactionType = transactionType;
        this.amount = amount;
        this.partyA = partyA;
        this.partyB = partyB;
        this.phoneNumber = phoneNumber;
        this.callBackUrl = callBackUrl;
        this.accountReference = accountReference;
        this.transactionDesc = transactionDesc;
    }
}

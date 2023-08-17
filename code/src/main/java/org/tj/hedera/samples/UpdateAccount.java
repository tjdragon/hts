package org.tj.hedera.samples;

import com.hedera.hashgraph.sdk.*;

import java.util.concurrent.TimeoutException;

public class UpdateAccount {
    public static void main(String[] args) throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        Client client = Client.forTestnet();
        client.setOperator(Constants.ACCOUNT_490575, Constants.PRI_KEY_490575);
        client.setDefaultMaxTransactionFee(new Hbar(100));
        client.setMaxQueryPayment(new Hbar(50));

        // New Key Pair
        PrivateKey newPrivateKey = PrivateKey.generateECDSA();

        // Create the transaction to update the key on the account
        AccountUpdateTransaction transaction = new AccountUpdateTransaction()
                .setAccountId(Constants.ACCOUNT_490575)
                .setKey(newPrivateKey.getPublicKey());

        // Sign the transaction with the old key and new key, submit to a Hedera network
        TransactionResponse txResponse = transaction.freezeWith(client).sign(Constants.PRI_KEY_490575).sign(newPrivateKey).execute(client);

        // Request the receipt of the transaction
        TransactionReceipt receipt = txResponse.getReceipt(client);

        // Get the transaction consensus status
        Status transactionStatus = receipt.status;

        System.out.println("The transaction consensus status is " +transactionStatus);
    }
}

package org.tj.hedera.samples;

import com.hedera.hashgraph.sdk.*;

import java.util.concurrent.TimeoutException;

import static org.tj.hedera.samples.Utils.log;

/**
 * https://docs.hedera.com/hedera/tutorials/token/create-and-transfer-your-first-fungible-token
 */
public class HelloStableCoin {
    public static void main(String[] args) throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        log("*** HelloStableCoin ***");

        AccountId myAccountId = AccountId.fromString("0.0.467739");
        log("AccountId: " + myAccountId);
        PrivateKey myPrivateKey = PrivateKey.fromString("3030020100300706052b8104000a04220420daf20fec209b4da654c4dc3de5c0df2951fcc726a8d90f0cc127be278ba9a650");
        log("Private Key: " + myPrivateKey);

        Client client = Client.forTestnet();
        client.setOperator(myAccountId, myPrivateKey);
        client.setDefaultMaxTransactionFee(new Hbar(100));
        client.setMaxQueryPayment(new Hbar(50));

        //Request the cost of the query
        //Check the new account's balance
        AccountBalance accountBalanceNew = new AccountBalanceQuery()
                .setAccountId(myAccountId)
                .execute(client);

        System.out.println("The new account balance is: " +accountBalanceNew.hbars);

        // CREATE FUNGIBLE TOKEN (STABLECOIN)
        final AccountId treasuryId = myAccountId;
        log("CREATE FUNGIBLE TOKEN (STABLECOIN)");
        TokenCreateTransaction tokenCreateTx = new TokenCreateTransaction()
                .setTokenName("ZODIA USD")
                .setTokenSymbol("ZUSD")
                .setTokenType(TokenType.FUNGIBLE_COMMON)
                .setDecimals(6)
                .setInitialSupply(1_000_000)
                .setTreasuryAccountId(treasuryId)
                .setSupplyType(TokenSupplyType.INFINITE)
                .setSupplyKey(myPrivateKey.getPublicKey())
                .freezeWith(client);

        //SIGN WITH TREASURY KEY
        log("SIGN WITH TREASURY KEY");
        TokenCreateTransaction tokenCreateSign = tokenCreateTx.sign(myPrivateKey);

        //SUBMIT THE TRANSACTION
        log("SUBMIT THE TRANSACTION");
        TransactionResponse tokenCreateSubmit = tokenCreateSign.execute(client);

        //GET THE TRANSACTION RECEIPT
        log("GET THE TRANSACTION RECEIPT");
        TransactionReceipt tokenCreateRx = tokenCreateSubmit.getReceipt(client);

        //GET THE TOKEN ID
        log("GET THE TOKEN ID");
        TokenId tokenId = tokenCreateRx.tokenId;

        //LOG THE TOKEN ID TO THE CONSOLE
        System.out.println("Created token with ID: " +tokenId);
        // Created token with ID: 0.0.477461
        // https://testnet.dragonglass.me/hedera/search?q=0.0.477461
    }
}

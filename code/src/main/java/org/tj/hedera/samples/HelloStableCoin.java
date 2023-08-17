package org.tj.hedera.samples;

import com.hedera.hashgraph.sdk.*;

import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import static org.tj.hedera.samples.Utils.log;

/**
 * https://docs.hedera.com/hedera/tutorials/token/create-and-transfer-your-first-fungible-token
 */
public class HelloStableCoin {
    public static void main(String[] args) throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        log("*** HelloStableCoin ***");

        AccountId myAccountId = Constants.ACCOUNT_467739;
        log("AccountId: " + myAccountId);
        PrivateKey myPrivateKey = Constants.PRI_KEY_467739;
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
        tokenCreateTx.signWith(myPrivateKey.getPublicKey(), signWithHsm());

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

    private static Function<byte[],byte[]> signWithHsm() {
        return (
               HelloStableCoin::sign
        );
    }

    private static byte[] sign(final byte[] data) {
        // Logic to call the HSM here and return the raw signature
        return new byte[]{};
    }
}

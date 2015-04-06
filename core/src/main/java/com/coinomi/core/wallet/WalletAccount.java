package com.coinomi.core.wallet;

import com.coinomi.core.coins.CoinType;
import com.coinomi.core.network.interfaces.ConnectionEventListener;
import com.coinomi.core.network.interfaces.TransactionEventListener;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBag;
import org.bitcoinj.crypto.KeyCrypter;
import org.bitcoinj.wallet.KeyBag;
import org.spongycastle.crypto.params.KeyParameter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static org.bitcoinj.wallet.KeyChain.KeyPurpose.CHANGE;
import static org.bitcoinj.wallet.KeyChain.KeyPurpose.RECEIVE_FUNDS;

/**
 * @author John L. Jegutanis
 */
public interface WalletAccount extends TransactionBag, KeyBag, TransactionEventListener, ConnectionEventListener, Serializable {

    String getId();
    String getDescription();
    void setDescription(String description);

    CoinType getCoinType();

    boolean isNew();

    void refresh();

    boolean isConnected();
    WalletPocketConnectivity getConnectivityStatus();
    /**
     * Returns the address used for change outputs. Note: this will probably go away in future.
     */
    Address getChangeAddress();

    /**
     * Get current receive address, does not mark it as used
     */
    Address getReceiveAddress();


    Map<Sha256Hash, Transaction> getUnspentTransactions();
    Map<Sha256Hash, Transaction> getPendingTransactions();
    Map<Sha256Hash, Transaction> getTransactions();

    List<Address> getActiveAddresses();
    void markAddressAsUsed(Address address);

    void setWallet(Wallet wallet);
    void walletSaveLater();
    void walletSaveNow();

    boolean isEncryptable();
    boolean isEncrypted();
    KeyCrypter getKeyCrypter();
    void encrypt(KeyCrypter keyCrypter, KeyParameter aesKey);
    void decrypt(KeyParameter aesKey);
}

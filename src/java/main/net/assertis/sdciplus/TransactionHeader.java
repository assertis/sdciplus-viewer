package net.assertis.sdciplus;

public abstract class TransactionHeader extends SDCIPlusRecord
{
    public String getTransactionHeaderNumber()
    {
        return getFields().get("Transaction Header Number").toString();
    }
}

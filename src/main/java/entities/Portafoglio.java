package entities;

import annotations.Column;
import annotations.Generated;
import annotations.Key;
import annotations.Table;
import lombok.Data;

@Data
@Table(tableName = "test_schema.portafoglio")
public class Portafoglio extends Entity {

    @Generated
    @Column(columnName = "wallet_id")
    protected Long walletId;

    @Key
    @Column(columnName = "user_id")
    protected Long userId;

    @Column(columnName = "amount")
    protected float amount;

}

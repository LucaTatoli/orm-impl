package entities;

import annotations.Column;
import annotations.Generated;
import annotations.Key;
import annotations.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table(tableName = "test_schema.spese")
public class Spese extends Entity {
    @Generated
    @Key
    @Column(columnName = "id")
    protected Long id;

    @Column(columnName = "importo")
    protected Float importo;

    @Column(columnName = "categoria")
    protected String categoria;

    @Column(columnName = "data_spesa")
    protected String dataSpesa;

    @Column(columnName = "descrizione")
    protected String descrizione;
}

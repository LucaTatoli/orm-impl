package entities;

import annotations.Column;
import annotations.Generated;
import annotations.Key;
import annotations.Table;
import lombok.Data;

@Data
@Table(tableName = "test_schema.users")
public class User extends Entity
{
    @Key
    @Generated
    @Column(columnName = "id")
    protected Long id ;
    @Column(columnName = "nome")
    protected String nome;
    @Column(columnName = "cognome")
    protected String cognome;
}

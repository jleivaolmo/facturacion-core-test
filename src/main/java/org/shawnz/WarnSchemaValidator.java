package org.shawnz;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.Sequence;
import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Selectable;
import org.hibernate.mapping.Table;
import org.hibernate.tool.schema.extract.spi.ColumnInformation;
import org.hibernate.tool.schema.extract.spi.SequenceInformation;
import org.hibernate.tool.schema.extract.spi.TableInformation;
import org.hibernate.tool.schema.internal.GroupedSchemaValidatorImpl;
import org.hibernate.tool.schema.internal.HibernateSchemaManagementTool;
import org.hibernate.tool.schema.spi.ExecutionOptions;
import org.hibernate.tool.schema.spi.SchemaFilter;
import org.hibernate.type.descriptor.JdbcTypeNameMapper;
import lombok.extern.slf4j.Slf4j;
import java.util.Iterator;
import java.util.Locale;

@Slf4j
public class WarnSchemaValidator extends GroupedSchemaValidatorImpl {

    public WarnSchemaValidator(HibernateSchemaManagementTool tool, SchemaFilter validateFilter) {
        super(tool, validateFilter);
    }

    // from https://github.com/hibernate/hibernate-orm/blob/2bcb1b0a6d8600ba3a60eae6460dcb52bf5628e7/hibernate-core/src/main/java/org/hibernate/tool/schema/internal/AbstractSchemaValidator.java#L113
    @Override
    protected void validateTable(
            Table table,
            TableInformation tableInformation,
            Metadata metadata,
            ExecutionOptions options,
            Dialect dialect) {
        if ( tableInformation == null ) {
            log.warn(
                    String.format(
                            "Schema-validation: missing table [%s]",
                            table.getQualifiedTableName().toString()
                    )
            );
            
            // don't validate columns of non-existent table
            return;
        }

        final Iterator selectableItr = table.getColumnIterator();
        while ( selectableItr.hasNext() ) {
            final Selectable selectable = (Selectable) selectableItr.next();
            if ( Column.class.isInstance( selectable ) ) {
                final Column column = (Column) selectable;
                final ColumnInformation existingColumn = tableInformation.getColumn( Identifier.toIdentifier( column.getQuotedName() ) );
                if ( existingColumn == null ) {
                    log.warn(String.format(
                                    "Schema-validation: missing column [%s] in table [%s]",
                                    column.getName(),
                                    table.getQualifiedTableName()
                            )
                    );
                    
                    // don't validate type of non-existent column
                    return;
                }
                validateColumnType( table, column, existingColumn, metadata, options, dialect );
            }
        }
    }

    @Override
    protected void validateColumnType(
            Table table,
            Column column,
            ColumnInformation columnInformation,
            Metadata metadata,
            ExecutionOptions options,
            Dialect dialect) {
        boolean typesMatch = column.getSqlTypeCode( metadata ) == columnInformation.getTypeCode()
                || column.getSqlType( dialect, metadata ).toLowerCase(Locale.ROOT).startsWith( columnInformation.getTypeName().toLowerCase(Locale.ROOT) );
        if ( !typesMatch ) {
            log.warn(
                    String.format(
                            "Schema-validation: wrong column type encountered in column [%s] in " +
                                    "table [%s]; found [%s (Types#%s)], but expecting [%s (Types#%s)]",
                            column.getName(),
                            table.getQualifiedTableName(),
                            columnInformation.getTypeName().toLowerCase(Locale.ROOT),
                            JdbcTypeNameMapper.getTypeName( columnInformation.getTypeCode() ),
                            column.getSqlType().toLowerCase(Locale.ROOT),
                            JdbcTypeNameMapper.getTypeName( column.getSqlTypeCode( metadata ) )
                    )
            );
        }

        // this is the old Hibernate check...
        //
        // but I think a better check involves checks against type code and then the type code family, not
        // just the type name.
        //
        // See org.hibernate.type.descriptor.sql.JdbcTypeFamilyInformation
        // todo : this ^^
    }

    @Override
    protected void validateSequence(Sequence sequence, SequenceInformation sequenceInformation) {
        if ( sequenceInformation == null ) {
            log.warn(
                    String.format( "Schema-validation: missing sequence [%s]", sequence.getName() )
            );
            
            // don't validate properties of non-existent sequence
            return;
        }

        if ( sequenceInformation.getIncrementSize() > 0
                && sequence.getIncrementSize() != sequenceInformation.getIncrementSize() ) {
            log.warn(
                    String.format(
                            "Schema-validation: sequence [%s] defined inconsistent increment-size; found [%s] but expecting [%s]",
                            sequence.getName(),
                            sequenceInformation.getIncrementSize(),
                            sequence.getIncrementSize()
                    )
            );
        }
    }
}
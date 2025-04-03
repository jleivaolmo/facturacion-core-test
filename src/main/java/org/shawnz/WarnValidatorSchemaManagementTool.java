package org.shawnz;

import org.hibernate.boot.registry.selector.spi.StrategySelector;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.tool.schema.internal.DefaultSchemaFilterProvider;
import org.hibernate.tool.schema.internal.HibernateSchemaManagementTool;
import org.hibernate.tool.schema.spi.SchemaFilterProvider;
import org.hibernate.tool.schema.spi.SchemaValidator;

import java.util.Map;

public class WarnValidatorSchemaManagementTool extends HibernateSchemaManagementTool {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5205670800558237115L;

	@Override
    public SchemaValidator getSchemaValidator(Map options) {
        return new WarnSchemaValidator(this, getSchemaFilterProvider( options ).getValidateFilter());
    }

    // from https://github.com/hibernate/hibernate-orm/blob/2bcb1b0a6d8600ba3a60eae6460dcb52bf5628e7/hibernate-core/src/main/java/org/hibernate/tool/schema/internal/HibernateSchemaManagementTool.java#L95
    private SchemaFilterProvider getSchemaFilterProvider(Map options) {
        final Object configuredOption = (options == null)
                ? null
                : options.get( AvailableSettings.HBM2DDL_FILTER_PROVIDER );
        return getServiceRegistry().getService( StrategySelector.class ).resolveDefaultableStrategy(
                SchemaFilterProvider.class,
                configuredOption,
                DefaultSchemaFilterProvider.INSTANCE
        );
    }
}
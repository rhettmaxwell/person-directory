package org.jasig.services.persondir.core;

import java.io.Serializable;
import java.util.List;

import org.jasig.services.persondir.AttributeQuery;
import org.jasig.services.persondir.PersonAttributes;
import org.jasig.services.persondir.core.config.CriteriaSearchableAttributeSourceConfig;
import org.jasig.services.persondir.core.config.PersonDirectoryConfig;
import org.jasig.services.persondir.criteria.Criteria;
import org.jasig.services.persondir.spi.CriteriaSearchableAttributeSource;
import org.jasig.services.persondir.spi.cache.CacheKeyGenerator;

class CriteriaSearchableAttributeQueryWorker 
        extends AbstractAttributeQueryWorker<Criteria, CriteriaSearchableAttributeSource, CriteriaSearchableAttributeSourceConfig> {
    
    public CriteriaSearchableAttributeQueryWorker(
            PersonDirectoryConfig personDirectoryConfig,
            CriteriaSearchableAttributeSourceConfig sourceConfig,
            AttributeQuery<Criteria> originalQuery) {
        
        super(personDirectoryConfig, sourceConfig, originalQuery);
    }

    @Override
    protected AttributeQueryCallable createQueryCallable(AttributeQuery<Criteria> filteredQuery) {
        return new CriteriaSearchableAttributeQueryCallable(filteredQuery);
    }

    @Override
    protected Serializable generateCacheKey(Criteria q, CacheKeyGenerator keyGenerator) {
        return keyGenerator.generateCacheKey(q);
    }

    @Override
    protected Criteria filterQuery(Criteria q) {
        // TODO how the heck do we filter criteria queries
        return null;
    }

    private final class CriteriaSearchableAttributeQueryCallable extends AttributeQueryCallable {
        private final AttributeQuery<Criteria> attributeQuery;
        
        public CriteriaSearchableAttributeQueryCallable(AttributeQuery<Criteria> attributeQuery) {
            this.attributeQuery = attributeQuery;
        }

        @Override
        protected List<PersonAttributes> doQuery() {
            final CriteriaSearchableAttributeSource attributeSource = sourceConfig.getAttributeSource();
            return attributeSource.searchForAttributes(this.attributeQuery);
        }
    }
}

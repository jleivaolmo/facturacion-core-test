package com.echevarne.sap.cloud.facturacion.odata.processor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAPage;

public class JPAPageCustom extends JPAPage {

    protected JPAPageCustom(int startPage, int nextPage, List<Object> pagedEntities, int pageSize) {
        super(startPage, nextPage, pagedEntities, pageSize);
    }

    public static class JPAPageBuilderCustom {

        private int pageSize;
        private int startPage;
        private int nextPage;
        private int top = -1;
        private int skip;
        private int skipToken;
        private Query query;
        private List<Object> entities;
        private List<Object> pagedEntities;
    
        private static class TopSkip {
          private Integer top;
          private Integer skip;
        }
    
        public JPAPageBuilderCustom() {}
    
        public JPAPageBuilderCustom pageSize(final int pageSize) {
          this.pageSize = pageSize;
          return this;
        }
    
        public JPAPageBuilderCustom query(final Query query) {
          this.query = query;
          return this;
        }
    
        public JPAPageCustom build() {
          if (entities != null) {
            return buildFromEntities();
          } else {
            return buildFromQuery();
          }
        }
    
        private JPAPageCustom buildFromEntities() {
          TopSkip topSkip = formulateTopSkip();
          pagedEntities = new ArrayList<Object>();
          Integer top = topSkip.top;
          Integer skip = topSkip.skip;
          if (skip == null || topSkip.skip <= 0) {
            skip = 1;
          }
          if(top == null || topSkip.top <= 0){
            top = 0;
          }
          for (int i = skip - 1, j = 0; (j < top && i < entities.size()); j++) {
            pagedEntities.add(entities.get(i++));
          }
          formulateNextPage();
          return new JPAPageCustom(startPage, nextPage, pagedEntities, pageSize);
        }
    
        @SuppressWarnings("unchecked")
        private JPAPageCustom buildFromQuery() {
          TopSkip topSkip = formulateTopSkip();
          if(topSkip.skip != null){
            query.setFirstResult(topSkip.skip);
          }
          if(topSkip.top != null){
            query.setMaxResults(topSkip.top);
          }
          pagedEntities = query.getResultList();
          formulateNextPage();
          return new JPAPageCustom(startPage, nextPage, pagedEntities, pageSize);
        }
    
        private TopSkip formulateTopSkip() {
          TopSkip topSkip = new TopSkip();
          if (pageSize <= 0) {
            if (skip > 0) {
              topSkip.skip = skip;
            }
            if (top > 0) {
              topSkip.top = top;
            }
          } else {
            if (top > 0) {
                topSkip.top = top;
            } else {
                topSkip.top = pageSize;
            }
    
            startPage = skipToken;
            if (skip > 0) {
            topSkip.skip = startPage + skip;
            } else {
            topSkip.skip = startPage;
            }
          }
          return topSkip;
        }
    
        private void formulateNextPage() {
          if (pagedEntities.isEmpty()) {
            nextPage = 0;
          } else if (pagedEntities.size() < pageSize) {
            nextPage = 0;
          } else if (pagedEntities.size() >= pageSize) {
            nextPage = startPage + pagedEntities.size();
          } else {
            nextPage = startPage + pageSize;
          }
        }
    
        public JPAPageBuilderCustom skip(final int skip) {
          this.skip = skip;
          if (skip < 0) {
            this.skip = 0;
          } else {
            this.skip = skip;
          }
          return this;
        }
    
        public JPAPageBuilderCustom skipToken(final String skipToken) throws NumberFormatException {
          if (skipToken == null) {
            this.skipToken = 0;
          } else {
            this.skipToken = new Integer(skipToken).intValue();
            if (this.skipToken < 0) {
              this.skipToken = 0;
            }
          }
    
          return this;
        }
    
        public JPAPageBuilderCustom top(final int top) {
          if (top < 0) {
            this.top = 0;
          } else {
            this.top = top;
          }
          return this;
        }
    
        public JPAPageBuilderCustom entities(final List<Object> result) {
          entities = result;
          return this;
        }
    }
    
}

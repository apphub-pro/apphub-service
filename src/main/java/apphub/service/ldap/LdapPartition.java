/*
 * Copyright (C) 2014 Dmitry Kotlyarov, Dmitriy Rogozhin.
 * All Rights Reserved.
 *
 * CONFIDENTIAL
 *
 * All information contained herein is, and remains the property
 * of copyright holders. The intellectual and technical concepts
 * contained herein are proprietary to copyright holders and may
 * be covered by U.S. and Foreign Patents, patents in process,
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this
 * material is strictly forbidden unless prior written permission
 * is obtained from copyright holders.
 */

package apphub.service.ldap;

import apphub.staff.database.Database;
import org.apache.directory.api.ldap.model.cursor.ListCursor;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.ldap.model.schema.SchemaManager;
import org.apache.directory.server.core.api.filtering.EntryFilteringCursor;
import org.apache.directory.server.core.api.filtering.EntryFilteringCursorImpl;
import org.apache.directory.server.core.api.interceptor.context.AddOperationContext;
import org.apache.directory.server.core.api.interceptor.context.DeleteOperationContext;
import org.apache.directory.server.core.api.interceptor.context.HasEntryOperationContext;
import org.apache.directory.server.core.api.interceptor.context.LookupOperationContext;
import org.apache.directory.server.core.api.interceptor.context.ModifyOperationContext;
import org.apache.directory.server.core.api.interceptor.context.MoveAndRenameOperationContext;
import org.apache.directory.server.core.api.interceptor.context.MoveOperationContext;
import org.apache.directory.server.core.api.interceptor.context.RenameOperationContext;
import org.apache.directory.server.core.api.interceptor.context.SearchOperationContext;
import org.apache.directory.server.core.api.interceptor.context.UnbindOperationContext;
import org.apache.directory.server.core.api.partition.AbstractPartition;

import javax.naming.InvalidNameException;
import java.util.Collections;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class LdapPartition extends AbstractPartition {
    protected final Database database;

    public LdapPartition(Database database, SchemaManager schemaManager, String id, Dn suffixDn) {
        this.database = database;

        try {
            this.setSchemaManager(schemaManager);
            this.setId(id);
            this.setSuffixDn(suffixDn);
        } catch (LdapInvalidDnException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDestroy() throws Exception {
    }

    @Override
    protected void doInit() throws InvalidNameException, Exception {
    }

    @Override
    public void sync() throws Exception {
    }

    @Override
    public Entry delete(DeleteOperationContext deleteContext) throws LdapException {
        return null;
    }

    @Override
    public void add(AddOperationContext addContext) throws LdapException {
    }

    @Override
    public void modify(ModifyOperationContext modifyContext) throws LdapException {
    }

    @Override
    public EntryFilteringCursor search(SearchOperationContext searchContext) throws LdapException {
        Entry entry = new DefaultEntry(schemaManager, suffixDn);
        return new EntryFilteringCursorImpl(new ListCursor<>(Collections.singletonList(entry)), searchContext, schemaManager);
    }

    @Override
    public Entry lookup(LookupOperationContext lookupContext) throws LdapException {
        return null;
    }

    @Override
    public boolean hasEntry(HasEntryOperationContext hasEntryContext) throws LdapException {
        return false;
    }

    @Override
    public void rename(RenameOperationContext renameContext) throws LdapException {
    }

    @Override
    public void move(MoveOperationContext moveContext) throws LdapException {
    }

    @Override
    public void moveAndRename(MoveAndRenameOperationContext moveAndRenameContext) throws LdapException {
    }

    @Override
    public void unbind(UnbindOperationContext unbindContext) throws LdapException {
    }

    @Override
    public void saveContextCsn() throws Exception {
    }
}

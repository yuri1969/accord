/**
 * Neociclo Accord, Open Source B2Bi Middleware
 * Copyright (C) 2005-2009 Neociclo, http://www.neociclo.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * $Id$
 */
package org.neociclo.odetteftp.test;

import java.io.File;

import org.neociclo.odetteftp.TransferMode;
import org.neociclo.odetteftp.support.Oftplet;
import org.neociclo.odetteftp.support.OftpletFactory;

/**
 * @author Rafael Marins
 * @version $Rev$ $Date$
 */
public class BasicOftpletFactory implements OftpletFactory {

    private File testDir;
    private MailboxManager mailboxManager;
    private TransferMode transferMode;

    public BasicOftpletFactory(File testDir, MailboxManager mailboxManager, TransferMode mode) {
        super();
        this.testDir = testDir;
        this.mailboxManager = mailboxManager;
        this.transferMode = mode;
    }

    public Oftplet createProvider() {
        return new ServerOftplet(testDir, mailboxManager, transferMode);
    }

}
/*
 * Copyright (C) 2016 Saxon State and University Library Dresden (SLUB)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.slub.nbnurn;

import de.slub.urn.URN;
import de.slub.urn.URNSyntaxException;

final public class NBNURN {

    public static final String NBN_NAMESPACE_IDENTIFIER = "nbn";
    private final String countryCode;
    private final String nationalBookNumber;
    private final String subnamespacePrefix;
    private final URN urn;

    private NBNURN(String countryCode, String subnamespacePrefix, String nationalBookNumber) throws URNSyntaxException {
        this.countryCode = countryCode;
        this.subnamespacePrefix = subnamespacePrefix;
        this.nationalBookNumber = nationalBookNumber;
        this.urn = URN.newInstance(NBN_NAMESPACE_IDENTIFIER,
                String.format("%s:%s-%s",
                        this.countryCode,
                        this.subnamespacePrefix,
                        this.nationalBookNumber));
    }

    public static NBNURN newInstance(String countryCode, String subnamespacePrefix, String nationalBookNumber)
            throws URNSyntaxException {
        return new NBNURN(countryCode, subnamespacePrefix, nationalBookNumber);
    }

    public URN toURN() {
        return urn;
    }

}

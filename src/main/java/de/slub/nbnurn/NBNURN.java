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

import java.util.regex.Pattern;

import static java.lang.String.format;

final public class NBNURN {

    private static final Pattern allowedNamespaceSpecificString = Pattern.compile("^([\\w]+)(:[\\w]+)*-[\\w]+$");
    private static final String NBN_NAMESPACE_IDENTIFIER = "nbn";

    private final String countryCode;
    private final String nationalBookNumber;
    private final String subnamespacePrefix;
    private final URN urn;

    private NBNURN(String countryCode, String subnamespacePrefix, String nationalBookNumber)
            throws URNSyntaxException {
        this.countryCode = countryCode;
        this.subnamespacePrefix = subnamespacePrefix;
        this.nationalBookNumber = nationalBookNumber;

        String namespaceSpecificString;
        if (subnamespacePrefix == null || subnamespacePrefix.isEmpty()) {
            namespaceSpecificString = format("%s-%s",
                    this.countryCode,
                    this.nationalBookNumber);
        } else {
            namespaceSpecificString = format("%s:%s-%s",
                    this.countryCode,
                    this.subnamespacePrefix,
                    this.nationalBookNumber);
        }
        this.urn = URN.newInstance(NBN_NAMESPACE_IDENTIFIER, namespaceSpecificString);
    }

    public static NBNURN newInstance(String countryCode, String subnamespacePrefix, String nationalBookNumber)
            throws URNSyntaxException {
        return new NBNURN(countryCode, subnamespacePrefix, nationalBookNumber);
    }

    public static NBNURN fromURN(URN urn) throws URNSyntaxException {
        assertValidNamespaceIdentifier(urn.getNamespaceIdentifier());
        assertValidNamespaceSpecificString(urn.getNamespaceSpecificString());

        String[] parts = urn.getNamespaceSpecificString().split("-");

        int colonIndex = parts[0].indexOf(':');
        String cc = (colonIndex > 0) ? parts[0].substring(0, colonIndex) : parts[0];
        String sp = (colonIndex > 0) ? parts[0].substring(colonIndex + 1) : null;
        String nbn = parts[1];
        return new NBNURN(cc, sp, nbn);
    }

    private static void assertValidNamespaceSpecificString(String namespaceSpecificString) throws URNSyntaxException {
        if (!allowedNamespaceSpecificString.matcher(namespaceSpecificString).matches()) {
            throw new URNSyntaxException(format(
                    "Not a valid NBN namespace specific string: '%s'", namespaceSpecificString));
        }
    }

    private static void assertValidNamespaceIdentifier(String namespaceIdentifier) throws URNSyntaxException {
        if (!NBN_NAMESPACE_IDENTIFIER.equalsIgnoreCase(namespaceIdentifier)) {
            throw new URNSyntaxException(format(
                    "Only '%s' is allowed as namespace identifier for NBN-URNs", NBN_NAMESPACE_IDENTIFIER));
        }
    }

    public URN toURN() {
        return urn;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getSubnamespacePrefix() {
        return subnamespacePrefix;
    }

    public String getNationalBookNumber() {
        return nationalBookNumber;
    }

    @Override
    public String toString() {
        return urn.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof NBNURN) && urn.equals(((NBNURN) obj).urn);
    }

    @Override
    public int hashCode() {
        return urn.hashCode();
    }
}

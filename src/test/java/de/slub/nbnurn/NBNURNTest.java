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
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NBNURNTest {

    @Test
    public void Returns_URN() throws Exception {
        NBNURN subject = NBNURN.newInstance("de", "bsz", "47110815");
        assertEquals("urn:nbn:de:bsz-47110815", subject.toURN().toString());
    }

    @Test
    public void Subnamespace_is_omitted_in_URN_representation_when_null() throws Exception {
        NBNURN subject = NBNURN.newInstance("hu", null, "3006");
        assertEquals("urn:nbn:hu-3006", subject.toURN().toString());
    }

    @Test
    public void Subnamespace_is_omitted_in_URN_representation_when_empty() throws Exception {
        NBNURN subject = NBNURN.newInstance("hu", "", "3006");
        assertEquals("urn:nbn:hu-3006", subject.toURN().toString());
    }

    @Test
    public void Creating_via_proper_NBN_URN_returns_valid_NBNURN() throws Exception {
        String countryCode = "de";
        String subnamespacePrefix = "bsz";
        String nationalBookNumber = "47110815";
        NBNURN subject = NBNURN.fromURN(URN.fromString(
                String.format("urn:nbn:%s:%s-%s", countryCode, subnamespacePrefix, nationalBookNumber)));
        assertEquals(countryCode, subject.getCountryCode());
        assertEquals(subnamespacePrefix, subject.getSubnamespacePrefix());
        assertEquals(nationalBookNumber, subject.getNationalBookNumber());
    }

    @Test
    public void Uppercase_NBN_is_a_valid_namespace_identifier() throws Exception {
        NBNURN subject = NBNURN.fromURN(URN.fromString("URN:NBN:fi-fe201003181510"));
        URN urn = subject.toURN();
        assertEquals("nbn", urn.getNamespaceIdentifier());
    }

    @Test
    public void Creating_via_NBN_URN_with_subnamespace_returns_valid_NBNURN() throws Exception {
        String countryCode = "se";
        String subnamespacePrefix = "uu:diva";
        String nationalBookNumber = "3475";
        NBNURN subject = NBNURN.fromURN(URN.fromString(
                String.format("urn:nbn:%s:%s-%s", countryCode, subnamespacePrefix, nationalBookNumber)));
        assertEquals(countryCode, subject.getCountryCode());
        assertEquals(subnamespacePrefix, subject.getSubnamespacePrefix());
        assertEquals(nationalBookNumber, subject.getNationalBookNumber());
    }

    @Test(expected = URNSyntaxException.class)
    public void Creating_without_NBN_as_NamespaceIdentifier_throws_exception() throws Exception {
        NBNURN.fromURN(URN.fromString("urn:non-nbn:foo"));
    }

    @Test(expected = URNSyntaxException.class)
    public void Creating_with_invalid_NamespaceSpecificString_throws_exception() throws Exception {
        NBNURN.fromURN(URN.fromString("urn:nbn:invalidnss"));
    }

    @Test
    public void Creating_from_String_is_equal_to_creating_from_URN() throws Exception {
        String nbnurnString = "URN:NBN:fi-fe201003181510";
        URN urn = URN.fromString(nbnurnString);
        NBNURN subject = NBNURN.fromString(nbnurnString);
        assertEquals(urn, subject.toURN());
    }

    @Test
    public void Calling_toString_returns_NBN_URN_literal() throws Exception {
        String countryCode = "de";
        String subnamespacePrefix = "bsz";
        String nationalBookNumber = "47110815";

        NBNURN subject = NBNURN.newInstance(countryCode, subnamespacePrefix, nationalBookNumber);
        String literal = subject.toString();
        assertEquals(
                String.format("urn:nbn:%s:%s-%s", countryCode, subnamespacePrefix, nationalBookNumber),
                literal);
    }

    @Test
    public void Equally_initialized_objects_are_equal() throws Exception {
        String countryCode = "de";
        String subnamespacePrefix = "bsz";
        String nationalBookNumber = "47110815";
        NBNURN subject1 = NBNURN.newInstance(countryCode, subnamespacePrefix, nationalBookNumber);
        NBNURN subject2 = NBNURN.newInstance(countryCode, subnamespacePrefix, nationalBookNumber);

        assertEquals(subject1, subject2);
    }

    @Test
    public void Equally_initialized_objects_have_equal_hash_codes() throws Exception {
        String countryCode = "de";
        String subnamespacePrefix = "bsz";
        String nationalBookNumber = "47110815";
        NBNURN subject1 = NBNURN.newInstance(countryCode, subnamespacePrefix, nationalBookNumber);
        NBNURN subject2 = NBNURN.newInstance(countryCode, subnamespacePrefix, nationalBookNumber);

        assertEquals(subject1.hashCode(), subject2.hashCode());
    }

    @Test
    public void Cloning_results_in_equal_object() throws Exception {
        String countryCode = "de";
        String subnamespacePrefix = "bsz";
        String nationalBookNumber = "47110815";
        NBNURN subject = NBNURN.newInstance(countryCode, subnamespacePrefix, nationalBookNumber);
        Object clone = subject.clone();

        assertEquals(subject, clone);
    }
}

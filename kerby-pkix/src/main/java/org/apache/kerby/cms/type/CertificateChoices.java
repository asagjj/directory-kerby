/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.kerby.cms.type;

import org.apache.kerby.asn1.Asn1FieldInfo;
import org.apache.kerby.asn1.EnumType;
import org.apache.kerby.asn1.ImplicitField;
import org.apache.kerby.asn1.type.Asn1Choice;
import org.apache.kerby.x509.type.Certificate;

/**
 * CertificateChoices ::= CHOICE {
 *   certificate Certificate,
 *   extendedCertificate [0] IMPLICIT ExtendedCertificate,  -- Obsolete
 *   v1AttrCert [1] IMPLICIT AttributeCertificateV1,        -- Obsolete
 *   v2AttrCert [2] IMPLICIT AttributeCertificateV2,
 *   other [3] IMPLICIT OtherCertificateFormat }
 */
public class CertificateChoices extends Asn1Choice {
    protected enum CertificateChoicesField implements EnumType {
        CERTIFICATE,
        EXTENDED_CERTIFICATE,
        V1_ATTR_CERT,
        V2_ATTR_CERT,
        OTHER;

        @Override
        public int getValue() {
            return ordinal();
        }

        @Override
        public String getName() {
            return name();
        }
    }

    static Asn1FieldInfo[] fieldInfos = new Asn1FieldInfo[] {
            new Asn1FieldInfo(CertificateChoicesField.CERTIFICATE, Certificate.class),
            new ImplicitField(CertificateChoicesField.EXTENDED_CERTIFICATE, 0, ExtendedCertificate.class),
            new ImplicitField(CertificateChoicesField.V1_ATTR_CERT, 1, AttributeCertificateV1.class),
            new ImplicitField(CertificateChoicesField.V2_ATTR_CERT, 2, AttributeCertificateV2.class),
            new ImplicitField(CertificateChoicesField.OTHER, 3, OtherCertificateFormat.class),
    };

    public CertificateChoices() {
        super(fieldInfos);
    }

    public Certificate getCertificate() {
        return getChoiceValueAs(CertificateChoicesField.CERTIFICATE, Certificate.class);
    }

    public void setCertificate(Certificate certificate) {
        setChoiceValue(CertificateChoicesField.CERTIFICATE, certificate);
    }

    public ExtendedCertificate getExtendedCertificate() {
        return getChoiceValueAs(CertificateChoicesField.EXTENDED_CERTIFICATE, ExtendedCertificate.class);
    }

    public void setExtendedCertificate(ExtendedCertificate extendedCertificate) {
        setChoiceValue(CertificateChoicesField.EXTENDED_CERTIFICATE, extendedCertificate);
    }

    public AttributeCertificateV1 getV1AttrCert() {
        return getChoiceValueAs(CertificateChoicesField.V1_ATTR_CERT, AttributeCertificateV1.class);
    }

    public void setV1AttrCert(AttributeCertificateV1 v1AttrCert) {
        setChoiceValue(CertificateChoicesField.V1_ATTR_CERT, v1AttrCert);
    }

    public AttributeCertificateV2 getV2AttrCert() {
        return getChoiceValueAs(CertificateChoicesField.V2_ATTR_CERT, AttributeCertificateV2.class);
    }

    public void setV2AttrCert(AttributeCertificateV2 v2AttrCert) {
        setChoiceValue(CertificateChoicesField.V2_ATTR_CERT, v2AttrCert);
    }

    public OtherCertificateFormat getOther() {
        return getChoiceValueAs(CertificateChoicesField.OTHER, OtherCertificateFormat.class);
    }

    public void setOther(OtherCertificateFormat other) {
        setChoiceValue(CertificateChoicesField.OTHER, other);
    }
}
